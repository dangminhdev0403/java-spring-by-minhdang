package com.vn.minh.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.vn.minh.domain.User;

public interface UserService {

    Page<User> getListUserWithPageable(Pageable pageable);

    <T> Page<T> getListUser(Class<T> type, Pageable pageable, Specification<User> specification);

    Page<User> getListUser(Pageable pageable, Specification<User> specification);

    User saveUser(User user);

    User findUserById(long id);

    void deleteUserById(long id);

    User findByUsername(String name);

    void updateRefreshToken(String username, String token);

    <T> Optional<T> getUserByRefreshTokenAndEmail(Class<T> type, String email, String refreshToken);

}
