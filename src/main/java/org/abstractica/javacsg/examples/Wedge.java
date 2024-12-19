package org.abstractica.javacsg.examples;

import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

public class Wedge
{
	public static void main(String[] args)
	{
		JavaCSG csg = JavaCSGFactory.createDefault();
		Geometry3D wedge = csg.wedge3D(3.6, 40, 20, true);
		wedge = csg.rotate3DY(csg.degrees(-90)).transform(wedge);
		Geometry3D box = csg.box3D(20, 20, 3.6, false);
		box = csg.translate3DY(-10).transform(box);
		Geometry3D res = csg.union3D(wedge, box);
		csg.view(res);
	}
}