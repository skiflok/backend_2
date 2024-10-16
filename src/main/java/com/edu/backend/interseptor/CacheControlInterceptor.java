package com.edu.backend.interseptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

//todo dont use. now use filter
//@Component
//public class CacheControlInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("Expires", "0");
//        return true;
//    }
//}
