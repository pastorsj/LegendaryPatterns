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
import legendary.detectors.AdapterDetector;
import legendary.patterns.AdapteePattern;
import legendary.patterns.AdapterPattern;
import legendary.patterns.AdapterTargetPattern;

public class AdapterPatternTest {
	private IClass testITarget;
	private IClass testAbstractTarget;
	private IClass testTarget;
	private IClass testAdaptee;
	private IClass testAdapter;
	private IClass client;
	private IMethod testMethod1;
	private IMethod testMethod2;
	private IMethod testMethod3;
	private IField testAdapterField;
	private IModel testModel;
	private IPatternDetector adapterDetector;

	@Before
	public void setUp() {
		DesignParser.AdapterThreshold = 0;
		DesignParser.packageName = "";
		this.testITarget = new LegendaryClass();
		this.testAbstractTarget = new LegendaryClass();
		this.testTarget = new LegendaryClass();
		this.testAdaptee = new LegendaryClass();
		this.testAdapter = new LegendaryClass();
		this.client = new LegendaryClass();
		this.testMethod1 = new LegendaryMethod();
		this.testMethod2 = new LegendaryMethod();
		this.testMethod3 = new LegendaryMethod();
		this.testAdapterField = new LegendaryField();
		this.testModel = new LegendaryModel();
		this.setUpITarget();
		this.setUpAbstractTarget();
		this.setUpTarget();
		this.setUpAdaptee();
		this.setUpAdapter();
		this.setUpClient();
		this.setUpTestMethod1();
		this.setUpTestMethod2();
		this.setUpTestMethod3();
		this.setUpTestAdapterField();
		this.adapterDetector = new AdapterDetector();
	}

	private void setUpITarget() {
		this.testITarget.setClassName("ITarget");
		this.testITarget.setDrawable(true);
		this.testITarget.setIsInterface(true);
		this.testITarget.addMethod(this.testMethod1);
		this.testITarget.addMethod(this.testMethod2);
	}
	
	private void setUpAbstractTarget() {
		this.testAbstractTarget.setClassName("AbstractTarget");
		this.testAbstractTarget.setDrawable(true);
		this.testAbstractTarget.addMethod(this.testMethod1);
		this.testAbstractTarget.addMethod(this.testMethod2);
	}
	
	private void setUpTarget() {
		this.testTarget.setClassName("Target");
		this.testTarget.setDrawable(true);
		this.testTarget.addMethod(this.testMethod1);
		this.testTarget.addMethod(this.testMethod2);
	}

	private void setUpAdaptee() {
		this.testAdaptee.setClassName("Adaptee");
		this.testAdaptee.setDrawable(true);
		this.testAdaptee.addMethod(this.testMethod1);
		this.testAdaptee.addMethod(this.testMethod2);
	}

	private void setUpAdapter() {
		this.testAdapter.setClassName("Adapter");
		this.testAdapter.setDrawable(true);
		this.testAdapter.addMethod(this.testMethod1);
		this.testAdapter.addMethod(this.testMethod2);
		this.testAdapter.addField(this.testAdapterField);
	}
	
