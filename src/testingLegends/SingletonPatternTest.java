package testingLegends;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import legendary.Classes.LegendaryClass;
import legendary.Classes.LegendaryField;
import legendary.Classes.LegendaryMethod;
import legendary.Classes.LegendaryModel;
import legendary.Classes.Relations;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IField;
import legendary.Interfaces.IMethod;
import legendary.Interfaces.IModel;
import legendary.Interfaces.IPatternDetector;
import legendary.detectors.SingletonDetector;

public class SingletonPatternTest {

	IClass testClass;
	IMethod testMethod;
	IMethod testConstuctor;
	IField testField;
	IModel testDetector;
	
	@Before
	public void setUp() {
		this.testClass = new LegendaryClass();
		this.testMethod = new LegendaryMethod();
		this.testConstuctor = new LegendaryMethod();
		this.testField = new LegendaryField();
		this.testDetector = new LegendaryModel();
	}
	
	@After
	public void takeDown() {
		this.testClass = null;
		this.testMethod = null;
		this.testConstuctor = null;
		this.testField = null;
		this.testDetector = null;
	}
	
	private void initializeSingleton() {
		this.testClass.setClassName("TestSingletonClass");
		this.testConstuctor.setMethodName("<init>");
		this.testConstuctor.setAccess("-");
		this.testConstuctor.setReturnType("ctor");
		this.testMethod.setMethodName("getInstance");
		this.testMethod.setAccess("+_");
		this.testMethod.setReturnType("TestSingletonClass");
		this.testField.setFieldName("instance");
		this.testField.setAccess("-_");
		this.testField.setType("TestSingletonClass");
		this.addParts();
		this.testDetector.addRelation("TestSingletonClass", "TestSingletonClass", Relations.ASSOCIATES);
	}
	
	private void initializeNonSingletonBase() {
		this.testClass.setClassName("TestNonSingletonClass");
		this.testConstuctor.setMethodName("testMethod");
		this.testConstuctor.setAccess("+");
		this.testConstuctor.setReturnType("void");
		this.testMethod.setMethodName("getInstance");
		this.testMethod.setAccess("-");
		this.testMethod.setReturnType("int");
		this.testField.setFieldName("instance");
		this.testField.setAccess("-_");
		this.testField.setType("String");
		this.addParts();
		this.testDetector.addRelation("TestNonSingletonClass", "TestOtherClass", Relations.ASSOCIATES);
	}
	
	private void initializeNonSingletonEdge() {
		this.testClass.setClassName("TestSingletonClass");
		this.testConstuctor.setMethodName("<init>");
		this.testConstuctor.setAccess("+");
		this.testConstuctor.setReturnType("ctor");
		this.testMethod.setMethodName("getInstance");
		this.testMethod.setAccess("+_");
		this.testMethod.setReturnType("TestSingletonClass");
		this.testField.setFieldName("instance");
		this.testField.setAccess("-_");
		this.testField.setType("TestSingletonClass");
		this.addParts();
		this.testDetector.addRelation("TestSingletonClass", "TestSingletonClass", Relations.ASSOCIATES);
	}
	
	private void addParts() {
		this.testClass.addField(testField);
		this.testClass.addMethod(testMethod);
		this.testClass.addMethod(testConstuctor);
		this.testDetector.addClass(testClass);
	}
	
	
	@Test
	public void detectSingletonBaseCase() {
		this.initializeSingleton();
		IPatternDetector singletonDetector = new SingletonDetector();
		Set<IClass> classDetected = new HashSet<>();
		classDetected.add(testClass);
		assertEquals(classDetected, singletonDetector.detect(testDetector));
	}
	
	@Test
	public void detectNonSingletonBaseCase() {
		this.initializeNonSingletonBase();
		IPatternDetector singletonDetector = new SingletonDetector();
		assertEquals(new HashSet<IClass>(), singletonDetector.detect(testDetector));
	}
	
	@Test
	public void detectNonSingletonEdgeCase() {
		this.initializeNonSingletonEdge();
		IPatternDetector singletonDetector = new SingletonDetector();
		assertEquals(new HashSet<IClass>(), singletonDetector.detect(testDetector));
	}
	
	@Test
	public void detectNonSingletonEdgeCase2() {
		this.initializeNonSingletonEdge();
		this.testMethod.setReturnType("String");
		IPatternDetector singletonDetector = new SingletonDetector();
		assertEquals(new HashSet<IClass>(), singletonDetector.detect(testDetector));
	}
	
	@Test
	public void detectNonSingletonEdgeCase3() {
		this.initializeNonSingletonEdge();
		this.testField.setAccess("+_");
		IPatternDetector singletonDetector = new SingletonDetector();
		assertEquals(new HashSet<IClass>(), singletonDetector.detect(testDetector));
	}
	
	
	
}
