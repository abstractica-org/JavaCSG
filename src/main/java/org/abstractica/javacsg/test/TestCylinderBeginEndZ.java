package org.abstractica.javacsg.test;

import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.impl.JavaCSGImpl;
import org.abstractica.javaopenscad.intf.Geometry3D;

import java.io.IOException;

public class TestCylinderBeginEndZ
{
	public static void main(String[] args) throws IOException
	{
		JavaCSG csg = new JavaCSGImpl("C:/Abstractica/Libraries/Java/JavaCSG/OpenSCAD/Modules", true);
		Geometry3D cylinder = csg.cylinder3D(10, 5, 20, 1024);
		csg.generateOpenSCADFile("C:/Abstractica/Libraries/Java/JavaCSG/OpenSCAD/output.scad", cylinder);
	}
}
