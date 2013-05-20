---
layout: docs
title: New analyser
prev_section: downloadthecode
next_section: architecture
permalink: /docs/newanalyser/
---
This proof of concept works with methods that has multiple integers as input or
a single string as input. If more advanced parsing is required a little more
programming is necessary to make it work.

<div class="note info">
    <h5>Parsing of parameters is done in the class
<code>ParameterParserHelper</code></h5>
    <p>Within the package <code>se.chho.tested.helpers</code> the class
<code>ParameterParserHelper</code> has the responsibility of parsing all method
input. As stated above if you need more advanced parsing this is where to look.</p>
</div>

## Input integers are passed in in ascending order
Let's say that you want to add the functionality of checking all methods with
integer input that the values sent in are all passed to the methods in
ascending order.

To solve this two things must be done. First you must create the "analyser" which
is a class that takes care of the actual analysis and the second thing is to
make sure that the new class is intantiated properly and loaded in the plugin.

#### Step 1. Create a new analyser
As this analyser is going to work on integers the easiest thing is to start by
copying one of the existing analysers. <code>OnlyMaximumIntegerAnalyser</code>
is a good start.

Each analyser loops through all non test methods and for each method the
analyser goes through each method invocation found in the different test cases.
Each method invocation has a set of input parameters that can be looped
through, in this case <code>methodInv.getIntParameters</code> will give all
integer parameters. For each method invocation it's now possible to detect what
you're looking for. Is the current parameter value higher than the previous?

If that kind of method invocation is not found add a marker (in the bottom of the
analyser) with a string message that gives the developer a hint on what can be
tested.


#### Step 2. Add the new analyser to <code>TestedMain</code>
In <code>TestedMain</code> all analysers are instantiated and then added to the
<code>analyserManager</code>. As you may expect from reading this page and
reading the code this is the Observable pattern where each analyser is a new observer.
