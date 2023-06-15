

package com.foogui.foo.common.security.utils;

import com.foogui.foo.common.core.constant.SecurityConstant;
import com.foogui.foo.common.security.exception.SecurityAuthException;
import com.foogui.foo.common.core.utils.StringUtils;
import com.foogui.foo.common.security.domain.LoginUserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class SecurityUtils {

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static void setAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 获取用户
     */
    public static LoginUserDetail getCurrentUser() {
        return getUserByAuthentication(getAuthentication());
    }

    /**
     * 获取用户
     */
    public static LoginUserDetail getUserByAuthentication(Authentication authentication) {
        if (authentication == null) {
            throw new SecurityAuthException("Authentication为空");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof LoginUserDetail) {
            return (LoginUserDetail) principal;
        }
        return null;
    }


    /**
     * 返回去掉角色信息，不带 ROLE_ 前缀
     *
     * @return {@link Set}<{@link String}>
     */
    public static Set<String> getRoles() {

        Collection<? extends GrantedAuthority> authorities = getAuthentication().getAuthorities();

        Set<String> set = new HashSet<String>();
        authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .distinct()
                .filter(ele -> StringUtils.startsWith(ele, SecurityConstant.ROLE_PREFIX))
                .forEach(ele -> {
                    String noPrefixAuthority = StringUtils.remove(ele, SecurityConstant.ROLE_PREFIX);
                    set.add(noPrefixAuthority);
                });
        return set;
    }

}
