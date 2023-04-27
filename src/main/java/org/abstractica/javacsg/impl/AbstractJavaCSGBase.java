package org.abstractica.javacsg.impl;

import org.abstractica.javacsg.*;

import java.io.IOException;

public class AbstractJavaCSGBase implements JavaCSGBase
{
	private final JavaCSGBase base;

	public AbstractJavaCSGBase(JavaCSGBase base)
	{
		this.base = base;
	}

	@Override
	public Angle rotations(double rotations)
	{
		return base.rotations(rotations);
	}

	@Override
	public Angle degrees(double degrees)
	{
		return base.degrees(degrees);
	}

	@Override
	public Angle radians(double radians)
	{
		return base.radians(radians);
	}

	@Override
	public Vector2D vector2D(double x, double y)
	{
		return base.vector2D(x, y);
	}

	@Override
	public double sqrLength(Vector2D vector)
	{
		return base.sqrLength(vector);
	}

	@Override
	public double length(Vector2D vector)
	{
		return base.length(vector);
	}

	@Override
	public double dist(Vector2D vector1, Vector2D vector2)
	{
		return base.dist(vector1, vector2);
	}

	@Override
	public Vector2D normalize(Vector2D vector)
	{
		return base.normalize(vector);
	}

	@Override
	public Vector2D add(Vector2D vector1, Vector2D vector2)
	{
		return base.add(vector1, vector2);
	}

	@Override
	public Vector2D sub(Vector2D vector1, Vector2D vector2)
	{
		return base.sub(vector1, vector2);
	}

	@Override
	public Vector2D mul(Vector2D vector, double scalar)
	{
		return base.mul(vector, scalar);
	}

	@Override
	public Vector2D div(Vector2D vector, double scalar)
	{
		return base.div(vector, scalar);
	}

	@Override
	public double dot(Vector2D vector1, Vector2D vector2)
	{
		return base.dot(vector1, vector2);
	}

	@Override
	public Vector2D fromTo(Vector2D from, Vector2D to)
	{
		return base.fromTo(from, to);
	}

	@Override
	public Polar2D polar2D(double r, Angle phi)
	{
		return base.polar2D(r, phi);
	}

	@Override
	public Polar2D asPolar2D(Vector2D vector)
	{
		return base.asPolar2D(vector);
	}

	@Override
	public Vector2D asVector2D(Polar2D polar)
	{
		return base.asVector2D(polar);
	}

	@Override
	public Geometry2D polygon2D(Iterable<Vector2D> vertices)
	{
		return base.polygon2D(vertices);
	}

	@Override
	public Geometry2D polygon2D(Iterable<Vector2D> vertices, Iterable<? extends Iterable<Integer>> paths)
	{
		return base.polygon2D(vertices, paths);
	}

	@Override
	public Transform2D identity2D()
	{
		return base.identity2D();
	}

	@Override
	public Transform2D compose3D(Transform2D... transforms)
	{
		return base.compose3D(transforms);
	}

	@Override
	public Transform2D translate2D(double x, double y)
	{
		return base.translate2D(x, y);
	}

	@Override
	public Transform2D translate2DX(double x)
	{
		return base.translate2DX(x);
	}

	@Override
	public Transform2D translate2DY(double y)
	{
		return base.translate2DY(y);
	}

	@Override
	public Transform2D rotate2D(Angle angle)
	{
		return base.rotate2D(angle);
	}

	@Override
	public Transform2D scale2D(double x, double y)
	{
		return base.scale2D(x, y);
	}

	@Override
	public Transform2D mirror2D(double normX, double normY)
	{
		return base.mirror2D(normX, normY);
	}

	@Override
	public Geometry2D resize2D(double x, double y, boolean autoX, boolean autoY, Geometry2D geometry)
	{
		return base.resize2D(x, y, autoX, autoY, geometry);
	}

	@Override
	public Geometry2D union2D(Geometry2D... geometries)
	{
		return base.union2D(geometries);
	}

	@Override
	public Geometry2D union2D(Iterable<Geometry2D> geometries)
	{
		return base.union2D(geometries);
	}

	@Override
	public Geometry2D intersection2D(Geometry2D... geometries)
	{
		return base.intersection2D(geometries);
	}

	@Override
	public Geometry2D intersection2D(Iterable<Geometry2D> geometries)
	{
		return base.intersection2D(geometries);
	}

	@Override
	public Geometry2D difference2D(Geometry2D filled, Geometry2D... cutouts)
	{
		return base.difference2D(filled, cutouts);
	}

