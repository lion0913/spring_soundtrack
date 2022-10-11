package com.lion.spring_soundtrack.app.product.repository;

import com.lion.spring_soundtrack.app.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
