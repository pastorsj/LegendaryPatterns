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
 * Author: Jason Lane
 */
public class LegendaryClass implements IClass, ITraverser {

	private String className;
	private String superClassName;
	private List<String> interfaces;
	private Map<String, Map<List<String>, IMethod>> methods;
	private List<IField> fields;
	private boolean isInterface;
	private int creationOrder;
	private boolean drawable;
	private static int count;

	public LegendaryClass() {
		this.className = "";
		this.superClassName = "";
		this.interfaces = new ArrayList<String>();
		this.methods = new HashMap<String, Map<List<String>, IMethod>>();
		this.fields = new ArrayList<IField>();
		this.isInterface = false;
		this.drawable = false;
		creationOrder = ++count;
	}

	@Override
	public void setClassName(String className) {
		String name = className.replace("/", ".");
		// name = name.substring(0, name.lastIndexOf(".")) + "::"
		// + name.substring(name.lastIndexOf(".") + 1, name.length());
		this.className = name;
	}

	@Override
	public void setSuper(String superClassName) {
		this.superClassName = superClassName.substring(superClassName.lastIndexOf("/") + 1);
	}

	@Override
	public void setInterfaces(List<String> interfaces) {
		this.interfaces = interfaces;
	}

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

	@Override
	public void addField(IField field) {
		this.fields.add(field);
	}

	@Override
	public String getClassName() {
		return this.className;
	}

	@Override
	public String getSuperName() {
		return this.superClassName;
	}

	@Override
	public List<String> getInterfaces() {
		return this.interfaces;
	}

	@Override
	public Map<String, Map<List<String>, IMethod>> getMethods() {
		return this.methods;
	}

	@Override
	public List<IField> getFields() {
		return this.fields;
	}

	@Override
	public void setIsInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}

	@Override
	public boolean isInterface() {
		return this.isInterface;
	}

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

	@Override
	public int getCreationOrder() {
		return this.creationOrder;
	}

	@Override
	public void setDrawable(boolean drawable) {
		this.drawable = drawable;
	}

	@Override
	public boolean isDrawable() {
		return this.drawable;
	}

}
