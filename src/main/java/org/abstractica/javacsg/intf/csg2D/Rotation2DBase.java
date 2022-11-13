package org.abstractica.javacsg.intf.csg2D;

import org.abstractica.javaopenscad.JavaOpenSCAD;
import org.abstractica.javaopenscad.intf.Geometry2D;
import org.abstractica.javaopenscad.intf.Geometry2DFrom2D;
import org.abstractica.javacsg.intf.angle.Angle;

public interface Rotation2DBase extends JavaOpenSCAD
{
	//Rotations using angles
	default Geometry2DFrom2D rotate2D(Angle angle) {return rotate2D(angle.asDegrees());}
	default Geometry2D rotate2DAroundPoint(Angle angle, double x, double y, Geometry2D geometry)
	{
		Geometry2DFrom2D t1 = translate2D(-x, -y).add(geometry);
		Geometry2DFrom2D r = rotate2D(angle).add(t1);
		Geometry2DFrom2D t2 = translate2D(x, y).add(r);
		return t2;
	}

}
