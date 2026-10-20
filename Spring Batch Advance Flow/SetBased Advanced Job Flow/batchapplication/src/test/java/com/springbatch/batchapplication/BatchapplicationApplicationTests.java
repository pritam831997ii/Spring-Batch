package com.springbatch.batchapplication;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class BatchapplicationApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Test
	void contextLoads() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<String> tables = jdbcTemplate.queryForList("SHOW TABLES", String.class).stream()
				.map(table -> table.toUpperCase())
				.toList();

		assertTrue(tables.contains("BATCH_JOB_INSTANCE"), "Spring Batch job metadata table was not created");
		assertTrue(tables.contains("BATCH_STEP_EXECUTION"), "Spring Batch step metadata table was not created");
		assertTrue(tables.contains("BATCH_JOB_EXECUTION"), "Spring Batch job execution metadata table was not created");
	}

}
