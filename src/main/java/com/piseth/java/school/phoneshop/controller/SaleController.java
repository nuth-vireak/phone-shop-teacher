package com.piseth.java.school.phoneshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piseth.java.school.phoneshop.dto.SaleDTO;
import com.piseth.java.school.phoneshop.service.SellService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/sells")
@RequiredArgsConstructor
public class SaleController {
	private final SellService sellService;
	
	@PostMapping
	public ResponseEntity<?> sell(@RequestBody SaleDTO dto){
		sellService.sell(dto);
		return ResponseEntity.ok().build();
	}	
	
	@PutMapping("{saleId}/cancel")
	public ResponseEntity<?> cancelSale(@PathVariable Long saleId){
		sellService.cancelSale(saleId);
		return ResponseEntity.noContent().build();
	}
}
