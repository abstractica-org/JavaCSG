package org.abstractica.javacsg;

import java.io.IOException;
import java.util.List;

/**
 * Provides a comprehensive set of immutable geometric modeling operations for both 2D and 3D.
 * <p>
 * This interface includes methods for:
 * <ul>
 *   <li>Creating and manipulating {@link Angle}, {@link Vector2D}, {@link Vector3D}, and polar coordinates.</li>
 *   <li>Constructing and transforming 2D and 3D {@link Geometry2D} and {@link Geometry3D} objects.</li>
 *   <li>Performing Boolean operations (union, intersection, difference), hull, and Minkowski operations on geometries.</li>
 *   <li>Extruding 2D geometries into 3D, slicing 3D geometries, and converting between 2D and 3D.</li>
 *   <li>Creating and applying transformations via {@link Transform2D} and {@link Transform3D}.</li>
 *   <li>Loading and saving 3D models in STL format.</li>
 *   <li>Viewing and caching geometries for analysis or inspection.</li>
 * </ul>
 * All methods return new immutable values and never modify their input arguments.
 * Use these methods to build, transform, and combine geometries for modeling, visualization, and computational geometry tasks.
 */
public interface JavaCSG
{
	////////////////////////////////////////////////////////////////////////////////////////////////
	// Color
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
 * Creates a new {@link Color} with the specified RGBA components.
 *
 * @param r the red component in the interval [0.0,1.0]
 * @param g the green component in the interval [0.0,1.0]
 * @param b the blue component in the interval [0.0,1.0]
 * @param a the alpha (transparency) in the interval [0.0,1.0]
 * @return a new {@link Color}
 */
Color color(double r, double g, double b, double a);

	/**
	 * Creates a new {@link Color} with the specified RGB components.
	 *
	 * @param r the red component in the interval [0.0,1.0]
	 * @param g the greencomponent in the interval [0.0,1.0]
	 * @param b the bluecomponent in the interval [0.0,1.0]
	 * @return a new {@link Color} with full opacity
	 */
	Color color(double r, double g, double b);

	/**
	 * Creates a new {@link Color} with the specified RGBA components.
	 *
	 * @param r the red component in the interval [0,255]
	 * @param g the green component in the interval [0,255]
	 * @param b the blue component in the interval [0,255]
	 * @param a the alpha (transparency) in the interval [0, 255]
	 * @return a new {@link Color}
	 */
	Color color(int r, int g, int b, int a);

	/**
	 * Creates a new {@link Color} with the specified RGB components.
	 *
	 * @param r the red component in the interval [0,255]
	 * @param g the green component in the interval [0,255]
	 * @param b the blue component in the interval [0,255]
	 * @return a new {@link Color} with full opacity
	 */
	Color color(int r, int g, int b);
	////////////////////////////////////////////////////////////////////////////////////////////////
	// Angle
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates a new {@link Angle} specified as a fraction of a full rotation.
	 * <p>
	 * Use this to define angles by how many full turns they represent.
	 *
	 * @param rotations the angle in full rotations (1.0 = 360°)
	 * @return a new {@link Angle} representing the given number of rotations
	 */
	Angle rotations(double rotations);

	/**
	 * Creates a new {@link Angle} measured in degrees.
	 *
	 * @param degrees the angle in degrees
	 * @return a new {@link Angle} representing the given degrees
	 */
	Angle degrees(double degrees);

	/**
	 * Creates a new {@link Angle} measured in radians.
	 *
	 * @param radians the angle in radians
	 * @return a new {@link Angle} representing the given radians
	 */
	Angle radians(double radians);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// Vector2D
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates a new {@link Vector2D} with the specified coordinates.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @return a new {@link Vector2D}
	 */
	Vector2D vector2D(double x, double y);

	/**
	 * Computes the squared length of the given 2D vector.
	 * <p>
	 * Use this to avoid unnecessary square root operations when comparing lengths.
	 * </p>
	 *
	 * @param vector the {@link Vector2D} whose squared length is desired
	 * @return the squared length of the vector
	 */
	double sqrLength(Vector2D vector);

	/**
	 * Computes the length (magnitude) of the given 2D vector.
	 *
	 * @param vector the {@link Vector2D} to measure
	 * @return the length of the vector
	 */
	double length(Vector2D vector);

	/**
	 * Computes the distance between two points in 2D space.
	 *
	 * @param vector1 the first point as a {@link Vector2D}
	 * @param vector2 the second point as a {@link Vector2D}
	 * @return the distance between the two points
	 */
	double dist(Vector2D vector1, Vector2D vector2);

	/**
	 * Normalizes the given 2D vector to unit length.
	 *
	 * @param vector the {@link Vector2D} to normalize
	 * @return a new {@link Vector2D} of unit length
	 */
	Vector2D normalized(Vector2D vector);

	/**
	 * Adds two 2D vectors component-wise.
	 *
	 * @param vector1 the first {@link Vector2D}
	 * @param vector2 the second {@link Vector2D}
	 * @return a new {@link Vector2D} representing the sum
	 */
	Vector2D add(Vector2D vector1, Vector2D vector2);

	/**
	 * Subtracts the second 2D vector from the first component-wise.
	 *
	 * @param vector1 the first {@link Vector2D}
	 * @param vector2 the second {@link Vector2D}
	 * @return a new {@link Vector2D} representing the difference
	 */
	Vector2D sub(Vector2D vector1, Vector2D vector2);

	/**
	 * Multiplies the given 2D vector by a scalar.
	 *
	 * @param vector the {@link Vector2D} to scale
	 * @param scalar the scale factor
	 * @return a new scaled {@link Vector2D}
	 */
	Vector2D mul(Vector2D vector, double scalar);

	/**
	 * Divides the given 2D vector by a scalar.
	 *
	 * @param vector the {@link Vector2D} to scale
	 * @param scalar the divisor
	 * @return a new scaled {@link Vector2D}
	 */
	Vector2D div(Vector2D vector, double scalar);

	/**
	 * Computes the dot product of two 2D vectors.
	 *
	 * @param vector1 the first {@link Vector2D}
	 * @param vector2 the second {@link Vector2D}
	 * @return the dot product
	 */
	double dot(Vector2D vector1, Vector2D vector2);

