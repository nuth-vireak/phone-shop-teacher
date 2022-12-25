package com.piseth.java.school.phoneshop.service;

import java.util.List;

import com.piseth.java.school.phoneshop.model.Brand;

public interface BrandService {
	Brand save(Brand entity);

	Brand getById(Long id);

	Brand update(Long id, Brand brand);

	void delete(Long id);

	List<Brand> getBrands();

}
