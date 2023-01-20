package org.abstractica.javacsg;

public interface Transform2D
{
	Transform2D inverse();
	Vector2D transformPoint(Vector2D vector);
	Vector2D transformDirection(Vector2D vector);
	Geometry2D transform(Geometry2D geometry);
}
