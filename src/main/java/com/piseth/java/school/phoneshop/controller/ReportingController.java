package com.piseth.java.school.phoneshop.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piseth.java.school.phoneshop.projections.SaleByDate;
import com.piseth.java.school.phoneshop.service.ReportingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportingController {
	
	private final ReportingService reportingService;
	
	@GetMapping("/dailyProduct/{soldDate}")
	public ResponseEntity<?> getProductSaleByDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate soldDate){
		List<SaleByDate> productSoldByDate = reportingService.getProductSoldByDate(soldDate);
		return ResponseEntity.ok(productSoldByDate);
	}
}
