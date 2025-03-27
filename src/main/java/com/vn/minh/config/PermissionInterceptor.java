
package com.vn.minh.config;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vn.minh.domain.model.User;
import com.vn.minh.service.UserService;
import com.vn.minh.service.util.SecurityUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@SuppressWarnings("null")
@Slf4j
public class PermissionInterceptor implements HandlerInterceptor {
    private final UserService userService;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response, Object handler)
            throws Exception {
        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        log.info(">>> RUN preHandle");
        log.info(">>> path= " + path);
        log.info(">>> httpMethod= " + httpMethod);
        log.info(">>> requestURI= " + requestURI);

        String email = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (email != null && !email.isEmpty()) {
            User user = this.userService.findByUsername(email);
            if (user != null) {

            }
        }

        return true;
    }

}