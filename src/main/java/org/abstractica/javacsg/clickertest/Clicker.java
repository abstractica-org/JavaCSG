package org.abstractica.javacsg.clickertest;

import org.abstractica.javacsg.Geometry2D;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSGBase;
import org.abstractica.javacsg.Vector2D;
import org.abstractica.javacsg.impl.javaopenscad.JavaCSGBaseOpenSCADImpl;

import java.util.ArrayList;
import java.util.List;

public class Clicker
{
	public static void main(String[] args)
	{
		JavaCSGBase jcsg = new JavaCSGBaseOpenSCADImpl(true);
		List<Vector2D> vertices = new ArrayList<>();
		vertices.add(jcsg.vector2D(0, 0));
		vertices.add(jcsg.vector2D(4, 0));
		vertices.add(jcsg.vector2D(4, 1.5));
		vertices.add(jcsg.vector2D(3, 2.5));
		vertices.add(jcsg.vector2D(3, 9.5));
		vertices.add(jcsg.vector2D(4.4, 10.1));
		vertices.add(jcsg.vector2D(2.6, 12));
		vertices.add(jcsg.vector2D(1, 12));
		vertices.add(jcsg.vector2D(1, 2.5));
		vertices.add(jcsg.vector2D(0, 2.5));
		Geometry2D profile = jcsg.cache(jcsg.polygon2D(vertices));
		Geometry2D mirrored = jcsg.mirror2D(1, 0).transform(profile);
		Geometry2D union = jcsg.union2D(profile, mirrored);
		Geometry2D adjusted = jcsg.offsetRound2D(-0.1, 16, union);
		Geometry3D extruded = jcsg.linearExtrude(4,0, 1,1,true, adjusted);
		Geometry3D result = jcsg.cache(extruded);
		jcsg.view(result, 1);
	}
}
