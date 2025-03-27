package com.vn.minh.config;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.vn.minh.domain.model.Role;
import com.vn.minh.repository.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataBaseInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        long countRole = roleRepository.count();
        if (countRole == 0) {
            String[] roleNames = { "ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_USER" }; // Danh sách các vai trò
            List<Role> roles = Arrays.stream(roleNames)
                    .map(roleName -> {
                        Role role = new Role();
                        role.setName(roleName); // Thiết lập tên vai trò
                        return role;
                    })
                    .collect(Collectors.toList()); // Chuyển thành danh sách

            roleRepository.saveAll(roles); // Lưu tất cả vào cơ sở dữ liệu
            log.info("Đã khởi tạo thành công {} vai trò.", roles.size());

        } else {
            log.info("Dữ liệu vai trò đã tồn tại, bỏ qua khởi tạo.");

        }
    }
}