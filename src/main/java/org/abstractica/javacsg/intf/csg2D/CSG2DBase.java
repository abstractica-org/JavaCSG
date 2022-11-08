package org.abstractica.javacsg.intf.csg2D;


import org.abstractica.javacsg.intf.angle.Angle;
import org.abstractica.javacsg.intf.extrude.ExtrudeBase;
import org.abstractica.javaopenscad.intf.Geometry2D;
import org.abstractica.javaopenscad.intf.Path;
import org.abstractica.javaopenscad.intf.Polygon2D;
import org.abstractica.javaopenscad.intf.Vector2D;

import java.util.ArrayList;
import java.util.List;

public interface CSG2DBase extends ExtrudeBase, Vector2DBase, Polar2DBase, Polygon2DBase, Translation2DBase, Rotation2DBase
{
	//Shortcut to polygon
	default Geometry2D polygon2DGeometry(List<Vector2D> vertices, int convexity)
	{
		Polygon2D polygon = polygon2D(vertices, convexity);
		return polygon2DGeometry(polygon);
	}
	default Geometry2D polygon2DGeometry(List<Vector2D> vertices, List<Path> paths, int convexity)
	{
		Polygon2D polygon = polygon2D(vertices, paths, convexity);
		return polygon2DGeometry(polygon);
	}
	//Ellipse based geometry
	Geometry2D ellipse2D(double diameterX, double diameterY, int angularResolution);
	default Geometry2D circle2D(double diameter, int angularResolution)
	{
		return ellipse2D(diameter, diameter, angularResolution);
	}
	default Geometry2D ellipse2D(double xBegin, double xEnd, double yBegin, double yEnd, int angularResolution)
	{
		double xSize = Math.abs(xEnd-xBegin);
		double ySize = Math.abs(yEnd-yBegin);
		double xPos = Math.min(xBegin, xEnd)+0.5*xSize;
		double yPos = Math.min(yBegin, yEnd)+0.5*ySize;
		Geometry2D res = ellipse2D(xSize, ySize, angularResolution);
		res = translate2D(xPos, yPos).add(res);
		return module(res);
	}
	default Geometry2D ellipse2D(Vector2D corner1, Vector2D corner2, int angularResolution)
	{
		return ellipse2D(corner1.x(), corner2.x(), corner1.y(), corner2.y(), angularResolution);
	}
	default Geometry2D cutoutPie2D(double diameter, Angle begin, Angle end)
	{
		int steps = 8;
		double fBegin = begin.asRotations();
		double fEnd = end.asRotations();
		double stepSize = fBegin < fEnd ?
				(fEnd-fBegin)/steps :
				(1.0 - (fBegin-fEnd))/steps;
		double r = diameter; //double the diameter
		List<Vector2D> points = new ArrayList<>();
		points.add(vector2D(0,0));
		for(int i = 0; i <= steps; ++i)
		{
			points.add(asVector2D(polar2D(r, rotations(fBegin + i*stepSize))));
		}
		return polygon2DGeometry(points, 1);
	}
	default Geometry2D circle2DSegment(double diameter, Angle begin, Angle end, int angularResolution)
	{
		Geometry2D circle = circle2D(diameter, angularResolution);
		Geometry2D cutout = cutoutPie2D(diameter, begin, end);
		return intersection2D().add(circle).add(cutout);
	}


	//Rectangle based geometry
	Geometry2D rectangle2D(double xSize, double ySize);
	default Geometry2D rectangle2D(double xBegin, double xEnd, double yBegin, double yEnd)
	{
		double xSize = Math.abs(xEnd-xBegin);
		double ySize = Math.abs(yEnd-yBegin);
		double xPos = Math.min(xBegin, xEnd)+0.5*xSize;
		double yPos = Math.min(yBegin, yEnd)+0.5*ySize;
		Geometry2D res = rectangle2D(xSize, ySize);
		res = translate2D(xPos, yPos).add(res);
		return module(res);
	}
	default Geometry2D rectangle2D(Vector2D corner1, Vector2D corner2)
	{
		return rectangle2D(corner1.x(), corner2.x(), corner1.y(), corner2.y());
	}
}