	/**
	 * Creates a vector from one point to another in 2D space.
	 *
	 * @param from the starting point as a {@link Vector2D}
	 * @param to the ending point as a {@link Vector2D}
	 * @return a new {@link Vector2D} representing the direction and distance from "from" to "to"
	 */
	Vector2D fromTo(Vector2D from, Vector2D to);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// Polar2D
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates a new {@link Polar2D} coordinate.
	 *
	 * @param r the radial distance
	 * @param phi the angle as an {@link Angle}
	 * @return a new {@link Polar2D}
	 */
	Polar2D polar2D(double r, Angle phi);

	/**
	 * Converts a 2D vector to polar coordinates.
	 *
	 * @param vector the {@link Vector2D} to convert
	 * @return a new {@link Polar2D} representing the vector in polar form
	 */
	Polar2D asPolar2D(Vector2D vector);

	/**
	 * Converts a polar coordinate to a 2D vector.
	 *
	 * @param polar the {@link Polar2D} to convert
	 * @return a new {@link Vector2D} representing the polar coordinate in Cartesian form
	 */
	Vector2D asVector2D(Polar2D polar);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// Polygon2D
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates a 2D polygon from a set of vertices.
	 *
	 * @param vertices an {@link Iterable} of {@link Vector2D} defining the polygon vertices
	 * @return a new {@link Geometry2D} polygon
	 */
	Geometry2D polygon2D(Iterable<Vector2D> vertices);

	/**
	 * Creates a 2D polygon with possibly multiple paths or holes.
	 *
	 * @param vertices an {@link Iterable} of {@link Vector2D} defining all polygon vertices
	 * @param paths an {@link Iterable} of paths, each path is an {@link Iterable} of vertex indices
	 * @return a new {@link Geometry2D} polygon
	 */
	Geometry2D polygon2D(Iterable<Vector2D> vertices, Iterable<? extends Iterable<Integer>> paths);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// 2D transformations
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the identity 2D transformation.
	 *
	 * @return a new identity {@link Transform2D}
	 */
	Transform2D identity2D();

	/**
	 * Composes multiple 2D transformations into one.
	 * <p>
	 * The transformations are applied in the order of matrix multiplikation.
	 * The list of {T1, T2, T3} is equivalent to T1 * T2 * T3.
	 * </p>
	 *
	 * @param transforms a list of {@link Transform2D}
	 * @return a new composed {@link Transform2D}
	 */
	Transform2D compose2D(List<Transform2D> transforms);

	/**
	 * Composes multiple 2D transformations into one.
	 * <p>
	 * The transformations are applied in the order of matrix multiplikation.
	 * The array of [T1, T2, T3] is equivalent to T1 * T2 * T3.
	 * </p>
	 *
	 * @param transforms an array of {@link Transform2D}
	 * @return a new composed {@link Transform2D}
	 */
	Transform2D compose2D(Transform2D... transforms);

	/**
	 * Creates a 2D translation transformation.
	 *
	 * @param x translation along the x-axis
	 * @param y translation along the y-axis
	 * @return a new {@link Transform2D} representing the translation
	 */
	Transform2D translate2D(double x, double y);

	/**
	 * Creates a 2D translation transformation along the x-axis.
	 *
	 * @param x translation along the x-axis
	 * @return a new {@link Transform2D} for the translation
	 */
	Transform2D translate2DX(double x);

	/**
	 * Creates a 2D translation transformation along the y-axis.
	 *
	 * @param y translation along the y-axis
	 * @return a new {@link Transform2D} for the translation
	 */
	Transform2D translate2DY(double y);

	/**
	 * Creates a 2D translation transformation.
	 *
	 * @param v translation along the x-axis and the y-axis as a {@link Vector2D}
	 * @return a new {@link Transform2D} representing the translation
	 */
	Transform2D translate2D(Vector2D v);

	/**
	 * Creates a 2D translation transformation from one point to another.
	 *
	 * @param from the starting point as a {@link Vector2D}
	 * @param to the ending point as a {@link Vector2D}
	 * @return a new {@link Transform2D} translating from "from" to "to"
	 */
	Transform2D translate2DFromTo(Vector2D from, Vector2D to);

	/**
	 * Creates a 2D rotation transformation around the origin.
	 *
	 * @param angle the rotation angle as an {@link Angle}
	 * @return a new {@link Transform2D} representing the rotation
	 */
	Transform2D rotate2D(Angle angle);

	/**
	 * Creates a 2D rotation transformation around a specified point.
	 *
	 * @param point the center point as a {@link Vector2D}
	 * @param angle the rotation angle as an {@link Angle}
	 * @return a new {@link Transform2D} representing the rotation
	 */
	Transform2D rotate2DAround(Vector2D point, Angle angle);

	/**
	 * Creates a 2D scaling transformation.
	 *
	 * @param x scale factor along the x-axis
	 * @param y scale factor along the y-axis
	 * @return a new {@link Transform2D} representing the scaling
	 */
	Transform2D scale2D(double x, double y);

	/**
	 * Creates a uniform 2D scaling transformation.
	 *
	 * @param s scale factor
	 * @return a new {@link Transform2D} representing the scaling
	 */
	Transform2D scale2D(double s);

	/**
	 * Creates a 2D mirroring transformation relative to a line defined by the given normal.
	 *
	 * @param normX the x-component of the mirror line’s normal vector
	 * @param normY the y-component of the mirror line’s normal vector
	 * @return a new {@link Transform2D} representing the mirror
	 */
	Transform2D mirror2D(double normX, double normY);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// 2D operations
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Computes the union of multiple 2D geometries.
	 *
	 * @param geometries an {@link Iterable} of {@link Geometry2D}
	 * @return a new {@link Geometry2D} representing the union
	 */
	Geometry2D union2D(Iterable<Geometry2D> geometries);

	/**
	 * Computes the union of multiple 2D geometries.
	 *
	 * @param geometries an array of {@link Geometry2D}
	 * @return a new {@link Geometry2D} representing the union
	 */
	Geometry2D union2D(Geometry2D... geometries);

	/**
	 * Computes the union of a base 2D geometry and additional geometries.
	 *
	 * @param geometry the base {@link Geometry2D}
	 * @param geometries an {@link Iterable} of additional {@link Geometry2D}
	 * @return a new {@link Geometry2D} representing the union
	 */
	Geometry2D union2D(Geometry2D geometry, Iterable<Geometry2D> geometries);

