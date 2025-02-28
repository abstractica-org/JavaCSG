package org.abstractica.javacsg.examples;

import org.abstractica.javacsg.*;

import java.io.IOException;

public class Example {
    public static void main(String[] args) throws IOException
    {
        JavaCSG csg = JavaCSGFactory.createDefault();

        Geometry3D box = csg.box3D(10, 10, 10, true);
        Geometry3D sphere = csg.sphere3D(12, 64, true);

        Geometry3D intersection = csg.intersection3D(box, sphere);
        // The 'intersection' now represents the shape where the box and sphere overlap.

        csg.view(intersection);
        // This creates the file OpenSCAD/view0.scad that can be opened with OpenSCAD
        // OpenSCAD will automatically update the view when the file is saved

        csg.saveSTL("STL/example.stl", intersection);
        // This creates the file STL/example.stl
        // For this to work OpenSCAD must be installed and in the system path
    }
}