package testingLegends;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import legendary.Classes.ClassParser;
import legendary.Classes.LegendaryClass;
import legendary.Classes.LegendaryField;
import legendary.Classes.LegendaryMethod;
import legendary.Classes.LegendaryModel;
import legendary.Classes.Relations;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IField;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.asm.DesignParser;

public class GraphVizTest {

	private IModel testModel;
	private IClass testClass1;
	private IClass testClass2;
	private IClass testClass3;
	private IClass testClass4;
	private IMethod testMethod;
	private IMethod callMethod1;
	private IMethod callMethod2;
	private IMethod callMethod3;
	private IField testField1;
	private IField testField2;
	private IField testField3;
	private IField testField4;
	private StringBuilder builder;
	private ClassParser parser;
	private Map<Relations, String> relationRep;

	@Before
	public void setUp() {
		DesignParser.packageName = "testingLegends";
		this.testModel = new LegendaryModel();
		this.testClass1 = new LegendaryClass();
		this.testClass2 = new LegendaryClass();
		this.testClass3 = new LegendaryClass();
		this.testClass4 = new LegendaryClass();
		this.testMethod = new LegendaryMethod();
		this.callMethod1 = new LegendaryMethod();
		this.callMethod2 = new LegendaryMethod();
		this.callMethod3 = new LegendaryMethod();
		this.testField1 = new LegendaryField();
		this.testField2 = new LegendaryField();
		this.testField3 = new LegendaryField();
		this.testField4 = new LegendaryField();
		this.builder = new StringBuilder();
		this.parser = ClassParser.getInstance();
		this.initializeFields();
		this.initializeMethods();
		this.initializeClasses();
		this.relationRep = new HashMap<>();
		this.initializeRep();
	}

	private void initializeFields() {
		this.testField1.setAccess("+");
		this.testField1.setFieldName("TestField1");
		this.testField1.setType("V");
		this.testField2.setAccess("#");
		this.testField2.setFieldName("TestField2");
		this.testField2.setType("S");
		this.testField3.setAccess("-");
		this.testField3.setFieldName("TestField3");
		this.testField3.setType("Z");
		this.testField4.setAccess("");
		this.testField4.setFieldName("TestField4");
		this.testField4.setType("I");
	}

	@After
	public void takeDown() {
		this.testModel = null;
		this.testClass1 = null;
		this.testClass2 = null;
		this.testClass3 = null;
		this.testClass4 = null;
		this.testMethod = null;
		this.testField1 = null;
		this.testField2 = null;
		this.testField3 = null;
		this.testField4 = null;
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
		this.testClass1.addField(testField1);
		this.testClass2.addField(testField1);
		this.testClass2.addField(testField2);
		this.testClass3.addField(testField1);
		this.testClass3.addField(testField2);
		this.testClass3.addField(testField3);
		this.testClass4.addField(testField1);
		this.testClass4.addField(testField2);
		this.testClass4.addField(testField3);
		this.testClass4.addField(testField4);
	}

	@Test
	public void testBaseCaseGraphViz() throws IOException {
		this.testModel.addClass(testClass1);
		this.testModel.convertToGraph();
		this.parser.makeGraphViz(testModel, builder);
		String formattedOutput = String.format(
				"digraph G{node [shape = \"record\"]%s [label = \"{%s|%s %s: %s\\l|%s %s() : %s\\l}\"]}",
				this.testClass1.getClassName(), this.testClass1.getClassName(), this.testField1.getAccess(),
				this.testField1.getFieldName(), this.testField1.getType(), this.testMethod.getAccess(),
				this.testMethod.getMethodName(), this.testMethod.getReturnType());
		assertEquals(this.removeSpecialCharacters(formattedOutput),
				this.removeSpecialCharacters(this.builder.toString()));
	}

	@Test
	public void testComplicatedCaseGraphViz() throws IOException {
		this.testModel.addClass(testClass1);
		this.testModel.addClass(testClass2);
		this.testModel.addClass(testClass3);
		this.testModel.addClass(testClass4);
		this.testModel.convertToGraph();
		this.parser.makeGraphViz(testModel, builder);
		String formattedOutput = String.format(
				"digraph G{node [shape = \"record\"]%s [label = \"{%s|%s %s: %s\\l|%s %s() : %s\\l}\"]"
						+ "%s [label = \"{%s|%s %s: %s\\l%s %s: %s\\l|%s %s() : %s\\l}\"]"
						+ "%s [label = \"{%s|%s %s: %s\\l%s %s: %s\\l%s %s: %s\\l|%s %s() : %s\\l}\"]"
						+ "%s [label = \"{%s|%s %s: %s\\l%s %s: %s\\l%s %s: %s\\l%s %s: %s\\l|%s %s() : %s\\l}\"]}",
				this.testClass1.getClassName(), this.testClass1.getClassName(), this.testField1.getAccess(),
				this.testField1.getFieldName(), this.testField1.getType(), this.testMethod.getAccess(),
				this.testMethod.getMethodName(), this.testMethod.getReturnType(), this.testClass2.getClassName(),
				this.testClass2.getClassName(), this.testField1.getAccess(), this.testField1.getFieldName(),
				this.testField1.getType(), this.testField2.getAccess(), this.testField2.getFieldName(),
				this.testField2.getType(), this.callMethod1.getAccess(), this.callMethod1.getMethodName(),
				this.callMethod1.getReturnType(), this.testClass3.getClassName(), this.testClass3.getClassName(),
				this.testField1.getAccess(), this.testField1.getFieldName(), this.testField1.getType(),
				this.testField2.getAccess(), this.testField2.getFieldName(), this.testField2.getType(),
				this.testField3.getAccess(), this.testField3.getFieldName(), this.testField3.getType(),
				this.callMethod2.getAccess(), this.callMethod2.getMethodName(), this.callMethod2.getReturnType(),
				this.testClass4.getClassName(), this.testClass4.getClassName(), this.testField1.getAccess(),
				this.testField1.getFieldName(), this.testField1.getType(), this.testField2.getAccess(),
				this.testField2.getFieldName(), this.testField2.getType(), this.testField3.getAccess(),
				this.testField3.getFieldName(), this.testField3.getType(), this.testField4.getAccess(),
				this.testField4.getFieldName(), this.testField4.getType(), this.callMethod3.getAccess(),
				this.callMethod3.getMethodName(), this.callMethod3.getReturnType());
		assertEquals(this.removeSpecialCharacters(formattedOutput),
				this.removeSpecialCharacters(this.builder.toString()));
	}

