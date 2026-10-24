package com.example.controller;


import java.util.List;

import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.support.CommandLineJobOperator;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.request.JobsParamRequest;
import com.example.service.JobService;

@RestController
@RequestMapping("/api/job")
public class JobController {
	
	@Autowired
	JobService jobService;
	
	@Autowired
	CommandLineJobOperator jobOperator;
	
	@GetMapping("/start/{jobName}")
	public String startJob(@PathVariable String jobName, @RequestBody List<JobsParamRequest> jobsParamRequestList) {
		
		jobService.startJob(jobName, jobsParamRequestList);
		return "Job Started.... ";
	}
	
	@GetMapping("/stop/{executionId}")
	public String stopJob(@PathVariable long executionId) {
		
		try {
			jobOperator.stop(executionId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Job Stopped";
	}
	
}
