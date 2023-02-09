package com.piseth.java.school.phoneshop.service;

import java.time.LocalDate;
import java.util.List;

import com.piseth.java.school.phoneshop.dto.SaleByDateDTO;
import com.piseth.java.school.phoneshop.projections.SaleByDate;

public interface ReportingService {
// daily sale by product
	List<SaleByDate> getProductSoldByDate(LocalDate soldDate);
	
	List<SaleByDateDTO> getProductSoldByDateV2(LocalDate soldDate);

}
