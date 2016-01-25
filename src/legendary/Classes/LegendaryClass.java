package legendary.Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IField;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.ITraverser;
import legendary.Interfaces.IVisitor;

/*
 * Author: Jason Lane
 */
public class LegendaryClass implements IClass, ITraverser, Comparable<IClass> {

	private String className;
	private String superClassName;
	private List<String> interfaces;
	private Map<String, Map<List<String>, IMethod>> methods;
	private List<IField> fields;
	private boolean isInterface;
	private int creationOrder;
	private static int count;

	public LegendaryClass() {
		this.className = "";
		this.superClassName = "";
		this.interfaces = new ArrayList<String>();
		this.methods = new HashMap<String, Map<List<String>, IMethod>>();
		this.fields = new ArrayList<IField>();
		this.isInterface = false;
		creationOrder = ++count;
	}

	@Override
	public void setClassName(String className) {
		// TODO Auto-generated method stub
		this.className = className.substring(className.lastIndexOf("/") + 1);
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
		v.previsit(this);
		for (IField f : this.fields) {
			ITraverser t = (ITraverser) f;
			t.accept(v);
		}
		v.visit(this);
		for (IMethod m : this.getMethodObjects()) {
			ITraverser t = (ITraverser) m;
			t.accept(v);
		}
		v.postvisit(this);
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
	public int compareTo(IClass o) {
		// TODO Auto-generated method stub
		return (this.getCreationOrder() > o.getCreationOrder()) ? 1 : 0;
	}

}
