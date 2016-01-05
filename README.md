# Legendary Patterns
Authors: Sam Pastoriza & Jason Lane

## Overall Design
Using the java asm library, we are able to extract large quantities of information  
about given classes and their internal fields and methods. This information is  
extracted in the legendary package that was provided at the beginning of the project.  
To work all of this data and analyze it, we built a set of classes and interfaces  
to record whether asm returned a method, field or class and we can store that information  
in an instance of that class. We have built the project to use the strategy pattern,  
given the layout of the overall design. We have a package of classes that deals with storing  
information, as described above, and a package of interfaces that help layout those classes.  

## Current Progress
We are currently able to generate a very basic UML diagram a set of classes in a project.  
We use GraphViz to generate the pictorial version of the UML diagram after parsing the  
classes and writing that to a dot file. Once converted to a dot file, a simple command can be run  
from the command line (or terminal if on a Mac), and a resulting png image is produced.

## How to use the code
Open DesignParser.java in an editor, probably Eclipse  
Add the project you want to analyze to your build path
Add class names (including package) to the static "classes" String array*  
Run DesignParser, where a text.dot file is created in the input_output folder  
Run graphviz (such as via dot.exe or gvedit.exe) on the .dot file  
The above step can also be done from the command line, by run the command: 'dot -Tpng text.dot -o output.png', where dot.exe is in the same folder as the text.dot file.


## Who did what

### Sam Pastoriza (Completed 50% of the milestone/s)
Class/Method/Field Representation detailing  
Parser framework  
Parser detailing  
Visitor detailing  
Tests  

### Jason Lane (Completed 50% of the milestone/s)
Class/Method/Field Representation framework  
Class/Method/Field Representation details  
Parser detailing  
Visitor framework  
Visitor detailing  

#### Notes
* framework = interfaces  
* detailing = implementation of those interfaces  
