package org.abstractica.javacsg.intf.extrude;

import org.abstractica.javacsg.intf.angle.Angle;
import org.abstractica.javacsg.intf.angle.AngleBase;
import org.abstractica.javaopenscad.intf.Geometry2D;
import org.abstractica.javaopenscad.intf.Geometry3D;
import org.abstractica.javaopenscad.intf.Geometry3DFrom2D;
import org.abstractica.javaopenscad.intf.Polygon2D;

public interface ExtrudeBase extends AngleBase
{
	//Linear extrude helper methods
	default Geometry3DFrom2D linearExtrude(double height, Angle twist, double scale, int slices, int convexity)
	{
		return linearExtrude(height, twist.asDegrees(), scale, slices, convexity);
	}

	default Geometry3DFrom2D linearExtrude(double height, int convexity)
	{
		return linearExtrude(height, 0, 1.0, 1, convexity);
	}

	default Geometry3D linearExtrude(double height, Angle twist, double scale, int slices, Polygon2D polygon)
	{
		Geometry2D polygonGeometry = polygon2DGeometry(polygon);
		return linearExtrude(height, twist, scale, slices, polygon.convexity()).add(polygonGeometry);
	}

	default Geometry3D linearExtrude(double height, Polygon2D polygon)
	{
		return linearExtrude(height, zeroAngle(), 1.0, 1, polygon);
	}

	//Rotate extrude helper methods
	default Geometry3DFrom2D rotateExtrude(Angle angle, int angularResolution, int convexity)
	{
		return rotateExtrude(angle.asDegrees(), angularResolution, convexity);
	}

	default Geometry3D rotateExtrude(Angle angle, int angularResolution, Polygon2D polygon)
	{
		Geometry2D polygonGeometry = polygon2DGeometry(polygon);
		return rotateExtrude(angle, angularResolution, polygon.convexity()).add(polygonGeometry);
	}
}
