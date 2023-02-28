package com.piseth.java.school.phoneshop.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piseth.java.school.phoneshop.dto.ExpenseDTO;
import com.piseth.java.school.phoneshop.dto.ProductSoldDTO;
import com.piseth.java.school.phoneshop.dto.SaleByDateDTO;
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
	
	@GetMapping("/dailyProduct/v2/{soldDate}")
	public ResponseEntity<?> getProductSaleByDateV2(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate soldDate){
		List<SaleByDateDTO> productSoldByDate = reportingService.getProductSoldByDateV2(soldDate);
		return ResponseEntity.ok(productSoldByDate);
	}
	
	@GetMapping("/product/{startDate}/{endDate}")
	public ResponseEntity<?> getProductSold(
			@PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate){
		List<ProductSoldDTO> productSold = reportingService.getProductSold(startDate, endDate);
		return ResponseEntity.ok(productSold);
	}
	
	@GetMapping("/expense/{startDate}/{endDate}")
	public ResponseEntity<?> getExpense(
			@PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate){
		List<ExpenseDTO> expenseDTOs = reportingService.getExpense(startDate, endDate);
		return ResponseEntity.ok(expenseDTOs);
	}
}
