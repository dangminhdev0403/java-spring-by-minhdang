package com.vn.minh.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.minh.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // ! dùng Pageable của Spring cung cấp
    Page<User> findAll(Pageable pageable);

    // projection
    <T> List<T> findAllBy(Class<T> type);

    <T> Optional<T> findByEmailAndRefreshToken(Class<T> type, String email, String refreshToken);

    Optional<User> findByEmail(String email);

}
