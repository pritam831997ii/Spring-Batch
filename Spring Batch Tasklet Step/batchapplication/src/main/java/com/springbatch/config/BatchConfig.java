package com.springbatch.config;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.listener.StepExecutionListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.decider.BatchExecutionDecider;
import com.springbatch.listener.MyStepBatchListener;


@Configuration
public class BatchConfig {
	
	
	
	@Bean
	public StepExecutionListener myStepBatchListener() {
		return new MyStepBatchListener();
	}
	 
	
	@Bean
	public BatchExecutionDecider batchExecutionDecider() {
		return new BatchExecutionDecider();
	}
	
	@Bean
	public Step Step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		
		
		Tasklet tasklet = new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("step 1 executed!!");
				return RepeatStatus.FINISHED;
			}
		};

		return new StepBuilder("step1", jobRepository)
				.tasklet(tasklet, transactionManager)
				.build();
	}
	
	@Bean
	public Step Step2(JobRepository jobRepository, PlatformTransactionManager transactionManager)  {
		
		
		
		
		Tasklet tasklet = new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				
				boolean flag=false;
				
				if(flag)
					throw new Exception("TEST EXCEPTION");
				System.out.println("step 2 executed!!");
				return RepeatStatus.FINISHED;
			}
		};

		return new StepBuilder("step2", jobRepository)
				.tasklet(tasklet, transactionManager)
				//.listener(myStepBatchListener())
				.build();
	}
	
	@Bean
	public Step Step3(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		
		
		Tasklet tasklet = new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("step 3 executed!!");
				return RepeatStatus.FINISHED;
			}
		};

		return new StepBuilder("step3", jobRepository)
				.tasklet(tasklet, transactionManager)
				.build();
	}
	
	
	@Bean
	public Step Step4(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		
		
		Tasklet tasklet = new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("step 4 executed!!");
				return RepeatStatus.FINISHED;
			}
		};

		return new StepBuilder("step4", jobRepository)
				.tasklet(tasklet, transactionManager)
				.build();
	}

	
	//	@Bean
//	public Job firstJob(JobRepository jobRepository, Step Step1, Step Step2) {
//
//		return new JobBuilder("JOB-3", jobRepository)
//				//.preventRestart()
//				.start(Step1)
//				.next(Step2)
//				.build();
//		
//	}
	
	
//	@Bean
//	public Job firstJob(JobRepository jobRepository, Step Step1, Step Step2, Step Step3) {
//		
//		return new JobBuilder("JOB-3", jobRepository)
//				.start(Step1)
//					.on("COMPLETED").to(Step2)
//				.from(Step2)
//					.on("TESTSTATUS").to(Step3)
//				.end()
//				.build();
//	}
	
	
	@Bean
	public Job firstJob(
			JobRepository jobRepository,
			@Qualifier("Step1") Step step1,
			@Qualifier("Step2") Step step2,
			@Qualifier("Step3") Step step3,
			JobExecutionDecider batchExecutionDecider) {

		return new JobBuilder("JOB-3", jobRepository)

				.start(step1)
					.on("COMPLETED").to(batchExecutionDecider())
						.on("TESTSTATUS").to(step2)
					.from(batchExecutionDecider())
					     .on("*").to(step3)
				.end() 
				.build();
		
	}
	
	
}
