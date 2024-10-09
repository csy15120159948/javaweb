package com.gzu.logindemo.code;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class LoginFilter implements Filter {

    private static final List<String> STATIC_EXTENSIONS = Arrays.asList(
            ".css", ".js", ".jpg", ".png", ".gif", ".ico", ".html", ".jsp"
    );

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        boolean isStaticResource = STATIC_EXTENSIONS.stream().anyMatch(ext ->{
            System.out.println("ext: " + ext);
            return request.getRequestURI().contains(ext);
        });
        if (isStaticResource) {
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            String username = (String) request.getSession().getAttribute("user");
            if (" ".equals(username) || username == null) {
                response.sendRedirect(request.getContextPath() + "/code/login.html");
            }else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}