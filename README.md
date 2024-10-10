# JavaCSG
Java Constructive Solid Geometry. Using OpenSCAD as CSG-engine though JavaOpenSCAD.

# JavaCSG Interface Documentation (Created by ChatGPT 4o, so let me know of any mistakes)

The `JavaCSG` interface provides a rich set of methods for constructing and manipulating 2D and 3D geometries programmatically. It utilizes OpenSCAD as the geometry engine and helps developers perform constructive solid geometry (CSG) operations through an easy-to-use API. This documentation combines all functionality, including the foundational features from `JavaCSGBase`.
## Angle Methods

### `rotations(double rotations)`
Creates an `Angle` instance from a number of full rotations.

- **Parameters**:
  - `rotations` *(double)*: The number of full rotations.
- **Returns**: An `Angle` instance representing the given number of rotations.

### `degrees(double degrees)`
Creates an `Angle` instance from a value in degrees.

- **Parameters**:
  - `degrees` *(double)*: The angle in degrees.
- **Returns**: An `Angle` instance representing the given value in degrees.

### `radians(double radians)`
Creates an `Angle` instance from a value in radians.

- **Parameters**:
  - `radians` *(double)*: The angle in radians.
- **Returns**: An `Angle` instance representing the given value in radians.

---

## Vector2D Methods

### `vector2D(double x, double y)`
Creates a `Vector2D` instance with the given `x` and `y` coordinates.

- **Parameters**:
  - `x` *(double)*: The x-coordinate of the vector.
  - `y` *(double)*: The y-coordinate of the vector.
- **Returns**: A `Vector2D` instance representing the given coordinates.

### `sqrLength(Vector2D vector)`
Calculates the squared length of a `Vector2D` instance. Useful for optimization when comparing lengths without needing to compute the square root.

- **Parameters**:
  - `vector` *(Vector2D)*: The vector whose squared length is to be calculated.
- **Returns**: The squared length of the vector as a `double`.

### `length(Vector2D vector)`
Calculates the length (magnitude) of a `Vector2D` instance.

- **Parameters**:
  - `vector` *(Vector2D)*: The vector whose length is to be calculated.
- **Returns**: The length of the vector as a `double`.

### `dist(Vector2D vector1, Vector2D vector2)`
Calculates the distance between two `Vector2D` instances.

- **Parameters**:
  - `vector1` *(Vector2D)*: The first vector.
  - `vector2` *(Vector2D)*: The second vector.
- **Returns**: The distance between the two vectors as a `double`.

### `normalize(Vector2D vector)`
Normalizes a `Vector2D` instance, resulting in a unit vector in the same direction.

- **Parameters**:
  - `vector` *(Vector2D)*: The vector to normalize.
- **Returns**: A `Vector2D` instance representing the normalized vector.

### `add(Vector2D vector1, Vector2D vector2)`
Adds two `Vector2D` instances together.

- **Parameters**:
  - `vector1` *(Vector2D)*: The first vector.
  - `vector2` *(Vector2D)*: The second vector.
- **Returns**: A `Vector2D` instance representing the sum of the two vectors.

### `sub(Vector2D vector1, Vector2D vector2)`
Subtracts one `Vector2D` from another.

- **Parameters**:
  - `vector1` *(Vector2D)*: The vector to subtract from.
  - `vector2` *(Vector2D)*: The vector to subtract.
- **Returns**: A `Vector2D` instance representing the result of the subtraction.

### `mul(Vector2D vector, double scalar)`
Multiplies a `Vector2D` by a scalar value.

- **Parameters**:
  - `vector` *(Vector2D)*: The vector to be multiplied.
  - `scalar` *(double)*: The scalar value by which to multiply the vector.
- **Returns**: A `Vector2D` instance representing the scaled vector.

### `div(Vector2D vector, double scalar)`
Divides a `Vector2D` by a scalar value.

- **Parameters**:
  - `vector` *(Vector2D)*: The vector to be divided.
  - `scalar` *(double)*: The scalar value by which to divide the vector.
- **Returns**: A `Vector2D` instance representing the divided vector.

### `dot(Vector2D vector1, Vector2D vector2)`
Calculates the dot product of two `Vector2D` instances.

- **Parameters**:
  - `vector1` *(Vector2D)*: The first vector.
  - `vector2` *(Vector2D)*: The second vector.
- **Returns**: The dot product as a `double`.

### `fromTo(Vector2D from, Vector2D to)`
Creates a `Vector2D` that points from one vector to another.

- **Parameters**:
  - `from` *(Vector2D)*: The starting vector.
  - `to` *(Vector2D)*: The target vector.
- **Returns**: A `Vector2D` instance representing the direction and magnitude from the `from` vector to the `to` vector.
## Angle Methods

### `rotations(double rotations)`
Creates an `Angle` instance from a number of full rotations.

- **Parameters**: 
  - `rotations` *(double)*: The number of full rotations.
- **Returns**: An `Angle` instance representing the given number of rotations.

### `degrees(double degrees)`
Creates an `Angle` instance from a value in degrees.

- **Parameters**: 
  - `degrees` *(double)*: The angle in degrees.
- **Returns**: An `Angle` instance representing the given value in degrees.

### `radians(double radians)`
Creates an `Angle` instance from a value in radians.

- **Parameters**: 
  - `radians` *(double)*: The angle in radians.
- **Returns**: An `Angle` instance representing the given value in radians.

---

## Vector2D Methods

### `vector2D(double x, double y)`
Creates a `Vector2D` instance with the given `x` and `y` coordinates.

- **Parameters**: 
  - `x` *(double)*: The x-coordinate of the vector.
  - `y` *(double)*: The y-coordinate of the vector.
