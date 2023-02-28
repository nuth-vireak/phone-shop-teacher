package com.piseth.java.school.phoneshop.service;

import java.time.LocalDate;
import java.util.List;

import com.piseth.java.school.phoneshop.dto.ExpenseDTO;
import com.piseth.java.school.phoneshop.dto.ProductSoldDTO;
import com.piseth.java.school.phoneshop.dto.SaleByDateDTO;
import com.piseth.java.school.phoneshop.projections.SaleByDate;

public interface ReportingService {
// daily sale by product
	List<SaleByDate> getProductSoldByDate(LocalDate soldDate);
	
	List<SaleByDateDTO> getProductSoldByDateV2(LocalDate soldDate);
	
	// startDate , endDate
	
	List<ProductSoldDTO> getProductSold(LocalDate startDate, LocalDate endDate);
	List<ExpenseDTO> getExpense(LocalDate startDate, LocalDate endDate);

}
