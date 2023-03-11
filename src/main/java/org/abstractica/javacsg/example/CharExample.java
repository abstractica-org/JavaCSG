package org.abstractica.javacsg.example;

import org.abstractica.javacsg.Geometry2D;
import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGFactory;


public class CharExample
{
	public static void main(String[] args)
	{
		JavaCSG jcsg = JavaCSGFactory.createDefault();
		Geometry2D ch = jcsg.char2D('0', 5.0,10,64);
		jcsg.view(ch);
		System.out.println(ch.getMin());
		System.out.println(ch.getMax());
	}
}
