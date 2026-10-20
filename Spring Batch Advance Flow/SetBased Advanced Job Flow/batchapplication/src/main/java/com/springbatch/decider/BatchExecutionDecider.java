package com.springbatch.decider;

import org.jspecify.annotations.Nullable;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.step.StepExecution;




public class BatchExecutionDecider implements JobExecutionDecider {

	
	
	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, @Nullable StepExecution stepExecution) {
		
		String sk1 = jobExecution.getExecutionContext().getString("sk1");
		String sk2 = jobExecution.getExecutionContext().getString("sk2");
		String exitStatus = "";
		if ("ABC".equals(sk1) && "KLM".equals(sk2)) {
			exitStatus = "STEP_3";
		} else if ("ABC".equals(sk1) && "TUV".equals(sk2)) {
			exitStatus = "STEP_4";
		} else {
			exitStatus = "STEP_5";
		}
		return new FlowExecutionStatus(exitStatus);
	}

}
