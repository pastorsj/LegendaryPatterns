package testingLegends;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
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
import legendary.detectors.CompositeDetector;
import legendary.patterns.CompositeComponentPattern;
import legendary.patterns.CompositeLeafPattern;
import legendary.patterns.CompositePattern;

public class CompositePatternTest {
	private IClass testComponent;
	private IClass testComposite;
	private IClass testLeafA;
	private IClass testLeafB;
	private IClass testLeafC;
	private IClass testLeafD;
	private IClass IComponent;
	private IMethod testMethod1;
	private IMethod testMethod2;
	private IMethod testMethod3;
	private IField testField;
	private IModel testModel;
	private IPatternDetector compositeDetector;

	@Before
	public void setUp() {
		DesignParser.packageName = "";
		this.testComponent = new LegendaryClass();
		this.testComposite = new LegendaryClass();
		this.testLeafA = new LegendaryClass();
		this.testLeafB = new LegendaryClass();
		this.testLeafC = new LegendaryClass();
		this.testLeafD = new LegendaryClass();
		this.IComponent = new LegendaryClass();
		this.testMethod1 = new LegendaryMethod();
		this.testMethod2 = new LegendaryMethod();
		this.testMethod3 = new LegendaryMethod();
		this.testField = new LegendaryField();
		this.testModel = new LegendaryModel();
		this.setUpComponent();
		this.setUpComposite();
		this.setUpLeafA();
		this.setUpLeafB();
		this.setUpLeafC();
		this.setUpLeafD();
		this.setUpIComponent();
		this.setUpTestMethod1();
		this.setUpTestMethod2();
		this.setUpTestMethod3();
		this.setUpTestField();
		this.compositeDetector = new CompositeDetector();
	}

	private void setUpComponent() {
		this.testComponent.setClassName("Component");
		this.testComponent.setDrawable(true);
		this.testComponent.addMethod(this.testMethod1);
		this.testComponent.addMethod(this.testMethod2);
	}
	
	private void setUpComposite() {
		this.testComposite.setClassName("Composite");
		this.testComposite.setDrawable(true);
		this.testComposite.addMethod(this.testMethod1);
		this.testComposite.addField(this.testField);
	}
	
	private void setUpLeafA() {
		this.testLeafA.setClassName("LeafA");
		this.testLeafA.setDrawable(true);
		this.testLeafA.addMethod(this.testMethod1);
		this.testLeafA.addMethod(this.testMethod2);
	}

	private void setUpLeafB() {
		this.testLeafB.setClassName("LeafB");
		this.testLeafB.setDrawable(true);
		this.testLeafB.addMethod(this.testMethod1);
		this.testLeafB.addMethod(this.testMethod2);
	}

	private void setUpLeafC() {
		this.testLeafC.setClassName("LeafC");
		this.testLeafC.setDrawable(true);
		this.testLeafC.addMethod(this.testMethod3);
	}
	
	private void setUpLeafD() {
		this.testLeafD.setClassName("LeafD");
		this.testLeafD.setDrawable(true);
		this.testLeafD.addMethod(this.testMethod3);
	}
	