	/**
	 * Computes the intersection of multiple 2D geometries.
	 *
	 * @param geometries an {@link Iterable} of {@link Geometry2D}
	 * @return a new {@link Geometry2D} representing the intersection
	 */
	Geometry2D intersection2D(Iterable<Geometry2D> geometries);

	/**
	 * Computes the intersection of multiple 2D geometries.
	 *
	 * @param geometries an array of {@link Geometry2D}
	 * @return a new {@link Geometry2D} representing the intersection
	 */
	Geometry2D intersection2D(Geometry2D... geometries);

	/**
	 * Computes the difference of a filled 2D geometry with multiple cutouts.
	 *
	 * @param filled the base {@link Geometry2D}
	 * @param cutouts an {@link Iterable} of {@link Geometry2D} to subtract
	 * @return a new {@link Geometry2D} representing the difference
	 */
	Geometry2D difference2D(Geometry2D filled, Iterable<Geometry2D> cutouts);

	/**
	 * Computes the difference of a filled 2D geometry with one or more cutouts.
	 *
	 * @param filled the base {@link Geometry2D}
	 * @param cutouts an array of {@link Geometry2D} to subtract
	 * @return a new {@link Geometry2D} representing the difference
	 */
	Geometry2D difference2D(Geometry2D filled, Geometry2D... cutouts);

	/**
	 * Computes the convex hull of multiple 2D geometries.
	 *
	 * @param geometries an {@link Iterable} of {@link Geometry2D}
	 * @return a new {@link Geometry2D} representing the hull
	 */
	Geometry2D hull2D(Iterable<Geometry2D> geometries);

	/**
	 * Computes the convex hull of multiple 2D geometries.
	 *
	 * @param geometries an array of {@link Geometry2D}
	 * @return a new {@link Geometry2D} representing the hull
	 */
	Geometry2D hull2D(Geometry2D... geometries);

	/**
	 * Computes the Minkowski sum of multiple 2D geometries.
	 *
	 * @param geometries an {@link Iterable} of {@link Geometry2D}
	 * @return a new {@link Geometry2D} representing the Minkowski sum
	 */
	Geometry2D minkowski2D(Iterable<Geometry2D> geometries);

	/**
	 * Computes the Minkowski sum of multiple 2D geometries.
	 *
	 * @param geometries an array of {@link Geometry2D}
	 * @return a new {@link Geometry2D} representing the Minkowski sum
	 */
	Geometry2D minkowski2D(Geometry2D... geometries);

	/**
	 * Offsets 2D geometries by a certain delta, optionally creating a chamfered offset.
	 *
	 * @param delta the offset distance
	 * @param chamfer if true, creates a chamfered offset
	 * @param geometries an array of {@link Geometry2D} to offset
	 * @return a new {@link Geometry2D} representing the offset geometry
	 */
	Geometry2D offset2D(double delta, boolean chamfer, Geometry2D... geometries);

	/**
	 * Offsets 2D geometries by a certain delta, optionally creating a chamfered offset.
	 *
	 * @param delta the offset distance
	 * @param chamfer if true, creates a chamfered offset
	 * @param geometries an {@link Iterable} of {@link Geometry2D}
	 * @return a new {@link Geometry2D} representing the offset geometry
	 */
	Geometry2D offset2D(double delta, boolean chamfer, Iterable<Geometry2D> geometries);

	/**
	 * Offsets 2D geometries with rounded corners.
	 *
	 * @param radius the rounding radius
	 * @param angularResolution the angular resolution for rounding
	 * @param geometries an array of {@link Geometry2D}
	 * @return a new {@link Geometry2D} with rounded offsets
	 */
	Geometry2D offsetRound2D(double radius, int angularResolution, Geometry2D... geometries);

	/**
	 * Offsets 2D geometries with rounded corners.
	 *
	 * @param radius the rounding radius
	 * @param angularResolution the angular resolution for rounding
	 * @param geometries an {@link Iterable} of {@link Geometry2D}
	 * @return a new {@link Geometry2D} with rounded offsets
	 */
	Geometry2D offsetRound2D(double radius, int angularResolution, Iterable<Geometry2D> geometries);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// Text2D
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates a 2D geometry representing a single character with a specified width.
	 *
	 * @param ch the character
	 * @param width the character width
	 * @param angularResolution the resolution for curved segments
	 * @return a new {@link Geometry2D} representing the character
	 */
	Geometry2D char2D(char ch, double width, int angularResolution);

	/**
	 * Creates a 2D geometry representing a single character with specified width and height.
	 *
	 * @param ch the character
	 * @param width the character width
	 * @param height the character height
	 * @param angularResolution the resolution for curved segments
	 * @return a new {@link Geometry2D} representing the character
	 */
	Geometry2D char2D(char ch, double width, double height, int angularResolution);

	/**
	 * Computes the character height corresponding to a given character width.
	 *
	 * @param width the character width
	 * @return the character height
	 */
	double charHeight2D(double width);

	/**
	 * Computes the character baseline given a character height.
	 *
	 * @param height the character height
	 * @return the baseline offset
	 */
	double charBaseline2D(double height);

	/**
	 * Creates a 2D geometry representing a text string with a specified letter width.
	 *
	 * @param text the text string
	 * @param letterWidth the width of each character
	 * @param angularResolution the resolution for curved segments
	 * @return a new {@link Geometry2D} representing the text
	 */
	Geometry2D text2D(String text, double letterWidth, int angularResolution);

	/**
	 * Creates a 2D geometry representing a text string with specified letter width and height.
	 *
	 * @param text the text string
	 * @param letterWidth the width of each character
	 * @param letterHeight the height of each character
	 * @param angularResolution the resolution for curved segments
	 * @return a new {@link Geometry2D} representing the text
	 */
	Geometry2D text2D(String text, double letterWidth, double letterHeight, int angularResolution);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// 2D shapes
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates a 2D circle.
	 *
	 * @param diameter the circle’s diameter
	 * @param angularResolution the resolution for the circle’s curve
	 * @return a new {@link Geometry2D} circle
	 */
	Geometry2D circle2D(double diameter, int angularResolution);

