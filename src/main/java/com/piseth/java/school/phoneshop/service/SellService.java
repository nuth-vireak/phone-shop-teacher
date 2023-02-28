package com.piseth.java.school.phoneshop.service;

import com.piseth.java.school.phoneshop.dto.SaleDTO;
import com.piseth.java.school.phoneshop.model.Sale;

public interface SellService {
	void sell(SaleDTO saleDTO);
	void cancelSale(Long saleId);
	Sale getById(Long saleId);
}
