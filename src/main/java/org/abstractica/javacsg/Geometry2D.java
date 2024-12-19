package org.abstractica.javacsg;

/**
 * Represents an immutable two-dimensional geometric entity.
 * <p>
 * In addition to the general functionality provided by {@link Geometry}, this interface
 * gives access to the geometry’s bounding region through minimum and maximum coordinates.
 * </p>
 */
public interface Geometry2D extends Geometry
{
	/**
	 * Returns the minimum coordinate defining the lower bounds of this geometry’s extent.
	 * <p>
	 * Use this method to determine the geometry’s position or to compute bounding boxes.
	 * </p>
	 *
	 * @return the minimum coordinate as a {@link Vector2D}
	 */
	Vector2D getMin();

	/**
	 * Returns the maximum coordinate defining the upper bounds of this geometry’s extent.
	 * <p>
	 * Use this method together with {@link #getMin()} to determine the geometry’s full
	 * spatial extent or to perform collision or containment checks.
	 * </p>
	 *
	 * @return the maximum coordinate as a {@link Vector2D}
	 */
	Vector2D getMax();
}
