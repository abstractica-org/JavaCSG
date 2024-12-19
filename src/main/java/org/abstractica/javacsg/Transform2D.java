package org.abstractica.javacsg;

/**
 * This interface represents an immutable 2D transformation. Provides methods to apply two-dimensional geometric transformations to points, directions, and geometries.
 * <p>
 * Use this interface to:
 * <ul>
 *   <li>Obtain an inverse transformation for reversing applied transformations.</li>
 *   <li>Transform individual points or direction vectors in 2D space.</li>
 *   <li>Apply transformations to entire 2D geometries.</li>
 *   <li>Convert a 2D transformation into a 3D transformation context.</li>
 * </ul>
 * These operations are useful for geometric modeling, manipulation, and rendering tasks.
 */
public interface Transform2D
{
	/**
	 * Returns a transformation that represents the inverse of this transformation.
	 * <p>
	 * Use this to revert a previously applied transformation.
	 * </p>
	 *
	 * @return the inverse transformation as a {@link Transform2D}
	 */
	Transform2D inverse();

	/**
	 * Transforms the given point according to this transformation.
	 * <p>
	 * Use this when you need to move a specific point into a new position
	 * defined by this transformation.
	 * </p>
	 *
	 * @param vector the point to transform as a {@link Vector2D}
	 * @return the transformed point as a {@link Vector2D}
	 */
	Vector2D transformPoint(Vector2D vector);

	/**
	 * Transforms the given direction vector according to this transformation.
	 * <p>
	 * Use this when adjusting direction vectors, for example, to change the orientation
	 * of a movement or a normal vector.
	 * </p>
	 *
	 * @param vector the direction vector to transform as a {@link Vector2D}
	 * @return the transformed direction vector as a {@link Vector2D}
	 */
	Vector2D transformDirection(Vector2D vector);

	/**
	 * Transforms the given 2D geometry according to this transformation.
	 * <p>
	 * Use this to reposition, rotate, or scale a geometry in 2D space.
	 * </p>
	 *
	 * @param geometry the geometry to transform as a {@link Geometry2D}
	 * @return the transformed geometry as a {@link Geometry2D}
	 */
	Geometry2D transform(Geometry2D geometry);

	/**
	 * Returns a 3D equivalent of this 2D transformation.
	 * <p>
	 * Use this to apply the same transformation logic in a 3D context.
	 * </p>
	 *
	 * @return the corresponding 3D transformation as a {@link Transform3D}
	 */
	Transform3D asTransform3D();
}
