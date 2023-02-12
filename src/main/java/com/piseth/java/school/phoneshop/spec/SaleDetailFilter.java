package com.piseth.java.school.phoneshop.spec;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SaleDetailFilter {
	private LocalDate soldDate;
	private Long productId;
	private Long modelId;
	private LocalDate startDate;
	private LocalDate endDate;
}
