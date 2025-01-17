package com.edu.shopservice.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class CacheControlFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // Добавляем заголовок Cache-Control
        httpResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setHeader("Expires", "0");

        chain.doFilter(request, response); // Продолжаем выполнение запроса
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Инициализация фильтра, если необходимо
    }

    @Override
    public void destroy() {
        // Освобождение ресурсов, если необходимо
    }
}
