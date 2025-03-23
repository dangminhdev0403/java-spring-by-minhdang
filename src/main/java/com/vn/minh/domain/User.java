package com.vn.minh.domain;

import com.vn.minh.domain.impl.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Setter
@Getter

@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

    // ! Quy định cột kiểu mediumtext
    @Column(columnDefinition = "LONGTEXT")
    private String refreshToken;

    private String email;
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
