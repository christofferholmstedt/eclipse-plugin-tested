---
layout: docs
title: Example
prev_section: about
next_section: downloadtheplugin
permalink: /docs/example/
---
As an example we have a simple method that takes two integers and multiplies
them.

{% highlight yaml %}
public int calculate(int x, int y) {
    return x*y;
}
{% endhighlight %}

As first step in TDD we created a simple test case.
{% highlight yaml %}
assertTrue(4 == calculate(2, 2));
{% endhighlight %}

Next test case was two digits in both position.
{% highlight yaml %}
assertTrue(100 == calculate(10,10));
{% endhighlight %}

It's now TestED eclipse plugin kicks in when the developer got two passing test
for one method, in this case the method "calculate". In the Problems tab within
eclipse a few hints are given, here are some examples.
{% highlight yaml %}
What happens if you test the method "calculate" with a negative integer as input?
What happens if you test the method "calculate" with only "-2147483647" as input?
What happens if you test the method "calculate" with only "2147483647" as input?
What happens if you test the method "calculate" with only zeros as input?
{% endhighlight %}

Similiar suggestions/hints are given if there is only a string as input for a
method.
