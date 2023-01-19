package org.abstractica.javacsg.example;

import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGBase;
import org.abstractica.javacsg.Transform3D;
import org.abstractica.javacsg.impl.JavaCSGImpl;
import org.abstractica.javacsg.impl.javaopenscad.JavaCSGBaseOpenSCADImpl;

public class TorusTest
{
	public static void main(String[] args)
	{
		JavaCSGBase base = new JavaCSGBaseOpenSCADImpl(true);
		JavaCSG csg = new JavaCSGImpl(base);


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
