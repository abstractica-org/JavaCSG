package org.abstractica.javacsg.clicker;

import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGBase;
import org.abstractica.javacsg.impl.JavaCSGImpl;
import org.abstractica.javacsg.impl.javaopenscad.JavaCSGBaseOpenSCADImpl;

public class ClickerLock
{
	public static void main(String[] args)
	{
		JavaCSGBase jcsgBase = new JavaCSGBaseOpenSCADImpl(true);
		JavaCSG jcsg = new JavaCSGImpl(jcsgBase);
		Geometry3D lock = jcsg.box3D(5, 7.6, 1.8, true);
		jcsg.view(lock);
	}
}
