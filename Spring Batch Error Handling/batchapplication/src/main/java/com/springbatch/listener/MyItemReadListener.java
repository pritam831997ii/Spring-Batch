package com.springbatch.listener;

import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.BeforeRead;
import org.springframework.batch.core.annotation.OnReadError;

import com.springbatch.model.Product;

public class MyItemReadListener {

	@BeforeRead
	public void beforeRead() {
		System.out.println("beforeRead() executed");
	}

	@AfterRead
	public void afterRead(Product item) {
		System.out.println("afterRead() executed for product " + item.getProductId());
	}

	@OnReadError
	public void onReadError(Exception ex) {
		System.out.println("onReadError() executed");
	}

}
