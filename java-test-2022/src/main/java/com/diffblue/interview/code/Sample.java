package com.diffblue.interview.code;

public class Sample {

	
	public int fibbonacci(int n) {
		if (n <= 1)
			return n;
		return fibbonacci(n - 1) + fibbonacci(n - 2);
	}
	
	public int getResponseCode(String status) {
		if ("Success".equals(status)) {
			return 200;
		}
		else if ("Internal Server Error".equals(status)) {
			return 500;
		}
		else if ("Unauthorized".equals(status)) {
			return 401;
		}
		else if ("Bad Request".equals(status)) {
			return 400;
		}
		else if ("Forbidden".equals(status)) {
			return 403;
		}
		else {
			return 0;
		}
	}
}
