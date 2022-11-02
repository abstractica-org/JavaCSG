package org.abstractica.javacsg.intf.csg2D;


import org.abstractica.javaopenscad.intf.Geometry2D;
import org.abstractica.javaopenscad.intf.polygon.Vector2D;

public interface CSG2DBase extends Vector2DBase, Polar2DBase, Polygon2DBase, Translation2DBase, Rotation2DBase
{
	//Ellipse based geometry
	Geometry2D ellipse(double diameterX, double diameterY, int angularResolution);
	default Geometry2D circle(double diameter, int angularResolution)
	{
		return ellipse(diameter, diameter, angularResolution);
	}
	default Geometry2D ellipse(double xBegin, double xEnd, double yBegin, double yEnd, int angularResolution)
	{
		double xSize = Math.abs(xEnd-xBegin);
		double ySize = Math.abs(yEnd-yBegin);
		double xPos = Math.min(xBegin, xEnd)+0.5*xSize;
		double yPos = Math.min(yBegin, yEnd)+0.5*ySize;
		Geometry2D res = ellipse(xSize, ySize, angularResolution);
		res = translate2D(xPos, yPos).add(res);
		return module(res);
	}
	default Geometry2D ellipse(Vector2D corner1, Vector2D corner2, int angularResolution)
	{
		return ellipse(corner1.x(), corner2.x(), corner1.y(), corner2.y(), angularResolution);
	}

	//Rectangle based geometry
	Geometry2D rectangle(double xSize, double ySize);
	default Geometry2D rectangle(double xBegin, double xEnd, double yBegin, double yEnd)
	{
		double xSize = Math.abs(xEnd-xBegin);
		double ySize = Math.abs(yEnd-yBegin);
		double xPos = Math.min(xBegin, xEnd)+0.5*xSize;
		double yPos = Math.min(yBegin, yEnd)+0.5*ySize;
		Geometry2D res = rectangle(xSize, ySize);
		res = translate2D(xPos, yPos).add(res);
		return module(res);
	}
	default Geometry2D rectangle(Vector2D corner1, Vector2D corner2)
	{
		return rectangle(corner1.x(), corner2.x(), corner1.y(), corner2.y());
	}
}
