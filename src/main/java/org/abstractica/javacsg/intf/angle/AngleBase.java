package org.abstractica.javacsg.intf.angle;

import org.abstractica.javacsg.intf.angle.Angle;
import org.abstractica.javaopenscad.JavaOpenSCAD;

public interface AngleBase extends JavaOpenSCAD
{
	//Angle creation
	Angle rotations(double rot);
	Angle degrees(double deg);
	Angle radians(double rad);
	default Angle zeroAngle() { return rotations(0);}

	//Trigonometry
	default double sin(Angle angle) {return Math.sin(angle.asRadians());}
	default double cos(Angle angle) {return Math.cos(angle.asRadians());}
	default double tan(Angle angle) {return Math.tan(angle.asRadians());}
	default Angle asin(double d) {return radians(Math.asin(d));}
	default Angle acos(double d) {return radians(Math.acos(d));}
	default Angle atan(double d) {return radians(Math.atan(d));}
	default Angle atan2(double y, double x) {return radians(Math.atan2(y, x));}

}
