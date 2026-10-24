package com.example.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.CommandLineJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.database.JpaCursorItemReader;
import org.springframework.batch.infrastructure.item.database.JpaItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.postgresql.entity.Student;
import com.example.processor.FirstItemProcessor;
import com.example.reader.FirstItemReader;
import com.example.writer.FirstItemWriter;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class SampleJob {
	
	
	@Autowired
	@Qualifier("datasource")
	private DataSource datasource;
	
	@Autowired
	@Qualifier("universitydatasource")
	private DataSource universitydatasource;
	
	@Autowired
	@Qualifier("postgresqlEntityManagerFactory")
	private EntityManagerFactory postgresqlEntityManagerFactory;
	
	@Autowired
	@Qualifier("mysqlEntityManagerFactory")
	private EntityManagerFactory mysqlEntityManagerFactory;
	
	
	
	/*
	 * @Value("${batch.job.current-item-count}") private int currentItemCount;
	 * 
	 * @Value("${batch.job.max-item-count}") private int maxItemCount;
	 */
	
	
	@Autowired
	private JpaTransactionManager jpaTransactionManager;

	
	@Bean
	public FirstItemReader firstItemReader() {
		return new FirstItemReader();
	}

	@Bean
	public FirstItemProcessor firstItemProcessor() {
		return new FirstItemProcessor();
	}

	@Bean
	public FirstItemWriter firstItemWriter() {
		return new FirstItemWriter();
	}

	@Bean
	public JobRegistry jobRegistry() {
		return new MapJobRegistry();
	}

	@Bean
	public CommandLineJobOperator commandLineJobOperator(JobOperator jobOperator,
			JobRepository jobRepository, JobRegistry jobRegistry) {
		return new CommandLineJobOperator(jobOperator, jobRepository, jobRegistry);
	}

	
	@Bean
	public Job job(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		
		
		return new JobBuilder("Job3", jobRepository)
				.start(firstChunkStep(jobRepository, transactionManager))
				.build();
	}
	
	@Bean
	public CommandLineRunner runJob(CommandLineJobOperator commandLineJobOperator,
			ApplicationArguments applicationArguments) {
	    return args -> {
	        var currentItemCountValues = applicationArguments.getOptionValues("currentItemCount");
	        var maxItemCountValues = applicationArguments.getOptionValues("maxItemCount");

	        if (currentItemCountValues.isEmpty() || maxItemCountValues.isEmpty()) {
	            throw new IllegalArgumentException(
	                    "Both currentItemCount and maxItemCount must be provided as application arguments");
	        }

	        Properties jobParameters = new Properties();
	        jobParameters.setProperty("currentItemCount", currentItemCountValues.get(0));
	        jobParameters.setProperty("maxItemCount", maxItemCountValues.get(0));

	        commandLineJobOperator.start("Job3", jobParameters);
	    };
	}
	
	
	
	private Step firstChunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		
		
		return new StepBuilder("First Chunk Step", jobRepository)
				.<Student, com.example.mysql.entity.Student>chunk(100)
				.reader(jpaCursorItemReader(null,null)) 
				.processor(firstItemProcessor()) 
				.writer(jpaItemWriter()) 
				.transactionManager(jpaTransactionManager)
				.build();
	}

	
	
	
	@StepScope
	@Bean
	public JpaCursorItemReader<Student> jpaCursorItemReader(
			@Value("#{jobParameters['currentItemCount']}") Integer currentItemCount,
			@Value("#{jobParameters['maxItemCount']}") Integer maxItemCount) {
		JpaCursorItemReader<Student> jpaCursorItemReader = 
				new JpaCursorItemReader<Student>(postgresqlEntityManagerFactory);
		
		jpaCursorItemReader.setEntityManagerFactory(postgresqlEntityManagerFactory);
		
		jpaCursorItemReader.setQueryString("From Student");
		
		jpaCursorItemReader.setCurrentItemCount(currentItemCount);
		jpaCursorItemReader.setMaxItemCount(maxItemCount);
		
		return jpaCursorItemReader;
	}
	
	
	

	/*
	 * public JpaCursorItemReader<Student> jpaCursorItemReader() {
	 * 
	 * JpaCursorItemReader<Student> reader = new
	 * JpaCursorItemReaderBuilder<Student>() .name("jpaCursorItemReader")
	 * .entityManagerFactory(postgresqlEntityManagerFactory)
	 * .queryString("From Student") .build();
	 * reader.setCurrentItemCount(currentItemCount);
	 * reader.setMaxItemCount(maxItemCount); return reader; }
	 */
	
	
	
	
	
	public JpaItemWriter<com.example.mysql.entity.Student> jpaItemWriter() {
		
		
		JpaItemWriter<com.example.mysql.entity.Student> jpaItemWriter = 
				new JpaItemWriter<com.example.mysql.entity.Student>(mysqlEntityManagerFactory);
		
		jpaItemWriter.setEntityManagerFactory(mysqlEntityManagerFactory);
		
		return jpaItemWriter;
	}
	
	
	
	/*   TO TRUNCATE DATA FROM UNIVERSITY.STUDENT IN MYSQL DATABASE
	 * USE university;
	 * 
	 * SET SQL_SAFE_UPDATES = 0;
	 * 
	 * DELETE FROM subjects_learning; DELETE FROM student;
	 * 
	 * SET SQL_SAFE_UPDATES = 1;
	 */

	[
   {
  "paramKey": "ABC",
  "paramValue": "123"
}
]
	
	
}