- **Returns**: A `Vector2D` instance representing the given coordinates.

### `sqrLength(Vector2D vector)`
Calculates the squared length of a `Vector2D` instance. Useful for optimization when comparing lengths without needing to compute the square root.

- **Parameters**: 
  - `vector` *(Vector2D)*: The vector whose squared length is to be calculated.
- **Returns**: The squared length of the vector as a `double`.

### `length(Vector2D vector)`
Calculates the length (magnitude) of a `Vector2D` instance.

- **Parameters**: 
  - `vector` *(Vector2D)*: The vector whose length is to be calculated.
- **Returns**: The length of the vector as a `double`.

### `dist(Vector2D vector1, Vector2D vector2)`
Calculates the distance between two `Vector2D` instances.

- **Parameters**: 
  - `vector1` *(Vector2D)*: The first vector.
  - `vector2` *(Vector2D)*: The second vector.
- **Returns**: The distance between the two vectors as a `double`.

### `normalize(Vector2D vector)`
Normalizes a `Vector2D` instance, resulting in a unit vector in the same direction.

- **Parameters**: 
  - `vector` *(Vector2D)*: The vector to normalize.
- **Returns**: A `Vector2D` instance representing the normalized vector.

### `add(Vector2D vector1, Vector2D vector2)`
Adds two `Vector2D` instances together.

- **Parameters**: 
  - `vector1` *(Vector2D)*: The first vector.
  - `vector2` *(Vector2D)*: The second vector.
- **Returns**: A `Vector2D` instance representing the sum of the two vectors.

### `sub(Vector2D vector1, Vector2D vector2)`
Subtracts one `Vector2D` from another.

- **Parameters**: 
  - `vector1` *(Vector2D)*: The vector to subtract from.
  - `vector2` *(Vector2D)*: The vector to subtract.
- **Returns**: A `Vector2D` instance representing the result of the subtraction.

### `mul(Vector2D vector, double scalar)`
Multiplies a `Vector2D` by a scalar value.

- **Parameters**: 
  - `vector` *(Vector2D)*: The vector to be multiplied.
  - `scalar` *(double)*: The scalar value by which to multiply the vector.
- **Returns**: A `Vector2D` instance representing the scaled vector.

### `div(Vector2D vector, double scalar)`
Divides a `Vector2D` by a scalar value.

- **Parameters**: 
  - `vector` *(Vector2D)*: The vector to be divided.
  - `scalar` *(double)*: The scalar value by which to divide the vector.
- **Returns**: A `Vector2D` instance representing the divided vector.

### `dot(Vector2D vector1, Vector2D vector2)`
Calculates the dot product of two `Vector2D` instances.

- **Parameters**: 
  - `vector1` *(Vector2D)*: The first vector.
  - `vector2` *(Vector2D)*: The second vector.
- **Returns**: The dot product as a `double`.

### `fromTo(Vector2D from, Vector2D to)`
Creates a `Vector2D` that points from one vector to another.

- **Parameters**: 
  - `from` *(Vector2D)*: The starting vector.
  - `to` *(Vector2D)*: The target vector.
- **Returns**: A `Vector2D` instance representing the direction and magnitude from the `from` vector to the `to` vector.

---

## Polar2D Methods

### `polar2D(double r, Angle phi)`
Creates a `Polar2D` instance with the given radius and angle.

- **Parameters**: 
  - `r` *(double)*: The radius of the polar coordinate.
  - `phi` *(Angle)*: The angle in polar coordinates.
- **Returns**: A `Polar2D` instance representing the given polar coordinates.

### `asPolar2D(Vector2D vector)`
Converts a `Vector2D` instance into a `Polar2D` instance.

- **Parameters**: 
  - `vector` *(Vector2D)*: The vector to convert.
- **Returns**: A `Polar2D` instance representing the equivalent polar coordinates.

### `asVector2D(Polar2D polar)`
Converts a `Polar2D` instance into a `Vector2D` instance.

- **Parameters**: 
  - `polar` *(Polar2D)*: The polar coordinates to convert.
- **Returns**: A `Vector2D` instance representing the equivalent Cartesian coordinates.

---

## Polygon2D Methods

### `polygon2D(Iterable<Vector2D> vertices)`
Creates a `Geometry2D` polygon from a collection of vertices.

- **Parameters**: 
  - `vertices` *(Iterable<Vector2D>)*: An iterable collection of `Vector2D` instances representing the vertices of the polygon.
- **Returns**: A `Geometry2D` instance representing the polygon.

### `polygon2D(Iterable<Vector2D> vertices, Iterable<? extends Iterable<Integer>> paths)`
Creates a `Geometry2D` polygon with specified paths connecting the vertices.

- **Parameters**: 
  - `vertices` *(Iterable<Vector2D>)*: An iterable collection of `Vector2D` instances representing the vertices of the polygon.
  - `paths` *(Iterable<? extends Iterable<Integer>>)*: An iterable collection of paths, where each path is a sequence of vertex indices defining the shape.
- **Returns**: A `Geometry2D` instance representing the polygon with specified paths.

---

## 2D Transformations Methods

### `identity2D()`
Creates an identity transformation for 2D space.

- **Returns**: A `Transform2D` instance representing the identity transformation.

### `compose2D(Transform2D... transforms)`
Composes multiple 2D transformations into a single transformation.

- **Parameters**: 
  - `transforms` *(Transform2D...)*: A varargs list of transformations to be composed.
- **Returns**: A `Transform2D` instance representing the composed transformation.

### `translate2D(double x, double y)`
Creates a 2D translation transformation.

- **Parameters**: 
  - `x` *(double)*: The translation distance along the x-axis.
  - `y` *(double)*: The translation distance along the y-axis.
