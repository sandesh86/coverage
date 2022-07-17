package com.diffblue.interview.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.diffblue.interview.CodeClass;
import com.diffblue.interview.CodeLine;
import com.diffblue.interview.impl.CodeClassImpl;
import com.diffblue.interview.impl.CodeLineImpl;

public class FileUtils {
	
	private static final Logger logger = LogManager.getLogger(FileUtils.class);
	
	private static final Map<String, CodeClass> codeClassMap;
	
	static {
		List<File> resultList = new ArrayList<>();

		List<File> files = listFiles(System.getProperty("user.dir"), resultList);

		codeClassMap = files.stream().map(FileUtils::readClass)
			.collect(Collectors.toMap(CodeClass::getName, Function.identity()));
	}
	
	public static Set<Class<? extends Object>> getAllClasses() {
		try {
			return getAllClasses("com.diffblue.interview");
		} catch (ClassNotFoundException | IOException e) {
			logger.info("Error while loading the classes", e);
			throw new RuntimeException(e);
		}
	}
	
	public static Map<String, CodeClass> getCodeClassMap() {
		return Collections.unmodifiableMap(codeClassMap);
	}

	public static List<File> listFiles(String directoryName, List<File> resultList) {
        File directory = new File(directoryName);

        File[] fList = directory.listFiles();
        List<File> javaFiles = Arrays.stream(fList)
        		.filter(file -> file.getName().endsWith(".java"))
        		.collect(Collectors.toList());
        resultList.addAll(javaFiles);
        
        Arrays.stream(fList)
        	.filter(File::isDirectory)
        	.forEach(file -> listFiles(file.getAbsolutePath(), resultList));
        
        return resultList;
    }
	
	public static CodeClass readClass(File file) {
		List<CodeLine> codeLines = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    int lineNumber = 1;
		    while ((line = br.readLine()) != null) {		    	
		    	CodeLineImpl codeLine = new CodeLineImpl(lineNumber, line);
		    	codeLines.add(codeLine);		    	
		    	lineNumber++;
		    }
		} catch (IOException e) {
			logger.error("Exception occured while reading class", e);
			throw new RuntimeException(e);
		}
		codeLines.sort((code1, code2) -> 
			Integer.compare(code1.getLineNumber(), code2.getLineNumber()));
		
		return new CodeClassImpl(codeLines, file);		
	}
	
	
	public static List<Method> getMethodsAnnotatedWith(final Class<?> type, 
			final Class<? extends Annotation> annotation) {
	    final List<Method> methods = new ArrayList<>();
	    Class<?> klass = type;
	    while (klass != null && klass != Object.class) { 
	    	
	        for (final Method method : klass.getDeclaredMethods()) {
	            if (method.isAnnotationPresent(annotation)) {
	                methods.add(method);
	            }
	        }
	        klass = klass.getSuperclass();
	    }
	    return methods;
	}
	
	/**
	 * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
	 *
	 * @param packageName The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static Set<Class<? extends Object>> getAllClasses(String packageName)
	        throws ClassNotFoundException, IOException {
	    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	    assert classLoader != null;
	    String path = packageName.replace('.', '/');
	    Enumeration<URL> resources = classLoader.getResources(path);
	    List<File> dirs = new ArrayList<File>();
	    while (resources.hasMoreElements()) {
	        URL resource = resources.nextElement();
	        dirs.add(new File(resource.getFile()));
	    }
	    Set<Class<? extends Object>> classes = new HashSet<>();
	    for (File directory : dirs) {
	        classes.addAll(findClasses(directory, packageName));
	    }
	    return classes;
	}

	/**
	 * Recursive method used to find all classes in a given directory and subdirs.
	 *
	 * @param directory   The base directory
	 * @param packageName The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	private static List<Class<? extends Object>> findClasses(File directory, String packageName) throws ClassNotFoundException {
	    List<Class<? extends Object>> classes = new ArrayList<>();
	    if (!directory.exists()) {
	        return classes;
	    }
	    File[] files = directory.listFiles();
	    for (File file : files) {
	        if (file.isDirectory()) {
	            assert !file.getName().contains(".");
	            classes.addAll(findClasses(file, packageName + "." + file.getName()));
	        } else if (file.getName().endsWith(".class")) {
	            classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
	        }
	    }
	    return classes;
	}
}
