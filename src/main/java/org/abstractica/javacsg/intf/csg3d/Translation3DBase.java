package org.abstractica.javacsg.intf.csg3d;

import org.abstractica.javaopenscad.intf.Geometry3DFrom3D;

public interface Translation3DBase extends Vector3DBase
{
	default Geometry3DFrom3D translate3DX(double x) {return translate3D(x, 0, 0);}
	default Geometry3DFrom3D translate3DY(double y) {return translate3D(0, y, 0);}
	default Geometry3DFrom3D translate3DZ(double z) {return translate3D(0, 0, z);}
}
