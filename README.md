# Legendary Patterns
Authors: Sam Pastoriza & Jason Lane

## Overall Design
Using the Java ASM library, we are able to extract large quantities of information  
about given classes and their internal fields and methods. This information is  
extracted in the "legendary" package that was provided at the beginning of the project.  
To work all of this data and analyze it, we built a set of classes and interfaces  
to record whether ASM returned a method, field, or class and store that information  
in an internal class representation we designed. We have built the project to use the strategy pattern,  
given the layout of the overall design, though it currently does not make heavy use of this pattern.  
We have a package of classes that deals with storing information, as described above, and a package of  
interfaces that help lay out those classes.  

## Current Progress
We are currently able to generate a very basic UML diagram of a set of classes in a project.  
We use GraphViz to generate the pictorial version of the UML diagram after parsing the  
classes and writing that to a .dot file. Once converted to a .dot file, a simple command can be run  
from the command line (or terminal if on a Mac), to produce a .png output.

## How to use the code
Open DesignParser.java in an editor, probably Eclipse  
Add the project you want to analyze to your build path  
Change the value of the variable 'packageName' to the outer most package name  
Change the 'directories' variable to the absolute path to your outermost package that you want to analyze  
Run DesignParser, where a text.dot file is created in the input_output folder  
Run graphviz (such as via dot.exe or gvedit.exe) on the .dot file  
The above step can also be done from the command line, by running the command: 'dot -Tpng text.dot -o output.png', where dot.exe is in the same folder as the text.dot file.


## Who did what

### Sam Pastoriza (Completed 50% of the milestone/s)
####Milestone 1  
Class/Method/Field Representation detailing  
Parser framework  
Parser detailing  
Visitor detailing  
Tests  
####Milestone 2  
Test Pictures  
Association & uses arrows framework
Association & uses arrows detailing
Intra-method object declaration framework and detailing

### Jason Lane (Completed 50% of the milestone/s)
####Milestone 1  
Class/Method/Field Representation framework  
Class/Method/Field Representation details  
Parser detailing  
Visitor framework  
Visitor detailing  
####Milestone 2  
Unit tests  
Parsing directories work  
Parameterized fields/types
Association and uses arrows detailing

## Evolution of the Design
### Milestone 1
We originally designed the tool around easily added support for new  
features such as more complicated class analysis and support for other arrows  
This translated pretty well into Milestone 2, where we needed to add support  
for uses and association arrows
### Milestone 2
When we add support for the uses and association arrows, we did not need to change  
the overall design significantly. We added a class that when visiting methods, would  
help discern the objects declared in each method. Much of the grunt work came from the parsing  
of the class names and making it easier to run for another user.


#### Notes
* framework = interfaces and basic class structures  
* detailing = implementation of those interfaces and functional code  
