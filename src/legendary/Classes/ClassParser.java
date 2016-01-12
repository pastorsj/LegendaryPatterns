package legendary.Classes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.Interfaces.ITraverser;
import legendary.Interfaces.IVisitor;
import legendary.ParsingUtil.ParsingMethodUtil;

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
		StringBuilder sbMethods = new StringBuilder();
		Set<String> sbClasses = new HashSet<>();
		outerloop: for (IClass c : model.getClasses()) {
			if (c.getClassName().equals(classname)) {
				for (IMethod method : c.getMethods().values()) {
					if (method.getMethodName().equals(methodname)) {
						ParsingMethodUtil.getCompleteCallStack(model, method, depth, depth, sbMethods, sbClasses);
						break outerloop;
					}
				}
			}
		}
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
