package com.springbatch.listener;

import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.batch.core.annotation.BeforeProcess;
import org.springframework.batch.core.annotation.OnProcessError;

import com.springbatch.model.OSProduct;
import com.springbatch.model.Product;

public class MyItemProcessListener {

	@BeforeProcess
	public void beforeProcess(Product item) {
		System.out.println("beforeProcess() executed for product " + item.getProductId());
	}

	@AfterProcess
	public void afterProcess(Product item, OSProduct result) {
		System.out.println("afterProcess() executed for product " + item.getProductId());
	}

	@OnProcessError
	public void onProcessError(Product item, Exception e) {
		System.out.println("onProcessError() executed for product " + item.getProductId());
	}

}
