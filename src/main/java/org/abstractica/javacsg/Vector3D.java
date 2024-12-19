package org.abstractica.javacsg;

/**
 * Represents an immutable three-dimensional vector defined by x, y, and z components.
 * <p>
 * This interface is intended to provide a simple, immutable view of a
 * three-dimensional vector’s coordinates.
 * </p>
 */
public interface Vector3D
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

	/**
	 * Returns the z-component of this vector.
	 *
	 * @return the z-component as a double
	 */
	double z();
}
