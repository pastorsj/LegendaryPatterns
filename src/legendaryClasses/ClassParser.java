package legendaryClasses;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import legendaryInterfaces.IClass;
import legendaryInterfaces.IField;
import legendaryInterfaces.IMethod;

/*
 * Author: Sam Pastoriza
 */
public class ClassParser {
	private Map<String, IClass> classes;
	
	public ClassParser() {
		this.classes = new HashMap<String, IClass>();
	}
	
	public boolean addClass(IClass class1) {
		if(!this.classes.containsKey(class1.getClassName())) {
			this.classes.put(class1.getClassName(), class1);
			return true;
		}
		return false;
	}
	
	public void parse() throws IOException {
		StringBuilder classRep = new StringBuilder();
		classRep.append("digraph G{\n\tnode [shape = \"record\"]\n\t");
		Set<String> keySet = this.classes.keySet();
		//For each node
		this.addNode(classRep, keySet);
		//For every super class arrow
		this.addExtensionArrows(classRep, keySet);
		//For every interface arrow
		this.addInterfaceArrows(classRep, keySet);
		
		classRep.append("}");
		BufferedWriter writer = new BufferedWriter(new FileWriter("./input_output/text.dot"));
		writer.write(classRep.toString());
		writer.close();
		//Runtime rt = Runtime.getRuntime();
		//Process pr = rt.exec("dot -Tpng dot.txt -o output.png");
		System.out.println(classRep.toString());
	}
	
	private String addFields(IClass legendaryClass) {
		String fieldRep = "";
		for (IField field : legendaryClass.getFields()) {
			fieldRep += field.getAccess() + " " + field.getMethodName() + ": " + field.getReturnType() + "\n\t";
		}
		return fieldRep;
	}
	
	private String addMethods(IClass legendaryClass) {
		String methodRep = "";
		String methodName = "";
		for (IMethod method : legendaryClass.getMethods()) {
			methodName = method.getMethodName();
			if(methodName.equals("<init>") || methodName.equals("<clinit>")) {
				continue;
			}
			String parameters = Arrays.toString(method.getParameters().toArray());
			methodRep += method.getAccess() 
					+ " " + method.getMethodName() 
					+ "(" + parameters.substring(1, parameters.length()-1)
					+ ") : " + method.getReturnType() + "\\l\n\t";
		}
		return methodRep;
	}
	
	private void addNode(StringBuilder classRep, Set<String> keySet) {
		for(String key : keySet) {
			IClass legendaryClass = this.classes.get(key);
			String className = legendaryClass.getClassName();
			classRep.append(className + " [\n\tlabel = \"");
			//Check for interface here
			if(legendaryClass.isInterface()) {
				classRep.append("\\<\\<interface\\>\\>\\l");
			}
			classRep.append(className + "|\n\t");
			classRep.append(addFields(legendaryClass));
			classRep.append("|\n\t");
			classRep.append(addMethods(legendaryClass));
			classRep.append("\"\n\t]\n");
		}
	}
	
	private void addExtensionArrows(StringBuilder classRep, Set<String> keySet) {
		classRep.append("\tedge [arrowhead = \"empty\"]\n");
		for(String key : keySet) {
			IClass legendaryClass = this.classes.get(key);
			String legendarySuperClass = legendaryClass.getSuperName();
			if(key.contains(legendarySuperClass)) {
				classRep.append(legendaryClass.getClassName() + "->" + legendarySuperClass + "\\l\n");
			}
		}
	}
	
	private void addInterfaceArrows(StringBuilder classRep, Set<String> keySet) {
		classRep.append("\tedge [style = \"dashed\"]\n");
		for(String key : keySet) {
			IClass legendaryClass = this.classes.get(key);
			for(String legendaryInterface : legendaryClass.getInterfaces()) {
				if(keySet.contains(legendaryInterface)) {
					classRep.append(legendaryClass.getClassName() + "->" + legendaryInterface + "\\l\n");
				}
			}
		}
	}
}
