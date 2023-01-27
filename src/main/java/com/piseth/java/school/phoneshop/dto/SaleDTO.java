package com.piseth.java.school.phoneshop.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDTO {
	private List<ProductOrderDTO> products;
	private String soldDate;
}
