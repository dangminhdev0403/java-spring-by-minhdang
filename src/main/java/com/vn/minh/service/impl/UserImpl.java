package com.vn.minh.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vn.minh.domain.User;
import com.vn.minh.domain.dto.UserDTO;
import com.vn.minh.repository.UserRepository;
import com.vn.minh.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDTO> getListUser() {
        List<UserDTO> listUsers = this.userRepository.findAllBy(UserDTO.class);
        return listUsers;
    }

    @Override
    public User saveUser(User user) {
        User current = this.userRepository.save(user);
        return current;
    }

    @Override
    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }

}
