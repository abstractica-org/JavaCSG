package org.abstractica.javacsg.intf.csg2D;


import org.abstractica.javaopenscad.JavaOpenSCAD;
import org.abstractica.javaopenscad.intf.Geometry2D;
import org.abstractica.javaopenscad.intf.Path;
import org.abstractica.javaopenscad.intf.Vector2D;

public interface Polygon2DBase extends JavaOpenSCAD
{
	default Geometry2D polygon2DGeometry(Iterable<Vector2D> vertices, int convexity)
	{
		return polygon2DGeometry(polygon2D(vertices, convexity));
	}
	default Geometry2D polygon2DGeometry(Iterable<Vector2D> vertices, Iterable<Path> paths, int convexity)
	{
		return polygon2DGeometry(polygon2D(vertices, convexity));
	}
}
