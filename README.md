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

## Project Process
We are able to generate a very basic UML diagram of a set of classes in a project.  
We use GraphViz to generate the pictorial version of the UML diagram after parsing the  
classes and writing that to a .dot file. Once converted to a .dot file, a simple command can be run  
from the command line (or terminal if on a Mac), to produce a .png output.
We are also able to produce sequence diagrams through the command line given a method call depth, a method and the owner of the method. The default depth is 5, but any depth can be specified.

## How to use the code
Open DesignParser.java in an editor, probably Eclipse  
Add the project you want to analyze to your build path  
Change the value of the variable 'packageName' to the outer most package name  
Change the 'directories' variable to the absolute path to your outermost package that you want to analyze  
### For a UML Diagram
Run DesignParser, where a text.dot file is created in the input_output folder  
Run graphviz (such as via dot.exe or gvedit.exe) on the .dot file  
The above step can also be done from the command line, by running the command: 'dot -Tpng text.dot -o output.png', where dot.exe is in the same folder as the text.dot file.

### For a Sequence Diagram plus UML Diagram
Download the sequence editor jar file from the SDEdit website
From the command line, type the following command:  
java -jar sdedit-3.0.jar -o /input_output/SDEoutput.png -t png /input_output/text.sd  
In the input_output file, the png image should be generated.

# Evolution of the Design
## Milestone 1
We originally designed the tool around easily added support for new  
features such as more complicated class analysis and support for other arrows  
This translated pretty well into Milestone 2, where we needed to add support  
for uses and association arrows.  
![UML Milestone 1](https://github.com/pastorsj/LegendaryPatterns/blob/master/docs/UMLLegendaryPatternsManualM1.png)  
#### Completed by Jason Lane
Class/Method/Field Representation framework  
Class/Method/Field Representation details  
Parser detailing  
Visitor framework  
Visitor detailing  
#### Completed by Sam Pastoriza
Class/Method/Field Representation detailing  
Parser framework  
Parser detailing  
Visitor detailing  
Tests  
### Milestone 2
When we add support for the uses and association arrows, we did not need to change  
the overall design significantly. We added a class that when visiting methods, would  
help discern the objects declared in each method. Much of the grunt work came from the parsing  
of the class names and making it easier to run for another user.  
![UML Milestone 2](https://github.com/pastorsj/LegendaryPatterns/blob/master/docs/UMLLegendaryPatternsManualM2.png)
#### Completed by Jason Lane
Unit tests  
Parsing directories work  
Parameterized fields/types  
Association and uses arrows detailing  
#### Completed by Sam Pastoriza
Test Pictures  
Association & uses arrows framework  
Association & uses arrows detailing  
Intra-method object declaration framework and detailing
### Milestone 3
We used the visitor pattern to refactor the Milestone 2 code, and then proceeded to   
use that visitor to implement the Sequence diagram code. The refactoring took most of the  
time, however the sequence diagram code was difficult to debug.  
![UML Milestone 3](https://github.com/pastorsj/LegendaryPatterns/blob/master/docs/UMLLegendaryPatternsManualM3.png)
#### Completed by Jason Lane
Util refactoring  
Major Milestone 2 refactoring using visitor pattern  
SDEdit visitor pattern framework and detailing  
Auto generated testing for GraphViz and SDEdit  
#### Completed by Sam Pastoriza
Major Milestone 2 refactoring using visitor pattern  
GraphViz and SDEdit framework, detailing and refactoring  
Arrow Testing using reflection  
Manual testing for SDEdit diagrams  

#### Notes
* framework = interfaces and basic class structures  
* detailing = implementation of those interfaces and functional code  
