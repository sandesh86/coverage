package com.diffblue.interview.code;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SampleTest {
	
	
	@Test
	public void test_getResponseCode_success() {
		Sample sample = new Sample();
		int response = sample.getResponseCode("Success");
		Assertions.assertEquals(200, response);
	}
	
	@Test
	public void test_getResponseCode_unauthorized() {
		Sample sample = new Sample();
		int response = sample.getResponseCode("Unauthorized");
		Assertions.assertEquals(401, response);
	}
	
	@Test
	public void test_getResponseCode_internalServerError() {
		Sample sample = new Sample();
		int response = sample.getResponseCode("Internal Server Error");
		Assertions.assertEquals(500, response);
	}
	
	@Test
	public void test_getResponseCode_forbidden() {
		Sample sample = new Sample();
		int response = sample.getResponseCode("Forbidden");
		Assertions.assertEquals(403, response);
	}
	
	@Test
	public void test_fibbonacci() {
		Sample sample = new Sample();
		int response = sample.fibbonacci(6);
		Assertions.assertEquals(8, response);
	}
}
