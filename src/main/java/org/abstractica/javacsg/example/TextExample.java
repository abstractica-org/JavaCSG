package org.abstractica.javacsg.example;

import org.abstractica.javacsg.Geometry2D;
import org.abstractica.javacsg.JavaCSGBase;
import org.abstractica.javacsg.impl.javaopenscad.JavaCSGBaseOpenSCADImpl;

public class TextExample
{
	public static void main(String[] args)
	{
		JavaCSGBase jcsg = new JavaCSGBaseOpenSCADImpl(true);
		Geometry2D text = jcsg.text2D("Hello world!", 100, 1);
		jcsg.view(text);
	}
}
