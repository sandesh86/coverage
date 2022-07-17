package com.diffblue.interview.impl;

import com.diffblue.interview.CodeLine;

public class CodeLineImpl implements CodeLine {

	private int lineNumber;
	private String contents;
	private String coverageStatus = "";
	
	public CodeLineImpl() {}
	
	public CodeLineImpl(int lineNumber, String content) {
		this.lineNumber = lineNumber;
		this.contents = content;
	}
	
	@Override
	public int getLineNumber() {
		return lineNumber;
	}

	@Override
	public String getContents() {
		return contents;
	}

	@Override
	public String getCoverageStatus() {
		return coverageStatus;
	}

	@Override
	public void setCoverageStatus(String coverageStatus) {
		this.coverageStatus = coverageStatus;
	}

}
