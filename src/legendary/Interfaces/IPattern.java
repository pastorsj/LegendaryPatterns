package legendary.Interfaces;

import java.util.Set;

/**
 * The representation of a pattern
 */
public interface IPattern {

	/**
	 * The appropriate tag of a pattern component
	 *
	 * @return the tag
	 */
	public String tag();

	/**
	 * The appropriate color of a pattern component
	 *
	 * @return the color
	 */
	public String color();

	/**
	 * Used to tag arrows between parts of patterns when applicable.
	 * 
	 * @param cPatterns
	 *            The patterns that the class at the arrow source is a part of
	 * @param c2
	 *            The patterns that the class at the arrow destination is a part
	 *            of
	 * @return the tag string to be appended
	 */
	public String tagArrow(Set<IPattern> cPatterns, Set<IPattern> c2Patterns);
}
