package legendaryTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import legendaryClasses.ClassParser;

public class ClassParserTest {
	
	ClassParser testParser;
	
	@Before
	public void setUp() {
		this.testParser = new ClassParser();
	}
	
	@After
	public void takeDown() {
		this.testParser = null;
	}
	
	@Test
	public void testAddFields() {
		
	}
	
	@Test
	public void testAddMethods() {
		
	}
	
	@Test
	public void testAddNode() {
		
	}
	
	@Test
	public void testAddExtensionArrows() {
		
	}
	
	@Test
	public void testAddInterfaceArrows() {
		
	}
}