- **Returns**: A `Transform2D` instance representing the translation.

### `translate2DX(double x)`
Creates a 2D translation transformation along the x-axis.

- **Parameters**: 
  - `x` *(double)*: The translation distance along the x-axis.
- **Returns**: A `Transform2D` instance representing the x-axis translation.

### `translate2DY(double y)`
Creates a 2D translation transformation along the y-axis.

- **Parameters**: 
  - `y` *(double)*: The translation distance along the y-axis.
- **Returns**: A `Transform2D` instance representing the y-axis translation.

### `rotate2D(Angle angle)`
Creates a 2D rotation transformation.

- **Parameters**: 
  - `angle` *(Angle)*: The angle to rotate by.
- **Returns**: A `Transform2D` instance representing the rotation.

### `rotate2DAround(Vector2D point, Angle angle)`
Creates a 2D rotation transformation around a specific point.

- **Parameters**: 
  - `point` *(Vector2D)*: The point around which to rotate.
  - `angle` *(Angle)*: The angle to rotate by.
- **Returns**: A `Transform2D` instance representing the rotation around the specified point.

### `scale2D(double x, double y)`
Creates a 2D scaling transformation.

- **Parameters**: 
  - `x` *(double)*: The scaling factor along the x-axis.
  - `y` *(double)*: The scaling factor along the y-axis.
- **Returns**: A `Transform2D` instance representing the scaling.

### `mirror2D(double normX, double normY)`
Creates a 2D mirror transformation along a specified normal vector.

- **Parameters**: 
  - `normX` *(double)*: The x-component of the normal vector.
  - `normY` *(double)*: The y-component of the normal vector.
- **Returns**: A `Transform2D` instance representing the mirror transformation.
## Resize 2D Geometry Methods

### `resize2D(double x, double y, boolean autoX, boolean autoY, Geometry2D geometry)`

Resizes a `Geometry2D` instance to the specified dimensions.

- **Parameters**:
  - `x` *(double)*: The target width.
  - `y` *(double)*: The target height.
  - `autoX` *(boolean)*: Whether to automatically adjust the width to maintain the aspect ratio.
  - `autoY` *(boolean)*: Whether to automatically adjust the height to maintain the aspect ratio.
  - `geometry` *(Geometry2D)*: The geometry to resize.
- **Returns**: A `Geometry2D` instance representing the resized geometry.

---

## 2D Operations Methods

### `union2D(Geometry2D... geometries)`

Performs a union operation on multiple `Geometry2D` instances.

- **Parameters**:
  - `geometries` *(Geometry2D...)*: A varargs list of geometries to unite.
- **Returns**: A `Geometry2D` instance representing the union of the provided geometries.

### `union2D(Iterable<Geometry2D> geometries)`

Performs a union operation on an iterable collection of `Geometry2D` instances.

- **Parameters**:
  - `geometries` *(Iterable**)*: An iterable collection of geometries to unite.
- **Returns**: A `Geometry2D` instance representing the union of the provided geometries.

### `intersection2D(Geometry2D... geometries)`

Finds the intersection of multiple `Geometry2D` instances.

- **Parameters**:
  - `geometries` *(Geometry2D...)*: A varargs list of geometries to intersect.
- **Returns**: A `Geometry2D` instance representing the intersection of the provided geometries.

### `intersection2D(Iterable<Geometry2D> geometries)`

Finds the intersection of an iterable collection of `Geometry2D` instances.

- **Parameters**:
  - `geometries` *(Iterable**)*: An iterable collection of geometries to intersect.
- **Returns**: A `Geometry2D` instance representing the intersection of the provided geometries.

### `difference2D(Geometry2D filled, Geometry2D... cutouts)`

Performs a difference operation on a `Geometry2D` instance, subtracting other geometries from it.

- **Parameters**:
  - `filled` *(Geometry2D)*: The base geometry.
  - `cutouts` *(Geometry2D...)*: A varargs list of geometries to subtract from the base.
- **Returns**: A `Geometry2D` instance representing the difference.

### `difference2D(Geometry2D filled, Iterable<Geometry2D> cutouts)`

Performs a difference operation on a `Geometry2D` instance, subtracting an iterable collection of geometries from it.

- **Parameters**:
  - `filled` *(Geometry2D)*: The base geometry.
  - `cutouts` *(Iterable**)*: An iterable collection of geometries to subtract from the base.
- **Returns**: A `Geometry2D` instance representing the difference.

### `hull2D(Geometry2D... geometries)`

Creates the convex hull of multiple `Geometry2D` instances.

- **Parameters**:
  - `geometries` *(Geometry2D...)*: A varargs list of geometries to create the hull from.
- **Returns**: A `Geometry2D` instance representing the convex hull.

### `hull2D(Iterable<Geometry2D> geometries)`

Creates the convex hull of an iterable collection of `Geometry2D` instances.

- **Parameters**:
  - `geometries` *(Iterable**)*: An iterable collection of geometries to create the hull from.
- **Returns**: A `Geometry2D` instance representing the convex hull.

### `minkowski2D(Geometry2D... geometries)`

Performs a Minkowski sum operation on multiple `Geometry2D` instances.

- **Parameters**:
  - `geometries` *(Geometry2D...)*: A varargs list of geometries to apply the Minkowski sum.
- **Returns**: A `Geometry2D` instance representing the Minkowski sum of the provided geometries.

### `minkowski2D(Iterable<Geometry2D> geometries)`

Performs a Minkowski sum operation on an iterable collection of `Geometry2D` instances.

- **Parameters**:
  - `geometries` *(Iterable**)*: An iterable collection of geometries to apply the Minkowski sum.
