package org.abstractica.javacsg;

import org.abstractica.javacsg.impl.JavaCSGImpl;
import org.abstractica.javacsg.impl.javaopenscad.JavaCSGBaseOpenSCADImpl;

public class JavaCSGFactory
{
	public static JavaCSG createDefault()
	{
		JavaCSGBase base = new JavaCSGBaseOpenSCADImpl(true);
		JavaCSG javaCSG = new JavaCSGImpl(base);
		return javaCSG;
	}

	public static JavaCSG createNoCaching()
	{
		JavaCSGBase base = new JavaCSGBaseOpenSCADImpl(false);
		JavaCSG javaCSG = new JavaCSGImpl(base);
		return javaCSG;
	}
}
