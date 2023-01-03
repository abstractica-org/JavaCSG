package org.abstractica.javacsg.example;

import org.abstractica.javacsg.Geometry2D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.impl.javaopenscad.JavaCSGOpenSCADImpl;

public class CharExample
{
	public static void main(String[] args)
	{
		JavaCSG jcsg = new JavaCSGOpenSCADImpl();
		Geometry2D ch = jcsg.char2D('f', 10.0,4);
		jcsg.view(ch);
	}
}
