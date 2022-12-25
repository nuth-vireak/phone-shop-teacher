package com.piseth.java.school.phoneshop.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;

import lombok.Data;

@Entity
@Table(name = "product_import_histories")
@Data
public class ProductImportHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	@Column(name = "date_import")
	private LocalDate dateImport;
	
	@DecimalMin(value = "0.000001")
	@Column(name = "price_per_unit")
	private BigDecimal pricePerUnit;
	
	@Column(name = "import_unit")
	private Integer importUnit;
}
