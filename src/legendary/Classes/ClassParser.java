package legendary.Classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.Interfaces.IPatternDetector;

// TODO: Auto-generated Javadoc
/**
 * The Class ClassParser.
 */
/*
 * Author: Sam Pastoriza
 */
public class ClassParser {
	
	/** The instance. */
	private static ClassParser instance;
	
	/** The detect. */
	private IPatternDetector detect;

	/**
	 * Instantiates a new class parser.
	 */
	private ClassParser() {
	}

	/**
	 * Gets the single instance of ClassParser.
	 *
	 * @return single instance of ClassParser
	 */
	public static ClassParser getInstance() {
		if (instance == null)
			instance = new ClassParser();
		return instance;
	}

	/**
	 * Adds the detector.
	 *
	 * @param detect the detect
	 */
	public void addDetector(IPatternDetector detect) {
		this.detect = detect;
	}

	/**
	 * Make sd edit.
	 *
	 * @param classname the classname
	 * @param methodname the methodname
	 * @param depth the depth
	 * @param model the model
	 * @param builder the builder
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void makeSDEdit(String classname, String methodname, int depth, IModel model, StringBuilder builder)
			throws IOException {
		String params = methodname.substring(methodname.indexOf("(") + 1, methodname.indexOf(")"));
		methodname = methodname.substring(0, methodname.indexOf("("));
		String[] paramTypeSplit;
		List<String> paramTypes = new ArrayList<>();
		if (params.length() != 0) {
			paramTypeSplit = params.split(", ");
			for (String s : paramTypeSplit) {
				paramTypes.add((s.contains(" ") ? s.substring(0, s.indexOf(" ")) : s));
			}
		}
		outerloop: for (IClass c : model.getClasses()) {
			if (c.getClassName().equals(classname)) {
				for (IMethod method : c.getMethodObjects()) {
					List<String> genParams = new ArrayList<>();
					for (String s : method.getParameters()) {
						if (s.contains(":"))
							s = s.substring(s.lastIndexOf(":")+1);
						genParams.add(
								(s.contains("<") ? (s.substring(0, s.indexOf("<") + 1).replace("\\", "") + "?>") : s));
					}
					if (method.getMethodName().equals(methodname)) {
						if (paramTypes.equals(genParams)) {
							@SuppressWarnings("unused")
							SDEditOutputStream stream = new SDEditOutputStream(model, depth, builder);
							break outerloop;
						}
					}
				}
			}
		}
	}

	/**
	 * Make graph viz.
	 *
	 * @param m the m
	 * @param builder the builder
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void makeGraphViz(IModel m, StringBuilder builder) throws IOException {
		@SuppressWarnings("unused")
		GraphVizOutputStream dotVisitor = new GraphVizOutputStream(builder,
				(this.detect == null) ? new HashMap<>() : this.detect.detect(m), m);
	}
}