	private void setUpClient() {
		this.client.setClassName("Client");
		this.client.setDrawable(true);
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

	private void setUpTestAdapterField() {
		this.testAdapterField.setFieldName("c");
		this.testAdapterField.setType("ITarget");
		this.testAdapterField.setAccess("#");
	}

	private void setUpInterfaceModel() {
		this.testModel.addClass(this.client);
		this.testModel.addClass(this.testAdaptee);
		this.testModel.addClass(this.testAdapter);
		this.testModel.addClass(this.testITarget);
		this.addInterfaceRelations();
	}

	private void addInterfaceRelations() {
		this.testModel.addRelation("Client", "ITarget", Relations.ASSOCIATES);
		this.testModel.addRelation("Adapter", "ITarget", Relations.IMPLEMENTS);
		this.testModel.addRelation("Adapter", "Adaptee", Relations.ASSOCIATES);
	}

	private void setUpAbstractModel() {
		this.testModel.addClass(this.client);
		this.testModel.addClass(this.testAdapter);
		this.testModel.addClass(this.testAdaptee);
		this.testModel.addClass(this.testAbstractTarget);
		this.addAbstractRelations();
	}

	private void addAbstractRelations() {
		this.testModel.addRelation("Client", "AbstractTarget", Relations.ASSOCIATES);
		this.testModel.addRelation("Adapter", "AbstractTarget", Relations.EXTENDS);
		this.testModel.addRelation("Adapter", "Adaptee", Relations.ASSOCIATES);
	}
	
	private void setUpClassModel() {
		this.testModel.addClass(this.client);
		this.testModel.addClass(this.testAdapter);
		this.testModel.addClass(this.testAdaptee);
		this.testModel.addClass(this.testTarget);
		this.addClassRelations();
	}
	
	private void addClassRelations() {
		this.testModel.addRelation("Client", "Target", Relations.ASSOCIATES);
		this.testModel.addRelation("Adapter", "Target", Relations.EXTENDS);
		this.testModel.addRelation("Adapter", "Adaptee", Relations.ASSOCIATES);
	}
	
	private void setUpInterfaceModel2() {
		this.testModel.addClass(this.testAdaptee);
		this.testModel.addClass(this.testAdapter);
		this.testModel.addClass(this.testITarget);
		this.addInterfaceRelations2();
	}

	private void addInterfaceRelations2() {
		this.testModel.addRelation("Adapter", "ITarget", Relations.IMPLEMENTS);
		this.testModel.addRelation("Adapter", "Adaptee", Relations.ASSOCIATES);
	}

	private void setUpAbstractModel2() {
		this.testModel.addClass(this.testAdapter);
		this.testModel.addClass(this.testAdaptee);
		this.testModel.addClass(this.testAbstractTarget);
		this.addAbstractRelations2();
	}

	private void addAbstractRelations2() {
		this.testModel.addRelation("Adapter", "AbstractTarget", Relations.EXTENDS);
		this.testModel.addRelation("Adapter", "Adaptee", Relations.ASSOCIATES);
	}
	
	private void setUpClassModel2() {
		this.testModel.addClass(this.testAdapter);
		this.testModel.addClass(this.testAdaptee);
		this.testModel.addClass(this.testTarget);
		this.addClassRelations2();
	}
	
	private void addClassRelations2() {
		this.testModel.addRelation("Adapter", "Target", Relations.EXTENDS);
		this.testModel.addRelation("Adapter", "Adaptee", Relations.ASSOCIATES);
	}
	
	private void setUpInterfaceModel3() {
		this.testModel.addClass(this.client);
		this.testModel.addClass(this.testAdaptee);
		this.testModel.addClass(this.testAdapter);
		this.testAdapter.setDrawable(false);
		this.testModel.addClass(this.testITarget);
		this.addInterfaceRelations3();
	}

	private void addInterfaceRelations3() {
		this.testModel.addRelation("Client", "ITarget", Relations.ASSOCIATES);
		this.testModel.addRelation("Adapter", "ITarget", Relations.IMPLEMENTS);
		this.testModel.addRelation("Adapter", "Adaptee", Relations.ASSOCIATES);
	}

	private void setUpAbstractModel3() {
		this.testModel.addClass(this.client);
		this.testModel.addClass(this.testAdapter);
		this.testModel.addClass(this.testAdaptee);
		this.testModel.addClass(this.testAbstractTarget);
		this.addAbstractRelations3();
	}

	private void addAbstractRelations3() {
		this.testModel.addRelation("Client", "AbstractTarget", Relations.USES);
		this.testModel.addRelation("Adapter", "AbstractTarget", Relations.EXTENDS);
		this.testModel.addRelation("Adapter", "Adaptee", Relations.ASSOCIATES);
	}
	
	private void setUpClassModel3() {
		this.testModel.addClass(this.client);
		this.testModel.addClass(this.testAdapter);
		this.testModel.addClass(this.testAdaptee);
		this.testModel.addClass(this.testTarget);
		this.addClassRelations3();
	}
	
	private void addClassRelations3() {
		this.testModel.addRelation("Client", "Target", Relations.ASSOCIATES);
		this.testModel.addRelation("Adapter", "Target", Relations.EXTENDS);
		this.testModel.addRelation("Adapter", "Adaptee", Relations.USES);
	}

	@After
	public void takeDown() {
		this.testITarget = null;
		this.testAbstractTarget = null;
		this.testTarget = null;
		this.testAdaptee = null;
		this.testAdapter = null;
		this.testMethod1 = null;
		this.testMethod2 = null;
		this.testMethod3 = null;
		this.testAdapterField = null;
		this.testModel = null;
	}

	@Test
	public void testBaseCaseDetectAbstract() {
		this.setUpAbstractModel();
		this.testModel.convertToGraph();
		Map<Class<? extends IPattern>, Set<IClass>> result = this.adapterDetector.detect(testModel);
		Set<IClass> adapterTarget = new HashSet<>();
		Set<IClass> adapterAdaptee = new HashSet<>();
		Set<IClass> adapterAdapter = new HashSet<>();
		adapterTarget.add(this.testAbstractTarget);
		adapterAdaptee.add(this.testAdaptee);
		adapterAdapter.add(this.testAdapter);
		assertEquals(adapterTarget, result.get(AdapterTargetPattern.class));
		assertEquals(adapterAdaptee, result.get(AdapteePattern.class));
		assertEquals(adapterAdapter, result.get(AdapterPattern.class));
	}

	@Test
	public void testBaseCaseDetectInterface() {
		this.setUpInterfaceModel();
		this.testModel.convertToGraph();
		Map<Class<? extends IPattern>, Set<IClass>> result = this.adapterDetector.detect(testModel);
		Set<IClass> adapterTarget = new HashSet<>();
		Set<IClass> adapterAdaptee = new HashSet<>();
		Set<IClass> adapterAdapter = new HashSet<>();
		adapterTarget.add(this.testITarget);
		adapterAdaptee.add(this.testAdaptee);
		adapterAdapter.add(this.testAdapter);
		assertEquals(adapterTarget, result.get(AdapterTargetPattern.class));
		assertEquals(adapterAdaptee, result.get(AdapteePattern.class));
		assertEquals(adapterAdapter, result.get(AdapterPattern.class));
	}
	
	@Test
	public void testBaseCaseDetectClass() {
		this.setUpClassModel();
		this.testModel.convertToGraph();
		Map<Class<? extends IPattern>, Set<IClass>> result = this.adapterDetector.detect(testModel);
		Set<IClass> adapterTarget = new HashSet<>();
		Set<IClass> adapterAdaptee = new HashSet<>();
		Set<IClass> adapterAdapter = new HashSet<>();
		adapterTarget.add(this.testTarget);
		adapterAdaptee.add(this.testAdaptee);
		adapterAdapter.add(this.testAdapter);
		assertEquals(adapterTarget, result.get(AdapterTargetPattern.class));
		assertEquals(adapterAdaptee, result.get(AdapteePattern.class));
		assertEquals(adapterAdapter, result.get(AdapterPattern.class));
	}
	
	@Test
	public void testBaseCaseDetectAbstractFail1() {
		this.setUpAbstractModel2();
		this.testModel.convertToGraph();
		Map<Class<? extends IPattern>, Set<IClass>> result = this.adapterDetector.detect(testModel);
		assertEquals(new HashSet<>(), result.get(AdapterTargetPattern.class));
		assertEquals(new HashSet<>(), result.get(AdapteePattern.class));
		assertEquals(new HashSet<>(), result.get(AdapterPattern.class));
	}

	@Test
	public void testBaseCaseDetectInterfaceFail1() {
		this.setUpInterfaceModel2();
		this.testModel.convertToGraph();
		Map<Class<? extends IPattern>, Set<IClass>> result = this.adapterDetector.detect(testModel);
		assertEquals(new HashSet<>(), result.get(AdapterTargetPattern.class));
		assertEquals(new HashSet<>(), result.get(AdapteePattern.class));
		assertEquals(new HashSet<>(), result.get(AdapterPattern.class));
	}
	
	@Test
	public void testBaseCaseDetectClassFail1() {
		this.setUpClassModel2();
		this.testModel.convertToGraph();
		Map<Class<? extends IPattern>, Set<IClass>> result = this.adapterDetector.detect(testModel);
		assertEquals(new HashSet<>(), result.get(AdapterTargetPattern.class));
		assertEquals(new HashSet<>(), result.get(AdapteePattern.class));
		assertEquals(new HashSet<>(), result.get(AdapterPattern.class));
	}
	
	@Test
	public void testBaseCaseDetectAbstractFail2() {
		this.setUpAbstractModel3();
		this.testModel.convertToGraph();
		Map<Class<? extends IPattern>, Set<IClass>> result = this.adapterDetector.detect(testModel);
		assertEquals(new HashSet<>(), result.get(AdapterTargetPattern.class));
		assertEquals(new HashSet<>(), result.get(AdapteePattern.class));
		assertEquals(new HashSet<>(), result.get(AdapterPattern.class));
	}

	@Test
	public void testBaseCaseDetectInterfaceFail2() {
		this.setUpInterfaceModel3();
		this.testModel.convertToGraph();
		Map<Class<? extends IPattern>, Set<IClass>> result = this.adapterDetector.detect(testModel);
		assertEquals(new HashSet<>(), result.get(AdapterTargetPattern.class));
		assertEquals(new HashSet<>(), result.get(AdapteePattern.class));
		assertEquals(new HashSet<>(), result.get(AdapterPattern.class));
	}
	
	@Test
	public void testBaseCaseDetectClassFail2() {
		this.setUpClassModel3();
		this.testModel.convertToGraph();
		Map<Class<? extends IPattern>, Set<IClass>> result = this.adapterDetector.detect(testModel);
		assertEquals(new HashSet<>(), result.get(AdapterTargetPattern.class));
		assertEquals(new HashSet<>(), result.get(AdapteePattern.class));
		assertEquals(new HashSet<>(), result.get(AdapterPattern.class));
	}
}
