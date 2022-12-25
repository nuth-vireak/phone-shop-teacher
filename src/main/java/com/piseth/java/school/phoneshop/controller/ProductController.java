package com.piseth.java.school.phoneshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piseth.java.school.phoneshop.dto.ProductImportDTO;
import com.piseth.java.school.phoneshop.mapper.ProductMapper;
import com.piseth.java.school.phoneshop.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
	private final ProductMapper productMapper;
	private final ProductService productService;
	
	@PostMapping
	public ResponseEntity<?> createProduct(@RequestBody ProductImportDTO dto){
		return ResponseEntity.ok(productMapper.toDTO(productService.save(dto)));
	}

}
