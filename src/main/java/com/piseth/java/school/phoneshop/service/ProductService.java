package com.piseth.java.school.phoneshop.service;

import com.piseth.java.school.phoneshop.dto.ProductImportDTO;
import com.piseth.java.school.phoneshop.model.Product;

public interface ProductService {
	Product save(ProductImportDTO productImportDTO);
	Product getById(Long id);
}
