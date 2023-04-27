package org.abstractica.javacsg.impl;

import org.abstractica.javacsg.*;

import java.util.ArrayList;
import java.util.List;

public class JavaCSGImpl extends AbstractJavaCSGBase implements JavaCSG
{
	public JavaCSGImpl(JavaCSGBase base)
	{
		super(base);
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
	public Geometry2D circle2D(double diameter, int angularResolution)
	{
		Transform2D scale = scale2D(diameter, diameter);
		return scale.transform(d1Circle2D(angularResolution));
	}

	@Override
	public Geometry2D pie2D(double diameter, Angle beginAngle, Angle endAngle, int angularResolution)
	{
		Transform2D scale = scale2D(diameter, diameter);
		return scale.transform(d1Pie2D(beginAngle, endAngle, angularResolution));
	}

	private Geometry2D d1Pie2D(Angle beginAngle, Angle endAngle, int angularResolution)
	{

		int steps = angularResolution;
		double fBegin = beginAngle.asRotations();
		double fEnd = endAngle.asRotations();
		double stepSize = fBegin < fEnd ?
				(fEnd-fBegin)/steps :
				(1.0 - (fBegin-fEnd))/steps;
		List<Vector2D> vertices = new ArrayList<>(9);
		vertices.add(vector2D(0, 0));
		for(int i = 0; i <= steps; ++i)
		{
			vertices.add(asVector2D(polar2D(1, rotations(fBegin + i*stepSize))));
		}
		return polygon2D(vertices);
	}

	@Override
	public Geometry2D cutoutPie2D(double diameter, Angle beginAngle, Angle endAngle)
	{
		int steps = 8;
		double fBegin = beginAngle.asRotations();
		double fEnd = endAngle.asRotations();
		double stepSize = fBegin < fEnd ?
				(fEnd-fBegin)/steps :
				(1.0 - (fBegin-fEnd))/steps;
		double r = diameter; //double the diameter
		List<Vector2D> vertices = new ArrayList<>(9);
		vertices.add(vector2D(0, 0));
		for(int i = 0; i <= steps; ++i)
		{
			vertices.add(asVector2D(polar2D(r, rotations(fBegin + i*stepSize))));
		}
		return polygon2D(vertices);
	}

	@Override
	public Geometry2D circleSegment2D(double diameter, Angle beginAngle, Angle endAngle, int angularResolution)
	{
		Geometry2D circle = circle2D(diameter, angularResolution);
		Geometry2D pie = cutoutPie2D(diameter, beginAngle, endAngle);
		return intersection2D(circle, pie);
	}

	@Override
	public Geometry2D ring2D(double innerDiameter, double outerDiameter, int angularResolution)
	{
		Geometry2D innerCircle = circle2D(innerDiameter, angularResolution);
		Geometry2D outerCircle = circle2D(outerDiameter, angularResolution);
		return difference2D(outerCircle, innerCircle);
	}

	@Override
	public Geometry2D ringSegment2D(double innerDiameter, double outerDiameter, Angle beginAngle, Angle endAngle, int angularResolution)
	{
		Geometry2D ring = ring2D(innerDiameter, outerDiameter, angularResolution);
		Geometry2D pie = cutoutPie2D(outerDiameter, beginAngle, endAngle);
		return intersection2D(ring, pie);
	}

	private Geometry2D unitSquare2D()
	{
		List<Vector2D> vertices = new ArrayList<>(4);
		vertices.add(vector2D(-0.5, -0.5));
		vertices.add(vector2D(0.5, -0.5));
		vertices.add(vector2D(0.5, 0.5));
		vertices.add(vector2D(-0.5, 0.5));
		return polygon2D(vertices);
	}

	@Override
	public Geometry2D rectangle2D(double width, double height)
	{
		Transform2D scale = scale2D(width, height);
		return scale.transform(unitSquare2D());
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
		return translate.transform(rect);
	}

	@Override
	public Geometry2D rectangleCenter2D(double cx, double cy, double width, double height)
	{
		Transform2D translate = translate2D(cx, cy);
		Geometry2D rect = rectangle2D(width, height);
		return translate.transform(rect);
	}

	private Geometry3D d1Sphere3D(int angularResolution)
	{
		if(angularResolution < 4)
		{
			throw new IllegalArgumentException("angularResolution must be at least 4");
		}
		Geometry2D pie = d1Pie2D(degrees(-90), degrees(90), angularResolution/2);
		Geometry3D res = rotateExtrude(degrees(360), angularResolution, pie);
		return res;
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
			return sphere;
		}
		Transform3D translate = translate3DZ(diameter/2);
		return translate.transform(sphere);
	}

	@Override
	public Geometry3D box3D(double xSize, double ySize, double zSize, boolean centerZ)
	{
		Geometry2D rect = rectangle2D(xSize, ySize);
		Geometry3D box = linearExtrude(zSize, centerZ, rect);
		return box;
	}

