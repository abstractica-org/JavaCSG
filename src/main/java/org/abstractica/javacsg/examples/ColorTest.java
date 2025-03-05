package org.abstractica.javacsg.examples;

import org.abstractica.javacsg.Color;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

import java.io.IOException;

public class ColorTest
{
	public static void main(String[] args) throws IOException
    {
		JavaCSG csg = JavaCSGFactory.createDefault();
		Color blue = csg.color(0,0, 200);
		Color red = csg.color(200,0, 0);
		Geometry3D torus1 = csg.torusSegment3D
				(
					10,
					40,
					csg.degrees(43),
					csg.degrees(132),
					64,
					256,
					true
				);
		torus1 = csg.translate3DY(-20).transform(torus1);
		torus1 = csg.color3D(blue, torus1);
		Geometry3D torus2 = csg.torusSegment3D
				(
						10,
						40,
						csg.degrees(132),
						csg.degrees(221),
						64,
						256,
						true
				);
		torus2 = csg.translate3DX(20).transform(torus2);
		torus2 = csg.color3D(red, torus2);
		Geometry3D res = csg.union3D(torus1, torus2);
		res = csg.cache(res);
		csg.save3MF("OpenSCAD/colorTest.3mf", res);
		csg.saveSTL("OpenSCAD/colorTest.stl", res);
	}
}
