package com.diffblue.interview.coverage;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.CoverageBuilder;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.ICounter;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.core.instr.Instrumenter;
import org.jacoco.core.runtime.IRuntime;
import org.jacoco.core.runtime.LoggerRuntime;
import org.jacoco.core.runtime.RuntimeData;

import com.diffblue.interview.CodeClass;
import com.diffblue.interview.code.FileUtils;

public class CoverageApp {
	
	public static final MemoryClassLoader memoryClassLoader = new MemoryClassLoader();

	private static final Logger logger = LogManager.getLogger(CoverageApp.class);
	
	/**
	 * Entry point to run this examples as a Java application.
	 *
	 * @param args fully qualified class name to check the code coverage
	 * @throws Exception in case of errors
	 */
	public static void main(final String[] args) throws Exception {
		new CoverageApp().executeCoverage(Class.forName(args[0]));
	}
	
	
	public void executeCoverage(Class<?> testTarget) throws Exception {
		final String targetName = testTarget.getName();

		final IRuntime runtime = new LoggerRuntime();

		final RuntimeData data = new RuntimeData();
		runtime.startup(data);

		instrumentClasses(runtime);

		executeTests();

		final ExecutionDataStore executionData = new ExecutionDataStore();
		final SessionInfoStore sessionInfos = new SessionInfoStore();
		data.collect(executionData, sessionInfos, false);
		runtime.shutdown();

		final CoverageBuilder coverageBuilder = new CoverageBuilder();
		final Analyzer analyzer = new Analyzer(executionData, coverageBuilder);
		InputStream original = getTargetClass(testTarget);
		analyzer.analyzeClass(original, targetName);
		original.close();

		displayCoverage(coverageBuilder);
		logger.info("########## Done with coverage ##########");
	}

	private void executeTests()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, 
			NoSuchMethodException, InvocationTargetException {
		final Class<?> targetClass = memoryClassLoader.loadClass(TestRunner.class.getName());

		Constructor<?> ctor = targetClass.getConstructor();
		final Runnable targetInstance = (Runnable) ctor.newInstance();
		targetInstance.run();
	}

	private void displayCoverage(final CoverageBuilder coverageBuilder) {
		Map<String, CodeClass> map = FileUtils.getCodeClassMap();

		for (final IClassCoverage cc : coverageBuilder.getClasses()) {
			logger.info("Coverage of class - {}", cc.getName());

			for (int i = cc.getFirstLine(); i <= cc.getLastLine(); i++) {
				
				map.get(cc.getSourceFileName()).getLinesOfCode().get(i - 1)
						.setCoverageStatus(getStatusColor(cc.getLine(i).getStatus()));

			}

			map.get(cc.getSourceFileName()).getLinesOfCode().forEach(srcFile -> 
				logger.info("Line {} {} \t| {}", srcFile.getLineNumber(), 
						srcFile.getCoverageStatus(), srcFile.getContents()));
		}
	}

	private void instrumentClasses(final IRuntime runtime) {
		
		Set<Class<? extends Object>> allClasses = FileUtils.getAllClasses();
		
		allClasses.stream().forEach(cls -> {
			try {
				final Instrumenter instr = new Instrumenter(runtime);
				InputStream original = getTargetClass(cls);
				
				final byte[] instrumented = instr.instrument(original, cls.getName());
				original.close();

				memoryClassLoader.addDefinition(cls.getName(), instrumented);
			} catch (IOException e) {
				logger.error("Error occured while instrumenting the classes", e);
				throw new RuntimeException(e);
			}
		});		
		
	}

	private InputStream getTargetClass(final Class<?> cls) {
		final String resource = '/' + cls.getName().replace('.', '/') + ".class";
		return cls.getResourceAsStream(resource);
	}

	private String getStatusColor(final int status) {
		switch (status) {
		case ICounter.NOT_COVERED:
			return "Red";
		case ICounter.PARTLY_COVERED:
			return "Yellow";
		case ICounter.FULLY_COVERED:
			return "Green";
		default:
			return "";
		}
	}

}
