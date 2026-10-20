package com.springbatch.skipPolicy;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.infrastructure.item.file.FlatFileParseException;
import org.springframework.batch.infrastructure.item.validator.ValidationException;

public class MySkipPolicy implements SkipPolicy{

	@Override
	public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
		
		System.out.println("Skip Count: "+ skipCount);
		if((t instanceof ValidationException || t instanceof FlatFileParseException) && skipCount < 3)
		{
			return true;
		}
		return false;
		
	}

}
