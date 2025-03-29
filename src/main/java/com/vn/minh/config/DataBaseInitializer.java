package com.vn.minh.config;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.vn.minh.domain.model.ApiDescription;
import com.vn.minh.domain.model.Permission;
import com.vn.minh.domain.model.Role;
import com.vn.minh.repository.PermissionRepository;
import com.vn.minh.repository.RoleRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DataBaseInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RequestMappingHandlerMapping handlerMapping;

    public DataBaseInitializer(RoleRepository roleRepository, PermissionRepository permissionRepository,
            @Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.handlerMapping = handlerMapping;
    }

    @Override
    public void run(String... args) throws Exception {
        // Khởi tạo Roles
        initializeRoles();

        // Khởi tạo Permissions từ endpoints
        initializePermissions();
    }

    private void initializeRoles() {
        long countRole = roleRepository.count();
        if (countRole == 0) {
            String[] roleNames = { "ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_USER" };
            List<Role> roles = Arrays.stream(roleNames)
                    .map(roleName -> {
                        Role role = new Role();
                        role.setName(roleName);
                        return role;
                    })
                    .collect(Collectors.toList());

            roleRepository.saveAll(roles);
            log.info("Đã khởi tạo thành công {} vai trò.", roles.size());
        } else {
            log.info("Data is exists, skip initialization.");
        }
    }

    @Transactional
    private void initializePermissions() {
        long countPermission = permissionRepository.count();
        long countEndpoints = handlerMapping.getHandlerMethods().size();
        if (countPermission == 0 || countPermission != countEndpoints) {
            handlerMapping.getHandlerMethods().entrySet().stream()
                    .flatMap(entry -> {
                        var requestMappingInfo = entry.getKey();
                        var methods = requestMappingInfo.getMethodsCondition().getMethods();
                        var patternsCondition = requestMappingInfo.getPathPatternsCondition();

                        Method method = entry.getValue().getMethod();

                        ApiDescription apiDescription = method.getAnnotation(ApiDescription.class);
                        String apiName = (apiDescription != null) ? apiDescription.value() : method.getName();

                        var patterns = (patternsCondition != null)
                                ? patternsCondition.getPatterns()
                                : Set.of(); // Tránh NullPointerException

                        return methods.stream()
                                .flatMap(m -> patterns.stream()
                                        .map(pattern -> new Permission(apiName, m.name(), pattern.toString())));
                    })
                    .distinct()
                    .filter(permission -> permissionRepository.findByMethodAndPath(permission.getMethod(),
                            permission.getPath()) == null)
                    .forEach(permission -> {
                        permissionRepository.save(permission);
                        // log.info("Added Permission: {} {}", permission.getMethod(),
                        // permission.getUrl());
                    });

            log.info("Permissions initialized from endpoints!");
        } else {
            log.info("Data is exists, skip initialization.");
        }
    }
}