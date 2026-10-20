package com.springbatch.listener;


import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.step.StepExecution;

public class MyStepBatchListener implements StepExecutionListener {
	
	
	public void beforeStep(StepExecution stepExecution) {	
		
	}
	
	
	
	public ExitStatus afterStep(StepExecution stepExecution) {
		
		return new ExitStatus("TEST_STATUS");
	}
	

}
