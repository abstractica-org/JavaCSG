package org.abstractica.javacsg;

/**
 * Represents an immutable two-dimensional vector defined by x and y components.
 * <p>
 * This interface is intended to provide a simple, immutable view of a
 * two-dimensional vector’s coordinates.
 * </p>
 */
public interface Vector2D
{
	/**
	 * Returns the x-component of this vector.
	 *
	 * @return the x-component as a double
	 */
	double x();

	/**
	 * Returns the y-component of this vector.
	 *
	 * @return the y-component as a double
	 */
	double y();
}
