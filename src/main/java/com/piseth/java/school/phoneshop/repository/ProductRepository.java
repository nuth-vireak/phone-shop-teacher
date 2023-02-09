package com.piseth.java.school.phoneshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.piseth.java.school.phoneshop.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	// model , color
	Optional<Product> findByModelIdAndColorId(Long modelId, Long colorId);
	
	
}
