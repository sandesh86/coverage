package com.diffblue.interview;

import java.util.Set;

/**
 * An interface representing code analysis functions
 */
public interface CodeAnalyzer {
    /**
     * Runs the given test and returns the covered lines of code
     * @param test to run
     * @return the covered lines of code
     */
    Set<CodeLine> runTest(CodeTest test);

    /**
     * Run all tests and returns the covered lines of code
     * @param tests to run
     * @return the covered lines of code
     */
    Set<CodeLine> runTestSuite(Set<CodeTest> tests);
}
