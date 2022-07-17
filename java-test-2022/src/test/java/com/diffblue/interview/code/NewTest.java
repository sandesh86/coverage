package com.diffblue.interview.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NewTest {

	@Test
	public void test_sample() {
		Sample sample = new Sample();
		int response = sample.getResponseCode("Bad Request");
		Assertions.assertEquals(400, response);
	}
}
