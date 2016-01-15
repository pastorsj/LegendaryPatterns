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

	public IModel testModel;
	public IClass testClass1;
	public IClass testClass2;
	public IClass testClass3;
	public IClass testClass4;
	public IMethod testMethod;
	public IMethod callMethod1;
	public IMethod callMethod2;
	public IMethod callMethod3;
	public StringBuilder builder;
	public SDEditOutputStream stream;
	public ClassParser parser;
	
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
		this.initializeMethods();
		this.initializeClasses();
		this.testModel.addClass(testClass1);
		this.testModel.addClass(testClass2);
		this.testModel.addClass(testClass3);
		this.testModel.addClass(testClass4);
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
	
	private void initializeMethods() {
		this.testMethod.setReturnType("boolean");
		this.testMethod.setMethodName("testOwnerMethod");
		this.testMethod.setAccess("public");
		this.callMethod1.setReturnType("int");
		this.callMethod1.setMethodName("testCallMethod1");
		this.callMethod1.setAccess("public");
		this.callMethod2.setReturnType("double");
		this.callMethod2.setMethodName("testCallMethod2");
		this.callMethod2.setAccess("public");
		this.callMethod3.setReturnType("List<String>");
		this.callMethod3.setMethodName("testCallMethod3");
		this.callMethod3.setAccess("public");
	}

	private void initializeClasses() {
		this.testClass1.setClassName("TestClass1");
		this.testClass2.setClassName("TestClass2");
		this.testClass3.setClassName("TestClass3");
		this.testClass4.setClassName("TestClass4");
		this.testClass1.addMethod(this.testMethod);
		this.testClass2.addMethod(this.callMethod1);
		this.testClass3.addMethod(this.callMethod2);
		this.testClass4.addMethod(this.callMethod3);
	}

	
	@Test
	public void testCallStack() throws IOException {
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass2.getClassName(), this.callMethod1.getMethodName());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass3.getClassName(), this.callMethod2.getMethodName());
		assertEquals("[TestClass1, TestClass2, testCallMethod1]", Arrays.toString(this.testMethod.getCallStack().peek().toArray()));
	}
	
	@Test
	public void testBaseCaseSDEdit() throws IOException {
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass2.getClassName(), this.callMethod1.getMethodName());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass3.getClassName(), this.callMethod2.getMethodName());
		this.parser.makeSDEdit("TestClass1", "testOwnerMethod()", 5, this.testModel, this.builder);
		assertEquals("TestClass3:TestClass3\n"
				+ "TestClass2:TestClass2\n"
				+ "TestClass1:TestClass1\n\n"
				+ "TestClass1:int=TestClass2.testCallMethod1()\n"
				+ "TestClass1:double=TestClass3.testCallMethod2()\n", this.builder.toString());
	}
	
	@Test
	public void testCaseSDEdit1() throws IOException {
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass2.getClassName(), this.callMethod1.getMethodName());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass3.getClassName(), this.callMethod2.getMethodName());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass4.getClassName(), this.callMethod3.getMethodName());
		this.parser.makeSDEdit("TestClass1", "testOwnerMethod()", 5, this.testModel, this.builder);
		assertEquals("TestClass4:TestClass4\n"
				+ "TestClass3:TestClass3\n"
				+ "TestClass2:TestClass2\n"
				+ "TestClass1:TestClass1\n\n"
				+ "TestClass1:int=TestClass2.testCallMethod1()\n"
				+ "TestClass1:double=TestClass3.testCallMethod2()\n"
				+ "TestClass1:List<String>=TestClass4.testCallMethod3()\n", this.builder.toString());
	}
	
	@Test
	public void testCaseSDEditDifferentDepth() throws IOException {
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass2.getClassName(), this.callMethod1.getMethodName());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass3.getClassName(), this.callMethod2.getMethodName());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass4.getClassName(), this.callMethod3.getMethodName());
		this.parser.makeSDEdit("TestClass1", "testOwnerMethod()", 10, this.testModel, this.builder);
		assertEquals("TestClass4:TestClass4\n"
				+ "TestClass3:TestClass3\n"
				+ "TestClass2:TestClass2\n"
				+ "TestClass1:TestClass1\n\n"
				+ "TestClass1:int=TestClass2.testCallMethod1()\n"
				+ "TestClass1:double=TestClass3.testCallMethod2()\n"
				+ "TestClass1:List<String>=TestClass4.testCallMethod3()\n", this.builder.toString());
	}
	
	@Test
	public void testBaseCaseSDEdit1() throws IOException {
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass4.getClassName(), this.callMethod3.getMethodName());
		this.parser.makeSDEdit("TestClass1", "testOwnerMethod()", 10, this.testModel, this.builder);
		assertEquals("TestClass4:TestClass4\n"
				+ "TestClass1:TestClass1\n\n"
				+ "TestClass1:List<String>=TestClass4.testCallMethod3()\n", this.builder.toString());
	}
	
	@Test
	public void testCaseSDEditEdgeDepth0() throws IOException {
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass2.getClassName(), this.callMethod1.getMethodName());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass3.getClassName(), this.callMethod2.getMethodName());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass4.getClassName(), this.callMethod3.getMethodName());
		this.parser.makeSDEdit("TestClass1", "testOwnerMethod()", 0, this.testModel, this.builder);
		assertEquals("\n", this.builder.toString());
	}
	
	@Test
	public void testCaseSDEditEdgeDepth1() throws IOException {
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass2.getClassName(), this.callMethod1.getMethodName());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass3.getClassName(), this.callMethod2.getMethodName());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass4.getClassName(), this.callMethod3.getMethodName());
		this.parser.makeSDEdit("TestClass1", "testOwnerMethod()", 1, this.testModel, this.builder);
		assertEquals("\n", this.builder.toString());
	}
}
