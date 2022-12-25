package com.piseth.java.school.phoneshop.service;

import com.piseth.java.school.phoneshop.model.Color;

public interface ColorService {
	Color save(Color entity);

	Color getById(Long id);

}
