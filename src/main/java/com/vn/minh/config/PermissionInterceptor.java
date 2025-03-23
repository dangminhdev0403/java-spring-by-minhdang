
package com.vn.minh.config;

import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vn.minh.domain.Role;
import com.vn.minh.domain.User;
import com.vn.minh.domain.response.ResponseData;
import com.vn.minh.service.UserService;
import com.vn.minh.service.util.SecurityUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
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
        System.out.println(">>> RUN preHandle");
        System.out.println(">>> path= " + path);
        System.out.println(">>> httpMethod= " + httpMethod);
        System.out.println(">>> requestURI= " + requestURI);

        String email = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (email != null && !email.isEmpty()) {
            User user = this.userService.findByUsername(email);
            if (user != null) {
                Role role = user.getRole();
                if (role != null && role.getName().equals("ROLE_USER")) {
                    if (requestURI.contains("admin")) {
                        response.setContentType("application/json;charset=UTF-8");

                        ResponseData<Object> res = new ResponseData<Object>();
                        res.setStatus(HttpStatus.FORBIDDEN.value());
                        response.setStatus(HttpStatus.FORBIDDEN.value());
                        res.setError("Không có quyền");
                        res.setMessage("Phần mềm khóa");
                        objectMapper.writeValue(response.getWriter(), res);
                        return false;

                    }

                }

            }
        }

        return true;
    }

}