package com.piseth.java.school.phoneshop.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.piseth.java.school.phoneshop.dto.ExpenseDTO;
import com.piseth.java.school.phoneshop.dto.ProductSoldDTO;
import com.piseth.java.school.phoneshop.dto.SaleByDateDTO;
import com.piseth.java.school.phoneshop.model.Product;
import com.piseth.java.school.phoneshop.model.ProductImportHistory;
import com.piseth.java.school.phoneshop.model.SaleDetail;
import com.piseth.java.school.phoneshop.projections.SaleByDate;
import com.piseth.java.school.phoneshop.repository.ProductImportHistoryRepository;
import com.piseth.java.school.phoneshop.repository.ProductRepository;
import com.piseth.java.school.phoneshop.repository.SaleDetailRepository;
import com.piseth.java.school.phoneshop.service.ReportingService;
import com.piseth.java.school.phoneshop.spec.ProductImportHistoryFilter;
import com.piseth.java.school.phoneshop.spec.ProductImportHistorySpec;
import com.piseth.java.school.phoneshop.spec.SaleDetailFilter;
import com.piseth.java.school.phoneshop.spec.SaleDetailSpec;

import liquibase.pro.packaged.ex;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportingServiceImpl implements ReportingService{
	private final SaleDetailRepository saleDetailRepository;
	private final ProductRepository productRepository;
	private final ProductImportHistoryRepository importHistoryRepository;
	
	@Override
	public List<SaleByDate> getProductSoldByDate(LocalDate soldDate) {
		return saleDetailRepository.findByProduct(soldDate);
	}

	@Override
	public List<SaleByDateDTO> getProductSoldByDateV2(LocalDate soldDate) {
		SaleDetailFilter detailFilter = new SaleDetailFilter();
		detailFilter.setSoldDate(soldDate);
		
		SaleDetailSpec spec = new SaleDetailSpec(detailFilter);
		List<SaleDetail> saleDetails = saleDetailRepository.findAll(spec);
		// Group by product
		Map<Product, List<SaleDetail>> saleByProductMap = saleDetails.stream()
			.collect(Collectors.groupingBy(SaleDetail::getProduct));
		
		List<Long> productIds = saleDetails.stream()
				.map(sd -> sd.getProduct().getId())
				.toList();
		// Find all product sold in that day	
		List<Product> products = productRepository.findAllById(productIds);
		Map<Long, Product> productMap = products.stream()
			.collect(Collectors.toMap(Product::getId, Function.identity()));
		
		List<SaleByDateDTO> saleByDateDTOs = new ArrayList<>();
		for(Map.Entry<Product, List<SaleDetail>> entry : saleByProductMap.entrySet()) {
			
			Product product = productMap.get(entry.getKey().getId());
			List<SaleDetail> saleDetailsList = entry.getValue();
			
			
			Integer totalUnit = saleDetailsList.stream()
				.collect(Collectors.summingInt(SaleDetail::getUnit));
			
			Double amount = saleDetailsList.stream()
				.collect(Collectors.summingDouble(sd -> sd.getAmount().doubleValue() * sd.getUnit()));
			
			
			SaleByDateDTO dto = new SaleByDateDTO();
			dto.setSoldDate(soldDate);
			dto.setProductId(product.getId());
			dto.setProductName(product.getName());
			dto.setTotalUnit(totalUnit);
			dto.setAmount(amount);
			saleByDateDTOs.add(dto);
		}
		
		return saleByDateDTOs;
	}

	@Override
	public List<ProductSoldDTO> getProductSold(LocalDate startDate, LocalDate endDate) {
		List<SaleDetail> saleDetails = getDetails(startDate, endDate);
		// Group by product
		Map<Product, List<SaleDetail>> saleByProductMap = saleDetails.stream()
			.collect(Collectors.groupingBy(SaleDetail::getProduct));
		
		List<Long> productIds = saleDetails.stream()
				.map(sd -> sd.getProduct().getId())
				.toList();
		// Find all product sold in that day	
		List<Product> products = productRepository.findAllById(productIds);
		Map<Long, Product> productMap = products.stream()
			.collect(Collectors.toMap(Product::getId, Function.identity()));
		
		List<ProductSoldDTO> productSoldDTOs = new ArrayList<>();
		for(Map.Entry<Product, List<SaleDetail>> entry : saleByProductMap.entrySet()) {
			
			Product product = productMap.get(entry.getKey().getId());
			List<SaleDetail> saleDetailsList = entry.getValue();
			
			
			Integer totalUnit = saleDetailsList.stream()
				.collect(Collectors.summingInt(SaleDetail::getUnit));
			
			Double amount = saleDetailsList.stream()
				.collect(Collectors.summingDouble(sd -> sd.getAmount().doubleValue() * sd.getUnit()));
			
			
			ProductSoldDTO productSoldDTO = toProductSoldDTO(product,totalUnit, amount);
			productSoldDTOs.add(productSoldDTO);
		}
				

		return productSoldDTOs;
	}
	
	private ProductSoldDTO toProductSoldDTO(Product product, int totalUnit, Double amount) {
		ProductSoldDTO dto = new ProductSoldDTO();
		dto.setProductId(product.getId());
		dto.setProductName(product.getName());
		dto.setTotalUnit(totalUnit);
		dto.setAmount(amount);
		return dto;
	}
	
	private List<SaleDetail> getDetails(LocalDate startDate, LocalDate endDate){
		SaleDetailFilter detailFilter = new SaleDetailFilter();
		detailFilter.setStartDate(startDate);
		detailFilter.setEndDate(endDate);
		detailFilter.setSaleStatus(true);
		
		SaleDetailSpec spec = new SaleDetailSpec(detailFilter);
		List<SaleDetail> saleDetails = saleDetailRepository.findAll(spec);
		return saleDetails;
	}

	@Override
	public List<ExpenseDTO> getExpense(LocalDate startDate, LocalDate endDate) {
		ProductImportHistoryFilter historyFilter = new ProductImportHistoryFilter();
		historyFilter.setStartDate(startDate);
		historyFilter.setEndDate(endDate);
		
		ProductImportHistorySpec historySpec = new ProductImportHistorySpec(historyFilter);
		
		List<ProductImportHistory> productImportHistories = importHistoryRepository.findAll(historySpec);
		
		Map<Product, List<ProductImportHistory>> historyMap = productImportHistories.stream()
			.collect(Collectors.groupingBy(history -> history.getProduct()));
		
		List<Long> productIds = productImportHistories.stream()
			.map(h -> h.getProduct().getId())
			.toList();
		
		List<Product> products = productRepository.findAllById(productIds);
		Map<Long, Product> productMap = products.stream()
			.collect(Collectors.toMap(Product::getId, Function.identity()));
		
		
		List<ExpenseDTO> expenseDTOs = new ArrayList<>();
		
		for(var entry : historyMap.entrySet()) {
			Product product = productMap.get(entry.getKey().getId());
			List<ProductImportHistory> historyList = entry.getValue();
			
			Double amount = historyList.stream()
				.collect(Collectors.summingDouble(history -> history.getPricePerUnit().doubleValue() * history.getImportUnit()));
			
			Integer unit = historyList.stream()
				.collect(Collectors.summingInt(history -> history.getImportUnit()));
			
			ExpenseDTO expenseDTO = new ExpenseDTO();
			expenseDTO.setProductId(product.getId());
			expenseDTO.setProductName(product.getName());
			expenseDTO.setAmount(BigDecimal.valueOf(amount));
			expenseDTO.setQuantity(unit);
			expenseDTOs.add(expenseDTO);
		}
		return expenseDTOs;
	}

}
