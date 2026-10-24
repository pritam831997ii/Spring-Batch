package com.example.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.SkipListener;
import org.springframework.batch.infrastructure.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.model.StudentCsv;
import com.example.model.StudentJson;


@Component
public class StudentSkipListener implements SkipListener<StudentCsv, StudentJson> {

	private static final Logger log = LoggerFactory.getLogger(StudentSkipListener.class);

	@Value("${batch.file-Path-To-Skip-Read}")
	String filePathRead;

	@Value("${batch.file-Path-To-Skip-Process}")
	String filePathProcess;

	@Value("${batch.file-Path-To-Skip-Write}")
	String filePathWrite;

	@Override
	public void onSkipInRead(Throwable th) {

		log.warn("Skipped record while reading: {}", th.toString());

		if (th instanceof FlatFileParseException ex) {
			createFile(filePathRead, ex.getInput() + " | line=" + ex.getLineNumber() + " | reason="
					+ (ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage()));
		} else {
			createFile(filePathRead, "UNKNOWN READ ERROR | reason=" + th);
		}
	}

	@Override
	public void onSkipInProcess(StudentCsv studentCsv, Throwable th) {

		log.warn("Skipped record while processing: {} - {}", studentCsv, th.toString());

		createFile(filePathProcess, studentCsv + " | reason=" + th.getMessage());
	}

	@Override
	public void onSkipInWrite(StudentJson studentJson, Throwable th) {

		log.warn("Skipped record while writing: {} - {}", studentJson, th.toString());

		createFile(filePathWrite, studentJson + " | reason=" + th.getMessage());
	}

	public void createFile(String filepath, String data) {

		File file = new File(filepath);

		File parent = file.getParentFile();
		if (parent != null && !parent.exists() && !parent.mkdirs()) {
			log.error("Could not create directory {}", parent.getAbsolutePath());
			return;
		}

		try (FileWriter filewriter = new FileWriter(file, true)) {
			filewriter.write(data + " | " + new Date() + System.lineSeparator());
			log.info("Failed record written to {}", file.getAbsolutePath());
		} catch (IOException e) {
			log.error("Could not write skipped record to {}", file.getAbsolutePath(), e);
		}
	}
}
