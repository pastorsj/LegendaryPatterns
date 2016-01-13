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
public class LegendaryClass implements IClass, ITraverser {

	private String className;
	private String superClassName;
	private List<String> interfaces;
	private Map<String, IMethod> methods;
	private List<IField> fields;
	private boolean isInterface;

	public LegendaryClass() {
		this.className = "";
		this.superClassName = "";
		this.interfaces = new ArrayList<String>();
		this.methods = new HashMap<String, IMethod>();
		this.fields = new ArrayList<IField>();
		this.isInterface = false;
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
		this.methods.put(method.getMethodName(), method);
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
	public Map<String, IMethod> getMethods() {
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
		for(IField f : this.fields) {
			ITraverser t = (ITraverser) f;
			t.accept(v);
		}
		v.visit(this);
		for(IMethod m : this.methods.values()) {
			ITraverser t = (ITraverser) m;
			t.accept(v);
		}
		v.postvisit(this);		
	}

	@Override
	public List<IMethod> getMethodObjects() {
		// TODO Auto-generated method stub
		List<IMethod> methodSet = new ArrayList<>();
		for(IMethod method : this.getMethods().values()) {
			methodSet.add(method);
		}
		return methodSet;
	}

}
