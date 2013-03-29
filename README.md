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

TODO
----
Eclipse does not have any "active" windows, instead all projects are either
open or closed. In TestED an active window will be defined as the window
were the user clicks with the mouse. Menus will only be available when right-
clicking in the package explorer on Java files and "inside" Java files.

Functionality needed
  1. Identify the package name of the package which the user has "active"
  2. Find all tests in that package
  3. Find all methods that are invoked by the tests found in step 2
  4. Mark invoked methods with a green marker
  5. Mark non-invoked methods with a red marker

Functionality needed
  1. Run a method after each test is ran.
  2. Run a method after all tests have finished.
