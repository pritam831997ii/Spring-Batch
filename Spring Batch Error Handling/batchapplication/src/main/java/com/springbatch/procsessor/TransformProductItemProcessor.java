package com.springbatch.procsessor;

import org.springframework.batch.infrastructure.item.ItemProcessor;

import com.springbatch.model.OSProduct;
import com.springbatch.model.Product;
import com.springbatchexception.MyException;

public class TransformProductItemProcessor implements ItemProcessor<Product, OSProduct>{

	@Override
	public OSProduct process(Product item) throws Exception {
		
		System.out.println("transformProductItemProcessor() executed for product " + item.getProductId());
		
		OSProduct osProduct=new OSProduct();
		
		osProduct.setProductId(item.getProductId());
		osProduct.setProductName(item.getProductName());
		osProduct.setProductCategory(item.getProductCategory());
		osProduct.setProductPrice(item.getProductPrice());
		
		osProduct.setTaxPercent(item.getProductCategory().equals("Sports Accessories") ? 5 : 12);
		osProduct.setSku(item.getProductCategory().substring(0,3) + item.getProductId());
		osProduct.setShippingRate(item.getProductPrice() < 1000 ? 75 : 0);
		
		if(item.getProductPrice() == 500) {
			
			System.out.println("Exception Thrown");
			throw new MyException("Test Exception");
		}
		
		return osProduct;
		
	}

}
