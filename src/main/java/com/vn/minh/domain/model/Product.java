package com.vn.minh.domain.model;

import org.springframework.data.relational.core.mapping.Table;

import com.vn.minh.domain.impl.BaseEntity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
    private double price;
}
