# JavaCSG
Java Constructive Solid Geometry. 

**JavaCSG** is a Java framework for constructive solid geometry (CSG), geometric modeling, and manipulation of 2D and 3D shapes. It provides an extensive set of immutable operations and data types, including vectors, angles, transformations, and boolean operations on geometries. This makes JavaCSG suitable for computational geometry tasks, CAD/CAM applications, and procedural shape generation.

## OpenSCAD
JavaCSG uses OpenSCAD as its CSG-engine though [JavaOpenSCAD](https://github.com/abstractica-org/JavaOpenSCAD). For JavaCSG to work, OpenSCAD must be installed and available on the system path. JavaCSG uses the [manifold](https://github.com/elalish/manifold) engine in OpenSCAD which (as of when this was written) is only available in the development snapshot of OpenSCAD:
[OpenSCAD development snapshot](https://openscad.org/downloads.html#snapshots)

**Make sure to add OpenSCAD to the system path after installation and check that OpenSCAD can be called from a command prompt (windows) or a terminal (linux / mac)**

## Features

- **Immutable Data Structures:**  
  All operations return new, immutable objects without altering the input data, ensuring a clean and functional programming style that’s easy to reason about.

- **2D and 3D Support:**  
  Work with both 2D and 3D geometries, convert between them, and apply transformations, extrusions, and projections.

- **Boolean Operations & Modeling Tools:**  
  Perform union, intersection, difference, hull, and Minkowski operations to create complex shapes from simpler primitives.

- **Transformations:**  
  Apply translations, rotations, scaling, mirroring, and more through dedicated 2D and 3D transformation interfaces.

- **Geometric Primitives & Constructive Techniques:**  
  Create circles, rectangles, polygons, spheres, boxes, cylinders, cones, tori, and more. Extrude 2D geometries into 3D, slice shapes along axes, and manipulate shapes with various geometric filters.

- **Angles & Vectors:**  
  Use unified interfaces for angles (rotations, degrees, radians) and vectors (2D and 3D) for precise geometric calculations.

- **Integration with External Tools:**  
  Load and save geometries in STL and 3MF formats, enabling interoperability with external modeling software and 3D printers.

## Getting Started

Add JavaCSG to your project using Maven or Gradle.

**Maven Example:**

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.abstractica-org</groupId>
        <artifactId>JavaCSG</artifactId>
        <version>v1.0.0</version>
    </dependency>
</dependencies>
```

**Gradle Example:**

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.abstractica-org:JavaCSG:v1.0.0'
}
```

## Usage example
```java
import org.abstractica.javacsg.*;

import java.io.IOException;

public class Example {
    public static void main(String[] args) throws IOException
    {
        JavaCSG csg = JavaCSGFactory.createNoCaching();

        Geometry3D box = csg.box3D(10, 10, 10, true);
        Geometry3D sphere = csg.sphere3D(12, 64, true);

        Geometry3D intersection = csg.intersection3D(box, sphere);
        // The 'intersection' now represents the shape where the box and sphere overlap.

        csg.view(intersection);
        // This creates the file OpenSCAD/view0.scad that can be opened with OpenSCAD
        // OpenSCAD will automatically update the view when the file is updated

        csg.saveSTL("STL/example.stl", intersection);
        // This creates the file STL/example.stl
        // For this to work OpenSCAD must be installed and in the system path
    }
}
```

## Example project

An example project using JavaCSG can be found here: [Quarto3D](https://github.com/abstractica-org/Quarto3D)

Feel free to clone or fork the project and play around with it :-)

## Documentation

Extensive Javadoc documentation is available at:  
**[JavaCSG Javadoc](https://abstractica-org.github.io/JavaCSG)**

This site provides detailed information on all the interfaces and the factory class that makes up the API.

## Contributing

Contributions are welcome! If you have ideas for enhancements, bug fixes, or additional features, feel free to open an issue or create a pull request.
