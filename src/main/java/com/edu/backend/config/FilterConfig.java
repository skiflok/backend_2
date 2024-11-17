package com.edu.backend.config;

import com.edu.backend.filter.CacheControlFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CacheControlFilter> cacheControlFilter() {
        FilterRegistrationBean<CacheControlFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CacheControlFilter());
        registrationBean.addUrlPatterns("/*"); // Применить ко всем URL
        return registrationBean;
    }
}