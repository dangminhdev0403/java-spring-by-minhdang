package com.vn.minh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.minh.domain.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Permission findByMethodAndPath(String method, String path);

}
