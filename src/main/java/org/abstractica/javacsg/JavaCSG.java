package org.abstractica.javacsg;

public interface JavaCSG extends JavaCSGBase
{
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 2D shapes
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Geometry2D circle2D(double diameter, int angularResolution);

	Geometry2D pie2D(double diameter, Angle beginAngle, Angle endAngle, int angularResolution);

	Geometry2D cutoutPie2D(double diameter, Angle beginAngle, Angle endAngle);

	Geometry2D circleSegment2D(double diameter, Angle beginAngle, Angle endAngle, int angularResolution);

	Geometry2D ring2D(double innerDiameter, double outerDiameter, int angularResolution);

	Geometry2D ringSegment2D(double innerDiameter,
	                         double outerDiameter,
	                         Angle beginAngle,
	                         Angle endAngle,
	                         int angularResolution);

	Geometry2D rectangle2D(double xSize, double ySize);

	Geometry2D rectangleCorners2D(double c1x, double c1y, double c2x, double c2y);

	Geometry2D rectangleCenter2D(double cx, double cy, double xSize, double ySize);

	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 3D shapes
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Geometry3D sphere3D(double diameter, int angularResolution, boolean centerZ);

	Geometry3D box3D(double xSize, double ySize, double zSize, boolean centerZ);

	Geometry3D boxCenter3D(double cx, double cy, double cz, double xSize, double ySize, double zSize);

	Geometry3D boxCorners3D(double c1x, double c1y, double c1z, double c2x, double c2y, double c2z);

	Geometry3D cylinder3D(double diameter, double height, int angularResolution, boolean centerZ);

	Geometry3D cylinderSegment3D(double diameter,
	                             double height,
	                             Angle beginAngle,
	                             Angle endAngle,
	                             int angularResolution,
	                             boolean centerZ);

	Geometry3D hollowCylinder3D(double outerDiameter, double innerDiameter, double height, int angularResolution, boolean centerZ);

	Geometry3D hollowCylinderSegment3D(double outerDiameter,
	                             double innerDiameter,
	                             double height,
	                             Angle beginAngle,
	                             Angle endAngle,
	                             int angularResolution,
	                             boolean centerZ);

	Geometry3D cone3D(double bottomDiameter,
	                  double topDiameter,
	                  double height,
	                  int angularResolution,
	                  boolean centerZ);

	Geometry3D coneSegment3D(double bottomDiameter,
	                         double topDiameter,
	                         double height,
	                         Angle beginAngle,
	                         Angle endAngle,
	                         int angularResolution,
	                         boolean centerZ);
	Geometry3D flatRing3D(double innerDiameter,
	                      double outerDiameter,
	                      double height,
	                      int angularResolution,
	                      boolean centerZ);
	Geometry3D flatRingSegment3D(double innerDiameter,
	                             double outerDiameter,
	                             double height,
	                             Angle beginAngle,
	                             Angle endAngle,
	                             int angularResolution,
	                             boolean centerZ);
	Geometry3D torus3D(double smallCircleDiameter,
	                   double largeCircleDiameter,
	                   int smallCircleResolution,
	                   int largeCircleResolution,
	                   boolean centerZ);

	Geometry3D torusSegment3D(double smallCircleDiameter,
	                          double largeCircleDiameter,
	                          Angle beginAngle,
	                          Angle endAngle,
	                          int smallCircleResolution,
							  int largeCircleResolution,
	                          boolean centerZ);
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 3D Operations
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	Geometry3D slice3DX(double xMin, double xMax, Geometry3D geometry);
	Geometry3D slice3DY(double yMin, double yMax, Geometry3D geometry);
	Geometry3D slice3DZ(double zMin, double zMax, Geometry3D geometry);

}
