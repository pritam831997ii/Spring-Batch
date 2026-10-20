package com.springbatch.config;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.item.ExecutionContext;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.decider.BatchExecutionDecider;
import com.springbatch.listener.MyJobExecutionListener;
import com.springbatch.listener.MyStepBatchListener;


@Configuration
public class BatchConfig {
	
	
	
	@Bean
	public MyJobExecutionListener myJobExecutionListener() {
		return new MyJobExecutionListener();
	}
	
	@Bean
	public MyStepBatchListener myStepBatchListener() {
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
				System.out.println("step 1 executed!! " + Thread.currentThread().getName());
				
				ExecutionContext jobExecutionContext = chunkContext
						.getStepContext()
						.getStepExecution()
						.getJobExecution()
						.getExecutionContext();
				
				System.out.println("Job Execution Context: " + jobExecutionContext);
				jobExecutionContext.put("sk1", "ABC");
				
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
				
				System.out.println("step 2 executed!! " + Thread.currentThread().getName());
				ExecutionContext jobExecutionContext = chunkContext
						.getStepContext()
						.getStepExecution()
						.getJobExecution()
						.getExecutionContext();
				
				System.out.println("Job Execution Context: " + jobExecutionContext);
				jobExecutionContext.put("sk2", "PRQ");
				
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
				System.out.println("step 3 executed!! " + Thread.currentThread().getName());
				
				ExecutionContext jobExecutionContext = chunkContext
						.getStepContext()
						.getStepExecution()
						.getJobExecution()
						.getExecutionContext();
				
				System.out.println("Job Execution Context: " + jobExecutionContext);
				return RepeatStatus.FINISHED;
			}
		};

		return new StepBuilder("step3", jobRepository)
				.tasklet(tasklet, transactionManager)
				.listener(myStepBatchListener())
				.build();
	}
	
	
	
	@Bean
	public Step Step4(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		
		Tasklet tasklet = new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("step 4 executed!! " + Thread.currentThread().getName());
				return RepeatStatus.FINISHED;
			}
		};

		return new StepBuilder("step4", jobRepository)
				.tasklet(tasklet, transactionManager)
				.build();
	}
	
	
	
	@Bean
	public Step Step5(JobRepository jobRepository, PlatformTransactionManager transactionManger) {
		
		return new StepBuilder("step5", jobRepository).tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("step5 executed!! " + Thread.currentThread().getName());
				return RepeatStatus.FINISHED;
			}
		}, transactionManger).build();
		
	}
	
	
	
	@Bean
	public Step Step6(JobRepository jobRepository, PlatformTransactionManager transactionManger) {
		
		return new StepBuilder("step6", jobRepository).tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("step6 executed!! " + Thread.currentThread().getName());
				return RepeatStatus.FINISHED;
			}
		}, transactionManger).build();
		
	}
	
	
	@Bean
	public Step Step7(JobRepository jobRepository, PlatformTransactionManager transactionManger) {
		
		return new StepBuilder("step7", jobRepository).tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("step7 executed!! " + Thread.currentThread().getName());
				return RepeatStatus.FINISHED;
			}
		}, transactionManger).build();
		
	}
	
	
	@Bean
	public Step Step8(JobRepository jobRepository, PlatformTransactionManager transactionManger) {
		
		return new StepBuilder("step8", jobRepository).tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("step8 executed!! " + Thread.currentThread().getName());
				return RepeatStatus.FINISHED;
			}
		}, transactionManger).build();
		
	}

	
	
	
	/*
	 * @Bean public Job firstJob(JobRepository jobRepository, Step Step1, Step
	 * Step2, Step Step3) {
	 * 
	 * return new JobBuilder("JOB-3", jobRepository) .preventRestart() .start(Step1)
	 * .next(Step2) .next(Step3) .build(); }
	 */
	
	
	
	
	/*
	 * @Bean public Job firstJob(JobRepository jobRepository, Step Step1, Step
	 * Step2, Step Step3, Step Step4) {
	 * 
	 * return new JobBuilder("JOB-3", jobRepository)
	 * 
	 * .start(Step1) .on("COMPLETED").to(Step2) .from(Step2)
	 * .on("COMPLETED").to(Step3) .end() .build(); }
	 */
	
	
	/*
	 * @Bean public Job firstJob(JobRepository jobRepository, Step Step1, Step
	 * Step2, Step Step3, Step Step4) {
	 * 
	 * return new JobBuilder("JOB-3", jobRepository)
	 * 
	 * .start(Step1) .on("COMPLETED").to(Step2) .from(Step2)
	 * .on("TEST_STATUS").to(Step3) .from(Step2) .on("*").to(Step4) .end() .build();
	 * }
	 */
	
	@Bean
	public Flow flow1(@Qualifier("Step3") Step step3, @Qualifier("Step4") Step step4) {
		FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow1");
		flowBuilder.start(step3)
				.next(step4)
				.end();
		return flowBuilder.build();
	}
	
	
	
	@Bean
	public Flow flow2(@Qualifier("Step5") Step step5, @Qualifier("Step6") Step step6) {
		FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow2");
		flowBuilder.start(step5)
				.next(step6)
				.end();
		return flowBuilder.build();
	}
	
	
	@Bean
	public Flow flow3(@Qualifier("Step7") Step step7, @Qualifier("Step8") Step step8) {
		FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow3");
		flowBuilder.start(step7)
				.next(step8)
				.end();
		return flowBuilder.build();
	}
	
	
	@Bean
	public Flow splitFlow(@Qualifier("flow1") Flow flow1, @Qualifier("flow2") Flow flow2, @Qualifier("flow3") Flow flow3) {
		return new FlowBuilder<Flow>("splitFlow")
				.split(new SimpleAsyncTaskExecutor())
				.add(flow1, flow2, flow3)
				.build();
	}
	
	
	@Bean
	public Job deciderjob(JobRepository jobRepository, @Qualifier("Step1") Step Step1, @Qualifier("Step2") Step Step2, 
			@Qualifier("Step3")Step Step3, 
			@Qualifier("Step4")Step Step4, @Qualifier("Step5") Step Step5) {
		
		
		return new JobBuilder("job1", jobRepository)
				.listener(myJobExecutionListener())
				.start(Step1)
				.next(Step2)
				.next(batchExecutionDecider())
					.on("STEP_3").to(Step3)
				.from(batchExecutionDecider())
					.on("STEP_4").to(Step4)
				.from(batchExecutionDecider())
					.on("STEP_5").to(Step5)
				.end()
				.build();
	}
	
	@Bean
	public Job Job_1(JobRepository jobRepository, @Qualifier("Step1") Step Step1,
			@Qualifier("Step2") Step Step2, @Qualifier("Step3") Step Step3) {
		
		return new JobBuilder("JOB-3", jobRepository) 
				.listener(myJobExecutionListener())
				.start(Step1)
				.next(Step2)
				.next(Step3)
				.build();
	}
	
	

	@Bean
	public Step job3Step(JobRepository jobRepository, @Qualifier("Job_3") Job job3) {
		return new StepBuilder("job3Step", jobRepository)
				.job(job3)
				.build();
	}
	
	
	

	/*
	 * @Bean public Job Job_2(JobRepository jobRepository, Step step5, Step step6,
	 * Flow flow1) { return new JobBuilder("job2", jobRepository) .start(flow1)
	 * .next(step5) .next(step6) .end() .build(); }
	 */
	
	/*
	 * @Bean public Job Job_2(JobRepository jobRepository, @Qualifier("job3Step")
	 * Step job3Step, Flow flow1) { return new JobBuilder("job2", jobRepository)
	 * .start(flow1) .next(job3Step) .end() .build(); }
	 */
	
	@Bean
	public Job Job_2(JobRepository jobRepository, @Qualifier("job3Step") Step job3Step, Flow splitFlow) {
		return new JobBuilder("job2", jobRepository)
				.listener(myJobExecutionListener())
				.start(splitFlow)
				.end()
				.build();
	}
	
	
	
	@Bean
	public Job Job_3(JobRepository jobRepository, @Qualifier("Step5") Step step5, @Qualifier("Step6") Step step6) {
		return new JobBuilder("job3", jobRepository)
				.start(step5)
				.next(step6)
				.build();
	}
	
}
