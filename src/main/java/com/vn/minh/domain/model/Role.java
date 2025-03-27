package com.vn.minh.domain.model;

import java.util.List;

import com.vn.minh.domain.impl.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Role extends BaseEntity {

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @ManyToMany
    @JoinTable(name = "role_permission", // Tên bảng phụ
            joinColumns = @JoinColumn(name = "role_id"), // Cột khóa ngoại tham chiếu đến Role
            inverseJoinColumns = @JoinColumn(name = "permission_id") // Cột khóa ngoại tham chiếu đến Permission
    )
    private List<Permission> permissions

    ;

}
