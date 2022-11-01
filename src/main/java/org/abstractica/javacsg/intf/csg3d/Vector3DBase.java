package org.abstractica.javacsg.intf.csg3d;

import org.abstractica.javacsg.intf.csg2D.CSG2DBase;

public interface Vector3DBase extends CSG2DBase
{
	Vector3D vector3D(double x, double y, double z);
	default double length(Vector3D v)
	{
		return Math.sqrt(v.x()*v.x() + v.y()*v.y() + + v.z()*v.z());
	}
	default Vector3D normalized(Vector3D v)
	{
		double l = length(v);
		return vector3D(v.x()/l, v.y()/l, v.z()/l);
	}
	default Vector3D add(Vector3D a, Vector3D b)
	{
		return vector3D(a.x()+b.x(), a.y()+b.y(), a.z()+b.z());
	}
	default Vector3D sub(Vector3D a, Vector3D b)
	{
		return vector3D(a.x()-b.x(), a.y()-b.y(), a.z()-b.z());
	}
	default Vector3D mul(Vector3D v, double d)
	{
		return vector3D(v.x()*d, v.y()*d, v.z()*d);
	}
	default Vector3D mul(double d, Vector3D v)
	{
		return mul(v, d);
	}
	default Vector3D fromTo(Vector3D from, Vector3D to)
	{
		return sub(to, from);
	}
}
