package org.abstractica.javacsg.impl;

import org.abstractica.javaopenscad.impl.JavaOpenSCADImpl;
import org.abstractica.javaopenscad.intf.Geometry2D;
import org.abstractica.javaopenscad.intf.polygon.Polygon2D;
import org.abstractica.javaopenscad.intf.polygon.Vector2D;
import org.abstractica.javacsg.intf.angle.Angle;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.intf.csg2D.Polar2D;
import org.abstractica.javacsg.intf.csg3d.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class JavaCSGImpl extends JavaOpenSCADImpl implements JavaCSG
{
	private static final double DEGREES_TO_ROTATIONS = 1.0/360.0;
	private static final double RADIANS_TO_ROTATIONS = 1.0/(2.0*Math.PI);

	public JavaCSGImpl(String moduleDirectoryName, boolean binarySTL)
	{
		super(moduleDirectoryName, binarySTL);
	}

	public JavaCSGImpl()
	{
		super(null, false);
	}

	@Override
	public Angle rotations(double rot)
	{
		return new AngleImpl(rot);
	}

	@Override
	public Angle degrees(double deg)
	{
		return new AngleImpl(deg*DEGREES_TO_ROTATIONS);
	}

	@Override
	public Angle radians(double rad)
	{
		return new AngleImpl(rad*RADIANS_TO_ROTATIONS);
	}

	@Override
	public Polar2D polar2D(double r, Angle theta)
	{
		return new Polar2DImpl(r, theta);
	}

	@Override
	public Vector3D vector3D(double x, double y, double z)
	{
		return new Vector3DImpl(x, y, z);
	}

	private Geometry2D d1Circle(int angularResolution)
	{
		List<Vector2D> vertices = new ArrayList<>();
		double r = 0.5;
		double delta = 1.0/angularResolution;
		for(int i = 0; i < angularResolution; ++i)
		{
			Polar2D polar = polar2D(r, rotations(i*delta));
			Vector2D vector = asVector2D(polar);
			vertices.add(vector);
		}
		Polygon2D polygon = polygon2D(vertices);
		Geometry2D circle =  polygon2DGeometry(polygon);
		return module(circle);
	}

	private Geometry2D d1Rect()
	{
		List<Vector2D> vertices = new ArrayList<>();
		vertices.add(vector2D(0.5, -0.5));
		vertices.add(vector2D(0.5, 0.5));
		vertices.add(vector2D(-0.5, 0.5));
		vertices.add(vector2D(-0.5, -0.5));
		Geometry2D rect =  polygon2DGeometry(vertices);
		return module(rect);
	}

	@Override
	public Geometry2D ellipse(double diameterX, double diameterY, int angularResolution)
	{
		return module(scale2D(diameterX, diameterY).add(d1Circle(angularResolution)));
	}

	@Override
	public Geometry2D rectangle(double xSize, double ySize)
	{
		return module(scale2D(xSize, ySize).add(d1Rect()));
	}
}
