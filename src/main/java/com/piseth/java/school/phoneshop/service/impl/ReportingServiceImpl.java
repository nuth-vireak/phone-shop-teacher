package com.piseth.java.school.phoneshop.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.piseth.java.school.phoneshop.dto.SaleByDateDTO;
import com.piseth.java.school.phoneshop.model.Product;
import com.piseth.java.school.phoneshop.model.SaleDetail;
import com.piseth.java.school.phoneshop.projections.SaleByDate;
import com.piseth.java.school.phoneshop.repository.ProductRepository;
import com.piseth.java.school.phoneshop.repository.SaleDetailRepository;
import com.piseth.java.school.phoneshop.service.ProductService;
import com.piseth.java.school.phoneshop.service.ReportingService;
import com.piseth.java.school.phoneshop.spec.SaleDetailFilter;
import com.piseth.java.school.phoneshop.spec.SaleDetailSpec;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReportingServiceImpl implements ReportingService{
	private final SaleDetailRepository saleDetailRepository;
	private final ProductRepository productRepository;
	
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
		
		Map<Product, List<SaleDetail>> saleByProductMap = saleDetails.stream()
			.collect(Collectors.groupingBy(SaleDetail::getProduct));
		
		List<Long> productIds = saleDetails.stream()
				.map(sd -> sd.getProduct().getId())
				.toList();
			
			List<Product> products = productRepository.findAllById(productIds);
			Map<Long, Product> productMap = products.stream()
				.collect(Collectors.toMap(Product::getId, Function.identity()));
		
		List<SaleByDateDTO> saleByDateDTOs = new ArrayList<>();
		for(Map.Entry<Product, List<SaleDetail>> entry : saleByProductMap.entrySet()) {
			
			Product product = productMap.get(entry.getKey().getId());
			List<SaleDetail> saleDetailsList = entry.getValue();
			/*
			Integer totalUnit = saleDetailsList.stream()
				.map(SaleDetail::getUnit)
				.reduce((a,b) -> a+b)
				.get();
			*/
			
			Integer totalUnit = saleDetailsList.stream()
				.collect(Collectors.summingInt(SaleDetail::getUnit));
			
			Double amount = saleDetailsList.stream()
				.collect(Collectors.summingDouble(sd -> sd.getAmount().doubleValue()));
			
			
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

}
