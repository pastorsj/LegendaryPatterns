package testingLegends;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import legendary.Interfaces.IPattern;
import legendary.Interfaces.IPatternDetector;
import legendary.asm.DesignParser;
import legendary.detectors.DecoratorDetector;
import legendary.patterns.DecoratorComponentPattern;
import legendary.patterns.DecoratorPattern;

public class DecoratorPatternTest {
	private IClass testIComponent;
	private IClass testConcreteComponent;
	private IClass testAbstractDecorator;
	private IClass testIDecorator;
	private IClass testConcreteDecorator1;
	private IClass testConcreteDecorator2;
	private IMethod testMethod1;
	private IMethod testMethod2;
	private IMethod testMethod3;
	private IField testComponentField;
	private IModel testModel;
	private IPatternDetector decoratorDetector;

	
	@Before
	public void setUp() {
		DesignParser.packageName = "";
		this.testIComponent = new LegendaryClass();
		this.testConcreteComponent = new LegendaryClass();
		this.testAbstractDecorator = new LegendaryClass();
		this.testIDecorator = new LegendaryClass();
		this.testConcreteDecorator1 = new LegendaryClass();
		this.testConcreteDecorator2 = new LegendaryClass();
		this.testMethod1 = new LegendaryMethod();
		this.testMethod2 = new LegendaryMethod();
		this.testMethod3 = new LegendaryMethod();
		this.testComponentField = new LegendaryField();
		this.testModel = new LegendaryModel();
		this.setUpIComponent();
		this.setUpConcreteComponent();
		this.setUpAbstractDecorator();
		this.setUpIDecorator();
		this.setUpConcreteDecorator1();
		this.setUpConcreteDecorator2();
		this.setUpTestMethod1();
		this.setUpTestMethod2();
		this.setUpTestMethod3();
		this.setUpTestComponentField();
		this.decoratorDetector = new DecoratorDetector();
	}
	
	private void setUpIComponent() {
		this.testIComponent.setClassName("IComponent");
		this.testIComponent.setDrawable(true);
		this.testIComponent.setIsInterface(true);
		this.testIComponent.addMethod(this.testMethod1);
		this.testIComponent.addMethod(this.testMethod2);
	}
	
	private void setUpConcreteComponent() {
		this.testConcreteComponent.setClassName("ConcreteComponent");
		List<String> interfaces = new ArrayList<>();
		interfaces.add("IComponent");
		this.testConcreteComponent.setDrawable(true);
		this.testConcreteComponent.setInterfaces(interfaces);
		this.testConcreteComponent.addMethod(this.testMethod1);
		this.testConcreteComponent.addMethod(this.testMethod2);
	}
	
	private void setUpAbstractDecorator() {
		this.testAbstractDecorator.setClassName("AbstractDecorator");
		this.testAbstractDecorator.setDrawable(true);
		this.testAbstractDecorator.addMethod(this.testMethod1);
		this.testAbstractDecorator.addMethod(this.testMethod2);
		this.testAbstractDecorator.addField(this.testComponentField);
	}
	
	private void setUpIDecorator() {
		this.testIDecorator.setClassName("IDecorator");
		this.testIDecorator.setDrawable(true);
		this.testIDecorator.setIsInterface(true);
		this.testIDecorator.addMethod(this.testMethod1);
		this.testIDecorator.addMethod(this.testMethod2);
	}
	
	private void setUpConcreteDecorator1() {
		this.testConcreteDecorator1.setClassName("ConcreteDecorator1");
		this.testConcreteDecorator1.setDrawable(true);
		this.testConcreteDecorator1.addMethod(this.testMethod1);
		this.testConcreteDecorator1.addMethod(this.testMethod2);
	}
	
	private void setUpConcreteDecorator2() {
		this.testConcreteDecorator2.setClassName("ConcreteDecorator2");
		this.testConcreteDecorator2.setDrawable(true);
		this.testConcreteDecorator2.addMethod(this.testMethod1);
		this.testConcreteDecorator2.addMethod(this.testMethod2);
	}
	
	private void setUpTestMethod1() {
		this.testMethod1.setMethodName("method1");
		this.testMethod1.setAccess("+");
		this.testMethod1.setReturnType("void");
	}
	
