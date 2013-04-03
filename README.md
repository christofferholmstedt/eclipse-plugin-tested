TestED (Eclipse plugin)
=======================
TestED is an Eclipse plugin that will parse your Java tests and related source
code and suggest improvements to you concerning testing. This is done
by analysing method parameters and arguments as well as the output from
at least two tests that passes for each corresponding.

TestED is developed as a project in the course "CDT412 - Software Engineering
Project" at Mälardalen University in Sweden.

License
-------
Eclipse Public License - v 1.0

Full license available at http://www.eclipse.org/legal/epl-v10.html or in the accompanying file "LICENSE".

TODO #2
-------
Working with tests. Our analysis might be best invoked when the developer
runs the tests created just recently. So to be able to plug-in to this process
a TestListener needs to be created as well as some basic functionaliy on how
to work with test results/repots from JUnit.

Functionality needed
  1. Print something to console after each test is ran. (done)
  2. Print something to console after all tests have finished. (done)
  3. Find out how to work with JUnit test results. (done)
  4. Modify TestedSearchRequestor to count how many times each method that is
     run within test methods that passes. If this count is equal to two or
higher add marker to method.
  5. Combining JUnit test results with the java project interface e.g. add
     markers. Some help may be found at
http://www.java2v.com/Open-Source/Java-Document/IDE-Eclipse/jdt/org/eclipse/jdt/junit/tests/TestRunListeners.java.htm

TODO #1
-------
Eclipse does not have any "active" windows, instead all projects are either
open or closed. In TestED an active window will be defined as the window
were the user clicks with the mouse. Menus will only be available when right-
clicking in the package explorer on Java files and "inside" Java files.

Functionality needed
  1. Identify the package name of the package which the user has "active"
     (done)
  2. Find all tests in that package (done)
  3. Find all methods that are invoked by the tests found in step 2 (done)
  4. Mark invoked methods with a green marker (postponed)
  5. Mark non-invoked methods with a red marker (postponed)

