package testingLegends;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import legendary.Classes.ClassParser;
import legendary.Classes.LegendaryClass;
import legendary.Classes.LegendaryMethod;
import legendary.Classes.LegendaryModel;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.asm.DesignParser;

public class SDEditTest {

	private IModel testModel;
	private IClass testClass1;
	private IClass testClass2;
	private IClass testClass3;
	private IClass testClass4;
	private IMethod testMethod;
	private IMethod callMethod1;
	private IMethod callMethod2;
	private IMethod callMethod3;
	private StringBuilder builder;
	private ClassParser parser;
	
	@Before
	public void setUp() {
		DesignParser.packageName = "";
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
		this.testModel.convertToGraph();
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
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass2.getClassName(), this.callMethod1.getMethodName(), new ArrayList<String>());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass3.getClassName(), this.callMethod2.getMethodName(), new ArrayList<String>());
		List<List<String>> res = this.testMethod.getCallStack().peek();
		assertEquals("[TestClass1, TestClass2, testCallMethod1]", Arrays.toString(res.get(0).toArray()));
	}
	
	@Test
	public void testBaseCaseSDEdit() throws IOException {
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass2.getClassName(), this.callMethod1.getMethodName(), new ArrayList<String>());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass3.getClassName(), this.callMethod2.getMethodName(), new ArrayList<String>());
		this.parser.makeSDEdit("TestClass1", "testOwnerMethod()", 5, this.testModel, this.builder);
		List<String> out = new ArrayList<>();
		out.add("TestClass3:TestClass3\n");
		out.add("TestClass2:TestClass2\n");
		out.add("TestClass1:TestClass1\n");
		out.add("TestClass1:int=TestClass2.testCallMethod1()\n");
		out.add("TestClass1:double=TestClass3.testCallMethod2()\n");
		System.out.println(this.builder.toString());
		assertTrue(this.builder.toString().contains(out.get(0)));
		assertTrue(this.builder.toString().contains(out.get(1)));
		assertTrue(this.builder.toString().contains(out.get(2)));
		assertTrue(this.builder.toString().contains(out.get(3)));
		assertTrue(this.builder.toString().contains(out.get(4)));
	}
	
	@Test
	public void testCaseSDEdit1() throws IOException {
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass2.getClassName(), this.callMethod1.getMethodName(), new ArrayList<String>());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass3.getClassName(), this.callMethod2.getMethodName(), new ArrayList<String>());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass4.getClassName(), this.callMethod3.getMethodName(), new ArrayList<String>());
		this.parser.makeSDEdit("TestClass1", "testOwnerMethod()", 5, this.testModel, this.builder);
		System.out.println(this.builder);
		List<String> out = new ArrayList<>();
		out.add("TestClass4:TestClass4\n");
		out.add("TestClass3:TestClass3\n");
		out.add("TestClass2:TestClass2\n");
		out.add("TestClass1:TestClass1\n");
		out.add("TestClass1:int=TestClass2.testCallMethod1()\n");
		out.add("TestClass1:double=TestClass3.testCallMethod2()\n");
		out.add("TestClass1:List<String>=TestClass4.testCallMethod3()\n");
		assertTrue(this.builder.toString().contains(out.get(0)));
		assertTrue(this.builder.toString().contains(out.get(1)));
		assertTrue(this.builder.toString().contains(out.get(2)));
		assertTrue(this.builder.toString().contains(out.get(3)));
		assertTrue(this.builder.toString().contains(out.get(4)));
		assertTrue(this.builder.toString().contains(out.get(5)));
		assertTrue(this.builder.toString().contains(out.get(6)));
	}
	
	@Test
	public void testCaseSDEditDifferentDepth() throws IOException {
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass2.getClassName(), this.callMethod1.getMethodName(), new ArrayList<String>());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass3.getClassName(), this.callMethod2.getMethodName(), new ArrayList<String>());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass4.getClassName(), this.callMethod3.getMethodName(), new ArrayList<String>());
		this.parser.makeSDEdit("TestClass1", "testOwnerMethod()", 10, this.testModel, this.builder);
		List<String> out = new ArrayList<>();
		out.add("TestClass4:TestClass4\n");
		out.add("TestClass3:TestClass3\n");
		out.add("TestClass2:TestClass2\n");
		out.add("TestClass1:TestClass1\n");
		out.add("TestClass1:int=TestClass2.testCallMethod1()\n");
		out.add("TestClass1:double=TestClass3.testCallMethod2()\n");
		out.add("TestClass1:List<String>=TestClass4.testCallMethod3()\n");
		assertTrue(this.builder.toString().contains(out.get(0)));
		assertTrue(this.builder.toString().contains(out.get(1)));
		assertTrue(this.builder.toString().contains(out.get(2)));
		assertTrue(this.builder.toString().contains(out.get(3)));
		assertTrue(this.builder.toString().contains(out.get(4)));
		assertTrue(this.builder.toString().contains(out.get(5)));
		assertTrue(this.builder.toString().contains(out.get(6)));
	}
	
	@Test
	public void testBaseCaseSDEdit1() throws IOException {
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass4.getClassName(), this.callMethod3.getMethodName(), new ArrayList<String>());
		this.parser.makeSDEdit("TestClass1", "testOwnerMethod()", 10, this.testModel, this.builder);
		List<String> out = new ArrayList<>();
		out.add("TestClass4:TestClass4\n");
		out.add("TestClass1:TestClass1\n");
		out.add("TestClass1:List<String>=TestClass4.testCallMethod3()\n");
		assertTrue(this.builder.toString().contains(out.get(0)));
		assertTrue(this.builder.toString().contains(out.get(1)));
		assertTrue(this.builder.toString().contains(out.get(2)));
	}
	
	@Test
	public void testCaseSDEditEdgeDepth0() throws IOException {
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass2.getClassName(), this.callMethod1.getMethodName(), new ArrayList<String>());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass3.getClassName(), this.callMethod2.getMethodName(), new ArrayList<String>());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass4.getClassName(), this.callMethod3.getMethodName(), new ArrayList<String>());
		this.parser.makeSDEdit("TestClass1", "testOwnerMethod()", 0, this.testModel, this.builder);
		assertEquals("\n", this.builder.toString());
	}
	
	@Test
	public void testCaseSDEditEdgeDepth1() throws IOException {
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass2.getClassName(), this.callMethod1.getMethodName(), new ArrayList<String>());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass3.getClassName(), this.callMethod2.getMethodName(), new ArrayList<String>());
		this.testMethod.addMethodToCallStack(this.testClass1.getClassName(), this.testClass4.getClassName(), this.callMethod3.getMethodName(), new ArrayList<String>());
		this.parser.makeSDEdit("TestClass1", "testOwnerMethod()", 1, this.testModel, this.builder);
		assertEquals("\n", this.builder.toString());
	}
}
