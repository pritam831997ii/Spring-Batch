package com.springbatch.config;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.infrastructure.item.database.JdbcBatchItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcCursorItemReader;
import org.springframework.batch.infrastructure.item.database.JdbcPagingItemReader;
import org.springframework.batch.infrastructure.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.FlatFileItemWriter;
import org.springframework.batch.infrastructure.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.infrastructure.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.infrastructure.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.infrastructure.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.infrastructure.item.support.CompositeItemProcessor;
import org.springframework.batch.infrastructure.item.validator.ValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.springbatch.model.OSProduct;
import com.springbatch.model.Product;
import com.springbatch.model.ProductFieldSetMapper;
import com.springbatch.model.ProductRowMapper;
import com.springbatch.model.ProductValidator;
import com.springbatch.procsessor.FilterProductItemProcessor;
import com.springbatch.procsessor.TransformProductItemProcessor;
import com.springbatch.reader.ProductNameItemReader;



@Configuration
public class BatchConfig {
	
	
	@Autowired
	public DataSource dataSource;
	
	
	@Value("${input-file-path-csv}")
	private String filepathscv;

	@Value("${output-file-path-csv}")
	private String outputPath;
	
	
	/*
	 * @Bean public ItemProcessor<Product, OSProduct> productItemProcessor() {
	 * return new ProductItemProcessor(); }
	 */
	
	@Bean
	public ItemProcessor<Product, Product> filterProductItemProcessor() {
		return new FilterProductItemProcessor();	
	}
	
	@Bean
	public ItemProcessor<Product, OSProduct> transformProductItemProcessor() {
		return new TransformProductItemProcessor();	
	}
	
	
	@Bean
	public ValidatingItemProcessor<Product> validateProductItemProcessor() {
		ValidatingItemProcessor<Product> validatingItemProcessor = new ValidatingItemProcessor<>(new ProductValidator());
		validatingItemProcessor.setFilter(true);
		return validatingItemProcessor;
	}
	
	

	@Bean
	public ItemReader<String> itemReader() {
		
		List<String> productList = new ArrayList<>();
		
		productList.add("Product 1");
		productList.add("Product 2");
		productList.add("Product 3");
		productList.add("Product 4");
		productList.add("Product 5");
		productList.add("Product 6");
		productList.add("Product 7");
		productList.add("Product 8");
		productList.add("Product 9");
		productList.add("Product 10");
		productList.add("Product 11");
		productList.add("Product 12");
		
		return new ProductNameItemReader(productList);
		
		
	}
	
	
	
	@Bean
	public ItemReader<Product> flatFileItemReader() {
		
		DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<Product>();

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setNames("product_id", "product_name", "product_category", "product_price");

		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(new ProductFieldSetMapper());
		
		FlatFileItemReader<Product> itemReader = new FlatFileItemReader<>(lineMapper);
		
		
		itemReader.setLinesToSkip(1);
		itemReader.setResource(new FileSystemResource(filepathscv));
		
		return itemReader;
		
	}
	
	
	@Bean
	public ItemReader<Product> jdbcCursorItemReader() {
		return new JdbcCursorItemReader<>(
				dataSource,
				"select * from product_details order by product_id",
				new ProductRowMapper());
	}
	
	
	@Bean
	public ItemReader<Product> jdbcPagingItemReader() throws Exception {
		
		SqlPagingQueryProviderFactoryBean factory = new SqlPagingQueryProviderFactoryBean();
		factory.setDataSource(dataSource);
		factory.setSelectClause("select product_id, product_name, product_category, product_price");
		factory.setFromClause("from product_details");
		factory.setSortKey("product_id");
		
		JdbcPagingItemReader<Product> itemReader = new JdbcPagingItemReader<>(dataSource, factory.getObject());
		itemReader.setRowMapper(new ProductRowMapper());
		itemReader.setPageSize(3);
		
		return itemReader;
	}
	
	
	@Bean
	public FlatFileItemWriter<Product> flatFileItemWriter() {
		DelimitedLineAggregator<Product> lineAggregator = new DelimitedLineAggregator<>();
		lineAggregator.setDelimiter(",");

		BeanWrapperFieldExtractor<Product> fieldExtractor = new BeanWrapperFieldExtractor<>();
		fieldExtractor.setNames(new String[] {"productId", "productName", "productCategory", "productPrice"});
		lineAggregator.setFieldExtractor(fieldExtractor);

		FlatFileItemWriter<Product> itemWriter = new FlatFileItemWriter<>(new FileSystemResource(outputPath), lineAggregator);
		return itemWriter;
	}
	
	
	/*    // JDBC ITEM WRITER 1
	 * @Bean public JdbcBatchItemWriter<Product> jdbcBatchItemWriter() {
	 * 
	 * 
	 * JdbcBatchItemWriter<Product> itemWriter = new JdbcBatchItemWriter<>();
	 * itemWriter.setDataSource(dataSource);
	 * itemWriter.setSql("insert into product_details_output values (?,?,?,?)");
	 * itemWriter.setItemPreparedStatementSetter(new
	 * ProductItemPreparedStatementSetter());
	 * 
	 * return itemWriter; }
	 */
	
	
	
	    // JDBC ITEM WRITER 2
	@Bean
	public JdbcBatchItemWriter<Product> jdbcBatchItemWriter() {
		
		
		JdbcBatchItemWriter<Product> itemWriter = new JdbcBatchItemWriter<>();
		itemWriter.setDataSource(dataSource);
		itemWriter.setSql("insert into product_details_output values (:productId, :productName, :productCategory, :productPrice)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
		
		return itemWriter;
		
	}
	
	@Bean
	public JdbcBatchItemWriter<Product> jdbcBatchItemWriterOsProduct() {
		
		
		JdbcBatchItemWriter<Product> itemWriter = new JdbcBatchItemWriter<>();
		itemWriter.setDataSource(dataSource);
		itemWriter.setSql("insert into os_product_details values (:productId, :productName, :productCategory, :productPrice, :taxPercent, :sku, :shippingRate)");
		itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider());
		
		return itemWriter;
		
	}
	
	
	@Bean
	public CompositeItemProcessor<Product, OSProduct> itemProcessor() {
		
		CompositeItemProcessor<Product, OSProduct> itemProcessor = new CompositeItemProcessor<>();
		
		List itemProcessors = new ArrayList();
		
		itemProcessors.add(validateProductItemProcessor());
		itemProcessors.add(filterProductItemProcessor());
		itemProcessors.add(transformProductItemProcessor());
		
		itemProcessor.setDelegates(itemProcessors);
		
		return itemProcessor;
	}
	
	
	@Bean
	public Step Step3(JobRepository jobRepository, PlatformTransactionManager transactionManager) throws Exception {
		
		
		return new StepBuilder("Chunk Step 3", jobRepository)
				.<Product, OSProduct>chunk(3)
				.reader(jdbcPagingItemReader()) 
				.processor(itemProcessor())
				.writer(jdbcBatchItemWriter())
				.build();
		
	}
	
	
	@Bean
	public Job firstJob(JobRepository jobRepository, Step Step3) {

		return new JobBuilder("JOB-3", jobRepository)
				.preventRestart()
				.start(Step3)
				.build();
		
	}
	
}