	private void setUpIComponent() {
		this.IComponent.setClassName("IComponent");
		this.IComponent.setDrawable(true);
		this.IComponent.addMethod(this.testMethod1);
		this.IComponent.setIsInterface(true);
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

	private void setUpTestField() {
		this.testField.setFieldName("TestField");
		this.testField.setType("Component[]");
		this.testField.setAccess("#");
	}

	private void setUpModel() {
		this.testModel.addClass(this.testLeafA);
		this.testModel.addClass(this.testLeafB);
		this.testModel.addClass(this.testLeafC);
		this.testModel.addClass(this.testLeafD);
		this.testModel.addClass(this.testComponent);
		this.testModel.addClass(this.testComposite);
	}

	private void addRelations(boolean nofail) {
		this.testModel.addRelation("LeafA", "Component", Relations.EXTENDS);
		this.testModel.addRelation("LeafB", "Component", Relations.EXTENDS);
		this.testModel.addRelation("LeafC", "Composite", Relations.EXTENDS);
		this.testModel.addRelation("LeafD", "Composite", Relations.EXTENDS);
		//Why: Composite must inherit Component
		if(nofail)
			this.testModel.addRelation("Composite", "Component", Relations.EXTENDS);
		this.testModel.addRelation("Composite", "Component", Relations.ASSOCIATES);
	}

	private void setUpModel2() {
		this.testModel.addClass(this.testLeafA);
		this.testModel.addClass(this.testLeafB);
		this.testModel.addClass(this.testLeafC);
		this.testModel.addClass(this.testLeafD);
		this.testModel.addClass(this.testComponent);
		this.testModel.addClass(this.testComposite);
		this.testModel.addClass(this.IComponent);
	}

	private void addRelations2(boolean nofail) {
		this.testModel.addRelation("LeafA", "Component", Relations.EXTENDS);
		this.testModel.addRelation("LeafB", "Component", Relations.EXTENDS);
		this.testModel.addRelation("LeafC", "Composite", Relations.EXTENDS);
		this.testModel.addRelation("LeafD", "Composite", Relations.EXTENDS);
		this.testModel.addRelation("Composite", "Component", Relations.EXTENDS);
		//Why: Composite must inherit from a Component
		if(nofail)
			this.testModel.addRelation("Composite", "IComponent", Relations.ASSOCIATES);
		this.testModel.addRelation("Component", "IComponent", Relations.IMPLEMENTS);
	}
	
	private void setUpModel3() {
		this.testModel.addClass(this.testLeafA);
		this.testModel.addClass(this.testLeafC);
		this.testModel.addClass(this.testComponent);
		this.testModel.addClass(this.testComposite);
	}

	private void addRelations3(boolean nofail) {
		this.testModel.addRelation("LeafA", "Component", Relations.EXTENDS);
		this.testModel.addRelation("LeafC", "Composite", Relations.EXTENDS);
		//Why: Composite must associate with that Component
		if(nofail)
			this.testModel.addRelation("Composite", "Component", Relations.ASSOCIATES);
		this.testModel.addRelation("Composite", "Component", Relations.EXTENDS);
	}

	@After
	public void takeDown() {
		this.testComponent = null;
		this.testComposite = null;
		this.IComponent = null;
		this.testLeafA = null;
		this.testLeafB = null;
		this.testLeafC = null;
		this.testLeafD = null;
		this.testMethod1 = null;
		this.testMethod2 = null;
		this.testMethod3 = null;
		this.testField = null;
		this.testModel = null;
	}

	@Test
	public void testBaseCaseDetect() {
		this.setUpModel();
		this.addRelations(true);
		this.testModel.convertToGraph();
		Map<Class<? extends IPattern>, Set<IClass>> result = this.compositeDetector.detect(testModel);
		Set<IClass> components = new HashSet<>();
		Set<IClass> composites = new HashSet<>();
		Set<IClass> leaves = new HashSet<>();
		components.add(this.testComponent);
		composites.add(this.testComposite);
		composites.add(this.testLeafC);
		composites.add(this.testLeafD);		
		leaves.add(this.testLeafA);
		leaves.add(this.testLeafB);
		assertEquals(components, result.get(CompositeComponentPattern.class));
		assertEquals(composites, result.get(CompositePattern.class));
		assertEquals(leaves, result.get(CompositeLeafPattern.class));
	}
	
	@Test
	public void testBaseCaseDetectWithInterface() {
		this.setUpModel2();
		this.addRelations2(true);
		this.testField.setType("IComponent[]");
		this.testModel.convertToGraph();
		Map<Class<? extends IPattern>, Set<IClass>> result = this.compositeDetector.detect(testModel);
		Set<IClass> components = new HashSet<>();
		Set<IClass> composites = new HashSet<>();
		Set<IClass> leaves = new HashSet<>();
		components.add(this.testComponent);
		components.add(this.IComponent);
		composites.add(this.testComposite);
		composites.add(this.testLeafC);
		composites.add(this.testLeafD);		
		leaves.add(this.testLeafA);
		leaves.add(this.testLeafB);
		assertEquals(components, result.get(CompositeComponentPattern.class));
		assertEquals(composites, result.get(CompositePattern.class));
		assertEquals(leaves, result.get(CompositeLeafPattern.class));
	}

	@Test
	public void testEdgeCaseDetect() {
		this.setUpModel3();
		this.addRelations3(true);
		this.testModel.convertToGraph();
		Map<Class<? extends IPattern>, Set<IClass>> result = this.compositeDetector.detect(testModel);
		Set<IClass> components = new HashSet<>();
		Set<IClass> composites = new HashSet<>();
		Set<IClass> leaves = new HashSet<>();
		components.add(this.testComponent);
		composites.add(this.testComposite);
		composites.add(this.testLeafC);
		leaves.add(this.testLeafA);
		assertEquals(components, result.get(CompositeComponentPattern.class));
		assertEquals(composites, result.get(CompositePattern.class));
		assertEquals(leaves, result.get(CompositeLeafPattern.class));
	}
	
	@Test
	public void testBaseCaseDetectFail() {
		this.setUpModel();
		this.addRelations(false);
		this.testModel.convertToGraph();
		Map<Class<? extends IPattern>, Set<IClass>> result = this.compositeDetector.detect(testModel);
		Set<IClass> components = new HashSet<>();
		Set<IClass> composites = new HashSet<>();
		Set<IClass> leaves = new HashSet<>();
		assertEquals(components, result.get(CompositeComponentPattern.class));
		assertEquals(composites, result.get(CompositePattern.class));
		assertEquals(leaves, result.get(CompositeLeafPattern.class));
	}
	
	@Test
	public void testBaseCaseDetectWithInterfaceFail() {
		this.setUpModel2();
		this.addRelations2(false);
		this.testField.setType("int"); //composite does not associate with IComponent in this case
		this.testModel.convertToGraph();
		Map<Class<? extends IPattern>, Set<IClass>> result = this.compositeDetector.detect(testModel);
		Set<IClass> components = new HashSet<>();
		Set<IClass> composites = new HashSet<>();
		Set<IClass> leaves = new HashSet<>();
		assertEquals(components, result.get(CompositeComponentPattern.class));
		assertEquals(composites, result.get(CompositePattern.class));
		assertEquals(leaves, result.get(CompositeLeafPattern.class));
	}

	@Test
	public void testEdgeCaseDetectFail() {
		this.setUpModel3();
		this.addRelations3(false);
		this.testModel.convertToGraph();
		Map<Class<? extends IPattern>, Set<IClass>> result = this.compositeDetector.detect(testModel);
		Set<IClass> components = new HashSet<>();
		Set<IClass> composites = new HashSet<>();
		Set<IClass> leaves = new HashSet<>();
		assertEquals(components, result.get(CompositeComponentPattern.class));
		assertEquals(composites, result.get(CompositePattern.class));
		assertEquals(leaves, result.get(CompositeLeafPattern.class));
	}	
}
