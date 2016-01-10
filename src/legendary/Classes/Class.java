package legendary.Classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import legendary.Interfaces.IClass;
import legendary.Interfaces.IField;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.ITraverser;
import legendary.Interfaces.IVisitor;

/*
 * Author: Jason Lane
 */
public class Class implements IClass, ITraverser {

	private String className;
	private String superClassName;
	private List<String> interfaces;
	private List<IMethod> methods;
	private List<IField> fields;
	private Set<String> usesClasses;
	private Set<String> associationClasses;
	private boolean isInterface;

	public Class() {
		this.className = "";
		this.superClassName = "";
		this.interfaces = new ArrayList<String>();
		this.methods = new ArrayList<IMethod>();
		this.fields = new ArrayList<IField>();
		this.usesClasses = new HashSet<String>();
		this.associationClasses = new HashSet<String>();
		this.isInterface = false;
	}

	@Override
	public void setClassName(String className) {
		// TODO Auto-generated method stub
		this.className = className.substring(className.lastIndexOf("/") + 1);
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

	@Override
	public void addUsesClass(String uclass) {
		if (this.associationClasses.contains(uclass))
			return;
		if (!this.usesClasses.contains(uclass)) {
			this.usesClasses.add(uclass);
		}
	}

	@Override
	public Set<String> getUsesClasses() {
		// TODO Auto-generated method stub
		return this.usesClasses;
	}

	@Override
	public void addAssociationClass(String aclass) {
		// TODO Auto-generated method stub
		if (this.usesClasses.contains(aclass))
			usesClasses.remove(aclass);
		if (!this.associationClasses.contains(aclass)) {
			this.associationClasses.add(aclass);
		}
	}

	@Override
	public Set<String> getAssociationClasses() {
		// TODO Auto-generated method stub
		return this.associationClasses;
	}

	@Override
	public void accept(IVisitor v) {
		v.previsit(this);
		v.visit(this);
		v.postvisit(this);		
	}

}