	/**
	 * Creates a 2D pie shape (a circular sector).
	 *
	 * @param diameter the full circle’s diameter
	 * @param beginAngle the starting angle as an {@link Angle}
	 * @param endAngle the ending angle as an {@link Angle}
	 * @param angularResolution the resolution for the curve
	 * @return a new {@link Geometry2D} pie shape
	 */
	Geometry2D pie2D(double diameter, Angle beginAngle, Angle endAngle, int angularResolution);

	/**
	 * Creates a cutout pie, essentially the edge of a circle defined by two angles.
	 *
	 * @param diameter the circle’s diameter
	 * @param beginAngle the starting angle as an {@link Angle}
	 * @param endAngle the ending angle as an {@link Angle}
	 * @return a new {@link Geometry2D} representing the cutout pie
	 */
	Geometry2D cutoutPie2D(double diameter, Angle beginAngle, Angle endAngle);

	/**
	 * Creates a circle segment, a portion of a circle’s circumference.
	 *
	 * @param diameter the circle’s diameter
	 * @param beginAngle the starting angle as an {@link Angle}
	 * @param endAngle the ending angle as an {@link Angle}
	 * @param angularResolution the resolution for the curve
	 * @return a new {@link Geometry2D} circle segment
	 */
	Geometry2D circleSegment2D(double diameter, Angle beginAngle, Angle endAngle, int angularResolution);

	/**
	 * Creates a 2D hollow circle defined by inner and outer diameters.
	 *
	 * @param innerDiameter the inner circle’s diameter
	 * @param outerDiameter the outer circle’s diameter
	 * @param angularResolution the resolution for the curves
	 * @return a new {@link Geometry2D} ring
	 */
	Geometry2D hollowCircle2D(double innerDiameter, double outerDiameter, int angularResolution);

	/**
	 * Creates a 2D segment of a hollow circle, a portion of a hollw circle defined by angles.
	 *
	 * @param innerDiameter the inner circle’s diameter
	 * @param outerDiameter the outer circle’s diameter
	 * @param beginAngle the starting angle as an {@link Angle}
	 * @param endAngle the ending angle as an {@link Angle}
	 * @param angularResolution the resolution for the curves
	 * @return a new {@link Geometry2D} ring segment
	 */
	Geometry2D hollowCircleSegment2D(double innerDiameter,
									 double outerDiameter,
									 Angle beginAngle,
									 Angle endAngle,
									 int angularResolution);

	/**
	 * Creates a 2D rectangle.
	 *
	 * @param xSize the width of the rectangle
	 * @param ySize the height of the rectangle
	 * @return a new {@link Geometry2D} rectangle
	 */
	Geometry2D rectangle2D(double xSize, double ySize);

	/**
	 * Creates a 2D rectangle defined by two corner points.
	 *
	 * @param c1x the x-coordinate of the first corner
	 * @param c1y the y-coordinate of the first corner
	 * @param c2x the x-coordinate of the opposite corner
	 * @param c2y the y-coordinate of the opposite corner
	 * @return a new {@link Geometry2D} rectangle
	 */
	Geometry2D rectangleCorners2D(double c1x, double c1y, double c2x, double c2y);

	/**
	 * Creates a 2D rectangle defined by a center point and sizes.
	 *
	 * @param cx the x-coordinate of the center
	 * @param cy the y-coordinate of the center
	 * @param xSize the width
	 * @param ySize the height
	 * @return a new {@link Geometry2D} rectangle
	 */
	Geometry2D rectangleCenter2D(double cx, double cy, double xSize, double ySize);

	/**
	 * Creates a 2D right-angled triangle.
	 *
	 * @param xSize the base length
	 * @param ySize the height
	 * @return a new {@link Geometry2D} right triangle
	 */
	Geometry2D rightTriangle2D(double xSize, double ySize);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// Vector3D
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates a new {@link Vector3D} with the specified coordinates.
	 *
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 * @param z the z-coordinate
	 * @return a new {@link Vector3D}
	 */
	Vector3D vector3D(double x, double y, double z);

	/**
	 * Computes the squared length of the given 3D vector.
	 *
	 * @param vector the {@link Vector3D}
	 * @return the squared length
	 */
	double sqrLength(Vector3D vector);

	/**
	 * Computes the length (magnitude) of the given 3D vector.
	 *
	 * @param vector the {@link Vector3D}
	 * @return the length
	 */
	double length(Vector3D vector);

	/**
	 * Computes the distance between two points in 3D space.
	 *
	 * @param vector1 the first point as a {@link Vector3D}
	 * @param vector2 the second point as a {@link Vector3D}
	 * @return the distance between the points
	 */
	double dist(Vector3D vector1, Vector3D vector2);

	/**
	 * Normalizes the given 3D vector to unit length.
	 *
	 * @param vector the {@link Vector3D} to normalize
	 * @return a new unit-length {@link Vector3D}
	 */
	Vector3D normalized(Vector3D vector);

	/**
	 * Adds two 3D vectors component-wise.
	 *
	 * @param vector1 the first {@link Vector3D}
	 * @param vector2 the second {@link Vector3D}
	 * @return a new {@link Vector3D} representing the sum
	 */
	Vector3D add(Vector3D vector1, Vector3D vector2);

	/**
	 * Subtracts the second 3D vector from the first component-wise.
	 *
	 * @param vector1 the first {@link Vector3D}
	 * @param vector2 the second {@link Vector3D}
	 * @return a new {@link Vector3D} representing the difference
	 */
	Vector3D sub(Vector3D vector1, Vector3D vector2);

	/**
	 * Multiplies the given 3D vector by a scalar.
	 *
	 * @param vector the {@link Vector3D} to scale
	 * @param scalar the scale factor
	 * @return a new scaled {@link Vector3D}
	 */
	Vector3D mul(Vector3D vector, double scalar);

	/**
	 * Divides the given 3D vector by a scalar.
	 *
	 * @param vector the {@link Vector3D} to scale
	 * @param scalar the divisor
	 * @return a new scaled {@link Vector3D}
	 */
	Vector3D div(Vector3D vector, double scalar);