	private void setUpTestMethod2() {
		this.testMethod2.setMethodName("method2");
		this.testMethod2.setAccess("+");
		this.testMethod2.setReturnType("void");
	}
	
	private void setUpTestMethod3() {
		this.testMethod3.setMethodName("method3");
		this.testMethod3.setAccess("-");
		this.testMethod3.setReturnType("void");
	}
	
	private void setUpTestComponentField() {
		this.testComponentField.setFieldName("c");
		this.testComponentField.setType("IComponent");
		this.testComponentField.setAccess("#");
	}
	
	
	private void setUpInterfaceModel() {
		this.testModel.addClass(testConcreteComponent);
		this.testModel.addClass(testIDecorator);
		this.testModel.addClass(testConcreteDecorator1);
		this.testModel.addClass(testConcreteDecorator2);
		this.testModel.addClass(testIComponent);
		this.addInterfaceRelations();
	}
	
	private void addInterfaceRelations() {
		this.testModel.addRelation("ConcreteComponent", "IComponent", Relations.IMPLEMENTS);
		this.testModel.addRelation("IDecorator", "IComponent", Relations.EXTENDS);
		this.testModel.addRelation("ConcreteDecorator1", "IDecorator", Relations.IMPLEMENTS);
		this.testModel.addRelation("ConcreteDecorator2", "IDecorator", Relations.IMPLEMENTS);
		this.testModel.addRelation("ConcreteDecorator1", "IComponent", Relations.ASSOCIATES);
		this.testModel.addRelation("ConcreteDecorator2", "IComponent", Relations.ASSOCIATES);
	}

	private void setUpAbstractModel() {
		this.testModel.addClass(this.testAbstractDecorator);
		this.testModel.addClass(this.testConcreteComponent);
		this.testModel.addClass(this.testConcreteDecorator1);
		this.testModel.addClass(this.testConcreteDecorator2);
		this.testModel.addClass(this.testIComponent);
		this.addAbstractRelations();
	}
	
	private void addAbstractRelations() {
		this.testModel.addRelation("ConcreteComponent", "IComponent", Relations.IMPLEMENTS);
		this.testModel.addRelation("AbstractDecorator", "IComponent", Relations.ASSOCIATES);
		this.testModel.addRelation("AbstractDecorator", "IComponent", Relations.IMPLEMENTS);
		this.testModel.addRelation("ConcreteDecorator1", "AbstractDecorator", Relations.EXTENDS);
		this.testModel.addRelation("ConcreteDecorator2", "AbstractDecorator", Relations.EXTENDS);
	}
	
	@After
	public void takeDown() {
		this.testIComponent = null;
		this.testConcreteComponent = null;
		this.testAbstractDecorator = null;
		this.testIDecorator = null;
		this.testConcreteDecorator1 = null;
		this.testConcreteDecorator2 = null;
		this.testMethod1 = null;
		this.testMethod2 = null;
		this.testMethod3 = null;
		this.testComponentField = null;
		this.testModel = null;
	}
	
	@Test
	public void testBaseCaseDetectAbstract() {
		this.setUpAbstractModel();
		this.testModel.convertToGraph();
		Map<Class<? extends IPattern>, Set<IClass>> result = this.decoratorDetector.detect(testModel);
		Set<IClass> components = new HashSet<>();
		Set<IClass> decorators = new HashSet<>();
		components.add(testIComponent);
		decorators.add(testAbstractDecorator);
		decorators.add(testConcreteDecorator1);
		decorators.add(testConcreteDecorator2);
		assertEquals(components, result.get(DecoratorComponentPattern.class));
		assertEquals(decorators, result.get(DecoratorPattern.class));
	}
	
	@Test
	public void testBaseCaseDetectInterface() {
		this.setUpInterfaceModel();
		this.testModel.convertToGraph();
		Map<Class<? extends IPattern>, Set<IClass>> result = this.decoratorDetector.detect(testModel);
		Set<IClass> components = new HashSet<>();
		Set<IClass> decorators = new HashSet<>();
		components.add(testIComponent);
		decorators.add(testIDecorator);
		decorators.add(testConcreteDecorator1);
		decorators.add(testConcreteDecorator2);
		assertEquals(components, result.get(DecoratorComponentPattern.class));
		assertEquals(decorators, result.get(DecoratorPattern.class));
	}
}
