package org.abstractica.javacsg.intf.csg3d;

import org.abstractica.javacsg.intf.angle.AngleBase;
import org.abstractica.javaopenscad.intf.Geometry3D;
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

	default Geometry3DFrom3D rotate3DAroundPoint(Angle x, Angle y, Angle z, double px, double py, double pz, Geometry3D geometry)
	{
		Geometry3DFrom3D t1 = translate3D(-px, -py, -pz).add(geometry);
		Geometry3DFrom3D r = rotate3D(x, y, z).add(t1);
		Geometry3DFrom3D t2 = translate3D(px, py, pz).add(r);
		return t2;
	}
	default Geometry3DFrom3D rotate3DXAroundPoint(Angle a, double py, double pz, Geometry3D geometry)
	{
		return rotate3DAroundPoint(a, zeroAngle(), zeroAngle(), 0, py, pz, geometry);
	}
	default Geometry3DFrom3D rotate3DYAroundPoint(Angle a, double px, double pz, Geometry3D geometry)
	{
		return rotate3DAroundPoint(zeroAngle(), a, zeroAngle(), px, 0, pz, geometry);
	}
	default Geometry3DFrom3D rotate3DZAroundPoint(Angle a, double px, double py, Geometry3D geometry)
	{
		return rotate3DAroundPoint(zeroAngle(), zeroAngle(), a, px, py, 0, geometry);
	}
}
