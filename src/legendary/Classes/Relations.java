package legendary.Classes;

/**
 * The Enumeration for Relations.
 */
public enum Relations {
	
	/** If a class extends another class. */
	EXTENDS,
	
	/** If a class implements another class. */
	IMPLEMENTS,
	
	/** If a class uses another class. */
	USES,
	
	/** If a class associates another class. */
	ASSOCIATES,
	
	/** If a class extends another class, add this reverse arrow to the 
	 * graph so we can traverse backwards. */
	REV_EXTENDS,
	
	/** If a class extends another class, add this reverse arrow to the 
	 * graph so we can traverse backwards. */
	REV_IMPLEMENTS,
	
	/** If a class extends another class, add this reverse arrow to the 
	 * graph so we can traverse backwards. */
	REV_USES,
	
	/** If a class extends another class, add this reverse arrow to the 
	 * graph so we can traverse backwards. */
	REV_ASSOCIATES
}
