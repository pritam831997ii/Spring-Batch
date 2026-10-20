package com.springbatch.reader;

import java.util.Iterator;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemReader;



public class ProductNameItemReader implements ItemReader<String> {

	
	private Iterator<String> productListIterator;

	
	public ProductNameItemReader(List<String> productList) {
		this.productListIterator = productList.iterator();
	}
	
	
	@Override
	public @Nullable String read() throws Exception {
		
		return this.productListIterator.hasNext() ? this.productListIterator.next() : null;

	}
	
}
