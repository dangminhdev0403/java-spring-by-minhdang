package com.vn.minh.config;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vn.minh.domain.model.Permission;
import com.vn.minh.domain.model.Role;
import com.vn.minh.domain.model.User;
import com.vn.minh.domain.response.ResponseData;
import com.vn.minh.repository.PermissionRepository;
import com.vn.minh.service.UserService;
import com.vn.minh.service.util.SecurityUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class PermissionInterceptor implements HandlerInterceptor {
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final PermissionRepository permissionRepository;

    @Override
    @Transactional
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        log.info(">>> RUN preHandle");
        log.info(">>> path= " + path);
        log.info(">>> httpMethod= " + httpMethod);
        log.info(">>> requestURI= " + requestURI);

        String email = SecurityUtils.getCurrentUserLogin().orElse(null);
        if (email == null || email.isEmpty()) {
            return true; // Không có user -> bỏ qua interceptor
        }

        User user = this.userService.findByUsername(email);
        if (user == null) {
            return false;
        }

        // Tải roles và permissions trước khi session đóng
        Hibernate.initialize(user.getRoles());
        user.getRoles().forEach(role -> Hibernate.initialize(role.getPermissions()));

        List<Role> roles = user.getRoles();
        Permission permission = this.permissionRepository.findByMethodAndPath(httpMethod, path);

        boolean hasPermission = roles.stream()
                .anyMatch(role -> "ROLE_ADMIN".equals(role.getName()) || role.getPermissions().contains(permission));

        if (hasPermission) {
            return true;
        }

        // Nếu không có quyền -> trả về JSON lỗi
        ResponseData<Object> res = new ResponseData<>();
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.setError("không có quyền hạn");
        res.setMessage("Làm gì có quyền");

        response.resetBuffer();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        log.info(">>> Returning error response: " + objectMapper.writeValueAsString(res));
        objectMapper.writeValue(response.getWriter(), res);
        response.flushBuffer();

        return false;
    }
}
