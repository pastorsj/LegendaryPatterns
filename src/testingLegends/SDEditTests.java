package testingLegends;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import legendary.Classes.ClassParser;
import legendary.Classes.LegendaryClass;
import legendary.Classes.LegendaryMethod;
import legendary.Classes.LegendaryModel;
import legendary.Classes.SDEditOutputStream;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;

public class SDEditTests {

	IModel testModel;
	IClass testClass1;
	IClass testClass2;
	IClass testClass3;
	IClass testClass4;
	IMethod testMethod;
	IMethod callMethod1;
	IMethod callMethod2;
	IMethod callMethod3;
	StringBuilder builder;
	SDEditOutputStream stream;
	ClassParser parser;
	
	@Before
	public void setUp() {
		this.testModel = new LegendaryModel();
		this.testClass1 = new LegendaryClass();
		this.testClass2 = new LegendaryClass();
		this.testClass3 = new LegendaryClass();
		this.testClass4 = new LegendaryClass();
		this.testMethod = new LegendaryMethod();
		this.callMethod1 = new LegendaryMethod();
		this.callMethod2 = new LegendaryMethod();
		this.callMethod3 = new LegendaryMethod();
		this.builder = new StringBuilder();
		this.parser = ClassParser.getInstance();
		this.initializeClasses();
		this.initializeMethods();
		this.testModel.addClass(testClass1);
		this.testModel.addClass(testClass2);
		this.testModel.addClass(testClass3);
		this.testModel.addClass(testClass4);
	}
	
	private void initializeMethods() {
		this.testMethod.setReturnType("boolean");
		this.testMethod.setMethodName("testOwnerMethod");
		this.testMethod.setParameters(new ArrayList<String>());
		this.testMethod.setAccess("public");
		this.callMethod1.setReturnType("int");
		this.callMethod1.setMethodName("testCallMethod1");
		this.callMethod1.setParameters(new ArrayList<String>());
		this.callMethod1.setAccess("public");
		this.callMethod2.setReturnType("double");
		this.callMethod2.setMethodName("testCallMethod2");
		this.callMethod2.setParameters(new ArrayList<String>());
		this.callMethod2.setAccess("public");
		this.callMethod3.setReturnType("List<String>");
		this.callMethod3.setMethodName("testCallMethod3");
		this.callMethod3.setParameters(new ArrayList<String>());
		this.callMethod3.setAccess("public");
	}

	private void initializeClasses() {
		this.testClass1.setClassName("TestClass1");
		this.testClass2.setClassName("TestClass2");
		this.testClass1.addMethod(testMethod);
		this.testClass2.addMethod(callMethod1);
		this.testClass3.addMethod(callMethod2);
		this.testClass4.addMethod(callMethod3);
	}

	@After
	public void takeDown() {
		this.testModel = null;
		this.testClass1 = null;
		this.testClass2 = null;
		this.testClass3 = null;
		this.testClass4 = null;
		this.testMethod = null;
		this.callMethod1 = null;
		this.callMethod2 = null;
		this.callMethod3 = null;
	}
	
	@Test
	public void testCallStack() throws IOException {
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass2.getClassName(), this.callMethod1.getMethodName());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass3.getClassName(), this.callMethod2.getMethodName());
		assertEquals("[TestClass1, TestClass2, testCallMethod1]", Arrays.toString(this.testMethod.getCallStack().peek().toArray()));
		this.parser.makeSDEdit("TestClass1", "testOwnerMethod()", 5, this.testModel, this.builder);	
	}
}
