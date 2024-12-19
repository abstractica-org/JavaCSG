package org.abstractica.javacsg;

/**
 * Represents an immutable vector in two-dimensional space using polar coordinates.
 * <p>
 * This interface provides a simple, immutable view of a two-dimensional vector’s
 * coordinates in polar form. The radius <code>r</code> describes the distance from
 * the origin, and the angle <code>phi</code> describes the counterclockwise angle relative to
 * the positive x-axis.
 * </p>
 */
public interface Polar2D
{
	/**
	 * Returns the distance from the origin.
	 *
	 * @return the distance as a double
	 */
	double r();

	/**
	 * Returns the angular coordinate of this vector.
	 *
	 * @return the angle of this vector as an {@link Angle}
	 */
	Angle phi();
}
