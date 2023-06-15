package com.foogui.foo.common.web.feign;

import com.foogui.foo.common.web.foo.common.core.constant.HttpConstant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 微服务调用，采用feign拦截器实现请求头携带JWT
 *
 * @author Foogui
 * @date 2023/05/25
 */
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes!=null){
            // 获得原请求,复制原请求所有头信息到新请求(这里包含了jwt,这样安全模块的jwt过滤器就可以获取用户信息放在SecurityContext中)
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames!=null){
                while (headerNames.hasMoreElements()){
                    String headerName = headerNames.nextElement();
                    String headerValue = request.getHeader(headerName);
                    template.header(headerName, headerValue);
                }
            }
        }
        // feign请求添加来源,避免GatewayFilter将请求拦截
        template.header(HttpConstant.FROM_WHERE, HttpConstant.INNER);
    }
}
