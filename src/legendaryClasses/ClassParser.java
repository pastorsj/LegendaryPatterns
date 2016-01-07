package legendaryClasses;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
		if (!this.classes.containsKey(class1.getClassName())) {
			this.classes.put(class1.getClassName(), class1);
			return true;
		}
		return false;
	}

	public void parse() throws IOException {
		StringBuilder classRep = new StringBuilder();
		classRep.append("digraph G{\n\tnode [shape = \"record\"]\n\t");
		Set<String> keySet = this.classes.keySet();
		// For each node
		this.addNode(classRep, keySet);
		// For every super class arrow
		this.addExtensionArrows(classRep, keySet);
		// For every interface arrow
		this.addInterfaceArrows(classRep, keySet);

		this.addUsageArrows(classRep, keySet);

		this.addAssociationArrows(classRep, keySet);
		classRep.append("}");
		BufferedWriter writer = new BufferedWriter(new FileWriter(
				"./input_output/text.dot"));
		writer.write(classRep.toString());
		writer.close();
//		Runtime rt = Runtime.getRuntime();
//		rt.exec("dot -Tpng test.dot -o output.png");
		System.out.println(classRep.toString());
	}

	private String addFields(IClass legendaryClass) {
		String fieldRep = "";
		for (IField field : legendaryClass.getFields()) {
			fieldRep += field.toString();
		}
		return fieldRep;
	}

	private String addMethods(IClass legendaryClass) {
		String methodRep = "";
		for (IMethod method : legendaryClass.getMethods()) {
			methodRep += method.toString();
		}
		return methodRep;
	}

	private void addNode(StringBuilder classRep, Set<String> keySet) {
		for (String key : keySet) {
			IClass legendaryClass = this.classes.get(key);
			String className = legendaryClass.getClassName();
			classRep.append(className + " [\n\tlabel = \"{");
			// Check for interface here
			if (legendaryClass.isInterface()) {
				classRep.append("\\<\\<interface\\>\\>\\n");
			}
			classRep.append(className + "|\n\t");
			classRep.append(addFields(legendaryClass));
			classRep.append("|\n\t");
			classRep.append(addMethods(legendaryClass));
			classRep.append("}\"\n\t]\n");
		}
	}

	private void addExtensionArrows(StringBuilder classRep, Set<String> keySet) {
		classRep.append("\tedge [arrowhead = \"empty\"]\n");
		for (String key : keySet) {
			IClass legendaryClass = this.classes.get(key);
			String legendarySuperClass = legendaryClass.getSuperName();
			String name = legendarySuperClass.substring(legendarySuperClass
					.lastIndexOf("/") + 1);
			if (key.contains(name)) {
				classRep.append(legendaryClass.getClassName() + "->" + name
						+ "\n\t");
			}
		}
	}

	private void addInterfaceArrows(StringBuilder classRep, Set<String> keySet) {
		classRep.append("\tedge [style = \"dashed\"]\n\t");
		for (String key : keySet) {
			IClass legendaryClass = this.classes.get(key);
			for (String legendaryInterface : legendaryClass.getInterfaces()) {
				String name = legendaryInterface.substring(legendaryInterface
						.lastIndexOf("/") + 1);
				if (keySet.contains(name)) {
					classRep.append(legendaryClass.getClassName() + "->" + name
							+ "\n\t");
				}
			}
		}
	}

	private void addUsageArrows(StringBuilder classRep, Set<String> keySet) {
		classRep.append("edge [style = \"dashed\"] [arrowhead = \"open\"]\n\t");
		for (String key : keySet) {
			IClass legendaryClass = this.classes.get(key);
			for (String useClass : legendaryClass.getUsesClasses()) {
				String name = useClass.substring(useClass.lastIndexOf("/") + 1);
				if (keySet.contains(name)) {
					String lsuperc = legendaryClass.getSuperName();
					ArrayList<String> higher = new ArrayList<String>();
					higher.add(lsuperc);
					higher.addAll(legendaryClass.getInterfaces());
					boolean add = true;
					for (String s : higher) {
						s = s.substring(s.lastIndexOf("/") + 1);
						if (this.classes.containsKey(s)
								&& this.classes.get(s).getUsesClasses()
										.contains(useClass)) {
							add = false;
						}
					}
					if (add)
						classRep.append(legendaryClass.getClassName() + "->"
								+ name + "\n\t");
				}
			}
		}
	}

	private void addAssociationArrows(StringBuilder classRep, Set<String> keySet) {
		classRep.append("edge [style = \"solid\"] [arrowhead = \"open\"]\n\t");
		for (String key : keySet) {
			IClass legendaryClass = this.classes.get(key);
			for (String legendaryInterface : legendaryClass
					.getAssociationClasses()) {
				String name = legendaryInterface.substring(legendaryInterface
						.indexOf("/") + 1);
				String lsuperc = legendaryClass.getSuperName();
				lsuperc = lsuperc.substring(lsuperc.lastIndexOf("/") + 1);
				if (keySet.contains(name)
						&& legendaryClass.getSuperName() != ""
						&& (!this.classes.containsKey(lsuperc) || !this.classes
								.get(lsuperc).getAssociationClasses()
								.contains(name))) {
					classRep.append(legendaryClass.getClassName() + "->" + name
							+ "\n\t");
				}
			}
		}
	}
}
