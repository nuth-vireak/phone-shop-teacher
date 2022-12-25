package com.piseth.java.school.phoneshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.piseth.java.school.phoneshop.model.Brand;

@DataJpaTest
public class BrandRepositoryTest {
	
	@Autowired
	private BrandRepository brandRepository;
	
	/*
	@BeforeEach
	public void setUp() {
		brandRepository.deleteAll();
	}
	*/
	
	@Test
	public void testExistsByName() {
		//given
		Brand brand = new Brand();
		brand.setName("Nokia");
		brandRepository.save(brand);
		//when
		boolean existsByName = brandRepository.existsByName("Nokia");
		boolean existByName2 = brandRepository.existsByName("Blackberry");
		
		//then
		//assertEquals(true, existsByName);
		assertTrue(existsByName);
		assertFalse(existByName2);
	}
	
	@Test
	public void findByIdIn() {
		
		Brand brand1 = new Brand("Apple");
		Brand brand2 = new Brand("Samsung");
		brandRepository.save(brand1);
		brandRepository.save(brand2);
		List<Brand> brands = brandRepository.findByIdIn(List.of(1L,2L));
		
		assertEquals(2, brands.size());
		assertEquals(1, brands.get(0).getId());
		assertEquals("Apple", brands.get(0).getName());
		
		assertEquals(2, brands.get(1).getId());
		assertEquals("Samsung", brands.get(1).getName());
		
	}
	
}
