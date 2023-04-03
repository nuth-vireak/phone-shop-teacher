package com.piseth.java.school.phoneshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.piseth.java.school.phoneshop.model.Brand;
import com.piseth.java.school.phoneshop.model.Model;
import com.piseth.java.school.phoneshop.repository.ModelRepository;
import com.piseth.java.school.phoneshop.service.impl.ModelServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ModelServiceTest {
	@Mock
	private ModelRepository modelRepository;

	@Mock
	private BrandService brandService;

	private ModelService modelService;

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@BeforeEach
	public void setup() {
		modelService = new ModelServiceImpl(modelRepository, brandService);
	}

	@Test
	public void testSaveModel() {
		// given
		Brand brand = new Brand();
		brand.setId(1L);
		brand.setName("Apple");
		Model model = new Model();
		model.setBrand(brand);
		model.setName("Iphone 14 pro");
		// when
		Model modelSave = modelService.save(model);

		// then
		verify(modelRepository, times(1)).save(model);
	}

	@Test
	public void testSaveModelFailValidation() {
		// given
		Brand brand = new Brand();
		brand.setId(1L);
		brand.setName("Apple");
		
		Model model = new Model();
		model.setBrand(brand);
		//model.setName("Iphone 14 pro");
		//when
		
		Set<ConstraintViolation<Model>> constraintViolations = validator.validate(model);
		
		assertEquals(2, constraintViolations.size());
		
		Iterator<ConstraintViolation<Model>> iterator = constraintViolations.iterator();
		
		while(iterator.hasNext()) {
			ConstraintViolation<Model> constraintViolation = iterator.next();
			
			if(constraintViolation.getPropertyPath().toString().equals("yearMade")) {
				assertEquals("{required.field}", constraintViolation.getMessage());
			}
			if(constraintViolation.getPropertyPath().toString().equals("name")) {
				assertEquals("{cannot.be.blank}", constraintViolation.getMessage());
			}
		}
	}

}
