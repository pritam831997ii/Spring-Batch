package com.springbatch.listener;

import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.AfterChunkError;
import org.springframework.batch.core.annotation.BeforeChunk;

public class MyChunkListener {
	
	
	@BeforeChunk
	public void beforeChunk() {
		System.out.println("beforeChunk() executed");
	}

	@AfterChunk
	public void afterChunk() {
		System.out.println("afterChunk() executed");
	}

	@AfterChunkError
	public void afterChunkError() {
		System.out.println("afterChunkError() executed");
	}

}
