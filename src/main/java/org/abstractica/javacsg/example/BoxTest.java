package org.abstractica.javacsg.example;

import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGBase;
import org.abstractica.javacsg.Transform3D;
import org.abstractica.javacsg.impl.JavaCSGImpl;
import org.abstractica.javacsg.impl.javaopenscad.JavaCSGBaseOpenSCADImpl;

public class BoxTest
{
	public static void main(String[] args)
	{
		JavaCSGBase base = new JavaCSGBaseOpenSCADImpl(true);
		JavaCSG csg = new JavaCSGImpl(base);


		Geometry3D box = csg.box3D(10, 20, 30, true);
		Transform3D rotate = csg.rotate3DY(csg.degrees(45));
		box = rotate.transform(box);
		Geometry3D cylinder = csg.cylinder3D(10, 40, 32, true);
		Geometry3D diff = csg.difference3D(box, cylinder);


		csg.view(csg.cache(diff));
	}
}
