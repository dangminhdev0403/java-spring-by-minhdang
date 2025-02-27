package com.vn.minh.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vn.minh.domain.User;
import com.vn.minh.domain.dto.UserDTO;
import com.vn.minh.repository.UserRepository;
import com.vn.minh.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserImpl implements UserService {

    private final UserRepository userRepository;

    private static String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

    public PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<UserDTO> getListUser() {
        List<UserDTO> listUsers = this.userRepository.findAllBy(UserDTO.class);
        return listUsers;
    }

    @Override
    public User saveUser(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String hashPass = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashPass);
        }
        User current = this.userRepository.save(user);
        return current;
    }

    @Override
    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public User findUserById(long id) {
        Optional<User> userOpt = this.userRepository.findById(id);
        if (userOpt.isPresent())
            return userOpt.get();
        return null;
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        User loginUser = new User();
        if (isValidEmail(username)) {
            Optional<User> optional = this.userRepository.findByEmail(username);
            loginUser = optional.isPresent() ? optional.get() : null;
            

        }
        return loginUser;
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
