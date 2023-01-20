package org.abstractica.javacsg.clicker;

import org.abstractica.javacsg.JavaCSG;
import org.abstractica.javacsg.JavaCSGBase;
import org.abstractica.javacsg.impl.JavaCSGImpl;
import org.abstractica.javacsg.impl.javaopenscad.JavaCSGBaseOpenSCADImpl;

public class ClickerTest
{
	public static void main(String[] args)
	{
		boolean[] ridges = {true, false, true};
		JavaCSGBase jcsgBase = new JavaCSGBaseOpenSCADImpl(true);
		JavaCSG jcsg = new JavaCSGImpl(jcsgBase);
		Clicker clicker = new Clicker(jcsg);
		jcsg.view(clicker.createClicker(ridges));
	}

}
