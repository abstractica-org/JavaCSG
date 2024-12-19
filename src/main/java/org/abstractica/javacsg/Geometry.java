package org.abstractica.javacsg;

/**
 * Represents a general immutable geometric entity.
 * <p>
 * Use this interface to:
 * <ul>
 *   <li>Mark a geometry for debugging or identification purposes.</li>
 *   <li>Temporarily disable a geometry, for example, when testing or modifying larger composite structures.</li>
 * </ul>
 * These actions can be helpful during development and analysis of complex geometries.
 * </p>
 */
public interface Geometry
{
	/**
	 * Marks this geometry for debugging purposes.
	 * <p>
	 * Use this to highlight the geometry or to track it through a processing pipeline.
	 * </p>
	 */
	void debugMark();

	/**
	 * Disables this geometry.
	 * <p>
	 * Use this to temporarily exclude the geometry from further processing or rendering.
	 * </p>
	 */
	void disable();
}
