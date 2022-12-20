package com.piseth.java.school.phoneshop.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;

import com.piseth.java.school.phoneshop.exception.ApiException;
import com.piseth.java.school.phoneshop.model.Brand;
import com.piseth.java.school.phoneshop.repository.BrandRepository;
import com.piseth.java.school.phoneshop.service.impl.BrandServiceImpl;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BrandServiceTest {
	
	@Mock
	private BrandRepository brandRepository;
	
	private BrandService brandService;
	
	@Captor
	private ArgumentCaptor<Brand> brandCapture;
	
	private Brand brand;
	
	@BeforeEach
	public void setup() {
		brandService = new BrandServiceImpl(brandRepository);
		brand = new Brand(1,"Apple", true);
		when(brandRepository.findById(1)).thenReturn(Optional.of(brand));
	}
	
	@Test
	public void testSavebrand() {
		
		//given
		Brand brand = new Brand();
		brand.setName("Apple");
		
		Brand brandReturn = brandService.save(brand);
		
		//then
		verify(brandRepository, times(1)).save(brand);
		
	}
	
	@Test
	public void getByIdSuccess() {
		//given
		//when
		
		//then
		Brand brandReturn = brandService.getById(1);
		assertNotNull(brandReturn);
		assertEquals("Apple", brandReturn.getName());
		assertEquals(1, brandReturn.getId());
	}
	
	@Test
	public void getByIdThrowException() {
		
		//given
		
		//when
		when(brandRepository.findById(2)).thenReturn(Optional.empty());
		
		//then
		
		assertThatThrownBy(() -> brandService.getById(2))
		.isInstanceOf(ApiException.class)
		.hasMessageStartingWith("brand not found for id=");
			
	}
	
	@Test
	public void testUpdateBrand() {
		//given
		Brand brandUpdate = new Brand(1, "Apple V2", true);
		//when
		Brand brandAfterUpdate = brandService.update(1, brandUpdate);
		
		
		//then
		//verify(brandRepository, times(1)).findById(1);
		verify(brandRepository, atMostOnce()).findById(1);
		verify(brandRepository).save(brandCapture.capture());
		assertEquals(brandCapture.getValue().getName(), brandUpdate.getName());
		
	}
	
	@Test
	public void testDeleteBrand() {
		//given 
		Integer brandToDelete = 1;
		// when
		brandService.delete(brandToDelete);
		//then
		verify(brandRepository).save(brandCapture.capture());
		assertEquals(false, brandCapture.getValue().getActive());
		
		verify(brandRepository, times(1)).save(brand);
	}
	
	@Test
	public void testListBrand() {
		//given
		List<Brand> brands = List.of(
				new Brand(1, "Apple", true),
				new Brand(2, "Samsung", true)
				);
		//when
		when(brandRepository.findByActiveTrue()).thenReturn(brands);
		List<Brand> brandsReturn = brandService.getBrands();
		
		//then
		assertEquals(2, brandsReturn.size());
		assertEquals("Apple", brandsReturn.get(0).getName());
		assertEquals("Samsung", brandsReturn.get(1).getName());
	}

}

