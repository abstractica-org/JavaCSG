package org.abstractica.javacsg.example;

import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

public class SphereTest
{
	public static void main(String[] args)
	{
		JavaCSG csg = JavaCSGFactory.createDefault();
		Geometry3D sphere = csg.sphere3D(10, 64, false);
		csg.view(sphere);
	}
}
