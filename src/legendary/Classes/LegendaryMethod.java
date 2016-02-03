package legendary.Classes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import legendary.Interfaces.IMethod;
import legendary.visitor.ITraverser;
import legendary.visitor.IVisitor;

// TODO: Auto-generated Javadoc
/**
 * The Class LegendaryMethod.
 */
/*
 * Author: Jason Lane
 */
public class LegendaryMethod implements IMethod, ITraverser {

	/** The method access. */
	private String methodAccess;
	
	/** The method name. */
	private String methodName;
	
	/** The parameters. */
	private List<String> parameters;
	
	/** The method call stack. */
	private Queue<List<List<String>>> methodCallStack;
	
	/** The method return type. */
	private String methodReturnType;

	/**
	 * Instantiates a new legendary method.
	 */
	public LegendaryMethod() {
		this.methodAccess = "";
		this.methodName = "";
		this.parameters = new ArrayList<>();
		this.methodCallStack = new LinkedList<>();
		this.methodReturnType = "";
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IMethod#setAccess(java.lang.String)
	 */
	@Override
	public void setAccess(String accessType) {
		this.methodAccess = accessType;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IMethod#setMethodName(java.lang.String)
	 */
	@Override
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IMethod#setParameters(java.util.List)
	 */
	@Override
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IMethod#setReturnType(java.lang.String)
	 */
	@Override
	public void setReturnType(String returnType) {
		this.methodReturnType = returnType;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IMethod#getAccess()
	 */
	@Override
	public String getAccess() {
		return this.methodAccess;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IMethod#getMethodName()
	 */
	@Override
	public String getMethodName() {
		return this.methodName;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IMethod#getParameters()
	 */
	@Override
	public List<String> getParameters() {
		return this.parameters;
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IMethod#getReturnType()
	 */
	@Override
	public String getReturnType() {
		return this.methodReturnType;
	}

	/* (non-Javadoc)
	 * @see legendary.visitor.ITraverser#accept(legendary.visitor.IVisitor)
	 */
	@Override
	public void accept(IVisitor v) {
		v.preVisit(this);
		v.visit(this);
		v.postVisit(this);
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IMethod#addMethodToCallStack(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public void addMethodToCallStack(String methodOwner, String className,
			String methodName, List<String> params) {
		List<String> classToMethod = new ArrayList<>();
		classToMethod.add(methodOwner);
		classToMethod.add(className);
		classToMethod.add(methodName);
		List<List<String>> res = new ArrayList<>();
		res.add(classToMethod);
		res.add(params);
		this.methodCallStack.add(res);
	}

	/* (non-Javadoc)
	 * @see legendary.Interfaces.IMethod#getCallStack()
	 */
	@Override
	public Queue<List<List<String>>> getCallStack() {
		return this.methodCallStack;
	}

}
