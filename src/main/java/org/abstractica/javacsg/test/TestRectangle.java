package org.abstractica.javacsg.test;

import org.abstractica.javaopenscad.impl.operationsimpl.identifier.AllStrings;
import org.abstractica.javaopenscad.intf.Geometry2D;
import org.abstractica.javacsg.impl.CSGImpl;
import org.abstractica.javacsg.intf.csg2D.CSG2DBase;

import java.io.IOException;

public class TestRectangle
{
	public static void main(String[] args) throws IOException
	{
		CSG2DBase gb = new CSGImpl();
		Geometry2D circle = gb.circle(10, 128);
		gb.generateOpenSCADFile("OpenSCAD/output.scad", circle);
		System.out.println(AllStrings.listAllStrings());
	}
}
