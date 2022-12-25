package com.piseth.java.school.phoneshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Table(name = "products", 
uniqueConstraints = {@UniqueConstraint(columnNames = {"model_id", "color_id"})})
@Data
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "{cannot.be.blank}")
	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "model_id")
	private Model model;

	@ManyToOne
	@JoinColumn(name = "color_id")
	private Color color;

	@Column(name = "sale_price")
	private Double salePrice;

	@Column(name = "available_unit")
	private Integer availableUnit;

	@Column(name = "image_path")
	private String imagePath;

}
