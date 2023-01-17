package org.abstractica.javacsg.clickertest;

import org.abstractica.javacsg.Geometry2D;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSGBase;
import org.abstractica.javacsg.Vector2D;
import org.abstractica.javacsg.impl.javaopenscad.JavaCSGBaseOpenSCADImpl;

import java.util.ArrayList;
import java.util.List;

public class CLickerCutout
{
	public static void main(String[] args)
	{
		JavaCSGBase jcsg = new JavaCSGBaseOpenSCADImpl();
		List<Vector2D> vertices = new ArrayList<>();
		vertices.add(jcsg.vector2D(0, -0.1));
		vertices.add(jcsg.vector2D(4, -0.1));
		vertices.add(jcsg.vector2D(4, 1.5));
		vertices.add(jcsg.vector2D(3, 2.5));
		vertices.add(jcsg.vector2D(3, 3.5));
		vertices.add(jcsg.vector2D(4, 4.5));
		vertices.add(jcsg.vector2D(4, 6.1));
		vertices.add(jcsg.vector2D(0, 6.1));
		Geometry2D profile = jcsg.cache(jcsg.polygon2D(vertices));
		Geometry2D mirrored = jcsg.mirror2D(1, 0).transform(profile);
		Geometry2D union = jcsg.union2D(profile, mirrored);
		Geometry2D adjusted = jcsg.offsetRound2D(0.1, 16, union);
		Geometry3D extruded = jcsg.linearExtrude(4.2,0, 1,1, adjusted);
		Geometry3D cutout = jcsg.rotate3DX(jcsg.degrees(90)).transform(extruded);
		vertices.clear();
		vertices.add(jcsg.vector2D(-6, -6));
		vertices.add(jcsg.vector2D(6, -6));
		vertices.add(jcsg.vector2D(6, 6));
		vertices.add(jcsg.vector2D(-6, 6));
		Geometry2D square = jcsg.cache(jcsg.polygon2D(vertices));
		Geometry3D brick = jcsg.linearExtrude(6,0, 1,1, square);
		brick = jcsg.translate3D(0,0,3).transform(brick);
		Geometry3D result = jcsg.difference3D(brick, cutout);
		jcsg.view(result);
	}
}
