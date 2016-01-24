package testingLegends;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import legendary.ParsingUtil.GeneralUtil;

public class CollectionParseTest {

	@Test
	public void testBasic() {
		String s = "Ljava/lang/String;";
		assertEquals("String", GeneralUtil.typeFieldCollections(s));
		Set<String> test = new HashSet<>();
		test.add("IClass");
		assertEquals(test, GeneralUtil.getBaseFields("Llegendary/Interfaces/IClass;"));
	}

	@Test
	public void testBrackets() {
		// ASM signature for List<Integer>
		String s = "Ljava/util/List<Ljava/lang/Integer;>;";
		assertEquals("List\\<Integer\\>", GeneralUtil.typeFieldCollections(s));
	}

	@Test
	public void testBracketsCommas() {
		// ASM signature for Map<Integer, String>
		String s = "Ljava/util/Map<Ljava/lang/Integer;Ljava/lang/String>;";
		assertEquals("Map\\<Integer, String\\>", GeneralUtil.typeFieldCollections(s));

	}

	@Test
	public void testNestedBracketsCommas() {
		// ASM signature for Map<List<List<List<Map<String, Integer>>>>, String>
		String s = "Ljava/util/Map<Ljava/util/List<Ljava/util/List<Ljava/util/L"
				+ "ist<Ljava/util/Map<Ljava/lang/String;Ljava/lang/Integer;>;>;>;>;Ljava/lang/String;>;";
		assertEquals("Map\\<List\\<List\\<List\\<Map\\<String, Integer\\>\\>\\>\\>, String\\>",
				GeneralUtil.typeFieldCollections(s));
	}
}
