package org.abstractica.javacsg.examples;

import org.abstractica.javacsg.Geometry2D;
import org.abstractica.javacsg.Geometry3D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;

import java.io.IOException;

public class TextExample
{
	public static void main(String[] args) throws IOException
    {
		JavaCSG jcsg = JavaCSGFactory.createDefault();
		Geometry2D text2D = jcsg.text2D("ÆØÅæøåHello world!", 5, 1);
        Geometry3D text3D = jcsg.linearExtrude(2, false, text2D);
		jcsg.view(text3D);
        jcsg.saveSTL("./STL/text.stl", text3D);
	}
}