- **Returns**: A `Geometry2D` instance representing the Minkowski sum of the provided geometries.

### `offset2D(double delta, boolean chamfer, Geometry2D... geometries)`

Offsets multiple `Geometry2D` instances by a specified distance.

- **Parameters**:
  - `delta` *(double)*: The offset distance.
  - `chamfer` *(boolean)*: Whether to apply a chamfered edge.
  - `geometries` *(Geometry2D...)*: A varargs list of geometries to offset.
- **Returns**: A `Geometry2D` instance representing the offset geometries.

### `offset2D(double delta, boolean chamfer, Iterable<Geometry2D> geometries)`

Offsets an iterable collection of `Geometry2D` instances by a specified distance.

- **Parameters**:
  - `delta` *(double)*: The offset distance.
  - `chamfer` *(boolean)*: Whether to apply a chamfered edge.
  - `geometries` *(Iterable**)*: An iterable collection of geometries to offset.
- **Returns**: A `Geometry2D` instance representing the offset geometries.

### `offsetRound2D(double radius, int angularResolution, Geometry2D... geometries)`

Offsets multiple `Geometry2D` instances with rounded edges.

- **Parameters**:
  - `radius` *(double)*: The radius for rounding.
  - `angularResolution` *(int)*: The angular resolution for the rounding.
  - `geometries` *(Geometry2D...)*: A varargs list of geometries to offset.
- **Returns**: A `Geometry2D` instance representing the rounded offset geometries.

### `offsetRound2D(double radius, int angularResolution, Iterable<Geometry2D> geometries)`

Offsets an iterable collection of `Geometry2D` instances with rounded edges.

- **Parameters**:
  - `radius` *(double)*: The radius for rounding.
  - `angularResolution` *(int)*: The angular resolution for the rounding.
  - `geometries` *(Iterable**)*: An iterable collection of geometries to offset.
- **Returns**: A `Geometry2D` instance representing the rounded offset geometries.

### `color2D(double r, double g, double b, double a, Geometry2D... geometries)`

Applies a color to multiple `Geometry2D` instances.

- **Parameters**:
  - `r` *(double)*: The red component of the color (0.0 to 1.0).
  - `g` *(double)*: The green component of the color (0.0 to 1.0).
  - `b` *(double)*: The blue component of the color (0.0 to 1.0).
  - `a` *(double)*: The alpha (transparency) component of the color (0.0 to 1.0).
  - `geometries` *(Geometry2D...)*: A varargs list of geometries to color.
- **Returns**: A `Geometry2D` instance representing the colored geometries.

### `color2D(double r, double g, double b, double a, Iterable<Geometry2D> geometries)`

Applies a color to an iterable collection of `Geometry2D` instances.

- **Parameters**:
  - `r` *(double)*: The red component of the color (0.0 to 1.0).
  - `g` *(double)*: The green component of the color (0.0 to 1.0).
  - `b` *(double)*: The blue component of the color (0.0 to 1.0).
  - `a` *(double)*: The alpha (transparency) component of the color (0.0 to 1.0).
  - `geometries` *(Iterable**)*: An iterable collection of geometries to color.
- **Returns**: A `Geometry2D` instance representing the colored geometries.
## Text2D Methods

### `char2D(char ch, double width, int angularResolution)`

Creates a `Geometry2D` representation of a character with the specified width and angular resolution.

- **Parameters**:
  - `ch` *(char)*: The character to create.
  - `width` *(double)*: The width of the character.
  - `angularResolution` *(int)*: The angular resolution for curved parts of the character.
- **Returns**: A `Geometry2D` instance representing the character.

### `char2D(char ch, double width, double height, int angularResolution)`

Creates a `Geometry2D` representation of a character with the specified width, height, and angular resolution.

- **Parameters**:
  - `ch` *(char)*: The character to create.
  - `width` *(double)*: The width of the character.
  - `height` *(double)*: The height of the character.
  - `angularResolution` *(int)*: The angular resolution for curved parts of the character.
- **Returns**: A `Geometry2D` instance representing the character.

### `charHeight2D(double width)`

Calculates the height of a character based on the specified width.

- **Parameters**:
  - `width` *(double)*: The width of the character.
- **Returns**: The height of the character as a `double`.

### `charBaseline2D(double height)`

Calculates the baseline position of a character based on the specified height.

- **Parameters**:
  - `height` *(double)*: The height of the character.
- **Returns**: The baseline position as a `double`.

### `text2D(String text, double letterWidth, int angularResolution)`

Creates a `Geometry2D` representation of a text string with the specified letter width and angular resolution.

- **Parameters**:
  - `text` *(String)*: The text to create.
  - `letterWidth` *(double)*: The width of each letter.
  - `angularResolution` *(int)*: The angular resolution for curved parts of the letters.
- **Returns**: A `Geometry2D` instance representing the text.

### `text2D(String text, double letterWidth, double letterHeight, int angularResolution)`

Creates a `Geometry2D` representation of a text string with the specified letter width, letter height, and angular resolution.

- **Parameters**:
  - `text` *(String)*: The text to create.
  - `letterWidth` *(double)*: The width of each letter.
  - `letterHeight` *(double)*: The height of each letter.
  - `angularResolution` *(int)*: The angular resolution for curved parts of the letters.
- **Returns**: A `Geometry2D` instance representing the text.
# Geometry3D Operations Documentation

## Matrix Multiplication

### `Geometry3D multMatrix3D(double m00, double m01, double m02, double m03, double m10, double m11, double m12, double m13, double m20, double m21, double m22, double m23, Geometry3D geometry)`
This method performs matrix multiplication on a `Geometry3D` object using the provided 4x3 transformation matrix values.

