package testingLegends;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import legendary.Classes.Class;
import legendary.Classes.ClassParser;
import legendary.Interfaces.IClass;

public class UseArrowTest {
	@Test
	public void testUsesArrow() {
		IClass c1 = new Class();
		c1.setClassName("Class 1");
		IClass c2 = new Class();
		c2.setClassName("Class 2");
		c1.addUsesClass(c2.getClassName());
		ClassParser cp = new ClassParser();
		cp.addClass(c1);
		cp.addClass(c2);
		Set<String> keys = new HashSet<String>();
		keys.add(c1.getClassName());
		keys.add(c2.getClassName());
		StringBuilder sb = new StringBuilder();
		cp.addUsageArrows(sb, keys);

		assertEquals("edge [style = \"dashed\"] [arrowhead = \"open\"]\n\t" + "Class 1->Class 2\n\t", sb.toString());
	}

	@Test
	public void testUseBeforeAssociate() {
		IClass c1 = new Class();
		c1.setClassName("Class 1");
		IClass c2 = new Class();
		c2.setClassName("Class 2");
		c1.addUsesClass(c2.getClassName());
		c1.addAssociationClass(c2.getClassName());
		ClassParser cp = new ClassParser();
		cp.addClass(c1);
		cp.addClass(c2);
		Set<String> keys = new HashSet<String>();
		keys.add(c1.getClassName());
		keys.add(c2.getClassName());
		StringBuilder sb = new StringBuilder();
		cp.addUsageArrows(sb, keys);

		assertEquals("edge [style = \"dashed\"] [arrowhead = \"open\"]\n\t" + "Class 1->Class 2\n\t", sb.toString());
	}

	@Test
	public void testAssociateBeforeUse() {
		IClass c1 = new Class();
		c1.setClassName("Class 1");
		IClass c2 = new Class();
		c2.setClassName("Class 2");
		c1.addAssociationClass(c2.getClassName());
		c1.addUsesClass(c2.getClassName());
		ClassParser cp = new ClassParser();
		cp.addClass(c1);
		cp.addClass(c2);
		Set<String> keys = new HashSet<String>();
		keys.add(c1.getClassName());
		keys.add(c2.getClassName());
		StringBuilder sb = new StringBuilder();
		cp.addUsageArrows(sb, keys);

		assertEquals("edge [style = \"dashed\"] [arrowhead = \"open\"]\n\t" + "Class 1->Class 2\n\t", sb.toString());
	}
}