	@Test
	public void testComplicatedCaseGraphVizWithArrows() throws IOException {
		this.testModel.addClass(testClass1);
		this.testModel.addClass(testClass2);
		this.testModel.addClass(testClass3);
		this.testModel.addClass(testClass4);
		this.testModel.addRelation(this.testClass1.getClassName(), this.testClass2.getClassName(), Relations.EXTENDS);
		this.testModel.addRelation(this.testClass1.getClassName(), this.testClass3.getClassName(),
				Relations.ASSOCIATES);
		this.testModel.addRelation(this.testClass2.getClassName(), this.testClass3.getClassName(),
				Relations.IMPLEMENTS);
		this.testModel.addRelation(this.testClass3.getClassName(), this.testClass4.getClassName(), Relations.USES);
		this.testModel.convertToGraph();
		this.parser.makeGraphViz(testModel, builder);
		String formattedOutput = String.format(
				"digraph G{node [shape = \"record\"]%s [label = \"{%s|%s %s: %s\\l|%s %s() : %s\\l}\"]"
						+ "%s [label = \"{%s|%s %s: %s\\l%s %s: %s\\l|%s %s() : %s\\l}\"]"
						+ "%s [label = \"{%s|%s %s: %s\\l%s %s: %s\\l%s %s: %s\\l|%s %s() : %s\\l}\"]"
						+ "%s [label = \"{%s|%s %s: %s\\l%s %s: %s\\l%s %s: %s\\l%s %s: %s\\l|%s %s() : %s\\l}\"]"
						+ "%sTestClass1->TestClass3%sTestClass1->TestClass2%sTestClass2->TestClass3%sTestClass3->TestClass4}",
				this.testClass1.getClassName(), this.testClass1.getClassName(), this.testField1.getAccess(),
				this.testField1.getFieldName(), this.testField1.getType(), this.testMethod.getAccess(),
				this.testMethod.getMethodName(), this.testMethod.getReturnType(), this.testClass2.getClassName(),
				this.testClass2.getClassName(), this.testField1.getAccess(), this.testField1.getFieldName(),
				this.testField1.getType(), this.testField2.getAccess(), this.testField2.getFieldName(),
				this.testField2.getType(), this.callMethod1.getAccess(), this.callMethod1.getMethodName(),
				this.callMethod1.getReturnType(), this.testClass3.getClassName(), this.testClass3.getClassName(),
				this.testField1.getAccess(), this.testField1.getFieldName(), this.testField1.getType(),
				this.testField2.getAccess(), this.testField2.getFieldName(), this.testField2.getType(),
				this.testField3.getAccess(), this.testField3.getFieldName(), this.testField3.getType(),
				this.callMethod2.getAccess(), this.callMethod2.getMethodName(), this.callMethod2.getReturnType(),
				this.testClass4.getClassName(), this.testClass4.getClassName(), this.testField1.getAccess(),
				this.testField1.getFieldName(), this.testField1.getType(), this.testField2.getAccess(),
				this.testField2.getFieldName(), this.testField2.getType(), this.testField3.getAccess(),
				this.testField3.getFieldName(), this.testField3.getType(), this.testField4.getAccess(),
				this.testField4.getFieldName(), this.testField4.getType(), this.callMethod3.getAccess(),
				this.callMethod3.getMethodName(), this.callMethod3.getReturnType(),
				this.relationRep.get(Relations.ASSOCIATES), this.relationRep.get(Relations.EXTENDS),
				this.relationRep.get(Relations.IMPLEMENTS), this.relationRep.get(Relations.USES));
		assertEquals(this.removeSpecialCharacters(formattedOutput),
				this.removeSpecialCharacters(this.builder.toString()));
	}

	private String removeSpecialCharacters(String str) {
		return str.replace("\n", "").replace("\t", "");
	}

	private void initializeRep() {
		this.relationRep.put(Relations.ASSOCIATES, "\tedge [style = \"solid\"] [arrowhead = \"open\"]\n\t");
		this.relationRep.put(Relations.EXTENDS, "\tedge [style = \"solid\"] [arrowhead = \"empty\"]\n\t");
		this.relationRep.put(Relations.IMPLEMENTS, "\tedge [style = \"dashed\"] [arrowhead = \"empty\"]\n\t");
		this.relationRep.put(Relations.USES, "\tedge [style = \"dashed\"] [arrowhead = \"open\"]\n\t");
	}
}
