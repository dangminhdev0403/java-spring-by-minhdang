package com.vn.minh.controller.admin;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.vn.minh.domain.model.User;
import com.vn.minh.domain.spes.UserSpes;
import com.vn.minh.service.UserService;
import com.vn.minh.service.util.error.MessageCustomExcetion;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/admin/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<User>> getListUserDTOWithPage(
            Pageable pageable,
            @RequestParam(value = "name", required = false) String name) {

        // ! nạp vào Pageable

        // List<User> listUser = this.userService.getListUser(
        // User.class, pageable, UserSpes.nameLike(
        // name))
        // .getContent();

        List<User> listUser = this.userService.getListUser(pageable,
                UserSpes.nameLike(name)).getContent();

        return ResponseEntity.ok().body(listUser);
    }

    @SuppressWarnings("null")
    @PostMapping("")
    public ResponseEntity<User> postMethodName(@RequestBody User user) {
        User saveUser = this.userService.saveUser(user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveUser.getId()) // Giả sử User có phương thức getId()
                .toUri();
        return ResponseEntity.created(location).body(saveUser);
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable long id) throws MessageCustomExcetion {
        User currentUser = this.userService.findUserById(id);
        if (currentUser == null) {
            throw new MessageCustomExcetion("Không tìm thấy user");
        }

        return "ok";
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        this.userService.deleteUserById(id);

        return ResponseEntity.ok().build();
    }
}
