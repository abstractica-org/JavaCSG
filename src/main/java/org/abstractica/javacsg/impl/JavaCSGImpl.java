package org.abstractica.javacsg.impl;

import org.abstractica.javacsg.*;
import org.abstractica.javacsg.impl.baseimpl.JavaCSGBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaCSGImpl implements JavaCSG
{
	private static final double DEGREES_TO_ROTATIONS = 1.0 / 360.0;
	private static final double RADIANS_TO_ROTATIONS = 1.0 / (2.0 * Math.PI);
	private final JavaCSGBase base;
	public JavaCSGImpl(JavaCSGBase base)
	{
		this.base = base;
	}

	private Geometry2D d1Circle2D(int angularResolution)
	{
		if(angularResolution < 3)
		{
			throw new IllegalArgumentException("angularResolution must be at least 3");
		}
		List<Vector2D> vertices = new ArrayList<>(angularResolution);
		double deltaRotation = 1.0 / angularResolution;
		for(int i = 0; i < angularResolution; i++)
		{
			Polar2D polar = polar2D(0.5, rotations(i * deltaRotation));
			vertices.add(asVector2D(polar));
		}
		return polygon2D(vertices);
	}

	@Override
	public Angle rotations(double rotations)
	{
		return new AngleImpl(rotations);
	}

	@Override
	public Angle degrees(double degrees)
	{
		return new AngleImpl(degrees * DEGREES_TO_ROTATIONS);
	}

	@Override
	public Angle radians(double radians)
	{
		return new AngleImpl(radians * RADIANS_TO_ROTATIONS);
	}

	@Override
	public Vector2D vector2D(double x, double y)
	{
		return new Vector2DImpl(x, y);
	}

	@Override
	public double sqrLength(Vector2D vector)
	{
		return vector.x() * vector.x() + vector.y() * vector.y();
	}

	@Override
	public double length(Vector2D vector)
	{
		return Math.sqrt(sqrLength(vector));
	}

	@Override
	public double dist(Vector2D vector1, Vector2D vector2)
	{
		return length(sub(vector1, vector2));
	}

	@Override
	public Vector2D normalized(Vector2D vector)
	{
		return div(vector, length(vector));
	}

	@Override
	public Vector2D add(Vector2D vector1, Vector2D vector2)
	{
		return new Vector2DImpl(vector1.x() + vector2.x(), vector1.y() + vector2.y());
	}

	@Override
	public Vector2D sub(Vector2D vector1, Vector2D vector2)
	{
		return new Vector2DImpl(vector1.x() - vector2.x(), vector1.y() - vector2.y());
	}

	@Override
	public Vector2D mul(Vector2D vector, double scalar)
	{
		return new Vector2DImpl(vector.x() * scalar, vector.y() * scalar);
	}

	@Override
	public Vector2D div(Vector2D vector, double scalar)
	{
		double invScalar = 1.0 / scalar;
		return new Vector2DImpl(vector.x() * invScalar, vector.y() * invScalar);
	}

	@Override
	public double dot(Vector2D vector1, Vector2D vector2)
	{
		return vector1.x() * vector2.x() + vector1.y() * vector2.y();
	}

	@Override
	public Vector2D fromTo(Vector2D from, Vector2D to)
	{
		return sub(to, from);
	}

	@Override
	public Polar2D polar2D(double r, Angle phi)
	{
		return new Polar2DImpl(r, phi);
	}

	@Override
	public Polar2D asPolar2D(Vector2D vector)
	{
		return polar2D(length(vector), radians(Math.atan2(vector.y(), vector.x())));
	}

	@Override
	public Vector2D asVector2D(Polar2D polar)
	{
		return vector2D
				(
						polar.r() * Math.cos(polar.phi().asRadians()),
						polar.r() * Math.sin(polar.phi().asRadians())
				);
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
	public Transform2D compose2D(List<Transform2D> transforms)
	{
		return base.compose2D(transforms);
	}

	@Override
	public Transform2D compose2D(Transform2D... transforms)
	{
		return base.compose2D(Arrays.asList(transforms));
	}

	@Override
	public Transform2D translate2D(double x, double y)
	{
		return base.translate2D(x, y);
	}

	@Override
	public Transform2D translate2DX(double x)
	{
		return base.translate2D(x, 0);
	}

	@Override
	public Transform2D translate2DY(double y)
	{
		return base.translate2D(0, y);
	}

	@Override
	public Transform2D translate2D(Vector2D v)
	{
		return base.translate2D(v.x(), v.y());
	}

	@Override
	public Transform2D translate2DFromTo(Vector2D from, Vector2D to)
	{
		return translate2D(sub(to, from));
	}

	@Override
	public Transform2D rotate2D(Angle angle)
	{
		return base.rotate2D(angle);
	}

	@Override
	public Transform2D rotate2DAround(Vector2D point, Angle angle)
	{
		Transform2D t = translate2D(-point.x(), -point.y());
		Transform2D r = rotate2D(angle);
		Transform2D tInv = translate2D(point.x(), point.y());
		return compose2D(tInv, r, t);
	}

	@Override
	public Transform2D scale2D(double x, double y)
	{
		return base.scale2D(x, y);
	}

	@Override
	public Transform2D scale2D(double s)
	{
		return base.scale2D(s, s);
	}

	@Override
	public Transform2D mirror2D(double normX, double normY)
	{
		return base.mirror2D(normX, normY);
	}

	@Override
	public Geometry2D union2D(Iterable<Geometry2D> geometries)
	{
		return base.union2D(geometries);
	}

	@Override
	public Geometry2D union2D(Geometry2D... geometries)
	{
		return base.union2D(Arrays.asList(geometries));
	}


	@Override
	public Geometry2D union2D(Geometry2D geometry, Iterable<Geometry2D> geometries)
	{
		List<Geometry2D> allGeometries = new ArrayList<>();
		allGeometries.add(geometry);
		for(Geometry2D g : geometries)
		{
			allGeometries.add(g);
		}
		return base.union2D(allGeometries);
	}

	@Override
	public Geometry2D intersection2D(Iterable<Geometry2D> geometries)
	{
		return base.intersection2D(geometries);
	}

	@Override
	public Geometry2D intersection2D(Geometry2D... geometries)
	{
		return base.intersection2D(Arrays.asList(geometries));
	}

	@Override
	public Geometry2D difference2D(Geometry2D filled, Iterable<Geometry2D> cutouts)
	{
		return base.difference2D(filled, cutouts);
	}

	@Override
	public Geometry2D difference2D(Geometry2D filled, Geometry2D... cutouts)
	{
		return base.difference2D(filled, Arrays.asList(cutouts));
	}

	@Override
	public Geometry2D hull2D(Iterable<Geometry2D> geometries)
	{
		return base.hull2D(geometries);
	}

	@Override
	public Geometry2D hull2D(Geometry2D... geometries)
	{
		return base.hull2D(Arrays.asList(geometries));
	}

	@Override
	public Geometry2D minkowski2D(Iterable<Geometry2D> geometries)
	{
		return base.hull2D(geometries);
	}

	@Override
	public Geometry2D minkowski2D(Geometry2D... geometries)
	{
		return base.minkowski2D(Arrays.asList(geometries));
	}

	@Override
	public Geometry2D offset2D(double delta, boolean chamfer, Iterable<Geometry2D> geometries)
	{
		return base.offset2D(delta, chamfer, geometries);
	}

	@Override
	public Geometry2D offset2D(double delta, boolean chamfer, Geometry2D... geometries)
	{
		return base.offset2D(delta, chamfer, Arrays.asList(geometries));
	}

	@Override
	public Geometry2D offsetRound2D(double radius, int angularResolution, Iterable<Geometry2D> geometries)
	{
		return base.offsetRound2D(radius, angularResolution, geometries);
	}

	@Override
	public Geometry2D offsetRound2D(double radius, int angularResolution, Geometry2D... geometries)
	{
		return base.offsetRound2D(radius, angularResolution, Arrays.asList(geometries));
	}

	@Override
	public Geometry2D color2D(double r, double g, double b, double a, Iterable<Geometry2D> geometries)
	{
		return base.color2D(r, g, b, a, geometries);
	}

	@Override
	public Geometry2D color2D(double r, double g, double b, double a, Geometry2D... geometries)
	{
		return base.color2D(r, g, b, a, Arrays.asList(geometries));
	}

	@Override
	public Geometry2D char2D(char ch, double width, int angularResolution)
	{
		return base.char2D(ch, width, angularResolution);
	}

	@Override
	public Geometry2D char2D(char ch, double width, double height, int angularResolution)
	{
		return base.char2D(ch, width, height, angularResolution);
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
		List<Geometry2D> chars = new ArrayList<>();
		for(int i = 0; i < text.length(); i++)
		{
			Transform2D t = translate2D(i*letterWidth, 0);
			chars.add(t.transform(char2D(text.charAt(i), letterWidth, angularResolution)));
		}
		return base.union2D(chars);
	}

	@Override
	public Geometry2D text2D(String text, double letterWidth, double letterHeight, int angularResolution)
	{
		List<Geometry2D> chars = new ArrayList<>();
		for(int i = 0; i < text.length(); i++)
		{
			Transform2D t = translate2D(i*letterWidth, 0);
			chars.add(t.transform(char2D(text.charAt(i), letterWidth, letterHeight, angularResolution)));
		}
		return base.union2D(chars);
	}

	@Override
	public Geometry2D circle2D(double diameter, int angularResolution)
	{
		Transform2D scale = base.scale2D(diameter, diameter);
		return base.cache(scale.transform(d1Circle2D(angularResolution)));
	}

	@Override
	public Geometry2D pie2D(double diameter, Angle beginAngle, Angle endAngle, int angularResolution)
	{
		Transform2D scale = base.scale2D(diameter, diameter);
		return base.cache(scale.transform(d1Pie2D(beginAngle, endAngle, angularResolution)));
	}

	private Geometry2D d1Pie2D(Angle beginAngle, Angle endAngle, int angularResolution)
	{

        double fBegin = beginAngle.asRotations();
		double fEnd = endAngle.asRotations();
		double stepSize = fBegin < fEnd ?
				(fEnd-fBegin)/ angularResolution :
				(1.0 - (fBegin-fEnd))/ angularResolution;
		List<Vector2D> vertices = new ArrayList<>(9);
		vertices.add(vector2D(0, 0));
		for(int i = 0; i <= angularResolution; ++i)
		{
			vertices.add(asVector2D(polar2D(0.5, rotations(fBegin + i*stepSize))));
		}
		return base.polygon2D(vertices);
	}

	@Override
	public Geometry2D cutoutPie2D(double diameter, Angle beginAngle, Angle endAngle)
	{
		Geometry2D cutoutPie = d1Pie2D(beginAngle, endAngle, 8);
		cutoutPie = scale2D(2*diameter, 2*diameter).transform(cutoutPie);
		return base.cache(cutoutPie);
	}

	@Override
	public Geometry2D circleSegment2D(double diameter, Angle beginAngle, Angle endAngle, int angularResolution)
	{
		Geometry2D circle = circle2D(diameter, angularResolution);
		Geometry2D pie = cutoutPie2D(diameter, beginAngle, endAngle);
		return base.cache(intersection2D(circle, pie));
	}

	@Override
	public Geometry2D hollowCircle2D(double innerDiameter, double outerDiameter, int angularResolution)
	{
		Geometry2D innerCircle = circle2D(innerDiameter, angularResolution);
		Geometry2D outerCircle = circle2D(outerDiameter, angularResolution);
		return base.cache(difference2D(outerCircle, innerCircle));
	}

	@Override
	public Geometry2D hollowCircleSegment2D(double innerDiameter, double outerDiameter, Angle beginAngle, Angle endAngle, int angularResolution)
	{
		Geometry2D ring = hollowCircle2D(innerDiameter, outerDiameter, angularResolution);
		Geometry2D pie = cutoutPie2D(outerDiameter, beginAngle, endAngle);
		return base.cache(intersection2D(ring, pie));
	}

	private Geometry2D unitSquare2D()
	{
		List<Vector2D> vertices = new ArrayList<>(4);
		vertices.add(vector2D(-0.5, -0.5));
		vertices.add(vector2D(0.5, -0.5));
		vertices.add(vector2D(0.5, 0.5));
		vertices.add(vector2D(-0.5, 0.5));
		return base.polygon2D(vertices);
	}

	@Override
	public Geometry2D rectangle2D(double width, double height)
	{
		Transform2D scale = base.scale2D(width, height);
		return base.cache(scale.transform(unitSquare2D()));
	}

	@Override
	public Geometry2D rectangleCorners2D(double c1x, double c1y, double c2x, double c2y)
	{
		double width = Math.abs(c2x - c1x);
		double height = Math.abs(c2y - c1y);
		double x = Math.min(c1x, c2x) + width/2;
		double y = Math.min(c1y, c2y) + height/2;
		Transform2D translate = translate2D(x, y);
		Geometry2D rect = rectangle2D(width, height);
		return base.cache(translate.transform(rect));
	}

	@Override
	public Geometry2D rectangleCenter2D(double cx, double cy, double width, double height)
	{
		Transform2D translate = base.translate2D(cx, cy);
		Geometry2D rect = rectangle2D(width, height);
		return base.cache(translate.transform(rect));
	}

	private Geometry2D unitTriangle2D()
	{
		List<Vector2D> vertices = new ArrayList<>(3);
		vertices.add(vector2D(0, 0));
		vertices.add(vector2D(1, 0));
		vertices.add(vector2D(0, 1));
		return base.polygon2D(vertices);
	}

	@Override
	public Geometry2D rightTriangle2D(double xSize, double ySize)
	{
		Transform2D scale = base.scale2D(xSize, ySize);
		return base.cache(scale.transform(unitTriangle2D()));
	}

	@Override
	public Vector3D vector3D(double x, double y, double z)
	{
		return new Vector3DImpl(x, y, z);
	}

	@Override
	public double sqrLength(Vector3D vector)
	{
		return vector.x() * vector.x() + vector.y() * vector.y() + vector.z() * vector.z();
	}

	@Override
	public double length(Vector3D vector)
	{
		return Math.sqrt(sqrLength(vector));
	}

	@Override
	public double dist(Vector3D vector1, Vector3D vector2)
	{
		return length(sub(vector1, vector2));
	}

	@Override
	public Vector3D normalized(Vector3D vector)
	{
		return div(vector, length(vector));
	}

	@Override
	public Vector3D add(Vector3D vector1, Vector3D vector2)
	{
		return new Vector3DImpl
				(
						vector1.x() + vector2.x(),
						vector1.y() + vector2.y(),
						vector1.z() + vector2.z()
				);
	}

	@Override
	public Vector3D sub(Vector3D vector1, Vector3D vector2)
	{
		return new Vector3DImpl
				(
						vector1.x() - vector2.x(),
						vector1.y() - vector2.y(),
						vector1.z() - vector2.z()
				);
	}

	@Override
	public Vector3D mul(Vector3D vector, double scalar)
	{
		return new Vector3DImpl
				(
						vector.x() * scalar,
						vector.y() * scalar,
						vector.z() * scalar
				);
	}

	@Override
	public Vector3D div(Vector3D vector, double scalar)
	{
		double invScalar = 1.0 / scalar;
		return new Vector3DImpl
				(
						vector.x() * invScalar,
						vector.y() * invScalar,
						vector.z() * invScalar
				);
	}

	@Override
	public double dot(Vector3D vector1, Vector3D vector2)
	{
		return vector1.x() * vector2.x() + vector1.y() * vector2.y() + vector1.z() * vector2.z();
	}

	@Override
	public Vector3D cross(Vector3D vector1, Vector3D vector2)
	{
		return new Vector3DImpl
				(
						vector1.y() * vector2.z() - vector1.z() * vector2.y(),
						vector1.z() * vector2.x() - vector1.x() * vector2.z(),
						vector1.x() * vector2.y() - vector1.y() * vector2.x()
				);
	}

	@Override
	public Vector3D fromTo(Vector3D from, Vector3D to)
	{
		return sub(to, from);
	}

	@Override
	public Geometry3D polyhedron3D(Iterable<Vector3D> vertices, Iterable<? extends Iterable<Integer>> faces)
	{
		return base.polyhedron3D(vertices, faces);
	}

	@Override
	public Geometry3D linearExtrude(double height, Angle twist, double scale, int slices, boolean centerZ, Geometry2D geometry)
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
	public Transform3D compose3D(List<Transform3D> transforms)
	{
		return base.compose3D(transforms);
	}

	@Override
	public Transform3D compose3D(Transform3D... transforms)
	{
		return base.compose3D(Arrays.asList(transforms));
	}

	@Override
	public Transform3D translate3D(double x, double y, double z)
	{
		return base.translate3D(x, y, z);
	}

	@Override
	public Transform3D translate3DX(double x)
	{
		return base.translate3D(x, 0, 0);
	}

	@Override
	public Transform3D translate3DY(double y)
	{
		return base.translate3D(0, y, 0);
	}

	@Override
	public Transform3D translate3DZ(double z)
	{
		return base.translate3D(0, 0, z);
	}

	@Override
	public Transform3D translate3D(Vector3D v)
	{
		return base.translate3D(v.x(), v.y(), v.z());
	}

	@Override
	public Transform3D translate3DFromTo(Vector3D from, Vector3D to)
	{
		return translate3D(sub(to, from));
	}

	@Override
	public Transform3D rotate3D(Angle angleX, Angle angleY, Angle angleZ)
	{
		Transform3D rotX = base.rotate3DX(angleX);
		Transform3D rotY = base.rotate3DY(angleY);
		Transform3D rotZ = base.rotate3DZ(angleZ);
		return compose3D(rotZ, rotY, rotX);
	}

	@Override
	public Transform3D rotate3DAround(Vector3D point, Angle angleX, Angle angleY, Angle angleZ)
	{
		Transform3D t = translate3D(-point.x(), -point.y(), -point.z());
		Transform3D r = rotate3D(angleX, angleY, angleZ);
		Transform3D tInv = translate3D(point.x(), point.y(), point.z());
		return compose3D(tInv, r, t);
	}

	@Override
	public Transform3D rotate3DX(Angle angle)
	{
		return base.rotate3DX(angle);
	}

	@Override
	public Transform3D rotate3DXAround(Vector3D point, Angle angle)
	{
		Transform3D t = translate3D(-point.x(), -point.y(), -point.z());
		Transform3D r = base.rotate3DX(angle);
		Transform3D tInv = translate3D(point.x(), point.y(), point.z());
		return compose3D(tInv, r, t);
	}

	@Override
	public Transform3D rotate3DY(Angle angle)
	{
		return base.rotate3DY(angle);
	}

	@Override
	public Transform3D rotate3DYAround(Vector3D point, Angle angle)
	{
		Transform3D t = translate3D(-point.x(), -point.y(), -point.z());
		Transform3D r = base.rotate3DY(angle);
		Transform3D tInv = translate3D(point.x(), point.y(), point.z());
		return compose3D(tInv, r, t);
	}

	@Override
	public Transform3D rotate3DZ(Angle angle)
	{
		return base.rotate3DZ(angle);
	}

	@Override
	public Transform3D rotate3DZAround(Vector3D point, Angle angle)
	{
		Transform3D t = translate3D(-point.x(), -point.y(), -point.z());
		Transform3D r = base.rotate3DZ(angle);
		Transform3D tInv = translate3D(point.x(), point.y(), point.z());
		return compose3D(tInv, r, t);
	}

	@Override
	public Transform3D scale3D(double x, double y, double z)
	{
		return base.scale3D(x, y, z);
	}

	@Override
	public Transform3D scale3D(double s)
	{
		return base.scale3D(s, s, s);
	}

	@Override
	public Transform3D mirror3D(double normX, double normY, double normZ)
	{
		return base.mirror3D(normX, normY, normZ);
	}

	@Override
	public Geometry3D union3D(Geometry3D... geometries)
	{
		return base.union3D(Arrays.asList(geometries));
	}

	@Override
	public Geometry3D union3D(Iterable<Geometry3D> geometries)
	{
		return base.union3D(geometries);
	}

	@Override
	public Geometry3D union3D(Geometry3D geometry, Iterable<Geometry3D> geometries)
	{
		List<Geometry3D> allGeometries = new ArrayList<>();
		allGeometries.add(geometry);
		for(Geometry3D g : geometries)
		{
			allGeometries.add(g);
		}
		return base.union3D(allGeometries);
	}

	@Override
	public Geometry3D intersection3D(Geometry3D... geometries)
	{
		return base.intersection3D(Arrays.asList(geometries));
	}

	@Override
	public Geometry3D intersection3D(Iterable<Geometry3D> geometries)
	{
		return base.intersection3D(geometries);
	}

	@Override
	public Geometry3D difference3D(Geometry3D solid, Geometry3D... cutouts)
	{
		return base.difference3D(solid, Arrays.asList(cutouts));
	}

	@Override
	public Geometry3D difference3D(Geometry3D solid, Iterable<Geometry3D> cutouts)
	{
		return base.difference3D(solid, cutouts);
	}

	@Override
	public Geometry3D hull3D(Geometry3D... geometries)
	{
		return base.hull3D(Arrays.asList(geometries));
	}

	@Override
	public Geometry3D hull3D(Iterable<Geometry3D> geometries)
	{
		return base.hull3D(geometries);
	}

	@Override
	public Geometry3D minkowski3D(Geometry3D... geometries)
	{
		return base.minkowski3D(Arrays.asList(geometries));
	}

	@Override
	public Geometry3D minkowski3D(Iterable<Geometry3D> geometries)
	{
		return base.minkowski3D(geometries);
	}

	private Geometry3D d1Sphere3D(int angularResolution)
	{
		if(angularResolution < 4)
		{
			throw new IllegalArgumentException("angularResolution must be at least 4");
		}
		Geometry2D pie = d1Pie2D(degrees(-90), degrees(90), angularResolution/2);
		Geometry3D res = rotateExtrude(degrees(360), angularResolution, pie);
		return base.cache(res);
	}

	@Override
	public Geometry3D sphere3D(double diameter, int angularResolution, boolean centerZ)
	{
		Geometry3D sphere = d1Sphere3D(angularResolution);
		if(diameter != 1.0)
		{
			Transform3D scale = scale3D(diameter, diameter, diameter);
			sphere = scale.transform(sphere);
		}
		if(centerZ)
		{
			return base.cache(sphere);
		}
		Transform3D translate = translate3DZ(0.5*diameter);
		return base.cache(translate.transform(sphere));
	}

	@Override
	public Geometry3D box3D(double xSize, double ySize, double zSize, boolean centerZ)
	{
		Geometry2D rect = rectangle2D(xSize, ySize);
		Geometry3D box = base.linearExtrude(zSize, centerZ, rect);
		return base.cache(box);
	}

	@Override
	public Geometry3D boxCenter3D(double cx, double cy, double cz, double xSize, double ySize, double zSize)
	{
		Transform3D translate = base.translate3D(cx, cy, cz);
		Geometry3D box = box3D(xSize, ySize, zSize, true);
		return base.cache(translate.transform(box));
	}

	@Override
	public Geometry3D boxCenter3D(Vector3D center, double xSize, double ySize, double zSize)
	{
		return boxCenter3D(center.x(), center.y(), center.z(), xSize, ySize, zSize);
	}

	@Override
	public Geometry3D boxCorners3D(double c1x, double c1y, double c1z, double c2x, double c2y, double c2z)
	{
		double xSize = Math.abs(c2x - c1x);
		double ySize = Math.abs(c2y - c1y);
		double zSize = Math.abs(c2z - c1z);
		double cx = Math.min(c1x, c2x) + xSize/2;
		double cy = Math.min(c1y, c2y) + ySize/2;
		double cz = Math.min(c1z, c2z) + zSize/2;
		return boxCenter3D(cx, cy, cz, xSize, ySize, zSize);
	}

	@Override
	public Geometry3D boxCorners3D(Vector3D cornerA, Vector3D cornerB)
	{
		return boxCorners3D(cornerA.x(), cornerA.y(), cornerA.z(), cornerB.x(), cornerB.y(), cornerB.z());
	}

	@Override
	public Geometry3D cylinder3D(double diameter, double height, int angularResolution, boolean centerZ)
	{
		Geometry2D circle = circle2D(diameter, angularResolution);
		Geometry3D cylinder = base.linearExtrude(height, centerZ, circle);
		return base.cache(cylinder);
	}

	@Override
	public Geometry3D cylinderSegment3D(double diameter, double height, Angle beginAngle, Angle endAngle, int angularResolution, boolean centerZ)
	{
		Geometry2D circleSegment = circleSegment2D(diameter, beginAngle, endAngle, angularResolution);
		Geometry3D cylinder = base.linearExtrude(height, centerZ, circleSegment);
		return base.cache(cylinder);
	}

	@Override
	public Geometry3D hollowCylinder3D(double innerDiameter, double outerDiameter, double height, int angularResolution, boolean centerZ)
	{
		Geometry2D ring = hollowCircle2D(innerDiameter, outerDiameter, angularResolution);
		Geometry3D cylinder = base.linearExtrude(height, centerZ, ring);
		return base.cache(cylinder);
	}

	@Override
	public Geometry3D hollowCylinderSegment3D(double innerDiameter, double outerDiameter, double height, Angle beginAngle, Angle endAngle, int angularResolution, boolean centerZ)
	{
		Geometry2D ringSegment = hollowCircleSegment2D(innerDiameter, outerDiameter, beginAngle, endAngle, angularResolution);
		Geometry3D cylinder = base.linearExtrude(height, centerZ, ringSegment);
		return base.cache(cylinder);
	}

	@Override
	public Geometry3D cone3D(double bottomDiameter,
	                         double topDiameter,
	                         double height,
	                         int angularResolution,
	                         boolean centerZ)
	{
		if(bottomDiameter < 0.0)
		{
			throw new IllegalArgumentException("bottomDiameter must be positive");
		}
		if(topDiameter < 0.0)
		{
			throw new IllegalArgumentException("topDiameter must be positive");
		}
		if(height < 0.0)
		{
			throw new IllegalArgumentException("height must be positive");
		}
		if(bottomDiameter == 0.0 && topDiameter == 0.0)
		{
			throw new IllegalArgumentException("bottomDiameter and topDiameter cannot both be zero");
		}
		boolean flip = bottomDiameter < topDiameter;
		if(flip)
		{
			double temp = bottomDiameter;
			bottomDiameter = topDiameter;
			topDiameter = temp;
		}
		Geometry2D circle = circle2D(bottomDiameter, angularResolution);
		Geometry3D cone = base.linearExtrude(height, degrees(0), topDiameter / bottomDiameter, 1, true, circle);
		if(flip)
		{
			Transform3D flipTransform = mirror3D(0, 0, 1);
			cone = flipTransform.transform(cone);
		}
		if(centerZ)
		{
			return base.cache(cone);
		}
		Transform3D translate = translate3DZ(height/2);
		return base.cache(translate.transform(cone));
	}

	@Override
	public Geometry3D coneSegment3D(double bottomDiameter, double topDiameter, double height, Angle beginAngle, Angle endAngle, int angularResolution, boolean centerZ)
	{
		if(bottomDiameter < 0.0)
		{
			throw new IllegalArgumentException("bottomDiameter must be positive");
		}
		if(topDiameter < 0.0)
		{
			throw new IllegalArgumentException("topDiameter must be positive");
		}
		if(height < 0.0)
		{
			throw new IllegalArgumentException("height must be positive");
		}
		if(bottomDiameter == 0.0 && topDiameter == 0.0)
		{
			throw new IllegalArgumentException("bottomDiameter and topDiameter cannot both be zero");
		}
		boolean flip = bottomDiameter < topDiameter;
		if(flip)
		{
			double temp = bottomDiameter;
			bottomDiameter = topDiameter;
			topDiameter = temp;
		}
		Geometry2D circleSegment = circleSegment2D(bottomDiameter, beginAngle, endAngle, angularResolution);
		Geometry3D cone = base.linearExtrude(height, degrees(0), topDiameter / bottomDiameter, 1, true, circleSegment);
		if(flip)
		{
			Transform3D flipTransform = mirror3D(0, 0, 1);
			cone = flipTransform.transform(cone);
		}
		if(centerZ)
		{
			return base.cache(cone);
		}
		Transform3D translate = translate3DZ(height/2);
		return base.cache(translate.transform(cone));
	}

	@Override
	public Geometry3D flatCylinder3D(double diameter, double width, double height, int angularResolution, boolean centerZ)
	{
		Geometry2D profile = circle2D(diameter, angularResolution);
		Geometry2D restrict = rectangle2D(width, diameter+2);
		profile = intersection2D(profile, restrict);
		Geometry3D flatCylinder = base.linearExtrude(height, centerZ, profile);
		return base.cache(flatCylinder);
	}

	@Override
	public Geometry3D torus3D(double smallCircleDiameter,
	                          double largeCircleDiameter,
	                          int smallCircleResolution,
	                          int largeCircleResolution,
	                          boolean centerZ)
	{
		Geometry2D smallCircle = circle2D(smallCircleDiameter, smallCircleResolution);
		smallCircle = translate2DX(largeCircleDiameter / 2).transform(smallCircle);
		Geometry3D torus = base.rotateExtrude(rotations(1), largeCircleResolution, smallCircle);
		if(centerZ)
		{
			return base.cache(torus);
		}
		return base.cache(translate3DZ(smallCircleDiameter / 2).transform(torus));
	}

	@Override
	public Geometry3D torusSegment3D(double smallCircleDiameter,
	                                 double largeCircleDiameter,
	                                 Angle beginAngle,
	                                 Angle endAngle,
	                                 int smallCircleResolution,
	                                 int largeCircleResolution,
	                                 boolean centerZ)
	{
		Geometry3D torus = torus3D( smallCircleDiameter,
									largeCircleDiameter,
									smallCircleResolution,
									largeCircleResolution,
									true);

		Geometry2D cutoutPie = cutoutPie2D( largeCircleDiameter + smallCircleDiameter,
											beginAngle,
											endAngle);
		Geometry3D cutoutPie3D = base.linearExtrude(smallCircleDiameter+2, true, cutoutPie);
		Geometry3D torusSegment = intersection3D(torus, cutoutPie3D);

		if(centerZ)
		{
			return base.cache(torusSegment);
		}
		Transform3D translate = translate3DZ(smallCircleDiameter/2);
		return base.cache(translate.transform(torusSegment));
	}

	@Override
	public Geometry3D wedge3D(double xSize, double ySize, double zSize, boolean centerZ)
	{
		Geometry2D triangle = rightTriangle2D(xSize, ySize);
		return base.cache(linearExtrude(zSize, centerZ, triangle));
	}

	@Override
	public Geometry3D slice3DX(double xMin, double xMax, Geometry3D geometry)
	{
		Vector3D min = geometry.getMin();
		Vector3D max = geometry.getMax();
		Geometry3D restrict = boxCorners3D
			(
				xMin,
				min.y()-1,
				min.z()-1,
				xMax,
				max.y()+1,
				max.z()+1
			);
		Geometry3D slice = intersection3D(geometry, restrict);
		return base.cache(slice);
	}

	@Override
	public Geometry3D slice3DY(double yMin, double yMax, Geometry3D geometry)
	{
		Vector3D min = geometry.getMin();
		Vector3D max = geometry.getMax();
		Geometry3D restrict = boxCorners3D
			(
				min.x()-1,
				yMin,
				min.z()-1,
				max.x()+1,
				yMax,
				max.z()+1
			);
		Geometry3D slice = intersection3D(geometry, restrict);
		return base.cache(slice);
	}

	@Override
	public Geometry3D slice3DZ(double zMin, double zMax, Geometry3D geometry)
	{
		Vector3D min = geometry.getMin();
		Vector3D max = geometry.getMax();
		Geometry3D restrict = boxCorners3D
			(
				min.x()-1,
				min.y()-1,
				zMin,
				max.x()+1,
				max.y()+1,
				zMax
			);
		Geometry3D slice = intersection3D(geometry, restrict);
		return base.cache(slice);
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
	public Geometry3D loadSTL(String fileName) throws IOException
	{
		return base.loadSTL(fileName);
	}

	@Override
	public void saveSTL(String fileName, Geometry3D geometry) throws IOException
	{
		base.saveSTL(fileName, geometry);
	}
}
