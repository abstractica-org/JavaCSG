package org.abstractica.javacsg.example;

import org.abstractica.javacsg.Geometry2D;
import org.abstractica.javacsg.JavaCSGBase;
import org.abstractica.javacsg.impl.javaopenscad.JavaCSGBaseOpenSCADImpl;

public class CharExample
{
	public static void main(String[] args)
	{
		JavaCSGBase jcsg = new JavaCSGBaseOpenSCADImpl();
		Geometry2D ch = jcsg.char2D('f', 10.0,4);
		jcsg.view(ch);
	}
}
