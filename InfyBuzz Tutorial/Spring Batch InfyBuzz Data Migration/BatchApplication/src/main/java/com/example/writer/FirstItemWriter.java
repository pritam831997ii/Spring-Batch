package com.example.writer;



import java.util.List;

import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.example.model.StudentCsv;
import com.example.model.StudentJdbc;
import com.example.model.StudentJson;
import com.example.model.StudentResponse;
import com.example.model.StudentXml;

@Component
public class FirstItemWriter implements ItemWriter<StudentJson>{

	@Override
	public void write(Chunk<? extends StudentJson> chunk) throws Exception {
		System.out.println("Inside Item Writer");
		chunk.getItems().forEach(System.out::println);
	}
	

}
