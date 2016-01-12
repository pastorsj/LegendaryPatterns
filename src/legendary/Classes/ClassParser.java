package legendary.Classes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.Interfaces.ITraverser;
import legendary.Interfaces.IVisitor;

/*
 * Author: Sam Pastoriza
 */
public class ClassParser {
	public static ClassParser instance;

	private ClassParser() {
	}
	
	public static ClassParser getInstance() {
		if (instance == null)
			instance = new ClassParser();
		return instance;
	}

	public void makeSDEdit(String classname, String methodname, int depth, IModel model) {
		StringBuilder builder = new StringBuilder();
		outerloop: for (IClass c : model.getClasses()) {
			if (c.getClassName().equals(classname)) {
				for (IMethod method : c.getMethods().values()) {
					if (method.getMethodName().equals(methodname)) {
						SDEditOutputStream stream = new SDEditOutputStream(model, depth, builder);
						ITraverser t = (ITraverser) method;
						t.accept(stream);
						break outerloop;
					}
				}
			}
		}
		System.out.println(builder.toString());
	}

	public void makeGraphViz(IModel m) throws IOException {
		StringBuilder builder = new StringBuilder();
		IVisitor dotVisitor = new GraphVizOutputStream(builder);
		ITraverser t = (ITraverser) m;
		t.accept(dotVisitor);
		BufferedWriter writer = new BufferedWriter(new FileWriter("./input_output/text.dot"));
		writer.write(builder.toString());
		writer.close();
		// Runtime rt = Runtime.getRuntime();
		// rt.exec("dot -Tpng test.dot -o output.png");
		// System.out.println(builder.toString());
	}
}
