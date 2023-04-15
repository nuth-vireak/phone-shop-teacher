package com.piseth.java.school.phoneshop.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.piseth.java.school.phoneshop.model.Product;

public class ProductColorValidator implements ConstraintValidator<ValidateProductColor, Product> {
	private final static Long APPLE = 1L;
	private final static List<Long> COLORS = List.of(1L, 2L, 3L);

	@Override
	public boolean isValid(Product product, ConstraintValidatorContext context) {
		Long brandId = product.getModel().getBrand().getId();
		Long colorId = product.getColor().getId();
		if (brandId == APPLE) {
			if (!COLORS.contains(colorId)) {
				return false;
			}
		}
		return true;
	}

}