	/**
	 * Computes the dot product of two 3D vectors.
	 *
	 * @param vector1 the first {@link Vector3D}
	 * @param vector2 the second {@link Vector3D}
	 * @return the dot product
	 */
	double dot(Vector3D vector1, Vector3D vector2);

	/**
	 * Computes the cross product of two 3D vectors.
	 *
	 * @param vector1 the first {@link Vector3D}
	 * @param vector2 the second {@link Vector3D}
	 * @return a new {@link Vector3D} representing the cross product
	 */
	Vector3D cross(Vector3D vector1, Vector3D vector2);

	/**
	 * Creates a vector from one point to another in 3D space.
	 *
	 * @param from the starting point as a {@link Vector3D}
	 * @param to the ending point as a {@link Vector3D}
	 * @return a new {@link Vector3D} representing the direction and distance
	 */
	Vector3D fromTo(Vector3D from, Vector3D to);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// Polyhedron3D
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates a 3D polyhedron from a set of vertices and faces.
	 *
	 * @param vertices an {@link Iterable} of {@link Vector3D} for the vertices
	 * @param faces an {@link Iterable} of faces, each face is an {@link Iterable} of vertex indices
	 * @return a new {@link Geometry3D} polyhedron
	 */
	Geometry3D polyhedron3D(Iterable<Vector3D> vertices, Iterable<? extends Iterable<Integer>> faces);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// 2D to 3D operations
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates a 3D geometry by linearly extruding a 2D geometry.
	 *
	 * @param height the extrusion height
	 * @param twist the rotation applied along the extrusion as an {@link Angle}
	 * @param scale the scale factor applied along the extrusion
	 * @param slices the number of slices along the extrusion height
	 * @param centerZ if true, centers the geometry along the Z-axis
	 * @param geometry the {@link Geometry2D} to extrude
	 * @return a new {@link Geometry3D} representing the extruded shape
	 */
	Geometry3D linearExtrude(double height,
							 Angle twist,
							 double scale,
							 int slices,
							 boolean centerZ,
							 Geometry2D geometry);

	/**
	 * Creates a 3D geometry by linearly extruding a 2D geometry.
	 *
	 * @param height the extrusion height
	 * @param centerZ if true, centers the geometry along the Z-axis
	 * @param geometry the {@link Geometry2D} to extrude
	 * @return a new {@link Geometry3D} representing the extruded shape
	 */
	Geometry3D linearExtrude(double height, boolean centerZ, Geometry2D geometry);

	/**
	 * Creates a 3D geometry by rotating a 2D geometry around an axis.
	 *
	 * @param angle the rotation angle as an {@link Angle}
	 * @param angularResolution the number of segments around the rotation
	 * @param geometry the {@link Geometry2D} to extrude
	 * @return a new {@link Geometry3D} representing the rotated extrusion
	 */
	Geometry3D rotateExtrude(Angle angle, int angularResolution, Geometry2D geometry);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// 3D to 2D operations
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Projects a 3D geometry onto a 2D plane.
	 *
	 * @param cutAtZeroZ if true, cuts the geometry at Z=0 before projecting
	 * @param geometry the {@link Geometry3D} to project
	 * @return a new {@link Geometry2D} representing the projection
	 */
	Geometry2D project(boolean cutAtZeroZ, Geometry3D geometry);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// 3D transformations
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the identity 3D transformation.
	 *
	 * @return a new identity {@link Transform3D}
	 */
	Transform3D identity3D();


	/**
	 * Composes multiple 3D transformations into one.
	 * <p>
	 * The transformations are applied in the order of matrix multiplikation.
	 * The list of {T1, T2, T3} is equivalent to T1 * T2 * T3.
	 *
	 * @param transforms a list of {@link Transform3D}
	 * @return a new composed {@link Transform3D}
	 */
	Transform3D compose3D(List<Transform3D> transforms);

	/**
	 * Composes multiple 3D transformations into one.
	 * <p>
	 * The transformations are applied in the order of matrix multiplikation.
	 * The array of [T1, T2, T3] is equivalent to T1 * T2 * T3.
	 *
	 * @param transforms an array of {@link Transform3D}
	 * @return a new composed {@link Transform3D}
	 */
	Transform3D compose3D(Transform3D... transforms);

	/**
	 * Creates a 3D translation transformation.
	 *
	 * @param x translation along the x-axis
	 * @param y translation along the y-axis
	 * @param z translation along the z-axis
	 * @return a new {@link Transform3D} translation
	 */
	Transform3D translate3D(double x, double y, double z);

	/**
	 * Creates a 3D translation transformation along the x-axis.
	 *
	 * @param x translation along the x-axis
	 * @return a new {@link Transform3D}
	 */
	Transform3D translate3DX(double x);

	/**
	 * Creates a 3D translation transformation along the y-axis.
	 *
	 * @param y translation along the y-axis
	 * @return a new {@link Transform3D}
	 */
	Transform3D translate3DY(double y);

	/**
	 * Creates a 3D translation transformation along the z-axis.
	 *
	 * @param z translation along the z-axis
	 * @return a new {@link Transform3D}
	 */
	Transform3D translate3DZ(double z);

	/**
	 * Creates a 3D translation transformation.
	 *
	 * @param v translation along the x-axis, y-axis, and z-axis as a {@link Vector3D}
	 * @return a new {@link Transform3D} translation
	 */
	Transform3D translate3D(Vector3D v);

	/**
	 * Creates a 3D translation transformation from one point to another.
	 *
	 * @param from the starting point as a {@link Vector3D}
	 * @param to the ending point as a {@link Vector3D}
	 * @return a new {@link Transform3D} translating from "from" to "to"
	 */
	Transform3D translate3DFromTo(Vector3D from, Vector3D to);

	/**
	 * Creates a 3D rotation transformation around the X, Y, and Z axes in that order.
	 *
	 * @param angleX rotation around the X-axis as an {@link Angle}
	 * @param angleY rotation around the Y-axis as an {@link Angle}
	 * @param angleZ rotation around the Z-axis as an {@link Angle}
	 * @return a new {@link Transform3D}
	 */
	Transform3D rotate3D(Angle angleX, Angle angleY, Angle angleZ);

