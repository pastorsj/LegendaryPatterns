package testingLegends;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import legendary.Classes.Field;
import legendary.Interfaces.IField;

public class FieldTest {
	
	IField field;
	
	@Before
	public void setUp() {
		this.field = new Field();
	}
	
	@After
	public void takeDown() {
		this.field = null;
	}
	
	@Test
	public void testAccess() {
		this.field.setAccess("public");
		String test = this.field.getAccess();
		assertEquals("public", test);
	}
	
	@Test
	public void testMethodName() {
		this.field.setFieldName("TestMethod");
		String testName = this.field.getFieldName();
		assertEquals("TestMethod", testName);
	}
	
	@Test
	public void testReturnType() {
		this.field.setType("String");
		String testReturn = this.field.getType();
		assertEquals("String", testReturn);
	}
}