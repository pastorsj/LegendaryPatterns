package legendary.Classes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IField;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.Interfaces.ITraverser;
import legendary.Interfaces.IVisitor;

/*
 * Author: Sam Pastoriza
 */
public class ClassParser {
	private Map<String, IClass> classes;

	public static ClassParser instance;
	
	private ClassParser() {
		this.classes = new HashMap<String, IClass>();
	}
	
	public static ClassParser getInstance(){
		if(instance == null)
			instance = new ClassParser();
		return instance;
	}

	public void parseModel(IModel m) throws IOException {
		StringBuilder builder = new StringBuilder();
		IVisitor dotVisitor = new GraphVizOutputStream(builder);
		ITraverser t = (ITraverser) m;
		t.accept(dotVisitor);
		BufferedWriter writer = new BufferedWriter(new FileWriter(
				"./input_output/text.dot"));
		writer.write(builder.toString());
		writer.close();
//		Runtime rt = Runtime.getRuntime();
//		rt.exec("dot -Tpng test.dot -o output.png");
		System.out.println(builder.toString());
	}
}
