package com.vn.minh.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.minh.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    <T> List<T> findAllBy(Class<T> type);
}
