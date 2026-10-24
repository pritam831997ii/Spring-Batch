package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameter;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.request.JobsParamRequest;

@Service
public class JobService {
	
	@Autowired
	JobOperator jobOperator;
	
	
	@Autowired
	JobRegistry jobRegistry;

	@Autowired
	@Qualifier("universitydatasource")
	private DataSource universityDataSource;
	
	@Async
	public void startJob(String jobName, List<JobsParamRequest> jobsParamRequestList) {
		
		JobParametersBuilder jobParametersBuilder = new JobParametersBuilder()
				.addLong("startAt", System.currentTimeMillis());
		
		
		jobsParamRequestList.stream().forEach(jobParam ->
		jobParametersBuilder.addString(jobParam.getParamKey(), jobParam.getParamValue()));
		
		
		
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("startAt", System.currentTimeMillis())
				.toJobParameters();
		
		try {
				
				Job job = jobRegistry.getJob(jobName);
				
				if (job == null) {
					System.out.println("No job registered with the name '" + jobName
							+ "'. Available job names: " + jobRegistry.getJobNames());
					return;
				}

				if ("Job3".equals(jobName)) {
					int deletedRows = new JdbcTemplate(universityDataSource)
							.update("delete from student");
					System.out.println("Cleared student rows before Job3: " + deletedRows);
				}
	 
				JobExecution jobExecution = jobOperator.run(job, jobParameters);
	 
				System.out.println("Job Execution ID = " + jobExecution.getId());
	 
				
			
		}
		catch(Exception e) {
			System.out.println( "Failed to start the job: "+e.getMessage());
		}	
	}
}




