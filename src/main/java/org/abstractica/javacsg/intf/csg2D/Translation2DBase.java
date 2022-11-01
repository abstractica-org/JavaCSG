package org.abstractica.javacsg.intf.csg2D;

import org.abstractica.javaopenscad.intf.Geometry2DFrom2D;

public interface Translation2DBase extends Polar2DBase
{
	//Translation helpers
	default Geometry2DFrom2D translate2DX(double x) {return translate2D(x, 0);}
	default Geometry2DFrom2D translate2DY(double y) {return translate2D(0, y);}
}
