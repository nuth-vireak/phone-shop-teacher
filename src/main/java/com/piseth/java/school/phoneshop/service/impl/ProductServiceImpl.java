package com.piseth.java.school.phoneshop.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.piseth.java.school.phoneshop.dto.ProductImportDTO;
import com.piseth.java.school.phoneshop.exception.ResourceNotFoundException;
import com.piseth.java.school.phoneshop.mapper.ProductImportHistoryMapper;
import com.piseth.java.school.phoneshop.mapper.ProductMapper;
import com.piseth.java.school.phoneshop.model.Color;
import com.piseth.java.school.phoneshop.model.Model;
import com.piseth.java.school.phoneshop.model.Product;
import com.piseth.java.school.phoneshop.model.ProductImportHistory;
import com.piseth.java.school.phoneshop.repository.ProductImportHistoryRepository;
import com.piseth.java.school.phoneshop.repository.ProductRepository;
import com.piseth.java.school.phoneshop.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
	private final ProductRepository productRepository;
	private final ProductImportHistoryRepository historyRepository;
	private final ProductMapper productMapper;

	@Override
	public Product save(ProductImportDTO dto) {
		/*
		 * model , color
		 */
		Long modelId = dto.getProduct().getModelId();
		Long colorId = dto.getProduct().getColorId();
		Optional<Product> existingProduct = productRepository.findByModelIdAndColorId(modelId, colorId);
		Product product = null;
		if(existingProduct.isPresent()) {
			/*
			 *  set new available unit in stock
			 *  get current available unit + new number of unit
			 */
			product = existingProduct.get();
			Integer availableUnit = product.getAvailableUnit();
			Integer importUnit = dto.getImportDetail().getImportUnit();
			product.setAvailableUnit(availableUnit + importUnit);
			
		}else {
			product = productMapper.toProduct(dto.getProduct());
			product.setAvailableUnit(dto.getImportDetail().getImportUnit());
		}
		
		product = productRepository.save(product);
		// set product import history
		ProductImportHistory importHistory = ProductImportHistoryMapper.INSTANCE.toEntity(dto.getImportDetail(), product);
		historyRepository.save(importHistory);
		
		return product;
	}

	@Override
	public Product getById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", id));
	}

}
