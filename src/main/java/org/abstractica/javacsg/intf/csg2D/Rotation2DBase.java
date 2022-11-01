package org.abstractica.javacsg.intf.csg2D;

import org.abstractica.javaopenscad.intf.Geometry2DFrom2D;
import org.abstractica.javacsg.intf.Angle;

public interface Rotation2DBase extends Translation2DBase
{
	//Rotations using angles
	default Geometry2DFrom2D rotate2D(Angle angle) {return rotate2D(angle.asDegrees());}

}