	@Override
	public Geometry3D boxCenter3D(double cx, double cy, double cz, double xSize, double ySize, double zSize)
	{
		Transform3D translate = translate3D(cx, cy, cz);
		Geometry3D box = box3D(xSize, ySize, zSize, true);
		return translate.transform(box);
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
	public Geometry3D cylinder3D(double diameter, double height, int angularResolution, boolean centerZ)
	{
		Geometry2D circle = circle2D(diameter, angularResolution);
		Geometry3D cylinder = linearExtrude(height, centerZ, circle);
		return cylinder;
	}

	@Override
	public Geometry3D cylinderSegment3D(double diameter, double height, Angle beginAngle, Angle endAngle, int angularResolution, boolean centerZ)
	{
		Geometry2D circleSegment = circleSegment2D(diameter, beginAngle, endAngle, angularResolution);
		Geometry3D cylinder = linearExtrude(height, centerZ, circleSegment);
		return cylinder;
	}

	@Override
	public Geometry3D hollowCylinder3D(double outerDiameter, double innerDiameter, double height, int angularResolution, boolean centerZ)
	{
		Geometry2D ring = ring2D(innerDiameter, outerDiameter, angularResolution);
		Geometry3D cylinder = linearExtrude(height, centerZ, ring);
		return cylinder;
	}

	@Override
	public Geometry3D hollowCylinderSegment3D(double outerDiameter, double innerDiameter, double height, Angle beginAngle, Angle endAngle, int angularResolution, boolean centerZ)
	{
		Geometry2D ringSegment = ringSegment2D(innerDiameter, outerDiameter, beginAngle, endAngle, angularResolution);
		Geometry3D cylinder = linearExtrude(height, centerZ, ringSegment);
		return cylinder;
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
		Geometry3D cone = linearExtrude(height, degrees(0), topDiameter / bottomDiameter, 1, true, circle);
		if(flip)
		{
			Transform3D flipTransform = mirror3D(0, 0, 1);
			cone = flipTransform.transform(cone);
		}
		if(centerZ)
		{
			return cone;
		}
		Transform3D translate = translate3DZ(height/2);
		return translate.transform(cone);
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
		Geometry3D cone = linearExtrude(height, degrees(0), topDiameter / bottomDiameter, 1, true, circleSegment);
		if(flip)
		{
			Transform3D flipTransform = mirror3D(0, 0, 1);
			cone = flipTransform.transform(cone);
		}
		if(centerZ)
		{
			return cone;
		}
		Transform3D translate = translate3DZ(height/2);
		return translate.transform(cone);
	}

	@Override
	public Geometry3D flatRing3D(double innerDiameter, double outerDiameter, double height, int angularResolution, boolean centerZ)
	{
		Geometry2D ring = ring2D(innerDiameter, outerDiameter, angularResolution);
		Geometry3D flatRing = linearExtrude(height, centerZ, ring);
		return flatRing;
	}

	@Override
	public Geometry3D flatRingSegment3D(double innerDiameter, double outerDiameter, double height, Angle beginAngle, Angle endAngle, int angularResolution, boolean centerZ)
	{
		Geometry2D ringSegment = ringSegment2D(innerDiameter, outerDiameter, beginAngle, endAngle, angularResolution);
		Geometry3D flatRing = linearExtrude(height, centerZ, ringSegment);
		return flatRing;
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
		Geometry3D torus = rotateExtrude(rotations(1), largeCircleResolution, smallCircle);
		if(centerZ)
		{
			return torus;
		}
		return translate3DZ(largeCircleDiameter / 2).transform(torus);
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
		Geometry3D cutoutPie3D = linearExtrude(smallCircleDiameter+2, true, cutoutPie);
		Geometry3D torusSegment = intersection3D(torus, cutoutPie3D);

		if(centerZ)
		{
			return torusSegment;
		}
		Transform3D translate = translate3DZ(smallCircleDiameter/2);
		return translate.transform(torusSegment);
	}

	@Override
	public Geometry3D slice3DX(double xMin, double xMax, Geometry3D geometry)
	{
		Vector3D min = geometry.getMin();
		Vector3D max = geometry.getMax();
		Geometry3D lower = boxCorners3D
			(
				min.x()-1,
				min.y()-1,
				min.z()-1,
				xMin,
				max.y()+1,
				max.z()+1
			);
		Geometry3D upper = boxCorners3D
			(
				xMax,
				min.y()-1,
				min.z()-1,
				max.x()+1,
				max.y()+1,
				max.z()+1
			);
		Geometry3D slice = difference3D(geometry, lower, upper);
		return slice;
	}

	@Override
	public Geometry3D slice3DY(double yMin, double yMax, Geometry3D geometry)
	{
		Vector3D min = geometry.getMin();
		Vector3D max = geometry.getMax();
		Geometry3D lower = boxCorners3D
			(
				min.x()-1,
				min.y()-1,
				min.z()-1,
				max.x()+1,
				yMin,
				max.z()+1
			);
		Geometry3D upper = boxCorners3D
			(
				min.x()-1,
				yMax,
				min.z()-1,
				max.x()+1,
				max.y()+1,
				max.z()+1
			);
		Geometry3D slice = difference3D(geometry, lower, upper);
		return slice;
	}

	@Override
	public Geometry3D slice3DZ(double zMin, double zMax, Geometry3D geometry)
	{
		Vector3D min = geometry.getMin();
		Vector3D max = geometry.getMax();
		Geometry3D lower = boxCorners3D
			(
				min.x()-1,
				min.y()-1,
				min.z()-1,
				max.x()+1,
				max.y()+1,
				zMin
			);
		Geometry3D upper = boxCorners3D
			(
				min.x()-1,
				min.y()-1,
				zMax,
				max.x()+1,
				max.y()+1,
				max.z()+1
			);
		Geometry3D slice = difference3D(geometry, lower, upper);
		return slice;
	}
}
