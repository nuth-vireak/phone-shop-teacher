package com.piseth.java.school.phoneshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import com.piseth.java.school.phoneshop.dto.ProductSoldDTO;
import com.piseth.java.school.phoneshop.helper.ReportingHelper;
import com.piseth.java.school.phoneshop.model.Product;
import com.piseth.java.school.phoneshop.model.SaleDetail;
import com.piseth.java.school.phoneshop.repository.ProductImportHistoryRepository;
import com.piseth.java.school.phoneshop.repository.ProductRepository;
import com.piseth.java.school.phoneshop.repository.SaleDetailRepository;
import com.piseth.java.school.phoneshop.service.impl.ReportingServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ReportingServiceTest {
	@Mock
	private SaleDetailRepository saleDetailRepository;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private ProductImportHistoryRepository importHistoryRepository;
	
	private ReportingService reportingService;
	
	@BeforeEach
	public void setup() {
		reportingService = new ReportingServiceImpl(saleDetailRepository, productRepository, importHistoryRepository);
	}

	@Test
	public void testGetProductSold() {
		//given
		List<SaleDetail> saleDetails = ReportingHelper.getSaleDetails();
		List<Product> products = ReportingHelper.getProducts();
		
		//when
		when(saleDetailRepository.findAll(Mockito.any(Specification.class))).thenReturn(saleDetails);
		when(productRepository.findAllById(anySet())).thenReturn(products);
		List<ProductSoldDTO> productSoldDTOs = reportingService.getProductSold(LocalDate.now().minusDays(-10), LocalDate.now());
		
		//then
		assertEquals(2, productSoldDTOs.size());
		
		ProductSoldDTO productSoldDTO1 = productSoldDTOs.get(0);
		assertEquals(1L, productSoldDTO1.getProductId());
		assertEquals("iphone 14 pro red", productSoldDTO1.getProductName());
		assertEquals(5, productSoldDTO1.getTotalUnit());
		assertEquals(1000d, productSoldDTO1.getAmount().doubleValue());
		
		ProductSoldDTO productSoldDTO2 = productSoldDTOs.get(1);
		assertEquals(2L, productSoldDTO2.getProductId());
		assertEquals("iphone 14 pro white", productSoldDTO2.getProductName());
		assertEquals(13, productSoldDTO2.getTotalUnit());
		assertEquals(3900d, productSoldDTO2.getAmount().doubleValue());
		
	}
}
