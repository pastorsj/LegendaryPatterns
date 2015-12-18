package legendaryClasses;

import java.util.ArrayList;
import java.util.List;

import legendaryInterfaces.IClass;
import legendaryInterfaces.IField;
import legendaryInterfaces.IMethod;

/*
 * Author: Jason Lane
 */
public class Class implements IClass{

	private String className;
	private String superClassName;
	private List<String> interfaces;
	private List<IMethod> methods;
	private List<IField> fields;
	private boolean isInterface;
	
	public Class() {
		this.className = "";
		this.superClassName = "";
		this.interfaces = new ArrayList<String>();
		this.methods = new ArrayList<IMethod>();
		this.fields = new ArrayList<IField>();
		this.isInterface = false;
	}
	
	@Override
	public void setClassName(String className) {
		// TODO Auto-generated method stub
		this.className = className.substring(className.lastIndexOf("/")+1);
	}

	@Override
	public void setSuper(String superClassName) {
		// TODO Auto-generated method stub
		this.superClassName = superClassName;
	}

	@Override
	public void setInterfaces(List<String> interfaces) {
		// TODO Auto-generated method stub
		this.interfaces = interfaces;
	}

	@Override
	public void addMethod(IMethod method) {
		// TODO Auto-generated method stub
		this.methods.add(method);
	}

	@Override
	public void addField(IField field) {
		// TODO Auto-generated method stub
		this.fields.add(field);
	}

	@Override
	public String getClassName() {
		// TODO Auto-generated method stub
		return this.className;
	}

	@Override
	public String getSuperName() {
		// TODO Auto-generated method stub
		return this.superClassName;
	}

	@Override
	public List<String> getInterfaces() {
		// TODO Auto-generated method stub
		return this.interfaces;
	}

	@Override
	public List<IMethod> getMethods() {
		// TODO Auto-generated method stub
		return this.methods;
	}

	@Override
	public List<IField> getFields() {
		// TODO Auto-generated method stub
		return this.fields;
	}

	@Override
	public void setIsInterface(boolean isInterface) {
		this.isInterface = isInterface;
	}
	
	@Override
	public boolean isInterface() {
		// TODO Auto-generated method stub
		return this.isInterface;
	}

}