	@Override
	public Geometry2D difference2D(Geometry2D filled, Iterable<Geometry2D> cutouts)
	{
		return base.difference2D(filled, cutouts);
	}

	@Override
	public Geometry2D hull2D(Geometry2D... geometries)
	{
		return base.hull2D(geometries);
	}

	@Override
	public Geometry2D hull2D(Iterable<Geometry2D> geometries)
	{
		return base.hull2D(geometries);
	}

	@Override
	public Geometry2D minkowski2D(Geometry2D... geometries)
	{
		return base.minkowski2D(geometries);
	}

	@Override
	public Geometry2D minkowski2D(Iterable<Geometry2D> geometries)
	{
		return base.minkowski2D(geometries);
	}

	@Override
	public Geometry2D offset2D(double delta, boolean chamfer, Geometry2D... geometries)
	{
		return base.offset2D(delta, chamfer, geometries);
	}

	@Override
	public Geometry2D offset2D(double delta, boolean chamfer, Iterable<Geometry2D> geometries)
	{
		return base.offset2D(delta, chamfer, geometries);
	}

	@Override
	public Geometry2D offsetRound2D(double radius, int angularResolution, Geometry2D... geometries)
	{
		return base.offsetRound2D(radius, angularResolution, geometries);
	}

	@Override
	public Geometry2D offsetRound2D(double radius, int angularResolution, Iterable<Geometry2D> geometries)
	{
		return base.offsetRound2D(radius, angularResolution, geometries);
	}

	@Override
	public Geometry2D color2D(double r, double g, double b, double a, Geometry2D... geometries)
	{
		return base.color2D(r, g, b, a, geometries);
	}

	@Override
	public Geometry2D color2D(double r, double g, double b, double a, Iterable<Geometry2D> geometries)
	{
		return base.color2D(r, g, b, a, geometries);
	}

	@Override
	public Geometry2D char2D(char ch, double height, int angularResolution)
	{
		return base.char2D(ch, height, angularResolution);
	}

	@Override
	public Geometry2D char2D(char ch, double height, double width, int angularResolution)
	{
		return base.char2D(ch, height, width, angularResolution);
	}

	@Override
	public double charHeight2D(double width)
	{
		return base.charHeight2D(width);
	}

	@Override
	public double charBaseline2D(double height)
	{
		return base.charBaseline2D(height);
	}

	@Override
	public Geometry2D text2D(String text, double letterWidth, int angularResolution)
	{
		return base.text2D(text, letterWidth, angularResolution);
	}

	@Override
	public Geometry2D text2D(String text, double letterWidth, double letterHeight, int angularResolution)
	{
		return base.text2D(text, letterWidth, letterHeight, angularResolution);
	}

	@Override
	public Vector3D vector3D(double x, double y, double z)
	{
		return base.vector3D(x, y, z);
	}

	@Override
	public double sqrLength(Vector3D vector)
	{
		return base.sqrLength(vector);
	}

	@Override
	public double length(Vector3D vector)
	{
		return base.length(vector);
	}

	@Override
	public double dist(Vector3D vector1, Vector3D vector2)
	{
		return base.dist(vector1, vector2);
	}

	@Override
	public Vector3D normalize(Vector3D vector)
	{
		return base.normalize(vector);
	}

	@Override
	public Vector3D add(Vector3D vector1, Vector3D vector2)
	{
		return base.add(vector1, vector2);
	}

	@Override
	public Vector3D sub(Vector3D vector1, Vector3D vector2)
	{
		return base.sub(vector1, vector2);
	}

	@Override
	public Vector3D mul(Vector3D vector, double scalar)
	{
		return base.mul(vector, scalar);
	}

	@Override
	public Vector3D div(Vector3D vector, double scalar)
	{
		return base.div(vector, scalar);
	}

	@Override
	public double dot(Vector3D vector1, Vector3D vector2)
	{
		return base.dot(vector1, vector2);
	}

	@Override
	public Vector3D fromTo(Vector3D from, Vector3D to)
	{
		return base.fromTo(from, to);
	}

	@Override
	public Geometry3D multMatrix3D(double m00, double m01, double m02, double m03, double m10, double m11, double m12, double m13, double m20, double m21, double m22, double m23, Geometry3D geometry)
	{
		return base.multMatrix3D(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, geometry);
	}

	@Override
	public Geometry3D polyhedron3D(Iterable<Vector3D> vertices, Iterable<? extends Iterable<Integer>> faces)
	{
		return base.polyhedron3D(vertices, faces);
	}

