package com.tool.springBoot.config;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

public class BeforeLoginFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("This is a filter before UsernamePasswordAuthenticationFilter.");
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        request.setAttribute("username", "user");
        request.setAttribute("password", "password");
        request.setAttribute("submit", "Login");
        // 继续调用 Filter 链
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

