package org.abstractica.javacsg.clickertest;

import org.abstractica.javacsg.*;
import org.abstractica.javacsg.impl.JavaCSGImpl;
import org.abstractica.javacsg.impl.javaopenscad.JavaCSGBaseOpenSCADImpl;

import java.util.ArrayList;
import java.util.List;

public class Clicker2
{
	public static void main(String[] args)
	{
		//Tolerances
		double dx = 0.1;
		double dy = 0.2;
		//Sizes
		double unitHeight = 6.0;
		double clickerWidth = 8.0;
		double clickerCutoutWidth = 5.0;
		double barbSize = 1.0;
		double slitWidth = 2.0;
		double slitHeight = 8.0;
		//Unit size (minimum is 2)
		int units = 6;

		//Calculated values
		double height = unitHeight * units;
		double width = 0.5 * clickerWidth;
		double bottom = -0.5*height;
		double bottomCenter = bottom + 0.5*unitHeight;
		double top = 0.5*height;
		double topCenter = top - 0.5*unitHeight;

		JavaCSGBase jcsgBase = new JavaCSGBaseOpenSCADImpl(true);
		JavaCSG jcsg = new JavaCSGImpl(jcsgBase);
		List<Vector2D> vertices = new ArrayList<>();
		//Bottom part
		vertices.add(jcsg.vector2D(0, bottom+dy));
		vertices.add(jcsg.vector2D(width-dx, bottom+dy));
		vertices.add(jcsg.vector2D(width-dx, bottomCenter-1.5*barbSize));
		vertices.add(jcsg.vector2D(width-barbSize-dx, bottomCenter-0.5*barbSize));
		//Top part
		vertices.add(jcsg.vector2D(width-barbSize-dx, topCenter+0.5*barbSize));
		vertices.add(jcsg.vector2D(width-dx, topCenter+1.5*barbSize));
		vertices.add(jcsg.vector2D(width-barbSize-dx, top-dy));
		vertices.add(jcsg.vector2D(0, top-dy));

		Geometry2D profile = jcsg.polygon2D(vertices);
		Geometry3D extruded = jcsg.rotateExtrude(jcsg.rotations(1), 64, profile);
		Geometry3D boxCutout = jcsg.boxCorners3D
				(
						-0.5*slitWidth,
						-0.5*clickerWidth-1.0,
						top-slitHeight,
						0.5*slitWidth,
						0.5*clickerWidth+1.0,
						top+1.0
				);
		Geometry3D result = jcsg.difference3D(extruded, boxCutout);
		Geometry3D constrainBox = jcsg.boxCorners3D
				(
						-0.5*clickerWidth-1.0,
						-0.5*clickerCutoutWidth,
						bottom-1.0,
						0.5*clickerWidth+1.0,
						0.5*clickerCutoutWidth,
						top+1.0
				);
		result = jcsg.intersection3D(result, constrainBox);
		result = jcsg.rotate3DX(jcsg.degrees(-90)).transform(result);
		jcsg.view(jcsg.cache(result), 2);
	}
}
