package org.abstractica.javacsg.intf.csg2D;


import org.abstractica.javaopenscad.intf.Geometry2D;
import org.abstractica.javaopenscad.intf.polygon.Path;
import org.abstractica.javaopenscad.intf.polygon.Vector2D;

public interface Polygon2DBase extends Rotation2DBase
{
	default Geometry2D polygon2DGeometry(Iterable<Vector2D> vertices)
	{
		return polygon2DGeometry(polygon2D(vertices));
	}
	default Geometry2D polygon2DGeometry(Iterable<Vector2D> vertices, Iterable<Path> paths)
	{
		return polygon2DGeometry(polygon2D(vertices, paths));
	}
}
