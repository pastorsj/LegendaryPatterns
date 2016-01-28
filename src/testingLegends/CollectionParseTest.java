package testingLegends;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import legendary.ParsingUtil.GeneralUtil;
import legendary.asm.DesignParser;

public class CollectionParseTest {

	@Before
	public void setUp() {
		DesignParser.packageName = "";
	}
	
	@Test
	public void testBasic() {
		String s = "Ljava/lang/String;";
		assertEquals("java.lang::String", GeneralUtil.typeFieldCollections(s));
		Set<String> test = new HashSet<>();
		test.add("legendary.Interfaces::IClass");
		assertEquals(test, GeneralUtil.getBaseFields("Llegendary/Interfaces/IClass;"));
	}

	@Test
	public void testBrackets() {
		// ASM signature for List<Integer>
		String s = "Ljava/util/List<Ljava/lang/Integer;>;";
		assertEquals("java.util::List<java.lang::Integer>", GeneralUtil.typeFieldCollections(s));
	}

	@Test
	public void testBracketsCommas() {
		// ASM signature for Map<Integer, String>
		String s = "Ljava/util/Map<Ljava/lang/Integer;Ljava/lang/String>;";
		assertEquals("java.util::Map<java.lang::Integer, java.lang::String>", GeneralUtil.typeFieldCollections(s));

	}

	@Test
	public void testNestedBracketsCommas() {
		// ASM signature for Map<List<List<List<Map<String, Integer>>>>, String>
		String s = "Ljava/util/Map<Ljava/util/List<Ljava/util/List<Ljava/util/L"
				+ "ist<Ljava/util/Map<Ljava/lang/String;Ljava/lang/Integer;>;>;>;>;Ljava/lang/String;>;";
		assertEquals("java.util::Map<java.util::List<java.util::List<java.util::List<java.util::Map<java.lang::String, java.lang::Integer>>>>, java.lang::String>",
				GeneralUtil.typeFieldCollections(s));
	}
}