	@Override
	public Geometry2D project(boolean cutAtZeroZ, Geometry3D geometry)
	{
		return base.project(cutAtZeroZ, geometry);
	}

	@Override
	public Transform3D identity3D()
	{
		return base.identity3D();
	}

	@Override
	public Transform3D compose3D(Transform3D... transforms)
	{
		return base.compose3D(transforms);
	}

	@Override
	public Transform3D translate3D(double x, double y, double z)
	{
		return base.translate3D(x, y, z);
	}

	@Override
	public Transform3D translate3DX(double x)
	{
		return base.translate3DX(x);
	}

	@Override
	public Transform3D translate3DY(double y)
	{
		return base.translate3DY(y);
	}

	@Override
	public Transform3D translate3DZ(double z)
	{
		return base.translate3DZ(z);
	}

	@Override
	public Transform3D rotate3D(Angle angleX, Angle angleY, Angle angleZ)
	{
		return base.rotate3D(angleX, angleY, angleZ);
	}

	@Override
	public Transform3D rotate3DX(Angle angle)
	{
		return base.rotate3DX(angle);
	}

	@Override
	public Transform3D rotate3DY(Angle angle)
	{
		return base.rotate3DY(angle);
	}

	@Override
	public Transform3D rotate3DZ(Angle angle)
	{
		return base.rotate3DZ(angle);
	}

	@Override
	public Transform3D scale3D(double x, double y, double z)
	{
		return base.scale3D(x, y, z);
	}

	@Override
	public Transform3D mirror3D(double normX, double normY, double normZ)
	{
		return base.mirror3D(normX, normY, normZ);
	}

	@Override
	public Geometry3D union3D(Geometry3D... geometries)
	{
		return base.union3D(geometries);
	}

	@Override
	public Geometry3D union3D(Iterable<Geometry3D> geometries)
	{
		return base.union3D(geometries);
	}

	@Override
	public Geometry3D intersection3D(Geometry3D... geometries)
	{
		return base.intersection3D(geometries);
	}

	@Override
	public Geometry3D intersection3D(Iterable<Geometry3D> geometries)
	{
		return base.intersection3D(geometries);
	}

	@Override
	public Geometry3D difference3D(Geometry3D solid, Geometry3D... cutouts)
	{
		return base.difference3D(solid, cutouts);
	}

	@Override
	public Geometry3D difference3D(Geometry3D solid, Iterable<Geometry3D> cutouts)
	{
		return base.difference3D(solid, cutouts);
	}

	@Override
	public Geometry3D hull3D(Geometry3D... geometries)
	{
		return base.hull3D(geometries);
	}

	@Override
	public Geometry3D hull3D(Iterable<Geometry3D> geometries)
	{
		return base.hull3D(geometries);
	}

	@Override
	public Geometry3D minkowski3D(Geometry3D... geometries)
	{
		return base.minkowski3D(geometries);
	}

	@Override
	public Geometry3D minkowski3D(Iterable<Geometry3D> geometries)
	{
		return base.minkowski3D(geometries);
	}

	@Override
	public Geometry3D linearExtrude(double height,
	                                Angle twist,
	                                double scale,
	                                int slices,
									boolean centerZ,
	                                Geometry2D geometry)
	{
		return base.linearExtrude(height, twist, scale, slices, centerZ, geometry);
	}

	@Override
	public Geometry3D linearExtrude(double height, boolean centerZ, Geometry2D geometry)
	{
		return base.linearExtrude(height, centerZ, geometry);
	}

	@Override
	public Geometry3D rotateExtrude(Angle angle, int angularResolution, Geometry2D geometry)
	{
		return base.rotateExtrude(angle, angularResolution, geometry);
	}

	@Override
	public void view(Geometry2D geometry)
	{
		base.view(geometry);
	}

	@Override
	public void view(Geometry3D geometry)
	{
		base.view(geometry);
	}

	@Override
	public void view(Geometry2D geometry, int windowID)
	{
		base.view(geometry, windowID);
	}

	@Override
	public void view(Geometry3D geometry, int windowID)
	{
		base.view(geometry, windowID);
	}

	@Override
	public Geometry2D cache(Geometry2D geometry)
	{
		return base.cache(geometry);
	}

	@Override
	public Geometry3D cache(Geometry3D geometry)
	{
		return base.cache(geometry);
	}

	@Override
	public Geometry3D loadSTL(String name) throws IOException
	{
		return base.loadSTL(name);
	}

	@Override
	public void saveSTL(String name, Geometry3D geometry) throws IOException
	{
		base.saveSTL(name, geometry);
	}
}
