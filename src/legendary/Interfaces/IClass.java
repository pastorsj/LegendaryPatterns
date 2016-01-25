package legendary.Interfaces;

import java.util.List;
import java.util.Map;

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
	public Map<String, Map<List<String>, IMethod>> getMethods();
	public List<IField> getFields();
	public void setIsInterface(boolean isInterface);
	public boolean isInterface();
	public List<IMethod> getMethodObjects();
	public int getCreationOrder();
};
