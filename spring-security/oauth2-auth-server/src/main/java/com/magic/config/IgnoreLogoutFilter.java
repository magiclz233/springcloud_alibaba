package com.magic.config;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * LogoutFilter过滤器会对/logout路径进行过滤
 * 这里直接转发到remove请求下
 * @author magic_lz
 * @version 1.0
 * @classname IgnoreLogoutFilter
 * @date 2020/10/16 : 16:55
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class IgnoreLogoutFilter implements Filter {
    private RequestMatcher requestMatcher;

    public IgnoreLogoutFilter() {
        this.setFilterProcessesUrl("/logout");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (requiresLogout(request, response)) {
            RequestDispatcher remove = request.getRequestDispatcher("remove");
            remove.forward(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }

    protected boolean requiresLogout(HttpServletRequest request, HttpServletResponse response) {
        return this.requestMatcher.matches(request);
    }


    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.requestMatcher = new AntPathRequestMatcher(filterProcessesUrl);
    }
}