package testingLegends;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import legendary.Classes.LegendaryClass;
import legendary.Classes.LegendaryField;
import legendary.Classes.LegendaryMethod;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IField;
import legendary.Interfaces.IMethod;

public class ClassTest {

	IClass legendaryTestClass;

	@Before
	public void setUp() {
		this.legendaryTestClass = new LegendaryClass();
	}

	@After
	public void takeDown() {
		this.legendaryTestClass = null;
	}

	@Test
	public void testClassName() {
		this.legendaryTestClass.setClassName("TestClass");
		String check = this.legendaryTestClass.getClassName();
		assertEquals("Test 1", "TestClass", check);
	}

	@Test
	public void testSuper() {
		this.legendaryTestClass.setSuper("SuperClass");
		String checkSuper = this.legendaryTestClass.getSuperName();
		assertEquals("Test 2", "SuperClass", checkSuper);
	}

	@Test
	public void testInterfaces() {
		List<String> interfaces = new ArrayList<>();
		interfaces.add("First Interface");
		interfaces.add("Second Interface");
		this.legendaryTestClass.setInterfaces(interfaces);
		assertEquals(interfaces, this.legendaryTestClass.getInterfaces());

	}

	@Test
	public void testMethod() {
		IMethod method = new LegendaryMethod();
		method.setMethodName("TestMethod");
		this.legendaryTestClass.addMethod(method);
		List<IMethod> checkMethod = this.legendaryTestClass.getMethodObjects();;
		assertEquals(method, checkMethod.get(0));

	}

	@Test
	public void testField() {
		IField field = new LegendaryField();
		field.setType("java/lang/String");
		IField field2 = new LegendaryField();
		field2.setType("java/util/List<java/lang/String>");
		this.legendaryTestClass.addField(field);
		this.legendaryTestClass.addField(field2);
		List<IField> checkFields = this.legendaryTestClass.getFields();
		assertEquals(field, checkFields.get(0));
		assertEquals(field2, checkFields.get(1));
	}

	@Test
	public void testIsInterface() {
		this.legendaryTestClass.setIsInterface(true);
		assertEquals(true, this.legendaryTestClass.isInterface());
	}

}