- **Parameters:**
  - `m00, m01, m02, m03`: Elements of the first row of the transformation matrix.
  - `m10, m11, m12, m13`: Elements of the second row of the transformation matrix.
  - `m20, m21, m22, m23`: Elements of the third row of the transformation matrix.
  - `geometry`: The `Geometry3D` object to be transformed.

- **Returns:**
  - A new `Geometry3D` object transformed by the provided matrix.

## Polyhedron3D

### `Geometry3D polyhedron3D(Iterable<Vector3D> vertices, Iterable<? extends Iterable<Integer>> faces)`
This method creates a 3D polyhedron by defining its vertices and faces.

- **Parameters:**
  - `vertices`: An iterable list of `Vector3D` objects representing the vertices of the polyhedron.
  - `faces`: An iterable of iterable lists of integers, where each list represents the indices of vertices forming a face.

- **Returns:**
  - A new `Geometry3D` object representing the defined polyhedron.

## 3D to 2D Operations

### `Geometry2D project(boolean cutAtZeroZ, Geometry3D geometry)`
This method projects a `Geometry3D` object into a 2D representation.

- **Parameters:**
  - `cutAtZeroZ`: A boolean value indicating whether parts of the geometry with a negative Z value should be cut.
  - `geometry`: The `Geometry3D` object to be projected.

- **Returns:**
  - A new `Geometry2D` object representing the 2D projection of the input geometry.

## 3D Transformations

### `Transform3D identity3D()`
This method creates an identity transformation, which does not alter the geometry.

- **Returns:**
  - A new `Transform3D` representing the identity transformation.

### `Transform3D compose3D(Transform3D... transforms)`
This method composes multiple 3D transformations into a single transformation.

- **Parameters:**
  - `transforms`: A variable number of `Transform3D` objects to be composed.

- **Returns:**
  - A new `Transform3D` representing the combined transformation.

### `Transform3D translate3D(double x, double y, double z)`
This method creates a translation transformation in 3D space.

- **Parameters:**
  - `x, y, z`: The translation distances along the X, Y, and Z axes.

- **Returns:**
  - A new `Transform3D` representing the translation.

### `Transform3D translate3DX(double x)`
This method creates a translation transformation along the X axis.

- **Parameters:**
  - `x`: The translation distance along the X axis.

- **Returns:**
  - A new `Transform3D` representing the X-axis translation.

### `Transform3D translate3DY(double y)`
This method creates a translation transformation along the Y axis.

- **Parameters:**
  - `y`: The translation distance along the Y axis.

- **Returns:**
  - A new `Transform3D` representing the Y-axis translation.

### `Transform3D translate3DZ(double z)`
This method creates a translation transformation along the Z axis.

- **Parameters:**
  - `z`: The translation distance along the Z axis.

- **Returns:**
  - A new `Transform3D` representing the Z-axis translation.

### `Transform3D translate3DFromTo(Vector3D from, Vector3D to)`
This method creates a translation transformation from one point to another in 3D space.

- **Parameters:**
  - `from`: The starting point as a `Vector3D`.
  - `to`: The destination point as a `Vector3D`.

- **Returns:**
  - A new `Transform3D` representing the translation.

### `Transform3D rotate3D(Angle angleX, Angle angleY, Angle angleZ)`
This method creates a rotation transformation around the X, Y, and Z axes.

- **Parameters:**
  - `angleX, angleY, angleZ`: The angles of rotation for the X, Y, and Z axes.

- **Returns:**
  - A new `Transform3D` representing the rotation.

### `Transform3D rotate3DAround(Vector3D point, Angle angleX, Angle angleY, Angle angleZ)`
This method creates a rotation transformation around a specific point in 3D space.

- **Parameters:**
  - `point`: The point around which to rotate as a `Vector3D`.
  - `angleX, angleY, angleZ`: The angles of rotation for the X, Y, and Z axes.

- **Returns:**
  - A new `Transform3D` representing the rotation.

### `Transform3D rotate3DX(Angle angle)`
This method creates a rotation transformation around the X axis.

- **Parameters:**
  - `angle`: The angle of rotation for the X axis.

- **Returns:**
  - A new `Transform3D` representing the X-axis rotation.

### `Transform3D rotate3DXAround(Vector3D point, Angle angle)`
This method creates a rotation transformation around a specific point along the X axis.

- **Parameters:**
  - `point`: The point around which to rotate as a `Vector3D`.
  - `angle`: The angle of rotation for the X axis.

- **Returns:**
  - A new `Transform3D` representing the X-axis rotation around the point.

### `Transform3D rotate3DY(Angle angle)`
This method creates a rotation transformation around the Y axis.

- **Parameters:**
  - `angle`: The angle of rotation for the Y axis.

- **Returns:**
  - A new `Transform3D` representing the Y-axis rotation.

### `Transform3D rotate3DYAround(Vector3D point, Angle angle)`
This method creates a rotation transformation around a specific point along the Y axis.

- **Parameters:**
  - `point`: The point around which to rotate as a `Vector3D`.
  - `angle`: The angle of rotation for the Y axis.

- **Returns:**
  - A new `Transform3D` representing the Y-axis rotation around the point.

### `Transform3D rotate3DZ(Angle angle)`
This method creates a rotation transformation around the Z axis.

- **Parameters:**
  - `angle`: The angle of rotation for the Z axis.

- **Returns:**
  - A new `Transform3D` representing the Z-axis rotation.

### `Transform3D rotate3DZAround(Vector3D point, Angle angle)`
This method creates a rotation transformation around a specific point along the Z axis.

- **Parameters:**
  - `point`: The point around which to rotate as a `Vector3D`.
  - `angle`: The angle of rotation for the Z axis.

