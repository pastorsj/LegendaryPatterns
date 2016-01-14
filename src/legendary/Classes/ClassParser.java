package legendary.Classes;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
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

	public void makeSDEdit(String classname, String methodname, int depth, IModel model) throws IOException {
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
		BufferedWriter writer = new BufferedWriter(new FileWriter("./input_output/text.sd"));
		writer.write(builder.toString());
		writer.close();
		Runtime rt = Runtime.getRuntime();
		rt.exec("java -jar ./lib/sdedit-4.2-beta1.jar -o ./input_output/SDEoutput.png -t png ./input_output/text.sd");
		//Desktop.getDesktop().open(new File("./input_output/SDEoutput.png"));
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
//		Runtime rt = Runtime.getRuntime();
//		rt.exec("./lib/Graphviz2.38/bin/dot -Tpng ./input_output/text.dot -o ./input_output/GraphVizoutput.png");
//		Desktop.getDesktop().open(new File("./input_output/GraphVizoutput.png"));
		// System.out.println(builder.toString());
	}
}
