package com.example.service;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class JobSchedular {
	
	@Autowired
	JobOperator jobOperator;
	
	
	@Autowired
	JobRegistry jobRegistry;
	
	@Autowired
	Job job;
	
	//@Scheduled(cron="*/3 * * * * *")
	public void TheJobSchedular() {
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
				.addLong("startAt", System.currentTimeMillis());
		
		
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("startAt", System.currentTimeMillis())
				.toJobParameters();
		
		try {
			
			
			
				JobExecution jobExecution = jobOperator.run(job, jobParameters);
	 
				System.out.println("Job Execution ID = " + jobExecution.getId());
			
		}
		catch(Exception e) {
			System.out.println( "Failed to start the job: "+e.getMessage());
		}	
		
	}
}
