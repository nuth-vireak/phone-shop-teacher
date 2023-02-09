package com.piseth.java.school.phoneshop.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SaleByDateDTO {
	private LocalDate soldDate;

	private Long productId;

	private String productName;

	private Integer totalUnit;

	private Double amount;
}
