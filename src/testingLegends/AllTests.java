package testingLegends;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	ArrowTests.class,
	ClassTest.class,
	CollectionParseTest.class,
	FieldTest.class,
	ManualTestClass.class,
	MethodTest.class,
	SDEditTest.class
})
public class AllTests {
	public static void main(String []args) {
		org.junit.runner.JUnitCore.main("AllTests");
	}
}
