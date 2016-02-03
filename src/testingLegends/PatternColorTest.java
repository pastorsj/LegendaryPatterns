package testingLegends;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import legendary.Interfaces.IPattern;
import legendary.patterns.*;

public class PatternColorTest {
	
	IPattern testPattern;
	
	@Before
	public void takeDown() {
		this.testPattern = null;
	}
	
	@Test
	public void testAdapteePattern() {
		this.testPattern = new AdapteePattern();
		assertEquals("adaptee, ", this.testPattern.tag());
		assertEquals("style = \"filled\"\nfillcolor = \"red\"", this.testPattern.color());
	}
	
	@Test
	public void testAdapterPattern() {
		this.testPattern = new AdapterPattern();
		assertEquals("adapter, ", this.testPattern.tag());
		assertEquals("style = \"filled\"\nfillcolor = \"red\"", this.testPattern.color());
	}
	
	@Test
	public void testAdapterTargetPattern() {
		this.testPattern = new AdapterTargetPattern();
		assertEquals("target, ", this.testPattern.tag());
		assertEquals("style = \"filled\"\nfillcolor = \"red\"", this.testPattern.color());
	}
	
	@Test
	public void testDecoratorComponentPattern() {
		this.testPattern = new DecoratorComponentPattern();
		assertEquals("component, ", this.testPattern.tag());
		assertEquals("style = \"filled\"\nfillcolor = \"green\"", this.testPattern.color());
	}
	
	@Test
	public void testDecoratorPattern() {
		this.testPattern = new DecoratorPattern();
		assertEquals("decorator, ", this.testPattern.tag());
		assertEquals("style = \"filled\"\nfillcolor = \"green\"", this.testPattern.color());
	}
	
	@Test
	public void testSingletonPattern() {
		this.testPattern = new SingletonPattern();
		assertEquals("Singleton, ", this.testPattern.tag());
		assertEquals("color = blue", this.testPattern.color());
	}
	
	@Test
	public void testCompositeComponentPattern() {
		this.testPattern = new CompositeComponentPattern();
		assertEquals("Component, ", this.testPattern.tag());
		assertEquals("style = \"filled\"\nfillcolor = \"yellow\"", this.testPattern.color());
	}
	
	@Test
	public void testCompositeLeafPattern() {
		this.testPattern = new CompositeLeafPattern();
		assertEquals("Leaf, ", this.testPattern.tag());
		assertEquals("style = \"filled\"\nfillcolor = \"yellow\"", this.testPattern.color());
	}
	
	@Test
	public void testCompositeCompositePattern() {
		this.testPattern = new CompositePattern();
		assertEquals("Composite, ", this.testPattern.tag());
		assertEquals("style = \"filled\"\nfillcolor = \"yellow\"", this.testPattern.color());
	}
	
	
	
}
