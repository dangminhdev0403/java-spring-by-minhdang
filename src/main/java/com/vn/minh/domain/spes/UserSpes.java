package com.vn.minh.domain.spes;

import org.springframework.data.jpa.domain.Specification;

import com.vn.minh.domain.User;
import com.vn.minh.domain.User_;

public class UserSpes {
    
    public static Specification<User> nameLike(String name) {
            return (root,query,builder) -> builder.like(root.get(User_.name),"%"+name+"%");
    }
}