	/**
	 * Creates a 3D rotation transformation around a specified point.
	 *
	 * @param point the center {@link Vector3D} around which to rotate
	 * @param angleX rotation around the X-axis
	 * @param angleY rotation around the Y-axis
	 * @param angleZ rotation around the Z-axis
	 * @return a new {@link Transform3D}
	 */
	Transform3D rotate3DAround(Vector3D point, Angle angleX, Angle angleY, Angle angleZ);

	/**
	 * Creates a 3D rotation transformation around the X-axis.
	 *
	 * @param angle the rotation angle as an {@link Angle}
	 * @return a new {@link Transform3D}
	 */
	Transform3D rotate3DX(Angle angle);

	/**
	 * Creates a 3D rotation transformation around the X-axis, centered at a specified point.
	 *
	 * @param point the center {@link Vector3D}
	 * @param angle the rotation angle
	 * @return a new {@link Transform3D}
	 */
	Transform3D rotate3DXAround(Vector3D point, Angle angle);

	/**
	 * Creates a 3D rotation transformation around the Y-axis.
	 *
	 * @param angle the rotation angle
	 * @return a new {@link Transform3D}
	 */
	Transform3D rotate3DY(Angle angle);

	/**
	 * Creates a 3D rotation transformation around the Y-axis, centered at a specified point.
	 *
	 * @param point the center {@link Vector3D}
	 * @param angle the rotation angle
	 * @return a new {@link Transform3D}
	 */
	Transform3D rotate3DYAround(Vector3D point, Angle angle);

	/**
	 * Creates a 3D rotation transformation around the Z-axis.
	 *
	 * @param angle the rotation angle
	 * @return a new {@link Transform3D}
	 */
	Transform3D rotate3DZ(Angle angle);

	/**
	 * Creates a 3D rotation transformation around the Z-axis, centered at a specified point.
	 *
	 * @param point the center {@link Vector3D}
	 * @param angle the rotation angle
	 * @return a new {@link Transform3D}
	 */
	Transform3D rotate3DZAround(Vector3D point, Angle angle);

	/**
	 * Creates a 3D scaling transformation.
	 *
	 * @param x scale factor along the x-axis
	 * @param y scale factor along the y-axis
	 * @param z scale factor along the z-axis
	 * @return a new {@link Transform3D}
	 */
	Transform3D scale3D(double x, double y, double z);

	/**
	 * Creates a uniform 3D scaling transformation.
	 *
	 * @param s scale factor
	 * @return a new {@link Transform3D}
	 */
	Transform3D scale3D(double s);

	/**
	 * Creates a 3D mirroring transformation relative to a plane defined by a normal vector.
	 *
	 * @param normX the x-component of the plane’s normal
	 * @param normY the y-component of the plane’s normal
	 * @param normZ the z-component of the plane’s normal
	 * @return a new {@link Transform3D}
	 */
	Transform3D mirror3D(double normX, double normY, double normZ);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// 3D operations
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Computes the union of multiple 3D geometries.
	 *
	 * @param geometries an array of {@link Geometry3D}
	 * @return a new {@link Geometry3D} representing the union
	 */
	Geometry3D union3D(Geometry3D... geometries);

	/**
	 * Computes the union of multiple 3D geometries.
	 *
	 * @param geometries an {@link Iterable} of {@link Geometry3D}
	 * @return a new {@link Geometry3D} representing the union
	 */
	Geometry3D union3D(Iterable<Geometry3D> geometries);

	/**
	 * Computes the union of a base 3D geometry and additional geometries.
	 *
	 * @param geometry the base {@link Geometry3D}
	 * @param geometries an {@link Iterable} of additional {@link Geometry3D}
	 * @return a new {@link Geometry3D} representing the union
	 */
	Geometry3D union3D(Geometry3D geometry, Iterable<Geometry3D> geometries);

	/**
	 * Computes the intersection of multiple 3D geometries.
	 *
	 * @param geometries an array of {@link Geometry3D}
	 * @return a new {@link Geometry3D} representing the intersection
	 */
	Geometry3D intersection3D(Geometry3D... geometries);

	/**
	 * Computes the intersection of multiple 3D geometries.
	 *
	 * @param geometries an {@link Iterable} of {@link Geometry3D}
	 * @return a new {@link Geometry3D} representing the intersection
	 */
	Geometry3D intersection3D(Iterable<Geometry3D> geometries);

	/**
	 * Computes the difference of a solid 3D geometry and one or more cutouts.
	 *
	 * @param solid the base {@link Geometry3D}
	 * @param cutouts an array of {@link Geometry3D} to subtract
	 * @return a new {@link Geometry3D} representing the difference
	 */
	Geometry3D difference3D(Geometry3D solid, Geometry3D... cutouts);

	/**
	 * Computes the difference of a solid 3D geometry and multiple cutouts.
	 *
	 * @param solid the base {@link Geometry3D}
	 * @param cutouts an {@link Iterable} of {@link Geometry3D} to subtract
	 * @return a new {@link Geometry3D} representing the difference
	 */
	Geometry3D difference3D(Geometry3D solid, Iterable<Geometry3D> cutouts);

	/**
	 * Computes the convex hull of multiple 3D geometries.
	 *
	 * @param geometries an array of {@link Geometry3D}
	 * @return a new {@link Geometry3D} representing the hull
	 */
	Geometry3D hull3D(Geometry3D... geometries);

	/**
	 * Computes the convex hull of multiple 3D geometries.
	 *
	 * @param geometries an {@link Iterable} of {@link Geometry3D}
	 * @return a new {@link Geometry3D} representing the hull
	 */
	Geometry3D hull3D(Iterable<Geometry3D> geometries);

	/**
	 * Computes the Minkowski sum of multiple 3D geometries.
	 *
	 * @param geometries an array of {@link Geometry3D}
	 * @return a new {@link Geometry3D} representing the Minkowski sum
	 */
	Geometry3D minkowski3D(Geometry3D... geometries);

