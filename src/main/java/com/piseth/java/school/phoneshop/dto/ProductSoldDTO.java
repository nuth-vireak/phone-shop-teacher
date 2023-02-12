package com.piseth.java.school.phoneshop.dto;

import lombok.Data;

@Data
public class ProductSoldDTO {
	private Long productId;

	private String productName;

	private Integer totalUnit;

	private Double amount;
}
