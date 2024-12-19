# JavaCSG
Java Constructive Solid Geometry. 

**JavaCSG** is a Java framework for constructive solid geometry (CSG), geometric modeling, and manipulation of 2D and 3D shapes. It provides an extensive set of immutable operations and data types, including vectors, angles, transformations, and boolean operations on geometries. This makes JavaCSG suitable for computational geometry tasks, CAD/CAM applications, and procedural shape generation.
JavaCSG uses OpenSCAD as CSG-engine though JavaOpenSCAD.

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
  Load and save geometries in STL format, enabling interoperability with external modeling software and 3D printers.

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
        <version>0.6.0</version>
    </dependency>
</dependencies>
```

**Gradle Example:**

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.abstractica-org:JavaCSG:0.6.0'
}
```

