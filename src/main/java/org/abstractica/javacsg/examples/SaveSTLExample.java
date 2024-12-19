package org.abstractica.javacsg.examples;

import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

import java.io.IOException;

public class SaveSTLExample
{
	public static void main(String[] args) throws IOException
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
		csg.saveSTL("C:/tmp/Torus.stl", torus);
	}
}
