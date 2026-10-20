package com.springbatch.batchapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = { "com.springbatch.controller", "com.springbatch.config" })
public class BatchapplicationApplication {

	
	public static void main(String[] args) {
		
		SpringApplication.run(BatchapplicationApplication.class, args);
		System.out.println("This is a Test");
	}
	
	
}