- **Returns:**
  - A new `Transform3D` representing the Z-axis rotation around the point.

### `Transform3D scale3D(double x, double y, double z)`
This method creates a scaling transformation in 3D space.

- **Parameters:**
  - `x, y, z`: The scaling factors for the X, Y, and Z axes.

- **Returns:**
  - A new `Transform3D` representing the scaling transformation.

### `Transform3D mirror3D(double normX, double normY, double normZ)`
This method creates a mirror transformation along a plane defined by a normal vector.

- **Parameters:**
  - `normX, normY, normZ`: The components of the normal vector defining the mirror plane.

- **Returns:**
  - A new `Transform3D` representing the mirror transformation.
# Geometry Methods Documentation

## 3D Operations

### Union Operations
- **`union3D(Geometry3D... geometries)`**  
  Computes the union of multiple 3D geometries.

- **`union3D(Iterable<Geometry3D> geometries)`**  
  Computes the union of multiple 3D geometries from an iterable.

### Intersection Operations
- **`intersection3D(Geometry3D... geometries)`**  
  Computes the intersection of multiple 3D geometries.

- **`intersection3D(Iterable<Geometry3D> geometries)`**  
  Computes the intersection of multiple 3D geometries from an iterable.

### Difference Operations
- **`difference3D(Geometry3D solid, Geometry3D... cutouts)`**  
  Computes the difference between a solid geometry and several cutout geometries.

- **`difference3D(Geometry3D solid, Iterable<Geometry3D> cutouts)`**  
  Computes the difference between a solid geometry and cutout geometries from an iterable.

### Convex Hull Operations
- **`hull3D(Geometry3D... geometries)`**  
  Computes the convex hull of multiple 3D geometries.

- **`hull3D(Iterable<Geometry3D> geometries)`**  
  Computes the convex hull of multiple 3D geometries from an iterable.

### Minkowski Sum Operations
- **`minkowski3D(Geometry3D... geometries)`**  
  Computes the Minkowski sum of multiple 3D geometries.

- **`minkowski3D(Iterable<Geometry3D> geometries)`**  
  Computes the Minkowski sum of multiple 3D geometries from an iterable.

## 2D to 3D Operations

### Linear Extrusion
- **`linearExtrude(double height, Angle twist, double scale, int slices, boolean centerZ, Geometry2D geometry)`**  
  Performs a linear extrusion of a 2D geometry along the z-axis, with optional twisting, scaling, and slicing parameters.
  - `height`: The height of the extrusion.
  - `twist`: The angle of twist.
  - `scale`: The scaling factor.
  - `slices`: The number of slices.
  - `centerZ`: Whether to center along the z-axis.
  - `geometry`: The 2D geometry to be extruded.

- **`linearExtrude(double height, boolean centerZ, Geometry2D geometry)`**  
  Performs a simple linear extrusion of a 2D geometry along the z-axis.
  - `height`: The height of the extrusion.
  - `centerZ`: Whether to center along the z-axis.
  - `geometry`: The 2D geometry to be extruded.

### Rotate Extrusion
- **`rotateExtrude(Angle angle, int angularResolution, Geometry2D geometry)`**  
  Creates a 3D geometry by rotating a 2D geometry around the z-axis.
  - `angle`: The angle of rotation.
  - `angularResolution`: The number of steps in the rotation.
  - `geometry`: The 2D geometry to be rotated.

## Viewing Geometry

- **`view(Geometry2D geometry)`**  
  Displays a 2D geometry in the viewer.

- **`view(Geometry3D geometry)`**  
  Displays a 3D geometry in the viewer.

- **`view(Geometry2D geometry, int windowID)`**  
  Displays a 2D geometry in a specific viewer window.

- **`view(Geometry3D geometry, int windowID)`**  
  Displays a 3D geometry in a specific viewer window.

## Caching Geometry

- **`cache(Geometry2D geometry)`**  
  Caches a 2D geometry for later use.

- **`cache(Geometry3D geometry)`**  
  Caches a 3D geometry for later use.

## Saving and Loading STL Files

- **`loadSTL(String fileName) throws IOException`**  
  Loads a 3D geometry from an STL file.
  - `fileName`: The name of the STL file to load.

- **`saveSTL(String fileName, Geometry3D geometry) throws IOException`**  
  Saves a 3D geometry to an STL file.
  - `fileName`: The name of the STL file to save.
  - `geometry`: The 3D geometry to save.


## 2D Geometry Methods

### circle2D
- **`circle2D(double diameter, int angularResolution)`**  
  Creates a 2D circle with a given diameter and angular resolution.
  - `diameter`: The diameter of the circle.
  - `angularResolution`: The number of segments used to approximate the circle.

### pie2D
- **`pie2D(double diameter, Angle beginAngle, Angle endAngle, int angularResolution)`**  
  Creates a 2D pie shape (a wedge of a circle) with specified begin and end angles.
  - `diameter`: The diameter of the pie.
  - `beginAngle`: The starting angle of the pie section.
  - `endAngle`: The ending angle of the pie section.
  - `angularResolution`: The number of segments used to approximate the pie.

### cutoutPie2D
- **`cutoutPie2D(double diameter, Angle beginAngle, Angle endAngle)`**  
  Creates a 2D pie shape without specifying angular resolution.
  - `diameter`: The diameter of the pie.
  - `beginAngle`: The starting angle of the pie section.
  - `endAngle`: The ending angle of the pie section.

### circleSegment2D
- **`circleSegment2D(double diameter, Angle beginAngle, Angle endAngle, int angularResolution)`**  
  Creates a segment of a circle between the specified begin and end angles.
  - `diameter`: The diameter of the circle.
  - `beginAngle`: The starting angle of the segment.
  - `endAngle`: The ending angle of the segment.
  - `angularResolution`: The number of segments used to approximate the segment.

