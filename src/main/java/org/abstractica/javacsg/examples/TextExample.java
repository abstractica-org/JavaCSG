package org.abstractica.javacsg.examples;

import org.abstractica.javacsg.Geometry2D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

public class TextExample
{
	public static void main(String[] args)
	{
		JavaCSG jcsg = JavaCSGFactory.createDefault();
		Geometry2D text = jcsg.text2D("ÆØÅæøåHello world!", 5, 1);
		jcsg.view(text);
	}
}
