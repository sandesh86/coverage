package com.diffblue.interview.coverage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import com.diffblue.interview.code.FileUtils;

public class TestRunner implements Runnable {

	private static final Logger logger = LogManager.getLogger(TestRunner.class);
	
	public void run() {
		logger.info("############ Test Execution Begins #############");
		Set<Class<? extends Object>> allClasses = FileUtils.getAllClasses();

		allClasses.stream().forEach(cls -> {
			
			List<Method> methods = FileUtils.getMethodsAnnotatedWith(cls, Test.class);
			try {
				if (!cls.isInterface() && !methods.isEmpty()) {
					Constructor<?> ctor = cls.getConstructor();
					Object object = ctor.newInstance();
					
					methods.forEach(method -> {
						try {							
							method.invoke(object, null);
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
							logger.error("Error while invoking the JUnit", e);
							throw new RuntimeException(e);
						}
					});
				}
				
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1) {
				logger.error("Error while invoking the JUnit", e1);
				throw new RuntimeException(e1);
			}
		});
		logger.info("########### Test Execution Complete ############");
	}
}
