package com.diffblue.interview.impl;

import java.util.Set;

import com.diffblue.interview.CodeLine;
import com.diffblue.interview.CodeTest;

public class CodeTestImpl implements CodeTest {

	private String name;
	private Set<CodeLine> coveredLines;
	
	public CodeTestImpl() {}
	
	public CodeTestImpl(String name, Set<CodeLine> coveredLines) {
		this.name = name;
		this.coveredLines = coveredLines;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Set<CodeLine> getCoveredLines() {
		return coveredLines;
	}

}
