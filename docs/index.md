---
layout: docs
title: Welcome
next_section: example
permalink: /docs/about/
---
TestED is an ecplise plugin aimed towards helping Java developers to improve
testing while doing test driven development. The inspiration is drawn from
pair programming and the benefits of practicing it. As an example it's very
easy to miss a simple test case with "null" as input when you're deep into your
code. Simple oversights like that can easily be detected during pair
programming or peer review of finished code, though even better would be if your
IDE could tell you about them.

TestED eclipse plugin is as of may 2013 a proof of concept working on simple
scenarios with only strings or integers as method input parameters.

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

## Who is TestED for?
As it still proof of concept, TestED is for developers that are interested in
testing and how it can help other developers develop better software.


<table>
  <thead>
    <tr>
      <th>Requirement</th>
      <th>Comment</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>
        <p><code>Java</code></p>
      </td>
      <td>
        <p>
            TestED is developed to work with Java code, nothing else.
        </p>
      </td>
    </tr>
    <tr>
      <td>
        <p><code>Eclipse</code></p>
      </td>
      <td>
        <p>
            TestED is an eclipse plugin.
        </p>
      </td>
    </tr>
    <tr>
      <td>
        <p><code>JUnit</code></p>
      </td>
      <td>
        <p>
            TestED requires JUnit to be run from within Eclipse.
        </p>
      </td>
    </tr>
  </tbody>
</table>
Continue to next section for an example on what kind of hints you get.
