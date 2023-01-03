package org.abstractica.javacsg.example;

import org.abstractica.javacsg.Geometry2D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.impl.javaopenscad.JavaCSGOpenSCADImpl;

public class TextExample
{
	public static void main(String[] args)
	{
		JavaCSG jcsg = new JavaCSGOpenSCADImpl();
		Geometry2D text = jcsg.text2D("Hello world!", 100, 1);
		jcsg.view(text);
	}
}
