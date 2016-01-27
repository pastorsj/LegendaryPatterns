package testingLegends;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
import legendary.patterns.SingletonPattern;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
	public void detectLazySingletonBaseCase() {
		this.initializeSingleton();
		this.testMethod.addMethodToCallStack("TestSingletonClass", "TestSingletonClass", "<init>", new ArrayList<String>());
		testDetector.convertToGraph();
		IPatternDetector singletonDetector = new SingletonDetector();
		Set<IClass> classDetected = new HashSet<>();
		classDetected.add(testClass);
		assertEquals(classDetected, singletonDetector.detect(testDetector).get(SingletonPattern.class));
	}
	
	@Test
	public void detectEagerSingletonBaseCase() {
		this.initializeSingleton();
		testDetector.convertToGraph();
		IPatternDetector singletonDetector = new SingletonDetector();
		Set<IClass> classDetected = new HashSet<>();
		classDetected.add(testClass);
		assertEquals(classDetected, singletonDetector.detect(testDetector).get(SingletonPattern.class));
	}
	
	@Test
	public void detectNonSingletonBaseCase() {
		this.initializeNonSingletonBase();
		testDetector.convertToGraph();
		IPatternDetector singletonDetector = new SingletonDetector();
		assertEquals(new HashSet<IClass>(), singletonDetector.detect(testDetector).get(SingletonPattern.class));
	}
	
	@Test
	public void detectNonSingletonEdgeCase() {
		this.initializeNonSingletonEdge();
		testDetector.convertToGraph();
		IPatternDetector singletonDetector = new SingletonDetector();
		assertEquals(new HashSet<IClass>(), singletonDetector.detect(testDetector).get(SingletonPattern.class));
	}
	
	@Test
	public void detectNonSingletonEdgeCase2() {
		this.initializeNonSingletonEdge();
		this.testMethod.setReturnType("String");
		testDetector.convertToGraph();
		IPatternDetector singletonDetector = new SingletonDetector();
		assertEquals(new HashSet<IClass>(), singletonDetector.detect(testDetector).get(SingletonPattern.class));
	}
	
	@Test
	public void detectNonSingletonEdgeCase3() {
		this.initializeNonSingletonEdge();
		this.testField.setAccess("+_");
		testDetector.convertToGraph();
		IPatternDetector singletonDetector = new SingletonDetector();
		assertEquals(new HashSet<IClass>(), singletonDetector.detect(testDetector).get(SingletonPattern.class));
	}
	
	
	
}
