---
layout: docs
title: Potential improvements
prev_section: architecture
permalink: /docs/potentialimprovements/
---
In the end this proof of concept has shown, at least to me, that simple hints
on how I can improve my testing is helpful when given at the right time. As with
all projects there are alot of potential improvements and I will list those
that I find the most important.

### Improve functionality
Current implementation only works on a single string input, null input as well
as one or more integers as input. Refactoring this to work for objects and more
advanced scenarios is a major task but would take it to the next level when it
comes to usefulness.

### Refactor AnalyserManager to work with the Java Abstract Syntax Tree
In the beginning the goal was to work with the Java Abstract Syntax Tree to
identify test cases, non-test methods and all method invocations from the test
cases. While the project progressed a "temporary solution" was to use the
built-in Java search functionality. It was easy to implement but the design of
that part of the plugin got a bit too messy for easy maintainability in the
future.

### Refactor how each analysis is done
In the current architecture each analysis is done in a separate class which is
an observer on its own in the observer pattern. The limitation of this is that
within each "analyser" at least three loops are run and with increased amount
of analysers the performance will drastically become worse.

I believe there must be a better solution for how each analysis is done while
keeping different analysis separate.

### Unit tests
This eclipse plugin is all about testing but no unit tests have been created during
this project. This is a must if more advanced functionality is planned for the
future.
