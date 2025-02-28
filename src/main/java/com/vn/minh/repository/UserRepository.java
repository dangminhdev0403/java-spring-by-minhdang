package com.vn.minh.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vn.minh.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // projection
    <T> List<T> findAllBy(Class<T> type);


    <T> Optional<T> findByEmailAndRefreshToken(Class<T> type, String email, String refreshToken);
    

    Optional<User> findByEmail(String email);




}
