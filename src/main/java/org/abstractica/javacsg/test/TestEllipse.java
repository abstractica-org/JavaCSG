package org.abstractica.javacsg.test;

import org.abstractica.javaopenscad.impl.core.identifier.AllStrings;
import org.abstractica.javaopenscad.intf.Geometry2D;
import org.abstractica.javacsg.impl.JavaCSGImpl;
import org.abstractica.javacsg.intf.csg2D.CSG2DBase;

import java.io.IOException;

public class TestEllipse
{
	public static void main(String[] args) throws IOException
	{
		CSG2DBase gb = new JavaCSGImpl();
		Geometry2D ellipse = gb.ellipse2D(gb.vector2D(10,20), gb.vector2D(0, -10), 128);
		//Geometry2D ellipse = gb.ellipse(0, 10, -10, 20, 128);
		gb.generateOpenSCADFile("OpenSCAD/output.scad", ellipse);
		System.out.println(AllStrings.listAllStrings());
	}
}
