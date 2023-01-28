package org.abstractica.javacsg.example;

import org.abstractica.javacsg.*;

public class TorusTest
{
	public static void main(String[] args)
	{
		JavaCSG csg = JavaCSGFactory.createDefault();
		Geometry3D torus = csg.torusSegment3D
				(
					10,
					40,
					csg.degrees(43),
					csg.degrees(132),
					16,
					64,
					true
				);
		csg.view(torus);
	}
}