	/**
	 * Computes the Minkowski sum of multiple 3D geometries.
	 *
	 * @param geometries an {@link Iterable} of {@link Geometry3D}
	 * @return a new {@link Geometry3D} representing the Minkowski sum
	 */
	Geometry3D minkowski3D(Iterable<Geometry3D> geometries);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// Color 3D geometries
	////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Colors a Geometry3D with a rgba color.
	 *
	 * @param color the color to use {@link Color}
	 * @param geometry the geometry to be colored {@link Geometry3D}
	 * @return a new {@link Geometry3D} representing the colored geometry
	 */
	Geometry3D color3D(Color color, Geometry3D geometry);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// 3D shapes
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates a 3D sphere.
	 *
	 * @param diameter the sphere’s diameter
	 * @param angularResolution the resolution of the sphere’s surface
	 * @param centerZ if true, centers the sphere along the Z-axis
	 * @return a new {@link Geometry3D} sphere
	 */
	Geometry3D sphere3D(double diameter, int angularResolution, boolean centerZ);

	/**
	 * Creates a 3D box.
	 *
	 * @param xSize the size along x-axis
	 * @param ySize the size along y-axis
	 * @param zSize the size along z-axis
	 * @param centerZ if true, centers the box along the Z-axis
	 * @return a new {@link Geometry3D} box
	 */
	Geometry3D box3D(double xSize, double ySize, double zSize, boolean centerZ);

	/**
	 * Creates a 3D box defined by a center point and extents.
	 *
	 * @param cx the center x-coordinate
	 * @param cy the center y-coordinate
	 * @param cz the center z-coordinate
	 * @param xSize the size along x-axis
	 * @param ySize the size along y-axis
	 * @param zSize the size along z-axis
	 * @return a new {@link Geometry3D} box
	 */
	Geometry3D boxCenter3D(double cx, double cy, double cz, double xSize, double ySize, double zSize);

	/**
	 * Creates a 3D box defined by a center point and extents.
	 *
	 * @param center the center point as a {@link Vector3D}
	 * @param xSize the size along x-axis
	 * @param ySize the size along y-axis
	 * @param zSize the size along z-axis
	 * @return a new {@link Geometry3D} box
	 */
	Geometry3D boxCenter3D(Vector3D center, double xSize, double ySize, double zSize);

	/**
	 * Creates a 3D box defined by two corner points.
	 *
	 * @param c1x the x of the first corner
	 * @param c1y the y of the first corner
	 * @param c1z the z of the first corner
	 * @param c2x the x of the opposite corner
	 * @param c2y the y of the opposite corner
	 * @param c2z the z of the opposite corner
	 * @return a new {@link Geometry3D} box
	 */
	Geometry3D boxCorners3D(double c1x, double c1y, double c1z, double c2x, double c2y, double c2z);

	/**
	 * Creates a 3D box defined by two corner points.
	 *
	 * @param cornerA the first corner as a {@link Vector3D}
	 * @param cornerB the opposite corner as a {@link Vector3D}
	 * @return a new {@link Geometry3D} box
	 */
	Geometry3D boxCorners3D(Vector3D cornerA, Vector3D cornerB);

	/**
	 * Creates a 3D cylinder.
	 *
	 * @param diameter the diameter of the cylinder
	 * @param height the height of the cylinder
	 * @param angularResolution the resolution around its circumference
	 * @param centerZ if true, centers the cylinder along the Z-axis
	 * @return a new {@link Geometry3D} cylinder
	 */
	Geometry3D cylinder3D(double diameter, double height, int angularResolution, boolean centerZ);

	/**
	 * Creates a cylindrical segment of a certain angular span.
	 *
	 * @param diameter the diameter of the cylinder
	 * @param height the height of the cylinder
	 * @param beginAngle the starting angle
	 * @param endAngle the ending angle
	 * @param angularResolution the resolution around its circumference
	 * @param centerZ if true, centers the cylinder along the Z-axis
	 * @return a new {@link Geometry3D} cylindrical segment
	 */
	Geometry3D cylinderSegment3D(double diameter,
								 double height,
								 Angle beginAngle,
								 Angle endAngle,
								 int angularResolution,
								 boolean centerZ);

	/**
	 * Creates a hollow cylinder with an inner and outer diameter.
	 *
	 * @param innerDiameter the inner diameter
	 * @param outerDiameter the outer diameter
	 * @param height the height of the hollow cylinder
	 * @param angularResolution the resolution around its circumference
	 * @param centerZ if true, centers the cylinder along the Z-axis
	 * @return a new {@link Geometry3D} hollow cylinder
	 */
	Geometry3D hollowCylinder3D(double innerDiameter, double outerDiameter, double height, int angularResolution, boolean centerZ);

	/**
	 * Creates a hollow cylindrical segment defined by angles and diameters.
	 *
	 * @param innerDiameter the inner diameter
	 * @param outerDiameter the outer diameter
	 * @param height the height
	 * @param beginAngle the starting angle
	 * @param endAngle the ending angle
	 * @param angularResolution the resolution
	 * @param centerZ if true, centers the cylinder along the Z-axis
	 * @return a new {@link Geometry3D} hollow cylindrical segment
	 */
	Geometry3D hollowCylinderSegment3D(double innerDiameter,
									   double outerDiameter,
									   double height,
									   Angle beginAngle,
									   Angle endAngle,
									   int angularResolution,
									   boolean centerZ);

	/**
	 * Creates a 3D cone (or frustum if topDiameter differs from bottomDiameter).
	 *
	 * @param bottomDiameter the bottom diameter
	 * @param topDiameter the top diameter
	 * @param height the height of the cone
	 * @param angularResolution the resolution around its circumference
	 * @param centerZ if true, centers the cone along the Z-axis
	 * @return a new {@link Geometry3D} cone or frustum
	 */
	Geometry3D cone3D(double bottomDiameter,
					  double topDiameter,
					  double height,
					  int angularResolution,
					  boolean centerZ);

	/**
	 * Creates a segment of a cone defined by angular bounds.
	 *
	 * @param bottomDiameter the bottom diameter
	 * @param topDiameter the top diameter
	 * @param height the height of the cone
	 * @param beginAngle the starting angle
	 * @param endAngle the ending angle
	 * @param angularResolution the resolution around its circumference
	 * @param centerZ if true, centers the cone along the Z-axis
	 * @return a new {@link Geometry3D} cone segment
	 */
	Geometry3D coneSegment3D(double bottomDiameter,
							 double topDiameter,
							 double height,
							 Angle beginAngle,
							 Angle endAngle,
							 int angularResolution,
							 boolean centerZ);

