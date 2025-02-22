package com.vn.minh.service;

import java.util.List;

import com.vn.minh.domain.User;
import com.vn.minh.domain.dto.UserDTO;

public interface UserService {

    List<UserDTO> getListUser();

    User saveUser(User user);

    User findUserById(long id) ;

    void deleteUserById(long id);

}
