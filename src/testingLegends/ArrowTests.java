package testingLegends;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import legendary.Classes.GraphVizOutputStream;
import legendary.Classes.LegendaryClass;
import legendary.Classes.LegendaryModel;
import legendary.Classes.Relations;
import legendary.Interfaces.IClass;
import legendary.Interfaces.IModel;

public class ArrowTests {

	protected IClass c1;
	protected IClass c2;
	protected IModel model;
	protected GraphVizOutputStream outputStream;
	protected Method arrowMethod;

	@Before
	public void setUp() throws NoSuchMethodException, SecurityException {
		this.c1 = new LegendaryClass();
		this.c2 = new LegendaryClass();
		c1.setClassName("Class 1");
		c2.setClassName("Class 2");
		this.model = new LegendaryModel();
		model.addClass(c1);
		model.addClass(c2);
		this.outputStream = new GraphVizOutputStream(new StringBuilder(), new HashMap<>());
		this.arrowMethod = GraphVizOutputStream.class.getDeclaredMethod("addArrows", IModel.class);
		this.arrowMethod.setAccessible(true);
	}

	@After
	public void takeDown() {
		this.c1 = null;
		this.c2 = null;
		this.model = null;
		this.outputStream = null;
		this.arrowMethod = null;
	}

	@Test
	public void testAssociateArrow() throws Exception {
		this.model.addRelation(c1.getClassName(), c2.getClassName(), Relations.ASSOCIATES);
		String arrowRet = (String) arrowMethod.invoke(outputStream, model);
		assertEquals("\tedge [style = \"solid\"] [arrowhead = \"open\"]\n\t" + "Class 1->Class 2\n", arrowRet);
	}

	@Test
	public void testUsesArrow() throws Exception {
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.USES);
		String arrowRet = (String) arrowMethod.invoke(this.outputStream, this.model);
		assertEquals("\tedge [style = \"dashed\"] [arrowhead = \"open\"]\n\t" + "Class 1->Class 2\n", arrowRet);
	}

	@Test
	public void testUseBeforeAssociate() throws Exception {
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.USES);
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.ASSOCIATES);
		String arrowRet = (String) arrowMethod.invoke(this.outputStream, this.model);
		assertEquals("\tedge [style = \"solid\"] [arrowhead = \"open\"]\n\t" + "Class 1->Class 2\n", arrowRet);
	}

	@Test
	public void testAssociateBeforeUse() throws Exception {
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.ASSOCIATES);
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.USES);
		String arrowRet = (String) this.arrowMethod.invoke(this.outputStream, this.model);
		assertEquals("\tedge [style = \"solid\"] [arrowhead = \"open\"]\n\t" + "Class 1->Class 2\n", arrowRet);
	}

	@Test
	public void testExtendsArrow() throws Exception {
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.EXTENDS);
		String arrowRet = (String) this.arrowMethod.invoke(this.outputStream, this.model);
		assertEquals("\tedge [style = \"solid\"] [arrowhead = \"empty\"]\n\t" + "Class 1->Class 2\n", arrowRet);
	}

	@Test
	public void testImplementsArrow() throws Exception {
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.IMPLEMENTS);
		String arrowRet = (String) this.arrowMethod.invoke(this.outputStream, this.model);
		assertEquals("\tedge [style = \"dashed\"] [arrowhead = \"empty\"]\n\t" + "Class 1->Class 2\n", arrowRet);
	}

	@Test
	public void testExtendsBeforeUses() throws Exception {
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.EXTENDS);
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.USES);
		String arrowRet = (String) this.arrowMethod.invoke(this.outputStream, this.model);
		assertEquals("\tedge [style = \"solid\"] [arrowhead = \"empty\"]\n\t" + "Class 1->Class 2\n"
				+ "\tedge [style = \"dashed\"] [arrowhead = \"open\"]\n\t" + "Class 1->Class 2\n", arrowRet);
	}

	@Test
	public void testExtendsAfterUses() throws Exception {
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.USES);
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.EXTENDS);
		String arrowRet = (String) this.arrowMethod.invoke(this.outputStream, this.model);
		assertEquals("\tedge [style = \"dashed\"] [arrowhead = \"open\"]\n\t" + "Class 1->Class 2\n"
				+ "\tedge [style = \"solid\"] [arrowhead = \"empty\"]\n\t" + "Class 1->Class 2\n", arrowRet);
	}

	@Test
	public void testExtendsBeforeAssociates() throws Exception {
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.EXTENDS);
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.ASSOCIATES);
		String arrowRet = (String) this.arrowMethod.invoke(this.outputStream, this.model);
		assertEquals("\tedge [style = \"solid\"] [arrowhead = \"empty\"]\n\t" + "Class 1->Class 2\n"
				+ "\tedge [style = \"solid\"] [arrowhead = \"open\"]\n\t" + "Class 1->Class 2\n", arrowRet);
	}

	@Test
	public void testExtendsAfterAssociates() throws Exception {
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.ASSOCIATES);
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.EXTENDS);
		String arrowRet = (String) this.arrowMethod.invoke(this.outputStream, this.model);
		assertEquals("\tedge [style = \"solid\"] [arrowhead = \"open\"]\n\t" + "Class 1->Class 2\n"
				+ "\tedge [style = \"solid\"] [arrowhead = \"empty\"]\n\t" + "Class 1->Class 2\n", arrowRet);
	}

	@Test
	public void testImplementsBeforeUses() throws Exception {
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.IMPLEMENTS);
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.USES);
		String arrowRet = (String) this.arrowMethod.invoke(this.outputStream, this.model);
		assertEquals("\tedge [style = \"dashed\"] [arrowhead = \"empty\"]\n\t" + "Class 1->Class 2\n"
				+ "\tedge [style = \"dashed\"] [arrowhead = \"open\"]\n\t" + "Class 1->Class 2\n", arrowRet);
	}

	@Test
	public void testImplementsAfterUses() throws Exception {
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.USES);
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.IMPLEMENTS);
		String arrowRet = (String) this.arrowMethod.invoke(this.outputStream, this.model);
		assertEquals("\tedge [style = \"dashed\"] [arrowhead = \"open\"]\n\t" + "Class 1->Class 2\n"
				+ "\tedge [style = \"dashed\"] [arrowhead = \"empty\"]\n\t" + "Class 1->Class 2\n", arrowRet);
	}

	@Test
	public void testImplementsBeforeAssociates() throws Exception {
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.IMPLEMENTS);
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.ASSOCIATES);
		String arrowRet = (String) this.arrowMethod.invoke(this.outputStream, this.model);
		assertEquals("\tedge [style = \"dashed\"] [arrowhead = \"empty\"]\n\t" + "Class 1->Class 2\n"
				+ "\tedge [style = \"solid\"] [arrowhead = \"open\"]\n\t" + "Class 1->Class 2\n", arrowRet);
	}

	@Test
	public void testImplementsAfterAssociates() throws Exception {
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.ASSOCIATES);
		this.model.addRelation(this.c1.getClassName(), this.c2.getClassName(), Relations.IMPLEMENTS);
		String arrowRet = (String) this.arrowMethod.invoke(this.outputStream, this.model);
		System.out.println(arrowRet);
		assertEquals("\tedge [style = \"solid\"] [arrowhead = \"open\"]\n\t" + "Class 1->Class 2\n"
				+ "\tedge [style = \"dashed\"] [arrowhead = \"empty\"]\n\t" + "Class 1->Class 2\n", arrowRet);
	}
}
