package com.diffblue.interview.impl;

import java.io.File;
import java.util.List;

import com.diffblue.interview.CodeClass;
import com.diffblue.interview.CodeLine;

public class CodeClassImpl implements CodeClass {

	private List<CodeLine> linesOfCode;
	private File file;
	
	public CodeClassImpl() {}
	
	public CodeClassImpl(List<CodeLine> linesOfCode, File file) {
		this.linesOfCode = linesOfCode;
		this.file = file;
	}
	@Override
	public List<CodeLine> getLinesOfCode() {
		return linesOfCode;
	}

	@Override
	public File getFile() {
		return file;
	}
	
	@Override
	public String getName() {
		return file.getName();
	}
}
