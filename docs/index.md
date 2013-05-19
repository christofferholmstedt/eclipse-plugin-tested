---
layout: docs
title: About
next_section: installation
permalink: /docs/home/
---
TestED is an ecplise plugin aimed towards helping Java developers to improve
testing while doing test driven development. The inspiration is drawn from
pair programming and the benefits of practicing it. As an example it's very
easy to miss a simple test case with "null" as input when you're deep into your
code. Simple oversights like that can easily be detected during pair
programming or peer review of finished code, though even better would be if your
IDE could tell you about them.

## How TestED works
It's not meaningful to give developers suggestions on how to improve testing if the developer is not in
the mindset of recieving feedback. So when is the right time?

In test driven development the idea is that you first create one test case, you
implement the code necessary to pass that test case. Then you add another test
case as well as minimum code that makes the new test case pass. This goes on
until the requested functionality is complete.

The best opportunity would be when the developer says "I'm done", though that
is impossible for the software to now and we didn't want this plugin to be yet
another software that needs to be run at the end of some development cycle. A compromise
here is that the TestED plugin will run as soon as JUnit from within eclipse
has finished. Suggestions or hints of improvements won't be given on functions that
hasn't been tested at least twice already. As a last note all tests must pass
for TestED to be executed this is because if a developer has written a test
case and that doesn't pass yet the priority for the developer must be to solve
that first.

