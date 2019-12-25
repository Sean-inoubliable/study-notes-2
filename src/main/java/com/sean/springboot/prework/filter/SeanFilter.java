package com.sean.springboot.prework.filter;

import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description: 过滤器
 * @Author: Sean, CSII
 * @Date: 2019-12-10 17:46
 */
@Service
@WebFilter(filterName="SeanFilter", urlPatterns = "/*")
public class SeanFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {

        System.out.println(" ------- seanFilter doFilter executed ... ... ");

        HttpServletRequest request = (HttpServletRequest)req;

        String requestURI = request.getRequestURI();
        StringBuffer requestURL = request.getRequestURL();

        System.out.println("URI: " + requestURI);
        System.out.println("URL: " + requestURL);

        filterChain.doFilter(req, resp);

        System.out.println(" ------- seanFilter doFilter after ... ... ");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        /* 可以加载 Filter启动之前需要的资源 */

        System.out.println(" ------- seanFilter init ... ... ");

        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {

    }
}
