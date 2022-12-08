package org.abstractica.javacsg;

public interface Transform3D
{
	Transform3D inverse();
	Vector3D transformPoint(Vector3D vector);
	Vector3D transformDirection(Vector3D vector);
	Geometry3D transform(Geometry3D geometry);
}
