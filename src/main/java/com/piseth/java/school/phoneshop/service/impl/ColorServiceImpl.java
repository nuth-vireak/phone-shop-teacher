package com.piseth.java.school.phoneshop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.piseth.java.school.phoneshop.exception.ApiException;
import com.piseth.java.school.phoneshop.exception.ResourceNotFoundException;
import com.piseth.java.school.phoneshop.model.Color;
import com.piseth.java.school.phoneshop.repository.ColorRepository;
import com.piseth.java.school.phoneshop.service.ColorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService{
	
	@Autowired
	private final ColorRepository colorRepository;

	@Override
	public Color save(Color entity) {
		return colorRepository.save(entity);
	}

	@Override
	public Color getById(Long id) {
		  return colorRepository.findById(id)
				 .orElseThrow(() -> new ResourceNotFoundException("Color", id));
	}


	

}
