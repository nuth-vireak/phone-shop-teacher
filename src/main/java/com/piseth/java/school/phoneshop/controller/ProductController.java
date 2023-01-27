package com.piseth.java.school.phoneshop.controller;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.piseth.java.school.phoneshop.dto.PageDTO;
import com.piseth.java.school.phoneshop.dto.PriceDTO;
import com.piseth.java.school.phoneshop.dto.ProductImportDTO;
import com.piseth.java.school.phoneshop.mapper.PageMapper;
import com.piseth.java.school.phoneshop.mapper.ProductMapper;
import com.piseth.java.school.phoneshop.model.Product;
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
	
	@PutMapping("/setPrice/{productId}")
	public ResponseEntity<?> setPrice(@PathVariable("productId") Long productId,
			@RequestBody PriceDTO priceDTO){
		Product product = productService.setPrice(productId, priceDTO.getSalePrice());
		return ResponseEntity.ok(productMapper.toDTO(product));
	}
	
	@GetMapping
	public ResponseEntity<?> listProducts(@RequestParam Map<String, String> params){
		Page<Product> productPage = productService.getProducts(params);
		
		PageDTO pageDTO = PageMapper.INSTANCE.toDTO(productPage);
		pageDTO.setList(productService.toProductDisplayDTOs(productPage.getContent()));
		
		return ResponseEntity.ok(pageDTO);
	}
	
	

}
