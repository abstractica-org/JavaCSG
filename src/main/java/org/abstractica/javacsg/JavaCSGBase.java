package org.abstractica.javacsg;

import java.io.IOException;

public interface JavaCSGBase
{
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Angle
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Angle rotations(double rotations);

	Angle degrees(double degrees);

	Angle radians(double radians);

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Vector2D
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Vector2D vector2D(double x, double y);

	double sqrLength(Vector2D vector);

	double length(Vector2D vector);

	double dist(Vector2D vector1, Vector2D vector2);

	Vector2D normalize(Vector2D vector);

	Vector2D add(Vector2D vector1, Vector2D vector2);

	Vector2D sub(Vector2D vector1, Vector2D vector2);

	Vector2D mul(Vector2D vector, double scalar);

	Vector2D div(Vector2D vector, double scalar);

	double dot(Vector2D vector1, Vector2D vector2);

	Vector2D fromTo(Vector2D from, Vector2D to);

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Polar2D
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Polar2D polar2D(double r, Angle phi);
	Polar2D asPolar2D(Vector2D vector);
	Vector2D asVector2D(Polar2D polar);

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Polygon2D
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Geometry2D polygon2D(Iterable<Vector2D> vertices);

	Geometry2D polygon2D(Iterable<Vector2D> vertices, Iterable<? extends Iterable<Integer>> paths);

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 2D transformations
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Transform2D identity2D();

	Transform2D compose3D(Transform2D... transforms);

	Transform2D translate2D(double x, double y);

	Transform2D translate2DX(double x);

	Transform2D translate2DY(double y);

	Transform2D rotate2D(Angle angle);

	Transform2D scale2D(double x, double y);

	Transform2D mirror2D(double normX, double normY);

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Resize 2D geometry
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Geometry2D resize2D(double x, double y, boolean autoX, boolean autoY, Geometry2D geometry);

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 2D operations
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Geometry2D union2D(Geometry2D... geometries);

	Geometry2D union2D(Iterable<Geometry2D> geometries);

	Geometry2D intersection2D(Geometry2D... geometries);

	Geometry2D intersection2D(Iterable<Geometry2D> geometries);

	Geometry2D difference2D(Geometry2D filled, Geometry2D... cutouts);

	Geometry2D difference2D(Geometry2D filled, Iterable<Geometry2D> cutouts);

	Geometry2D hull2D(Geometry2D... geometries);

	Geometry2D hull2D(Iterable<Geometry2D> geometries);

	Geometry2D minkowski2D(Geometry2D... geometries);

	Geometry2D minkowski2D(Iterable<Geometry2D> geometries);

	Geometry2D offset2D(double delta, boolean chamfer, Geometry2D... geometries);

	Geometry2D offset2D(double delta, boolean chamfer, Iterable<Geometry2D> geometries);

	Geometry2D offsetRound2D(double radius, int angularResolution, Geometry2D... geometries);

	Geometry2D offsetRound2D(double radius, int angularResolution, Iterable<Geometry2D> geometries);

	Geometry2D color2D(double r, double g, double b, double a, Geometry2D... geometries);

	Geometry2D color2D(double r, double g, double b, double a, Iterable<Geometry2D> geometries);

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Text2D
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Geometry2D char2D(char ch, double width, int angularResolution);

	Geometry2D char2D(char ch, double width, double height, int angularResolution);

	double charHeight2D(double width);

	double charBaseline2D(double height);

	Geometry2D text2D(String text, double letterWidth, int angularResolution);

	Geometry2D text2D(String text, double letterWidth, double letterHeight, int angularResolution);

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Vector3D
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Vector3D vector3D(double x, double y, double z);

	double sqrLength(Vector3D vector);

	double length(Vector3D vector);

	double dist(Vector3D vector1, Vector3D vector2);

	Vector3D normalize(Vector3D vector);

	Vector3D add(Vector3D vector1, Vector3D vector2);

	Vector3D sub(Vector3D vector1, Vector3D vector2);

	Vector3D mul(Vector3D vector, double scalar);

	Vector3D div(Vector3D vector, double scalar);

	double dot(Vector3D vector1, Vector3D vector2);

	Vector3D fromTo(Vector3D from, Vector3D to);

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Matrix multiplication
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Geometry3D multMatrix3D(double m00, double m01, double m02, double m03,
	                        double m10, double m11, double m12, double m13,
	                        double m20, double m21, double m22, double m23,
	                        Geometry3D geometry);

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Polyhedron3D
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Geometry3D polyhedron3D(Iterable<Vector3D> vertices, Iterable<? extends Iterable<Integer>> faces);

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 3D to 2D operations
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Geometry2D project(boolean cutAtZeroZ, Geometry3D geometry);

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 3D transformations
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Transform3D identity3D();

	Transform3D compose3D(Transform3D... transforms);

	Transform3D translate3D(double x, double y, double z);

	Transform3D translate3DX(double x);

	Transform3D translate3DY(double y);

	Transform3D translate3DZ(double z);

	Transform3D rotate3D(Angle angleX, Angle angleY, Angle angleZ);

	Transform3D rotate3DX(Angle angle);

	Transform3D rotate3DY(Angle angle);

	Transform3D rotate3DZ(Angle angle);

	//Transform3D rotate3D(Vector3D axis, Angle angle);
	Transform3D scale3D(double x, double y, double z);

	Transform3D mirror3D(double normX, double normY, double normZ);

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 3D operations
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Geometry3D union3D(Geometry3D... geometries);

	Geometry3D union3D(Iterable<Geometry3D> geometries);

	Geometry3D intersection3D(Geometry3D... geometries);

	Geometry3D intersection3D(Iterable<Geometry3D> geometries);

	Geometry3D difference3D(Geometry3D solid, Geometry3D... cutouts);

	Geometry3D difference3D(Geometry3D solid, Iterable<Geometry3D> cutouts);

	Geometry3D hull3D(Geometry3D... geometries);

	Geometry3D hull3D(Iterable<Geometry3D> geometries);

	Geometry3D minkowski3D(Geometry3D... geometries);

	Geometry3D minkowski3D(Iterable<Geometry3D> geometries);

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 2D to 3D operations
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Geometry3D linearExtrude(double height,
	                         Angle twist,
	                         double scale,
	                         int slices,
							 boolean centerZ,
	                         Geometry2D geometry);

	Geometry3D linearExtrude(double height, boolean centerZ, Geometry2D geometry);

	Geometry3D rotateExtrude(Angle angle, int angularResolution, Geometry2D geometry);

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// View geometry
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	void view(Geometry2D geometry);
	void view(Geometry3D geometry);
	void view(Geometry2D geometry, int windowID);
	void view(Geometry3D geometry, int windowID);

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Cache geometry
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Geometry2D cache(Geometry2D geometry);
	Geometry3D cache(Geometry3D geometry);

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Save and load STL
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Geometry3D loadSTL(String fileName) throws IOException;
	void saveSTL(String fileName, Geometry3D geometry) throws IOException;
}