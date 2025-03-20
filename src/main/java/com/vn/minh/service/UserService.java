package com.vn.minh.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vn.minh.domain.User;
import com.vn.minh.domain.dto.UserDTO;

public interface UserService {

    Page<User> getListUserWithPageable(Pageable pageable);

    List<UserDTO> getListUser();

    User saveUser(User user);

    User findUserById(long id);

    void deleteUserById(long id);

    User findByUsername(String name);

    void updateRefreshToken(String username, String token);

    <T> Optional<T> getUserByRefreshTokenAndEmail(Class<T> type, String email, String refreshToken);

}
