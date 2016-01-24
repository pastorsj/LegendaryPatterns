package legendary.Classes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.Interfaces.IPatternDetector;
import legendary.Interfaces.ITraverser;
import legendary.Interfaces.IVisitor;

/*
 * Author: Sam Pastoriza
 */
public class ClassParser {
	private static ClassParser instance;
	private IPatternDetector detect;

	private ClassParser() {
	}

	public static ClassParser getInstance() {
		if (instance == null)
			instance = new ClassParser();
		return instance;
	}

	public void addDetector(IPatternDetector detect) {
		this.detect = detect;
	}

	public void makeSDEdit(String classname, String methodname, int depth,
			IModel model, StringBuilder builder) throws IOException {
		String params = methodname.substring(methodname.indexOf("(") + 1,
				methodname.indexOf(")"));
		methodname = methodname.substring(0, methodname.indexOf("("));
		String[] paramTypeSplit;
		List<String> paramTypes = new ArrayList<>();
		if (params.length() != 0) {
			paramTypeSplit = params.split(", ");
			for (String s : paramTypeSplit) {
				paramTypes
						.add((s.contains(" ") ? s.substring(0, s.indexOf(" "))
								: s));
			}
		}
		outerloop: for (IClass c : model.getClasses()) {
			if (c.getClassName().equals(classname)) {
				for (IMethod method : c.getMethods().values()) {
					List<String> genParams = new ArrayList<>();
					for (String s : method.getParameters()) {
						genParams.add((s.contains("<") ? (s.substring(0,
								s.indexOf("<") + 1).replace("\\", "") + "?>")
								: s));
					}
					if (method.getMethodName().equals(methodname)) {
						if (paramTypes.equals(genParams)) {
							SDEditOutputStream stream = new SDEditOutputStream(
									model, depth, builder);
							ITraverser t = (ITraverser) method;
							t.accept(stream);
							break outerloop;
						}
					}
				}
			}
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(
				"./input_output/text.sd"));
		writer.write(builder.toString());
		writer.close();
		// Runtime rt = Runtime.getRuntime();
		// rt.exec("java -jar ./lib/sdedit-4.2-beta1.jar -o
		// ./input_output/SDEoutput.png -t png ./input_output/text.sd");
		// Desktop.getDesktop().open(new File("./input_output/SDEoutput.png"));
		// System.out.println(builder.toString());
	}

	public void makeGraphViz(IModel m, StringBuilder builder)
			throws IOException {
		IVisitor dotVisitor = new GraphVizOutputStream(builder,
				(this.detect == null) ? new HashMap<>() : this.detect.detect(m));
		ITraverser t = (ITraverser) m;
		t.accept(dotVisitor);
		BufferedWriter writer = new BufferedWriter(new FileWriter(
				"./input_output/text.dot"));
		writer.write(builder.toString());
		writer.close();
		Runtime rt = Runtime.getRuntime();
		rt.exec("./lib/Graphviz2.38/bin/dot -Tpng ./input_output/text.dot -o"
				+ "./input_output/GraphVizoutput.png");
		// Desktop.getDesktop().open(new
		// File("./input_output/GraphVizoutput.png"));
		// System.out.println(builder.toString());
	}
}
