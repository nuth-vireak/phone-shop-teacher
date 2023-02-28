package com.piseth.java.school.phoneshop.spec;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.piseth.java.school.phoneshop.model.ProductImportHistory;
import com.piseth.java.school.phoneshop.model.ProductImportHistory_;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductImportHistorySpec implements Specification<ProductImportHistory>{
	private final ProductImportHistoryFilter importFilter;
	
	List<Predicate> predicates = new ArrayList<>();

	@Override
	public Predicate toPredicate(Root<ProductImportHistory> productImport, CriteriaQuery<?> query, CriteriaBuilder cb) {
		
		if(importFilter.getStartDate() != null) {
			LocalDateTime startDateTime = importFilter.getStartDate().atStartOfDay();
			Predicate startDate = cb.greaterThanOrEqualTo(productImport.get(ProductImportHistory_.DATE_IMPORT), startDateTime);
			predicates.add(startDate);
		}
		
		if(importFilter.getEndDate() != null) {
			LocalDateTime endDateTime = importFilter.getEndDate().atTime(LocalTime.MAX);
			Predicate endDate = cb.lessThanOrEqualTo(productImport.get(ProductImportHistory_.DATE_IMPORT), endDateTime);
			predicates.add(endDate);
		}
				
		Predicate[] predicateArr = predicates.toArray(Predicate[]::new);
		return cb.and(predicateArr);
	}

}
