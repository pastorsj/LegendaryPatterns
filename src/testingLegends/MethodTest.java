package testingLegends;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import legendary.Classes.LegendaryMethod;
import legendary.Interfaces.IMethod;

public class MethodTest {
	
	IMethod method;
	
	@Before
	public void setUp() {
		this.method = new LegendaryMethod();
	}
	
	@After
	public void takeDown() {
		this.method = null;
	}
	
	@Test
	public void testAccess() {
		this.method.setAccess("private");
		String access = this.method.getAccess();
		assertEquals("private", access);
	}
	
	@Test
	public void testMethodName() {
		this.method.setMethodName("TestMethod");
		String testName = this.method.getMethodName();
		assertEquals("TestMethod", testName);
	}
	
	@Test
	public void testParameters() {
		List<String> parameters = new ArrayList<>();
		parameters.add("String");
		parameters.add("int");
		parameters.add("double");
		this.method.setParameters(parameters);
		List<String> testParams = this.method.getParameters();
		assertEquals(parameters, testParams);
	}
	
	@Test
	public void testReturnType() {
		this.method.setReturnType("int");
		String testReturn = this.method.getReturnType();
		assertEquals("int", testReturn);
	}
}
