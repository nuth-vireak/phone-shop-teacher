package com.piseth.java.school.phoneshop.service;

import java.util.Map;

import org.springframework.data.domain.Page;

import com.piseth.java.school.phoneshop.model.Model;

public interface ModelService {
	Model save(Model entity);
	Model getById(Long id);
	Page<Model> getModels(Map<String, String> params);
}
