---
layout: docs
title: Download the code
prev_section: faq
next_section: newanalyser
permalink: /docs/downloadthecode/
---
The code is available through Git/GitHub
[here](https://github.com/christofferholmstedt/eclipse-plugin-tested). I
haven't had any great experience with version control systems from within
eclipse. What I do instead is to use Git from command line (Ubuntu 12.04 LTS)
and import the project to eclipse.

The code for the plugin is available in the master branch and the code for this
website you're reading now is available in the gh-pages branch. Those two
branches should never be merged so to prevent that from happening I've checked
out each branch in separate folders, which is normally not necessary with git.

Depending on which eclipse version you have installed you may have to install
the [Plug-in Development Environment (PDE)](http://www.eclipse.org/pde/). This could easily be done from
witihin eclipse and the "Install New Software..." option.

Here is a quick walkthrough on how to get it to run. Lines that should be executed from the
command line start with the $-sign.
{% highlight yaml %}
$ cd <directory_for_development>
$ git clone https://github.com/christofferholmstedt/eclipse-plugin-tested.git

Open Eclipse and set <directory_for_development> as workspace.
In the project explorer import the eclipse-plugin-tested project into
workspace.
{% endhighlight %}

You should now be able to open the plugin project in Eclipse and run it as an
"Eclipse application". Another Eclipse instance will now start with the TestED
plugin activated.
