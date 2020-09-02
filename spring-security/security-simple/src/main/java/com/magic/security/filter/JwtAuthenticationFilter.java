package com.magic.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.magic.security.constants.SecurityConstants;
import com.magic.security.dto.LoginRequest;
import com.magic.security.entity.JwtUser;
import com.magic.security.util.JwtUtil;
import com.magic.system.exception.LoginFailedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录验证过滤器  用户名密码错误的话会创建一个jwt token 并在response Header中返回
 *
 * @author magic_lz
 * @version 1.0
 * @date 2020/9/2 21:56
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /*
        自定义认证过滤器, 继承UsernamePasswordAuthenticationFilter
        重写 attemptAuthentication 验证用户身份
        successfulAuthentication 认证成功 生成token并返回
        unsuccessfulAuthentication 认证失败  相应处理
     */


    private final ThreadLocal<Boolean> rememberMe = new ThreadLocal<>();
    private final AuthenticationManager authenticationManager;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        // 设置URL, 确定是否需要身份验证
        super.setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 获取登录信息
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            rememberMe.set(loginRequest.getRememberMe());
            // 这部分的代码和attemptAuthentication源码相同,只不过源码是把用户名
            // 和密码写死的,这块修改一下
            Object principal;
            Object credentials;
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException | AuthenticationException e) {
            if (e instanceof AuthenticationException) {
                throw new LoginFailedException("登录失败! 请检查用户名和密码.");
            }
            throw new LoginFailedException(e.getMessage());
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        List<String> authorities = jwtUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        // 创建token
        String token = JwtUtil.creatToken(jwtUser.getUsername(), authorities, rememberMe.get());
        // Response Header中返回token
        response.setHeader(SecurityConstants.TOKEN_HEADER, token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
