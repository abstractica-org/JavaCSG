package org.abstractica.javacsg.examples;

import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

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
