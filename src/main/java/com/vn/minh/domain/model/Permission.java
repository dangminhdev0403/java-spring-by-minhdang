package com.vn.minh.domain.model;

import java.util.List;
import java.util.Objects;

import com.vn.minh.domain.impl.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "permissions") // Sửa lỗi đánh máy
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends BaseEntity { // Sửa tên lớp
    private String method;
    private String path;

    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;

    public Permission(String name, String method, String path) {
        super(name);
        this.method = method;
        this.path = path;
    }

    // Override equals và hashCode để tránh trùng lặp
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Permission that))
            return false; // Tham chiếu đúng lớp Permission của bạn
        return method.equals(that.method) && path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, path);
    }
}