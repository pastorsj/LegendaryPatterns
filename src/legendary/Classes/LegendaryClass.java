package legendary.Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IField;
import legendary.Interfaces.IMethod;
import legendary.visitor.ITraverser;
import legendary.visitor.IVisitor;

/*
 * A structure that defined what a class in java should 
 * look like
 * Author: Jason Lane
 */
public class LegendaryClass implements IClass, ITraverser {

	/** The class name. */
	private String className;
	
	/** The super class name. */
	private String superClassName;
	
	/** The interfaces. */
	private List<String> interfaces;
	
	/** The methods. */
	private Map<String, Map<List<String>, IMethod>> methods;
	
	/** The fields. */
	private List<IField> fields;
	
	/** If the class is an interface. */
	private boolean isInterface;
	
	/** Whether the class is drawable */
	private boolean drawable;


	/**
	 * Instantiates a new legendary class.
	 */
	public LegendaryClass() {
		this.className = "";
		this.superClassName = "";
		this.interfaces = new ArrayList<String>();
		this.methods = new HashMap<String, Map<List<String>, IMethod>>();
		this.fields = new ArrayList<IField>();
		this.isInterface = false;
		this.drawable = false;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IClass#setClassName(java.lang.String)
	 */
	@Override
	public void setClassName(String className) {
		String name = className.replace("/", ".");
		this.className = name;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IClass#setSuper(java.lang.String)
	 */
	@Override
	public void setSuper(String superClassName) {
		this.superClassName = superClassName.substring(superClassName.lastIndexOf("/") + 1);
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IClass#setInterfaces(java.util.List)
	 */
	@Override
	public void setInterfaces(List<String> interfaces) {
		this.interfaces = interfaces;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IClass#addMethod(legendary.Interfaces.IMethod)
	 */
	@Override
	public void addMethod(IMethod method) {
		if (this.methods.containsKey(method.getMethodName())) {
			methods.get(method.getMethodName()).put(method.getParameters(), method);
		} else {
			Map<List<String>, IMethod> val = new HashMap<>();
			val.put(method.getParameters(), method);
			this.methods.put(method.getMethodName(), val);
		}
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IClass#addField(legendary.Interfaces.IField)
	 */
	@Override
	public void addField(IField field) {
		this.fields.add(field);
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IClass#getClassName()
	 */
	@Override
	public String getClassName() {
		return this.className;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IClass#getSuperName()
	 */
	@Override
	public String getSuperName() {
		return this.superClassName;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IClass#getInterfaces()
	 */
	@Override
	public List<String> getInterfaces() {
		return this.interfaces;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IClass#getMethods()
	 */
	@Override
	public Map<String, Map<List<String>, IMethod>> getMethods() {
		return this.methods;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IClass#getFields()
	 */
	@Override
	public List<IField> getFields() {
		return this.fields;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IClass#setIsInterface(boolean)
	 */
	@Override
	public void setIsInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IClass#isInterface()
	 */
	@Override
	public boolean isInterface() {
		return this.isInterface;
	}

	/* (non-Javadoc)
	 * @see legendary.visitor.ITraverser#accept(legendary.visitor.IVisitor)
	 */
	@Override
	public void accept(IVisitor v) {
		if (this.isDrawable()) {
			v.preVisit(this);
			for (IField f : this.fields) {
				ITraverser t = (ITraverser) f;
				t.accept(v);
			}
			v.visit(this);
			for (IMethod m : this.getMethodObjects()) {
				ITraverser t = (ITraverser) m;
				t.accept(v);
			}
			v.postVisit(this);
		}
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IClass#getMethodObjects()
	 */
	@Override
	public List<IMethod> getMethodObjects() {
		List<IMethod> methodSet = new ArrayList<>();
		for (Map<List<String>, IMethod> method : this.getMethods().values()) {
			for (IMethod method2 : method.values()) {
				methodSet.add(method2);
			}
		}
		return methodSet;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IClass#setDrawable(boolean)
	 */
	@Override
	public void setDrawable(boolean drawable) {
		this.drawable = drawable;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IClass#isDrawable()
	 */
	@Override
	public boolean isDrawable() {
		return this.drawable;
	}

}
