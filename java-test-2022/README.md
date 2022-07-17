Testing Test
============

This project contains an interview question designed to assess your Java
coding ability.

Please note that we will look as much at readability, structure, documentation
and testing as we will at accuracy and correctness. Show us how good you are!

This project contains some basic interfaces representing Java code to be tested for 
coverage:

- CodeLine represents a line of Java code in a file
- CodeClass represents a Java class or interface file
- CodeAnalyzer represents the coverage analysis engine
- CodeTest represents a Java test

Question 1
------
Please implement these interfaces to do something useful.
You should include a main function that takes the file name of a Java source file and generates
a printout of the file with lines prefixed with whether they are covered by tests or not.
You may initialise a CodeTest to cover a random set of CodeLines of a CodeClass as long as the
output is consistent between calls to CodeAnalyzer.runTest().

Question 2
------
Ensure that CodeAnalyzer.runTestSuite() only produces unique CodeLine elements.

Question 3
------
Add a function to the analyser - uniqueTests() - that returns all tests
that cover something not covered by other tests.

Question 4 
------
If you have not done so already, write jUnit tests for the code that you have written.