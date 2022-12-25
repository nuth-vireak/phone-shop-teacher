package com.piseth.java.school.phoneshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.piseth.java.school.phoneshop.dto.ImportDTO;
import com.piseth.java.school.phoneshop.model.Product;
import com.piseth.java.school.phoneshop.model.ProductImportHistory;

@Mapper
public interface ProductImportHistoryMapper {
	ProductImportHistoryMapper INSTANCE = Mappers.getMapper(ProductImportHistoryMapper.class);
	
	@Mapping(target = "product", source = "product")
	@Mapping(target = "id" , ignore = true)
	ProductImportHistory toEntity(ImportDTO importDTO, Product product);
}
