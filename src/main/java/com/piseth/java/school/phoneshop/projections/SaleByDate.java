package com.piseth.java.school.phoneshop.projections;

import java.time.LocalDate;

public interface SaleByDate {
	LocalDate getSoldDate();

	Long getProductId();

	String getProductName();

	Long getTotalUnit();

	Double getAmount();
}
