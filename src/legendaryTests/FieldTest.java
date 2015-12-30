package legendaryTests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import legendaryClasses.Field;
import legendaryInterfaces.IField;

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
		this.field.setMethodName("TestMethod");
		String testName = this.field.getMethodName();
		assertEquals("TestMethod", testName);
	}
	
	@Test
	public void testReturnType() {
		this.field.setReturnType("String");
		String testReturn = this.field.getReturnType();
		assertEquals("String", testReturn);
	}
}
