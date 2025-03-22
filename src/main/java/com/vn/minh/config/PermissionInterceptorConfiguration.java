package com.vn.minh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class PermissionInterceptorConfiguration  implements WebMvcConfigurer{
     @Bean
    PermissionInterceptor getPermissionInterceptor() {
        return new PermissionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        
        String[] whiteList = { "/", "/api/v1/auth/**", "/storage/**", "/api/v1/companies", "/api/v1/jobs",
                "/api/v1/skills" };
        registry.addInterceptor(getPermissionInterceptor()).excludePathPatterns(whiteList);
    }
}
