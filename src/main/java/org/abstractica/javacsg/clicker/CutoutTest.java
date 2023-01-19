package org.abstractica.javacsg.clicker;

import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGBase;
import org.abstractica.javacsg.impl.JavaCSGImpl;
import org.abstractica.javacsg.impl.javaopenscad.JavaCSGBaseOpenSCADImpl;

public class CutoutTest
{
	public static void main(String[] args)
	{
		JavaCSGBase jcsgBase = new JavaCSGBaseOpenSCADImpl(true);
		JavaCSG jcsg = new JavaCSGImpl(jcsgBase);
		Clicker clicker = new Clicker(jcsg);
		Geometry3D box = jcsg.box3D(12, 12, 6, true);
		Geometry3D clickerCutout = clicker.createClickerCutout();
		jcsg.view(clickerCutout, 2);
		Geometry3D diff = jcsg.difference3D(box, clickerCutout);
		jcsg.view(diff);
	}

}
