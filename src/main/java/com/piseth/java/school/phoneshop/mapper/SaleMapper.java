package com.piseth.java.school.phoneshop.mapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.piseth.java.school.phoneshop.dto.ProductOrderDTO;
import com.piseth.java.school.phoneshop.dto.SaleDTO;
import com.piseth.java.school.phoneshop.model.Sale;
import com.piseth.java.school.phoneshop.model.SaleDetail;
import com.piseth.java.school.phoneshop.service.ProductService;

@Mapper(componentModel = "spring", uses = {ProductService.class})
public interface SaleMapper {
	
	@Mapping(target = "soldDate", expression = "java(toLocalDateTime(dto.getSoldDate()))")
	Sale toSale(SaleDTO dto);

	@Mapping(target = "sale", source = "sale")
	@Mapping(target = "product", source = "dto.productId")
	SaleDetail toSaleDetail(ProductOrderDTO dto, Sale sale, BigDecimal amount);
	
	default LocalDateTime toLocalDateTime(String textDateTime) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
		LocalDateTime dateTime = LocalDateTime.parse(textDateTime, dateTimeFormatter);
		return dateTime;
	}
}
