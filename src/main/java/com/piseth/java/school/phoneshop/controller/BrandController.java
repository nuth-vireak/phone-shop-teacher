package com.piseth.java.school.phoneshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piseth.java.school.phoneshop.dto.BrandDTO;
import com.piseth.java.school.phoneshop.exception.ApiException;
import com.piseth.java.school.phoneshop.mapper.BrandMapper;
import com.piseth.java.school.phoneshop.model.Brand;
import com.piseth.java.school.phoneshop.service.BrandService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
public class BrandController {
	
	private final BrandService brandService;

	//@PreAuthorize("hasAuthority('brand:write')")
	//hasAnyAuthority
	@PreAuthorize("hasAnyAuthority('brand:write','brand:read','ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<Brand> create(@RequestBody BrandDTO brandDTO) {
		Brand brand = BrandMapper.INSTANCE.toEntity(brandDTO);
		brand = brandService.save(brand);
		return ResponseEntity.ok(brand);
	}
	
	
	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Long id) throws ApiException{
		return ResponseEntity.ok(brandService.getById(id));
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody BrandDTO brandDTO) throws ApiException {
		Brand brand = BrandMapper.INSTANCE.toEntity(brandDTO);
		return ResponseEntity.ok(brandService.update(id, brand));
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) throws ApiException{
		brandService.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@PreAuthorize("hasAuthority('brand:read')")
	@GetMapping
	public ResponseEntity<?> list(){
		
		List<BrandDTO> listBrand = brandService.getBrands()
				.stream()
				.map(b -> BrandMapper.INSTANCE.toDTO(b))
				.toList();
		
		return ResponseEntity.ok(listBrand);
	}
	
	
	
}
