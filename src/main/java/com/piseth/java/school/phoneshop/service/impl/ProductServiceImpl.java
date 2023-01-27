package com.piseth.java.school.phoneshop.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.piseth.java.school.phoneshop.dto.ProductDisplayDTO;
import com.piseth.java.school.phoneshop.dto.ProductImportDTO;
import com.piseth.java.school.phoneshop.exception.ApiException;
import com.piseth.java.school.phoneshop.exception.ResourceNotFoundException;
import com.piseth.java.school.phoneshop.mapper.ProductImportHistoryMapper;
import com.piseth.java.school.phoneshop.mapper.ProductMapper;
import com.piseth.java.school.phoneshop.model.Color;
import com.piseth.java.school.phoneshop.model.Model;
import com.piseth.java.school.phoneshop.model.Product;
import com.piseth.java.school.phoneshop.model.ProductImportHistory;
import com.piseth.java.school.phoneshop.repository.ColorRepository;
import com.piseth.java.school.phoneshop.repository.ModelRepository;
import com.piseth.java.school.phoneshop.repository.ProductImportHistoryRepository;
import com.piseth.java.school.phoneshop.repository.ProductRepository;
import com.piseth.java.school.phoneshop.service.ProductService;
import com.piseth.java.school.phoneshop.spec.ProductSpec;
import com.piseth.java.school.phoneshop.utils.PageUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
	private final ProductRepository productRepository;
	private final ProductImportHistoryRepository historyRepository;
	private final ProductMapper productMapper;
	private final ModelRepository modelRepository;
	private final ColorRepository colorRepository;

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

	@Override
	public Product setPrice(Long productId, BigDecimal price) {
		// check if product exist , get product
		Product product = getById(productId);
		// update price
		product.setSalePrice(price);
		return productRepository.save(product);
	}

	@Override
	public Page<Product> getProducts(Map<String, String> params) {
		Pageable pageable = PageUtils.getPageable(params);
		return productRepository.findAll(new ProductSpec(), pageable);
	}
	
	@Override
	public List<ProductDisplayDTO> toProductDisplayDTOs(List<Product> products){
		List<ProductDisplayDTO> diplayDTOs = new ArrayList<>();
		//products find all model id;
		List<Long> modelIds = products.stream().map(p -> p.getModel().getId()).toList();
		List<Model> models = modelRepository.findAllById(modelIds);
		Map<Long, String> modelMap = models.stream().collect(Collectors.toMap(p -> p.getId(), p -> p.getName()));
		
		List<Long> colorIds = products.stream().map(p -> p.getColor().getId()).toList();
		List<Color> colors = colorRepository.findAllById(colorIds);
		Map<Long, String> colorMap = colors.stream().collect(Collectors.toMap(Color::getId, Color::getName));
		
		for(Product product : products) {
			ProductDisplayDTO dto = new ProductDisplayDTO();
			dto.setId(product.getId());
			dto.setName(product.getName());
			dto.setSalePrice(product.getSalePrice());
			dto.setModel(modelMap.get(product.getModel().getId()));
			dto.setColor(colorMap.get(product.getColor().getId()));
			diplayDTOs.add(dto);
		}
		return diplayDTOs;
	}

	@Override
	public boolean hasAvailableUnit(Long productId, Integer orderUnit) {
		Product product = getById(productId);
		if(product.getAvailableUnit() < orderUnit) {
			throw new ApiException(HttpStatus.BAD_REQUEST, 
					"Product (%s) with id = %d doesn't have enough unit in stock"
					.formatted(product.getName(), productId));
		}
		
		return true;
	}

	@Override
	public boolean salePriceIsSet(Long productId) {
		Product product = getById(productId);
		if(Objects.isNull(product.getSalePrice())) {
			throw new ApiException(HttpStatus.BAD_REQUEST, 
					"Product (%s) with id = %d haven't set sale price yet."
					.formatted(product.getName(), productId));
		}
		return false;
	}

}
