package com.diffblue.interview.code;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.diffblue.interview.CodeClass;

public class FileUtilsTest {
	
	@Test
	public void test_listFiles() {
		List<File> files = FileUtils.listFiles(System.getProperty("user.dir"), new ArrayList<>());
		
		Assertions.assertEquals(16, files.size());
	}
	
	@Test
	public void test_readClass() {
		List<File> files = FileUtils.listFiles(System.getProperty("user.dir"), new ArrayList<>());
		
		CodeClass codeClass = FileUtils.readClass(files.get(0));
		
		Assertions.assertEquals(files.get(0), codeClass.getFile());
	}
	
	@Test
	public void test_getMethodsAnnotatedWith() {
		List<Method> list = FileUtils.getMethodsAnnotatedWith(this.getClass(), Test.class);
		Optional<Method> methodOptional = list.stream()
				.filter(method -> "test_getMethodsAnnotatedWith".equals(method.getName()))
				.findAny();
		Assertions.assertTrue(methodOptional.isPresent());
	}
	
	@Test
	public static void test_getCodeClassMap() {
		Map<String, CodeClass> map = FileUtils.getCodeClassMap();
		Assertions.assertNotNull(map);
	}

	
	
}
