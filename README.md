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
We are also able to detect four types of software design patterns in the code, mainly the  
Singleton pattern, the Decorator Pattern, the Adapter Pattern and the Composite Pattern. Any class that is a  
Singleton class will have an association arrow pointing to itself, be outlined in blue, and  
will have a <<Singleton>> tag under the name of the class. The Decorator pattern will consist of  
four parts, the Component, the Abstract Decorator or Decorator Interface, the Concrete Decorators,  
and the Concrete Component. Each of these will have their own unique tags and will have a fill  
color of green. The Adapter Patter will consist of three parts, the Target, which can be  
an interface, abstract class or regular class, the Adaptee, and the Adapter. Each of these classes  
will be filled with a red color and have unique identifying tags. The Composite pattern will consist of  
at least a composite class containing leaves, and a component, which will also contain at least  
one leaf.  Each of these classes are colored yellow and will have corresponding tags that identify  
which part of the pattern they are.

The current progress we made for milestone 7 allows the user to analyze the code with  
a gui as a frontend. This make it much easier for the user to study the code and  
figure out which patterns they should use to make their code more extensible. The  
pattern detection has not changed since Milestone 6.

## How to use the code
For the current iteration of the project:
Write a config.properties file similar to the example in the project  
that will specify certain properties such as output file path, input file path,  
dot file path, and phases of execution. Run the Driver class, choose the  
.properties file and click analyze. Once on the next screen, click through the  
checkboxes to find the patterns and classes desired. Feel free to load a new config,  
export the current graph, or view the about or help tabs through the File Menu.
#### Directions for previous versions (Before Milestone 7/GUI)
To run on previous version without the gui, make sure that you checkout a branch  
at milestone 6 and earlier. This allows you to run on DesignParser.  
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
![UML Milestone 3](https://github.com/pastorsj/LegendaryPatterns/blob/master/docs/UMLLegendaryPatternsM3.png)
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
### Milestone 4
We began to implement pattern detection by adding an interface that would allow for easy addition  
of new detectors. We also added a Singleton Detector that implements that interface and detects  
whether the current model has any type of singleton class.
![UML Milestone 4](https://github.com/pastorsj/LegendaryPatterns/blob/master/docs/UMLLegendaryPatternsManualM4.png)
#### Completed by Jason Lane
Pattern Detection framework and details  
Refactoring in GraphViz output stream  
Singleton pattern testing  
Singleton detection implementation  
#### Completed by Sam Pastoriza
Added tests, both manually and automated that detected singleton classes  
UML Generation  
General refactoring  
Design of milestone additions  
### Milestone 5
We continued to implement pattern detection by adding support for the Adapter pattern and Decorator  
pattern. We had to change much of the code structure to accomplish the additions and to make it  
easier to add more patterns. We first added support for more visitors by converting our existing code  
into lambda expressions. This will allow for more easier additions to the visiting code if required.  
The next major step was that we converted the current model into a graph, where we can visit nodes,   
which are classes, and if a node/class has a relation to another class, we can add a back arrow for that  relation. This allows for much easier traversal, especially when detecting patterns. The last major piece   in this milestone that we worked on was the pattern detection. We now allow for multiple patterns to be detected by adding IPatternDetectors to a complicated data structure that we parse to figure out which  
patterns we want to detect. This method follows to Open-Closed Principle, where we can add patterns without changing the framework we developed.  
![UML Milestone 5](https://github.com/pastorsj/LegendaryPatterns/blob/master/docs/UMLLegendaryPatternsManualM5.png)
#### Completed by Jason Lane
Adapter Pattern and Decorator Pattern framework  
Adapter Pattern and Decorator Pattern details  
Model to Graph Refactoring  
Refactor of General Util  
#### Completed by Sam Pastoriza
Lambda Refactor for Visiting SDEdit class and GraphViz class  
Unit Tests for Adapter and Decorator pattern framework and details  
Adapter and Decorator pattern specifics/details  
Recursive search for classes extends/implements/associated by other classes
###Milestone 6
We add pattern detection support for the Composite Pattern. We did not change much of the overall  
design, however, we changed the way we set up the colors and tags for patterns. We abstracted the color  
since each pattern has the same color for the entire pattern, but different tags. This eliminated some  
color duplication.
![UML Milestone 6](https://github.com/pastorsj/LegendaryPatterns/blob/master/docs/UMLLegendaryPatternsManualM6.png)  
#### Completed by Jason Lane
Composite Pattern framework  
Composite Pattern details  
Pattern color/tag framework refactor  
#### Completed by Sam Pastoriza
Composite Pattern details  
Unit/Integration/Regression Tests for Composite Pattern  
Massive documentation of entire project  
###Milestone 7
We added an entire gui to our project that allows users to view specific patterns and the  
corresponding classes in a concise and compact fashion. We used several pattern to implement  
this feature, including the observer and proxy patterns to update the display every time a  
checkbox is checked. When a checkbox is checked, a new class will show up with the correct  
pattern tags. Most of the core code was not changed while creating the gui, which implies that  
any additions for pattern matching wold not change the core framework or the gui. This  
implementation closely followed the open closed principle, where the core code is open  
for extension, but closed for modification.
![UML Milestone 7](https://github.com/pastorsj/LegendaryPatterns/blob/master/docs/UMLLegendaryPatternsManualM7.png)  
#### Completed by Jason Lane
Progress Bars  
Slight modifications made in core framework  
Pattern detection fixes  
Overall GUI framework
#### Completed by Sam Pastoriza
Checkbox trees  
Overall GUI framework and details   
Main and Display screen creation  
Manual testing  

#### Notes
* framework = interfaces and basic class structures  
* detailing = implementation of those interfaces and functional code  
