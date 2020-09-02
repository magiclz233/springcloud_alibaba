package com.magic.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * 过滤器处理所有请求, 并检查是否存在带有正确令牌的Authorization标志
 * @author magic_lz
 * @version 1.0
 * @date 2020/9/2 22:31
 */

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {


    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

}
