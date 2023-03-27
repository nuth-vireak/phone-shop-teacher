package com.piseth.java.school.phoneshop.controller;

import java.util.Map;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.piseth.java.school.phoneshop.dto.ModelDTO;
import com.piseth.java.school.phoneshop.dto.PageDTO;
import com.piseth.java.school.phoneshop.exception.ApiException;
import com.piseth.java.school.phoneshop.mapper.ModelEntityMapper;
import com.piseth.java.school.phoneshop.mapper.PageMapper;
import com.piseth.java.school.phoneshop.model.Model;
import com.piseth.java.school.phoneshop.service.ModelService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/models")
public class ModelController {
	
	private final ModelService modelService;
	
	private final ModelEntityMapper modelMapper;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody ModelDTO dto) throws ApiException{
		Model model = modelMapper.toModel(dto);
		model = modelService.save(model);
		ModelDTO modelDTO = ModelEntityMapper.INSTANCE.toDTO(model);
		return ResponseEntity.ok(modelDTO);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id) throws ApiException{
		Model model = modelService.getById(id);
		return ResponseEntity.ok(ModelEntityMapper.INSTANCE.toDTO(model));
	}
	
	//@PreAuthorize("hasRole('ROLE_SALE')")
	@RolesAllowed("ROLE_SALE")
	@GetMapping
	public ResponseEntity<?> getModelList(@RequestParam Map<String, String> params){
		Page<Model> page = modelService.getModels(params);
		
		PageDTO dto = PageMapper.INSTANCE.toDTO(page);
		dto.setList(page.get().map(ModelEntityMapper.INSTANCE::toDTO).toList());
		return ResponseEntity.ok(dto);
	}

}
