package org.abstractica.javacsg.test;

import org.abstractica.javacsg.impl.JavaCSGImpl;
import org.abstractica.javacsg.intf.csg2D.CSG2DBase;
import org.abstractica.javaopenscad.impl.core.identifier.AllStrings;
import org.abstractica.javaopenscad.intf.Geometry2D;

import java.io.IOException;

public class TestCircleSegment
{
	public static void main(String[] args) throws IOException
	{
		CSG2DBase gb = new JavaCSGImpl(true);
		Geometry2D circleSegment = gb.circle2DSegment(
				10,
				gb.degrees(45),
				gb.degrees(135),
				16);
		gb.generateOpenSCADFile("OpenSCAD/output.scad", circleSegment);
		System.out.println(AllStrings.listAllStrings());
	}
}
