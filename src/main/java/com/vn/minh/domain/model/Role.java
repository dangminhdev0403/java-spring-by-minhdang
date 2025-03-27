package com.vn.minh.domain.model;

import java.util.List;

import com.vn.minh.domain.impl.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Object stream() {
        throw new UnsupportedOperationException("Unimplemented method 'stream'");
    }

}
