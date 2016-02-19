package legendary.Classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.Interfaces.IPattern;
import legendary.Interfaces.IPatternDetector;
import legendary.ParsingUtil.GeneralUtil;

/*
 * This class allows the user to parse the data in the model
 * into a UML representation and/or a Sequence Diagram 
 * representation
 * Author: Sam Pastoriza
 */
public class ClassParser {

	/** A Singleton instance of ClassParser */
	private static ClassParser instance;

	/** The pattern detector for uml diagrams */
	private IPatternDetector detect;

	private Map<Class<? extends IPattern>, Set<IClass>> patMap;

	private IModel model;

	/**
	 * Instantiates a new class parser.
	 */
	private ClassParser() {
	}

	/**
	 * Gets and sets the single instance of ClassParser.
	 *
	 * @return single instance of ClassParser
	 */
	public static ClassParser getInstance() {
		if (instance == null)
			instance = new ClassParser();
		return instance;
	}

	/**
	 * Adds the pattern detector.
	 *
	 * @param detect
	 *            (see field)
	 */
	public void addDetector(IPatternDetector detect) {
		this.detect = detect;
	}

	/**
	 * Make the sequence diagram given the model and other params listed below
	 *
	 * @param classname
	 *            The Class containing the method we start at
	 * @param methodname
	 *            The method name that we start the sequence diagram
	 * @param depth
	 *            The depth at which we want to generate the diagram
	 * @param model
	 *            The model of the current project
	 * @param builder
	 *            A string builder that will contain the representation of the
	 *            sequence diagram at the end of the method
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
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
				for (IMethod method : c.getMethodObjects()) {
					List<String> genParams = new ArrayList<>();
					for (String s : method.getParameters()) {
						if (s.contains(":"))
							s = s.substring(s.lastIndexOf(":") + 1);
						genParams.add((s.contains("<") ? (s.substring(0,
								s.indexOf("<") + 1).replace("\\", "") + "?>")
								: s));
					}
					if (method.getMethodName().equals(methodname)) {
						if (paramTypes.equals(genParams)) {
							@SuppressWarnings("unused")
							SDEditOutputStream stream = new SDEditOutputStream(
									method, model, depth, builder);
							break outerloop;
						}
					}
				}
			}
		}
	}

	/**
	 * Given the current model of the project, this method generates a UML
	 * diagram representation that can be parsed and exported into a picture
	 * using GraphViz
	 *
	 * @param m
	 *            The model of the current representation of the project
	 * @param builder
	 *            The string builder that will contain the representation of the
	 *            uml diagram at the end of the method
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void makeGraphViz(IModel m, StringBuilder builder)
			throws IOException {
		this.patMap = new HashMap<>();
		this.model = m;
		if (this.detect != null) {
			this.patMap = this.detect.detect(m);
		}
		for (IClass c : m.getClasses()) {
//			c.setDrawable(false);
		}
		@SuppressWarnings("unused")
		GraphVizOutputStream dotVisitor = new GraphVizOutputStream(builder,
				patMap, this.model);
	}

	public IPatternDetector getDetector() {
		return this.detect;
	}

	public void regenGV(){
		StringBuilder sb = new StringBuilder();
		for(IClass c : this.model.getClasses()){
			if(c.isDrawable()){
//				System.out.println(c.getClassName());
			}
		}
		new GraphVizOutputStream(sb, patMap, this.model);
		try {
			GeneralUtil.writeAndExecGraphViz(sb);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
