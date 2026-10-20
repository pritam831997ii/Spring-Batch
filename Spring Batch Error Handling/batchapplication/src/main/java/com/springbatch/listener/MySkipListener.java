package com.springbatch.listener;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.batch.core.listener.SkipListener;
import org.springframework.batch.infrastructure.item.file.FlatFileParseException;

import com.springbatch.model.OSProduct;
import com.springbatch.model.Product;

public class MySkipListener implements SkipListener<Product, OSProduct> {

	@Override
	public void onSkipInRead(Throwable t) {
		
		if(t instanceof FlatFileParseException) {
			System.out.println("Skipped Item:- ");
			System.out.println(((FlatFileParseException)t).getInput());
			writeToFile(((FlatFileParseException)t).getInput());
		}
	}

	@Override
	public void onSkipInWrite(OSProduct item, Throwable t) {
		// TODO Auto-generated method stub
		SkipListener.super.onSkipInWrite(item, t);
	}

	@Override
	public void onSkipInProcess(Product item, Throwable t) {
		System.out.println("Skipped Item:- ");
		System.out.println(item);
		writeToFile(item.toString());
	}
	
	public void writeToFile(String data) {
		try {
			FileWriter fileWriter = new FileWriter("rejected/Product_Details_Rejected.txt", true);
			fileWriter.write(data + "\n");
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
}
