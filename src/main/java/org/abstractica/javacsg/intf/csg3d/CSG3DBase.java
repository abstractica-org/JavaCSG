package org.abstractica.javacsg.intf.csg3d;

import org.abstractica.javacsg.intf.csg2D.CSG2DBase;
import org.abstractica.javaopenscad.intf.*;

import java.util.List;

public interface CSG3DBase extends CSG2DBase, Vector3DBase, Translation3DBase, Rotation3DBase
{
	//Shortcut to polyhedron
	default Geometry3D polyhedron3DGeometry(List<Vector3D> vertices, List<Path> faces, int convexity)
	{
		Polyhedron3D polyhedron = polyhedron3D(vertices, faces, convexity);
		return polyhedron3DGeometry(polyhedron);
	}

	//round geometry
	default Geometry3D cylinder3D(double diameter, double height, boolean centerZ, int angularResolution)
	{
		Geometry2D circle = circle2D(diameter, angularResolution);
		Geometry3D res = linearExtrude(height, 0, 1.0, 1, 1).add(circle);
		if(!centerZ)
		{
			res = translate3DZ(0.5*height).add(res);
		}
		return module(res);
	}

	default Geometry3D cylinder3D(double diameter, double beginZ, double endZ, int angularResolution)
	{
		double height = Math.abs(endZ-beginZ);
		Geometry2D circle = circle2D(diameter, angularResolution);
		Geometry3D res = linearExtrude(height, 0, 1.0, 1, 1).add(circle);
		res = translate3DZ(0.5*height + Math.min(beginZ, endZ)).add(res);
		return module(res);
	}

	default Geometry3D cone3D(
			double bottomDiameter,
			double topDiameter,
			double height,
			boolean centerZ,
			int angularResolution)
	{
		Geometry3D cone;
		if(bottomDiameter < 0.001)
		{
			double ratio = bottomDiameter / topDiameter;
			//make it upside down
			cone = linearExtrude(height, zeroAngle(), ratio, 1, 1)
					.add(circle2D(topDiameter, angularResolution));
			cone = rotate3DX(rotations(0.5)).add(cone);
		}
		else
		{
			double ratio = topDiameter / bottomDiameter;
			cone = linearExtrude(height, zeroAngle(), ratio, 1, 1)
					.add(circle2D(bottomDiameter, angularResolution));
		}
		if(!centerZ)
		{
			cone = translate3DZ(0.5*height).add(cone);
		}
		return module(cone);
	}

	default Geometry3D cone3D(
			double bottomDiameter,
			double topDiameter,
			double beginZ,
			double endZ,
			int angularResolution)
	{
		double height = Math.abs(endZ-beginZ);
		Geometry3D cone = cone3D(bottomDiameter, topDiameter, height, true, angularResolution);
		cone = translate3DZ(0.5*height + Math.min(beginZ, endZ)).add(cone);
		return module(cone);
	}

	//square geometry
	default Geometry3D box3D(double xSize, double ySize, double zSize, boolean centerZ)
	{
		Geometry2D rect = rectangle2D(xSize, ySize);
		Geometry3D box = linearExtrude(zSize, 1).add(rect);
		if(!centerZ)
		{
			box = translate3DZ(0.5*zSize).add(box);
		}
		return module(box);
	}
}
