package com.piseth.java.school.phoneshop.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class ImportDTO {
	private Integer importUnit;
	private BigDecimal pricePerUnit;
	private LocalDate dateImport;
}
