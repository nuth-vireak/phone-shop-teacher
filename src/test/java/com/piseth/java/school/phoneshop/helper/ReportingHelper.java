package com.piseth.java.school.phoneshop.helper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.piseth.java.school.phoneshop.model.Brand;
import com.piseth.java.school.phoneshop.model.Color;
import com.piseth.java.school.phoneshop.model.Model;
import com.piseth.java.school.phoneshop.model.Product;
import com.piseth.java.school.phoneshop.model.Sale;
import com.piseth.java.school.phoneshop.model.SaleDetail;

public class ReportingHelper {
	private static final Sale sale1 = new Sale(1L, LocalDateTime.now().minusDays(-5), true);
	private static final Sale sale2 = new Sale(2L, LocalDateTime.now().minusDays(-2), true);
	private static final Brand brand1 = new Brand(1L, "Apple", true);
	private static final Model model1 = new Model(1L, "iphone 14 pro", brand1, 2014);
	private static final Color color1 = new Color(1l, "Red");
	private static final Color color2 = new Color(2l, "White");
	private static final Product product1 = Product.builder()
			.id(1L)
			.name("iphone 14 pro red")
			.availableUnit(10)
			.color(color1)
			.model(model1)
			.salePrice(BigDecimal.valueOf(1500))
			.build();
	
	private static final Product product2 = Product.builder()
			.id(2L)
			.name("iphone 14 pro white")
			.availableUnit(10)
			.color(color2)
			.model(model1)
			.salePrice(BigDecimal.valueOf(1500))
			.build();
	
	public static List<SaleDetail> getSaleDetails() {
		SaleDetail detail1 = SaleDetail.builder()
				.id(1L)
				.sale(sale1)
				.amount(BigDecimal.valueOf(200))
				.product(product1)
				.unit(5)
				.build();
		
		SaleDetail detail2 = SaleDetail.builder()
				.id(2L)
				.sale(sale1)
				.amount(BigDecimal.valueOf(300))
				.product(product2)
				.unit(10)
				.build();
		
		SaleDetail detail3 = SaleDetail.builder()
				.id(3L)
				.sale(sale2)
				.amount(BigDecimal.valueOf(300))
				.product(product2)
				.unit(3)
				.build();
		
		return List.of(detail1, detail2, detail3);
	}
	
	public static List<Product> getProducts(){
		return List.of(product1, product2);
	}
}