	/**
	 * Creates a flat 3D cylinder defined by diameter, width, and height.
	 *
	 * @param diameter the diameter of the cylinder
	 * @param width the width of the cylinder (along the X-axis)
	 * @param height the height of the cylinder (along the Z-axis)
	 * @param angularResolution the resolution around its circumference
	 * @param centerZ if true, centers the cylinder along the Z-axis
	 * @return a new {@link Geometry3D} flat cylinder
	 */
	Geometry3D flatCylinder3D(double diameter,
							  double width,
							  double height,
							  int angularResolution,
							  boolean centerZ);

	/**
	 * Creates a torus (doughnut shape).
	 *
	 * @param smallCircleDiameter the diameter of the small cross-sectional circle
	 * @param largeCircleDiameter the diameter of the large ring
	 * @param smallCircleResolution the resolution of the small circle
	 * @param largeCircleResolution the resolution of the large circle
	 * @param centerZ if true, centers the torus along the Z-axis
	 * @return a new {@link Geometry3D} torus
	 */
	Geometry3D torus3D(double smallCircleDiameter,
					   double largeCircleDiameter,
					   int smallCircleResolution,
					   int largeCircleResolution,
					   boolean centerZ);

	/**
	 * Creates a torus segment defined by angular bounds.
	 *
	 * @param smallCircleDiameter the diameter of the small cross-sectional circle
	 * @param largeCircleDiameter the diameter of the large ring
	 * @param beginAngle the starting angle
	 * @param endAngle the ending angle
	 * @param smallCircleResolution the resolution of the small circle
	 * @param largeCircleResolution the resolution of the large circle
	 * @param centerZ if true, centers the torus along the Z-axis
	 * @return a new {@link Geometry3D} torus segment
	 */
	Geometry3D torusSegment3D(double smallCircleDiameter,
							  double largeCircleDiameter,
							  Angle beginAngle,
							  Angle endAngle,
							  int smallCircleResolution,
							  int largeCircleResolution,
							  boolean centerZ);

	/**
	 * Creates a 3D wedge shape.
	 *
	 * @param xSize the size along x-axis
	 * @param ySize the size along y-axis
	 * @param zSize the height along z-axis
	 * @param centerZ if true, centers the wedge along the Z-axis
	 * @return a new {@link Geometry3D} wedge
	 */
	Geometry3D wedge3D(double xSize, double ySize, double zSize, boolean centerZ);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// 3D Operations (Slicing)
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Slices a 3D geometry along the x-axis, keeping only the portion within [xMin, xMax].
	 *
	 * @param xMin the minimum x-bound
	 * @param xMax the maximum x-bound
	 * @param geometry the {@link Geometry3D} to slice
	 * @return a new sliced {@link Geometry3D}
	 */
	Geometry3D slice3DX(double xMin, double xMax, Geometry3D geometry);

	/**
	 * Slices a 3D geometry along the y-axis, keeping only the portion within [yMin, yMax].
	 *
	 * @param yMin the minimum y-bound
	 * @param yMax the maximum y-bound
	 * @param geometry the {@link Geometry3D} to slice
	 * @return a new sliced {@link Geometry3D}
	 */
	Geometry3D slice3DY(double yMin, double yMax, Geometry3D geometry);

	/**
	 * Slices a 3D geometry along the z-axis, keeping only the portion within [zMin, zMax].
	 *
	 * @param zMin the minimum z-bound
	 * @param zMax the maximum z-bound
	 * @param geometry the {@link Geometry3D} to slice
	 * @return a new sliced {@link Geometry3D}
	 */
	Geometry3D slice3DZ(double zMin, double zMax, Geometry3D geometry);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// View geometry
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Displays a 2D geometry for viewing.
	 *
	 * @param geometry the {@link Geometry2D} to view
	 */
	void view(Geometry2D geometry);

	/**
	 * Displays a 3D geometry for viewing.
	 *
	 * @param geometry the {@link Geometry3D} to view
	 */
	void view(Geometry3D geometry);

	/**
	 * Displays a 2D geometry in a specified window for viewing.
	 *
	 * @param geometry the {@link Geometry2D} to view
	 * @param windowID the window identifier
	 */
	void view(Geometry2D geometry, int windowID);

	/**
	 * Displays a 3D geometry in a specified window for viewing.
	 *
	 * @param geometry the {@link Geometry3D} to view
	 * @param windowID the window identifier
	 */
	void view(Geometry3D geometry, int windowID);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// Cache geometry
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Caches a 2D geometry for repeated use.
	 *
	 * @param geometry the {@link Geometry2D} to cache
	 * @return a new cached {@link Geometry2D}
	 */
	Geometry2D cache(Geometry2D geometry);

	/**
	 * Caches a 3D geometry for repeated use.
	 *
	 * @param geometry the {@link Geometry3D} to cache
	 * @return a new cached {@link Geometry3D}
	 */
	Geometry3D cache(Geometry3D geometry);

	////////////////////////////////////////////////////////////////////////////////////////////////
	// Save and load STL
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Loads a 3D geometry from an STL file.
	 *
	 * @param fileName the path to the STL file
	 * @return a new {@link Geometry3D} representing the loaded model
	 * @throws IOException if an I/O error occurs while reading the file
	 */
	Geometry3D loadSTL(String fileName) throws IOException;

	/**
	 * Saves a 3D geometry to an STL file.
	 *
	 * @param fileName the path to the output STL file
	 * @param geometry the {@link Geometry3D} to save
	 * @throws IOException if an I/O error occurs while writing the file
	 */
	void saveSTL(String fileName, Geometry3D geometry) throws IOException;

	////////////////////////////////////////////////////////////////////////////////////////////////
	// Save and load 3MF
	////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Loads a 3D geometry from a 3MF file.
	 *
	 * @param fileName the path to the 3MF file
	 * @return a new {@link Geometry3D} representing the loaded model
	 * @throws IOException if an I/O error occurs while reading the file
	 */
	Geometry3D load3MF(String fileName) throws IOException;

	/**
	 * Saves a 3D geometry to a 3MF file.
	 *
	 * @param fileName the path to the output 3MF file
	 * @param geometry the {@link Geometry3D} to save
	 * @throws IOException if an I/O error occurs while writing the file
	 */
	void save3MF(String fileName, Geometry3D geometry) throws IOException;
}
