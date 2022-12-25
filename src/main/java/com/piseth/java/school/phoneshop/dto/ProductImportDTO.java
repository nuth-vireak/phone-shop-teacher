package com.piseth.java.school.phoneshop.dto;

import lombok.Data;

@Data
public class ProductImportDTO {
	private ProductDTO product;
	private ImportDTO importDetail;
}
