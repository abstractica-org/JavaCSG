package org.abstractica.javacsg.intf.csg3d;

import org.abstractica.javacsg.intf.angle.AngleBase;
import org.abstractica.javaopenscad.JavaOpenSCAD;
import org.abstractica.javaopenscad.intf.Geometry3DFrom3D;
import org.abstractica.javacsg.intf.angle.Angle;

public interface Rotation3DBase extends AngleBase
{
	default Geometry3DFrom3D rotate3D(Angle x, Angle y, Angle z)
	{
		return rotate3D(x.asDegrees(), y.asDegrees(), z.asDegrees());
	}
	default Geometry3DFrom3D rotate3DX(Angle x) {return rotate3D(x, zeroAngle(), zeroAngle());}
	default Geometry3DFrom3D rotate3DY(Angle y) {return rotate3D(zeroAngle(), y, zeroAngle());}
	default Geometry3DFrom3D rotate3DZ(Angle z) {return rotate3D(zeroAngle(), zeroAngle(), z);}
}