### ring2D
- **`ring2D(double innerDiameter, double outerDiameter, int angularResolution)`**  
  Creates a 2D ring with specified inner and outer diameters.
  - `innerDiameter`: The inner diameter of the ring.
  - `outerDiameter`: The outer diameter of the ring.
  - `angularResolution`: The number of segments used to approximate the ring.

### ringSegment2D
- **`ringSegment2D(double innerDiameter, double outerDiameter, Angle beginAngle, Angle endAngle, int angularResolution)`**  
  Creates a 2D ring segment between the specified begin and end angles.
  - `innerDiameter`: The inner diameter of the ring segment.
  - `outerDiameter`: The outer diameter of the ring segment.
  - `beginAngle`: The starting angle of the segment.
  - `endAngle`: The ending angle of the segment.
  - `angularResolution`: The number of segments used to approximate the ring segment.

### rectangle2D
- **`rectangle2D(double xSize, double ySize)`**  
  Creates a rectangle with specified width and height.
  - `xSize`: The width of the rectangle.
  - `ySize`: The height of the rectangle.

### rectangleCorners2D
- **`rectangleCorners2D(double c1x, double c1y, double c2x, double c2y)`**  
  Creates a rectangle using two opposite corner coordinates.
  - `c1x`: X-coordinate of the first corner.
  - `c1y`: Y-coordinate of the first corner.
  - `c2x`: X-coordinate of the opposite corner.
  - `c2y`: Y-coordinate of the opposite corner.

### rectangleCenter2D
- **`rectangleCenter2D(double cx, double cy, double xSize, double ySize)`**  
  Creates a rectangle centered at the specified coordinates.
  - `cx`: X-coordinate of the center.
  - `cy`: Y-coordinate of the center.
  - `xSize`: The width of the rectangle.
  - `ySize`: The height of the rectangle.

## 3D Shapes Methods

### `Geometry3D sphere3D(double diameter, int angularResolution, boolean centerZ)`
Creates a sphere with the specified `diameter` and `angularResolution`.

- **Parameters**:
  - `diameter` (double): Diameter of the sphere.
  - `angularResolution` (int): Number of segments to divide the sphere.
  - `centerZ` (boolean): Whether to center the sphere along the Z-axis.
- **Returns**: A `Geometry3D` object representing the sphere.

### `Geometry3D box3D(double xSize, double ySize, double zSize, boolean centerZ)`
Creates a box with specified dimensions (`xSize`, `ySize`, `zSize`).

- **Parameters**:
  - `xSize` (double): Length of the box along the X-axis.
  - `ySize` (double): Length of the box along the Y-axis.
  - `zSize` (double): Length of the box along the Z-axis.
  - `centerZ` (boolean): Whether to center the box along the Z-axis.
- **Returns**: A `Geometry3D` object representing the box.

### `Geometry3D boxCenter3D(double cx, double cy, double cz, double xSize, double ySize, double zSize)`
Creates a box centered at the specified coordinates (`cx`, `cy`, `cz`).

- **Parameters**:
  - `cx` (double): X-coordinate of the center.
  - `cy` (double): Y-coordinate of the center.
  - `cz` (double): Z-coordinate of the center.
  - `xSize` (double): Length along the X-axis.
  - `ySize` (double): Length along the Y-axis.
  - `zSize` (double): Length along the Z-axis.
- **Returns**: A `Geometry3D` object representing the box.

### `Geometry3D boxCorners3D(double c1x, double c1y, double c1z, double c2x, double c2y, double c2z)`
Creates a box defined by two corners (`c1x`, `c1y`, `c1z`) and (`c2x`, `c2y`, `c2z`).

- **Parameters**:
  - `c1x`, `c1y`, `c1z` (double): Coordinates of the first corner.
  - `c2x`, `c2y`, `c2z` (double): Coordinates of the opposite corner.
- **Returns**: A `Geometry3D` object representing the box.

### `Geometry3D cylinder3D(double diameter, double height, int angularResolution, boolean centerZ)`
Creates a cylinder with specified `diameter` and `height`.

- **Parameters**:
  - `diameter` (double): Diameter of the cylinder.
  - `height` (double): Height of the cylinder.
  - `angularResolution` (int): Number of segments to divide the cylinder.
  - `centerZ` (boolean): Whether to center the cylinder along the Z-axis.
- **Returns**: A `Geometry3D` object representing the cylinder.

### `Geometry3D cylinderSegment3D(double diameter, double height, Angle beginAngle, Angle endAngle, int angularResolution, boolean centerZ)`
Creates a segment of a cylinder with specified dimensions and angle range.

- **Parameters**:
  - `diameter` (double): Diameter of the cylinder segment.
  - `height` (double): Height of the segment.
  - `beginAngle` (Angle): Starting angle of the segment.
  - `endAngle` (Angle): Ending angle of the segment.
  - `angularResolution` (int): Number of segments to divide the cylinder.
  - `centerZ` (boolean): Whether to center the segment along the Z-axis.
- **Returns**: A `Geometry3D` object representing the cylinder segment.

### `Geometry3D hollowCylinder3D(double outerDiameter, double innerDiameter, double height, int angularResolution, boolean centerZ)`
Creates a hollow cylinder with specified inner and outer diameters.

- **Parameters**:
  - `outerDiameter` (double): Outer diameter of the cylinder.
  - `innerDiameter` (double): Inner diameter of the cylinder.
  - `height` (double): Height of the cylinder.
  - `angularResolution` (int): Number of segments to divide the cylinder.
  - `centerZ` (boolean): Whether to center the cylinder along the Z-axis.
