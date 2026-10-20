package com.springbatch.model;

import java.util.Arrays;

import java.util.List;

import org.springframework.batch.infrastructure.item.validator.ValidationException;
import org.springframework.batch.infrastructure.item.validator.Validator;



public class ProductValidator implements Validator<Product>{

	List<String> validProductCategories = Arrays.asList("Mobile Phones", "Tablets", "Televisions", "Sports Accessories");

	@Override
	public void validate(Product value) throws ValidationException {
		
		
		if(!validProductCategories.contains(value.getProductCategory())) {
			throw new ValidationException("Invalid Product Category");
		}
		if(value.getProductPrice() > 100000) {
			throw new ValidationException("Invalid Product Price");	
		}
	}

}
