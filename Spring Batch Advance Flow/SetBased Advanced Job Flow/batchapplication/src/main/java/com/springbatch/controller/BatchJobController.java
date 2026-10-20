package com.springbatch.controller;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchJobController {
	
	  	@Autowired
	    private JobOperator jobOperator;

	    @Autowired
	    @Qualifier("deciderjob")
	    private Job job;

	    @GetMapping("/launchJob/{id}")
	    public void handle(@PathVariable("id") String id) throws Exception {

	        JobParameters jobParameters = new JobParametersBuilder()
	                .addString("param", id)
	                .toJobParameters();

	        jobOperator.start(job, jobParameters);
	        
	        System.out.println("JOB HAS STARTED ... !");
	    }

}
