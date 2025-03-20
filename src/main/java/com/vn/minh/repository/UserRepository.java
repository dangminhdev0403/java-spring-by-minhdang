package com.vn.minh.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vn.minh.domain.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    // ! dùng Pageable của Spring cung cấp
    Page<User> findAll(Specification<User> specification ,Pageable pageable);

    // projection
    <T> Page<T> findAllBy(  Specification<User> specification ,Pageable pageable, Class<T> type);

    <T> Optional<T> findByEmailAndRefreshToken(Class<T> type, String email, String refreshToken);

    Optional<User> findByEmail(String email);

}
