---
layout: docs
title: Architecture
prev_section: newanalyser
next_section: potentialimprovements
permalink: /docs/architecture/
---
This is a proof of concept and therefore not too much can be expected from the
design. This page goes through the current architecture so anyone in the future
can continue on the work done here or start from scratch and avoid same
mistakes as have happened throughout this proof of concept implementation.

## Overview
### Package <code>se.chho.tested</code>
The plugin is divided in four different packages. The package <code>se.chho.tested</code> includes the eclipse plugin plumbing.
<code>TestedPlugin.java</code> is the activator for the plugin, defines plugin
id but not much else.
<div class="note">
  <h5>Start the plugin from TestedPlugin</h5>
  <p>During the development there were some problems with loading the
TestRunListener from TestedPlugin. Sometimes it loaded twice and gave duplicate
output and sometimes it didn't load at all. The TestRunListener is currently
loaded by the definition in plugin.xml.</p>
</div>

A TestRunListener is a class that is invoked when JUnit runs from within
Eclipse. It's possible to listen on the process of all test runs here, if a
test case fails or passes the information can be accessed here. In the end it's also possible to get the result of all tests. In
<code>TestedTestRunListener</code> all previous defined markers are wiped out and
if all test passes <code>TestedMain</code> is invoked.

<div class="note info">
  <h5>A marker is a hint in this plugin</h5>
    <p>A marker can be many things in Eclipse. In this plugin a marker is line
number marker (placed on a specific line, beginning of a method) and have a
string message that is shown in the "Problems" tab.</p>
</div>

### Package <code>se.chho.tested.core</code>
In <code>TestedMain</code> the plugin is started by instantiating and running
the "AnalyserManager" which then all the different "analysers" are attached to.
The AnalyserManager process the entire active java project and extracts all non
test methods and all matching method invocations from test cases. So for each
found, non test method, all method invocations are listed. If a new
AnalyserManager is to be implemented the important part is, as defined in the
interface, the arraylist of "FoundMethods".

<div class="note info">
  <h5>The Observer pattern</h5>
    <p>
       <a href="http://en.wikipedia.org/wiki/Observer_pattern">The Observer
pattern</a>
were chosen for two things. I wanted each specific analysis to be done in a
separate class (observer) and I wanted it to be very easy to change the way a
java project is parsed for all its test cases and method invocations
(observable object).
    </p>
</div>

### Package <code>se.chho.tested.analysers</code>
Each different analysis is implemented in its own class. Each class starts of
by iterating through all "non-test" methods and all invocations done from test
cases. In the end each analyser is responsible for adding a marker for that
analysis done if needed.

### Package <code>se.chho.tested.helpers</code>
This package includes some helpers. The <code>LineNumberHelper</code> returns
the linenumber as integer when given an ICompilationUnit and an IMethod. This
is used from within each analyser to find out which linenumber the marker
should be placed on. Likewise is the <code>MarkerHelper</code> there to make it easier to
create the marker.

The class <code>ParameterParserHelper</code> is responsible for parsing a given string
and creating a list of input variables. It's here each method invocation is
parsed to gather its input values.
