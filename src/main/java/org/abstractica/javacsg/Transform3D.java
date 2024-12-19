package org.abstractica.javacsg;

/**
 * This interface represents an immutable 3D transformation. Provides methods to apply three-dimensional geometric
 * transformations to points, directions, and entire geometries.
 * <p>
 * Use this interface to:
 * <ul>
 *   <li>Obtain an inverse transformation for reverting previously applied transformations.</li>
 *   <li>Transform individual points or direction vectors in 3D space.</li>
 *   <li>Apply transformations to entire 3D geometries.</li>
 * </ul>
 * These operations are useful for geometric modeling, manipulation, and rendering tasks.
 */
public interface Transform3D
{
	/**
	 * Returns a transformation that represents the inverse of this transformation.
	 * <p>
	 * Use this to revert a previously applied transformation.
	 * </p>
	 *
	 * @return the inverse transformation as a {@link Transform3D}
	 */
	Transform3D inverse();

	/**
	 * Transforms the given point according to this transformation.
	 * <p>
	 * Use this when repositioning points in 3D space.
	 * </p>
	 *
	 * @param vector the point to transform as a {@link Vector3D}
	 * @return the transformed point as a {@link Vector3D}
	 */
	Vector3D transformPoint(Vector3D vector);

	/**
	 * Transforms the given direction vector according to this transformation.
	 * <p>
	 * Use this when adjusting direction vectors, for example, to change orientation
	 * or modify vector directions in 3D.
	 * </p>
	 *
	 * @param vector the direction vector to transform as a {@link Vector3D}
	 * @return the transformed direction vector as a {@link Vector3D}
	 */
	Vector3D transformDirection(Vector3D vector);

	/**
	 * Transforms the given 3D geometry according to this transformation.
	 * <p>
	 * Use this to reposition, rotate, or scale a geometry in 3D space.
	 * </p>
	 *
	 * @param geometry the geometry to transform as a {@link Geometry3D}
	 * @return the transformed geometry as a {@link Geometry3D}
	 */
	Geometry3D transform(Geometry3D geometry);
}
