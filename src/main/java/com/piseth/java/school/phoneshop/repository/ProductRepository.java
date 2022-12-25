package com.piseth.java.school.phoneshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.piseth.java.school.phoneshop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	//model , color
	Optional<Product> findByModelIdAndColorId(Long modelId, Long colorId);
}
