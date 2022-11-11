package org.abstractica.javacsg.test;

import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.impl.JavaCSGImpl;
import org.abstractica.javaopenscad.intf.Geometry3D;

import java.io.IOException;

public class TestBox
{
	public static void main(String[] args) throws IOException
	{
		JavaCSG csg = new JavaCSGImpl(true);
		Geometry3D box = csg.box3D(10, 20, 30, false);
		csg.generateOpenSCADFile("C:/Abstractica/Libraries/Java/JavaCSG/OpenSCAD/output.scad", box);
	}
}
