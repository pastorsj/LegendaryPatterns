package legendary.Interfaces;

import java.util.List;
import java.util.Set;

/*
 * Author: Jason Lane
 */

public interface IClass {
	public void setClassName(String className);
	public void setSuper(String superClassName);
	public void setInterfaces(List<String> interfaces);
	public void addMethod(IMethod method);
	public void addField(IField field);
	
	public String getClassName();
	public String getSuperName();
	public List<String> getInterfaces();
	public List<IMethod> getMethods();
	public List<IField> getFields();
	public void setIsInterface(boolean isInterface);
	public boolean isInterface();
	public void addUsesClass(String uclass);
	public Set<String> getUsesClasses();
	public void addAssociationClass(String aclass);
	public Set<String> getAssociationClasses();
	
};
