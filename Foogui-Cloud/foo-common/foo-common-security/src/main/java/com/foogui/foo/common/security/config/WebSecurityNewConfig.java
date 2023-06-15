package com.foogui.foo.common.security.config;

import com.foogui.foo.common.core.utils.JwtUtil;
import com.foogui.foo.common.redis.service.RedisObjectUtil;
import com.foogui.foo.common.security.filter.JwtAuthenticationFilter;
import com.foogui.foo.common.security.filter.LoginEnterFilter;
import com.foogui.foo.common.security.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
// 开启方法的动态权限控制，方法上加@PreAuthorize("hasAuthority('pms:product:create')")实现控制
public class WebSecurityNewConfig {
    @Autowired
    private RedisObjectUtil redisObjectUtil;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private SuccessHandler successHandler;


    /**
     * 定义一个加密器
     * 把BCryptPasswordEncoder对象注入Spring容器中，SpringSecurity就会使用该PasswordEncoder来进行密码校验
     *
     * @return {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 过滤器链
     *
     * @param httpSecurity http安全性
     * @return {@link SecurityFilterChain}
     * @throws Exception 异常
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // HttpSecurity的配置demo
        httpSecurity

                .cors().and()
                .csrf().disable()       // 关闭csrf

                .exceptionHandling().authenticationEntryPoint(unAuthorizedEntryPoint()) // 认证失败异常处理
                .accessDeniedHandler(unAccessDeniedHandler()) // 鉴权失败异常处理
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()       // 禁用session
                .authenticationProvider(authenticationProvider)     //  将自定义的AuthenticationProvider加入环境
                .userDetailsService(userDetailsService)     //  将自定义的UserDetailsService加入环境
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)     // 新增自定义过滤器
                .addFilterAt(loginEnterFilter(), UsernamePasswordAuthenticationFilter.class)

                .authorizeHttpRequests((authorize) -> {
                    authorize.antMatchers("/auth/login").permitAll()              // 对于登录接口 允许匿名访问
                            .antMatchers("/sys-user/searchById/*").hasRole("ADMIN") // 这里不能加ROLE_，否则报错。最终实际上是ROLE_admin
                            .antMatchers("/level2/**").hasRole("normal");
                });
                // .logout().logoutUrl("/auth/logout").addLogoutHandler(tokenLogoutHandler());


        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public LoginEnterFilter loginEnterFilter() {
        return new LoginEnterFilter(authenticationManager(), authenticationProvider, redisObjectUtil, jwtUtil);
    }

    @Bean
    public TokenLogoutHandler tokenLogoutHandler() {
        return new TokenLogoutHandler(redisObjectUtil, jwtUtil);
    }
    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public UnAuthorizedEntryPoint unAuthorizedEntryPoint() {
        return new UnAuthorizedEntryPoint();
    }

    @Bean
    public UnAccessDeniedHandler unAccessDeniedHandler() {
        return new UnAccessDeniedHandler();
    }


}
