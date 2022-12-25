package com.piseth.java.school.phoneshop.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "brands", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Brand {
	@Id
	@GeneratedValue(generator = "brand_seq_generator")
	@SequenceGenerator(name = "brand_seq_generator", initialValue = 1, sequenceName = "brand_seq")
	private Long id;
	private String name;
	private Boolean active;
	
	
	public Brand(String name){
		this.name = name;
	}
}
