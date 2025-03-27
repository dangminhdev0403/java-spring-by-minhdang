
package com.vn.minh.config;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vn.minh.domain.model.Role;
import com.vn.minh.domain.model.User;
import com.vn.minh.domain.response.ResponseData;
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
                Role role = user.getRole();
                if (role != null && role.getName().equals("ROLE_USER") && requestURI.contains("admin")) {
                   
                        response.setContentType("application/json;charset=UTF-8");

                        ResponseData<Object> res = new ResponseData<>();
                        res.setStatus(HttpStatus.FORBIDDEN.value());
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        res.setError("Không có quyền");
                        res.setMessage("Phần mềm khóa");
                        objectMapper.writeValue(response.getWriter(), res);
                        return false;

                    

                }

            }
        }

        return true;
    }

}