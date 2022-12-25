package com.piseth.java.school.phoneshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.piseth.java.school.phoneshop.dto.ModelDTO;
import com.piseth.java.school.phoneshop.model.Model;
import com.piseth.java.school.phoneshop.service.BrandService;

@Mapper(componentModel = "spring", uses = {BrandService.class})
public interface ModelMapper {
	ModelMapper INSTANCE = Mappers.getMapper(ModelMapper.class);

	@Mapping(target = "brand", source = "dto.brandId")
	Model toModel(ModelDTO dto);

	@Mapping(target = "brandId", source = "brand.id")
	ModelDTO toDTO(Model entity);
	
}
