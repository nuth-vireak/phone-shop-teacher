package com.piseth.java.school.phoneshop.service.impl;

import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.piseth.java.school.phoneshop.dto.ModelDTO;
import com.piseth.java.school.phoneshop.exception.ResourceNotFoundException;
import com.piseth.java.school.phoneshop.mapper.ModelEntityMapper;
import com.piseth.java.school.phoneshop.model.Model;
import com.piseth.java.school.phoneshop.repository.ModelRepository;
import com.piseth.java.school.phoneshop.service.BrandService;
import com.piseth.java.school.phoneshop.service.ModelService;
import com.piseth.java.school.phoneshop.spec.ModelFilter;
import com.piseth.java.school.phoneshop.spec.ModelSpec;
import com.piseth.java.school.phoneshop.utils.PageUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService{
	
	private final ModelRepository modelRepository;
	
	private final BrandService brandService;

	@Override
	public Model save(Model entity)  {
		
		//brandService.getById(entity.getBrand().getId());
		
		//Model model = ModelMapper.INSTANCE.toModel(entity);
		return modelRepository.save(entity);
	}

	@Override
	public Model getById(Long id)  {
		return modelRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Model", id));
	}
	
	@Override
	public Page<Model> getModels(Map<String, String> params) {
		Pageable pageable = PageUtils.getPageable(params);
		
		ModelFilter modelFilter = new ModelFilter();
		if(params.containsKey("modelId")) {
			modelFilter.setModelId(MapUtils.getLong(params, "modelId"));
		}
		if(params.containsKey("modelName")) {
			modelFilter.setModelName(MapUtils.getString(params, "modelName"));
		}
		if(params.containsKey("brandId")) {
			modelFilter.setBrandId(MapUtils.getLong(params, "brandId"));
		}
		if(params.containsKey("brandName")) {
			modelFilter.setBrandName(MapUtils.getString(params, "brandName"));
		}
		
		ModelSpec modelSpec = new ModelSpec(modelFilter);
		
		Page<Model> page = modelRepository.findAll(modelSpec, pageable);
		return page;
	}
	
}