- **Returns**: A `Geometry3D` object representing the hollow cylinder.

### `Geometry3D hollowCylinderSegment3D(double outerDiameter, double innerDiameter, double height, Angle beginAngle, Angle endAngle, int angularResolution, boolean centerZ)`
Creates a segment of a hollow cylinder.

- **Parameters**:
  - `outerDiameter`, `innerDiameter` (double): Outer and inner diameters of the segment.
  - `height` (double): Height of the segment.
  - `beginAngle`, `endAngle` (Angle): Start and end angles for the segment.
  - `angularResolution` (int): Number of segments to divide the hollow cylinder.
  - `centerZ` (boolean): Whether to center along the Z-axis.
- **Returns**: A `Geometry3D` object representing the hollow cylinder segment.

### `Geometry3D cone3D(double bottomDiameter, double topDiameter, double height, int angularResolution, boolean centerZ)`
Creates a cone or frustum with specified `bottomDiameter`, `topDiameter`, and `height`.

- **Parameters**:
  - `bottomDiameter`, `topDiameter` (double): Diameters at the base and top.
  - `height` (double): Height of the cone.
  - `angularResolution` (int): Number of segments to divide the cone.
  - `centerZ` (boolean): Whether to center along the Z-axis.
- **Returns**: A `Geometry3D` object representing the cone.

### `Geometry3D coneSegment3D(double bottomDiameter, double topDiameter, double height, Angle beginAngle, Angle endAngle, int angularResolution, boolean centerZ)`
Creates a segment of a cone with the specified dimensions and angles.

- **Parameters**:
  - `bottomDiameter`, `topDiameter` (double): Diameters at the base and top.
  - `height` (double): Height of the cone segment.
  - `beginAngle`, `endAngle` (Angle): Start and end angles for the segment.
  - `angularResolution` (int): Number of segments.
  - `centerZ` (boolean): Whether to center along the Z-axis.
- **Returns**: A `Geometry3D` object representing the cone segment.

### `Geometry3D flatRing3D(double innerDiameter, double outerDiameter, double height, int angularResolution, boolean centerZ)`
Creates a flat ring with specified inner and outer diameters.

- **Parameters**:
  - `innerDiameter`, `outerDiameter` (double): Inner and outer diameters of the ring.
  - `height` (double): Height of the ring.
  - `angularResolution` (int): Number of segments.
  - `centerZ` (boolean): Whether to center along the Z-axis.
- **Returns**: A `Geometry3D` object representing the flat ring.

### `Geometry3D flatRingSegment3D(double innerDiameter, double outerDiameter, double height, Angle beginAngle, Angle endAngle, int angularResolution, boolean centerZ)`
Creates a segment of a flat ring.

- **Parameters**:
  - `innerDiameter`, `outerDiameter` (double): Diameters of the segment.
  - `height` (double): Height of the segment.
  - `beginAngle`, `endAngle` (Angle): Start and end angles for the segment.
  - `angularResolution` (int): Number of segments.
  - `centerZ` (boolean): Whether to center along the Z-axis.
- **Returns**: A `Geometry3D` object representing the flat ring segment.

### `Geometry3D torus3D(double smallCircleDiameter, double largeCircleDiameter, int smallCircleResolution, int largeCircleResolution, boolean centerZ)`
Creates a torus with specified inner and outer diameters.

- **Parameters**:
  - `smallCircleDiameter`, `largeCircleDiameter` (double): Diameters of the small and large circles.
  - `smallCircleResolution`, `largeCircleResolution` (int): Resolution of small and large circles.
  - `centerZ` (boolean): Whether to center along the Z-axis.
- **Returns**: A `Geometry3D` object representing the torus.

### `Geometry3D torusSegment3D(double smallCircleDiameter, double largeCircleDiameter, Angle beginAngle, Angle endAngle, int smallCircleResolution, int largeCircleResolution, boolean centerZ)`
Creates a segment of a torus.

- **Parameters**:
  - `smallCircleDiameter`, `largeCircleDiameter` (double): Diameters of the small and large circles.
  - `beginAngle`, `endAngle` (Angle): Start and end angles for the segment.
  - `smallCircleResolution`, `largeCircleResolution` (int): Resolution of the small and large circles.
  - `centerZ` (boolean): Whether to center along the Z-axis.
- **Returns**: A `Geometry3D` object representing the torus segment.

## 3D Operations

### `Geometry3D slice3DX(double xMin, double xMax, Geometry3D geometry)`
Slices the given 3D geometry along the X-axis within the range `[xMin, xMax]`.

- **Parameters**:
  - `xMin`, `xMax` (double): Minimum and maximum X values for slicing.
  - `geometry` (Geometry3D): The geometry to slice.
- **Returns**: A `Geometry3D` object representing the sliced geometry.

### `Geometry3D slice3DY(double yMin, double yMax, Geometry3D geometry)`
Slices the given 3D geometry along the Y-axis within the range `[yMin, yMax]`.

- **Parameters**:
  - `yMin`, `yMax` (double): Minimum and maximum Y values for slicing.
  - `geometry` (Geometry3D): The geometry to slice.
- **Returns**: A `Geometry3D` object representing the sliced geometry.

### `Geometry3D slice3DZ(double zMin, double zMax, Geometry3D geometry)`
Slices the given 3D geometry along the Z-axis within the range `[zMin, zMax]`.

- **Parameters**:
  - `zMin`, `zMax` (double): Minimum and maximum Z values for slicing.
  - `geometry` (Geometry3D): The geometry to slice.
- **Returns**: A `Geometry3D` object representing the sliced geometry.

