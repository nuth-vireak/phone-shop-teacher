package com.piseth.java.school.phoneshop.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.piseth.java.school.phoneshop.dto.ProductDisplayDTO;
import com.piseth.java.school.phoneshop.dto.ProductImportDTO;
import com.piseth.java.school.phoneshop.model.Product;

public interface ProductService {
	Product save(ProductImportDTO productImportDTO);
	Product getById(Long id);
	Product setPrice(Long productId, BigDecimal price);
	Page<Product> getProducts(Map<String, String> params);
	List<ProductDisplayDTO> toProductDisplayDTOs(List<Product> products);
	boolean hasAvailableUnit(Long productId, Integer orderUnit);
	boolean salePriceIsSet(Long productId);
	Map<Long, String> uploadProducts(MultipartFile file);
}
