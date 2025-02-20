package com.vn.minh.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vn.minh.domain.User;
import com.vn.minh.domain.dto.UserDTO;
import com.vn.minh.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/admin/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> getListUserDTO() {
        List<UserDTO> listUsers = this.userService.getListUser();
        return ResponseEntity.ok().body(listUsers);
    }

    @PostMapping("")
    public ResponseEntity<User> postMethodName(@RequestBody User user) {
        User saveUser = this.userService.saveUser(user);

        return ResponseEntity.created(null).body(saveUser);
    }

    @PutMapping("/{id}")
    public String putMethodName() {

        return "ok";
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        this.userService.deleteUserById(id);

        return ResponseEntity.ok().build();
    }
}
