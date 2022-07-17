package com.diffblue.interview;

import java.util.Set;

/**
 * An interface representing a Java test
 */
public interface CodeTest {
    /**
     * @return the name of the test
     */
    String getName();

    /**
     * @return the set of line numbers covered by an execution of this test
     */
    Set<CodeLine> getCoveredLines();
}
