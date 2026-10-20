package com.springbatch.procsessor;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;

import com.springbatch.model.Product;

public class FilterProductItemProcessor implements ItemProcessor<Product, Product>{

	@Override
	public @Nullable Product process(Product item) throws Exception {
		
		System.out.println("filtereProductItemProcessor() exexuted for product " + item.getProductId());
		
		/*
		 * if(item.getProductPrice() > 100) { return item; } else { return null; }
		 */
		
		return item;
		
	}

}
