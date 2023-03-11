package org.abstractica.javacsg.example;

import org.abstractica.javacsg.*;


public class BoxTest
{
	public static void main(String[] args)
	{
		JavaCSG csg = JavaCSGFactory.createDefault();
		Geometry3D box = csg.box3D(10, 20, 30, true);
		Transform3D rotate = csg.rotate3DY(csg.degrees(45));
		box = rotate.transform(box);
		Geometry3D cylinder = csg.cylinder3D(10, 40, 32, true);
		Geometry3D diff = csg.difference3D(box, cylinder);
		csg.view(csg.cache(diff));

		System.out.println(diff.getMin());
		System.out.println(diff.getMax());
	}
}
