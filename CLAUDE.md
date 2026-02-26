# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**JavaCSG** is a Java framework for constructive solid geometry (CSG), geometric modeling, and manipulation of 2D and 3D shapes. It provides immutable operations, vectors, angles, transformations, and boolean operations on geometries, using OpenSCAD as its CSG engine.

**Key Requirement:** OpenSCAD (development snapshot with manifold engine) must be installed and available on the system path for the library to function.

## Build Commands

### Maven Build
```bash
mvn clean compile          # Compile the source code
mvn clean package          # Build JAR and attach sources/Javadoc
mvn clean install          # Install to local Maven repository
mvn clean verify           # Run full build with tests (if any)
```

### Generate Javadoc
```bash
mvn javadoc:javadoc       # Generate Javadoc (excludes impl packages)
```

The build configuration automatically:
- Attaches source JARs via `maven-source-plugin`
- Generates Javadoc JARs via `maven-javadoc-plugin`
- Excludes implementation packages from public Javadoc

### Key Build Configuration
- **Target Java Version:** Java 22 (both source and target)
- **Encoding:** UTF-8
- **Source:** `src/main/java/`
- **Target Artifact:** `target/JavaCSG-1.6.0.jar`

## High-Level Architecture

### Layered Architecture (API → Implementation → External Engine)

```
┌─────────────────────────────────────────────────┐
│  Public API Layer (org.abstractica.javacsg)     │
│  - Interfaces: JavaCSG, Geometry2D/3D, etc.     │
│  - Value types: Vector2D/3D, Angle, Color       │
│  - Factory: JavaCSGFactory                      │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│  Implementation Layer (org.abstractica.javacsg  │
│  .impl & .impl.baseimpl)                        │
│  - JavaCSGImpl (delegates to base)               │
│  - Value type implementations                   │
│  - JavaCSGBase interface (strategy pattern)     │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│  Adapter Layer (.impl.baseimpl.javaopenscad)   │
│  - JavaCSGBaseOpenSCADImpl                       │
│  - Inner classes: Geometry2DImpl, Geometry3DImpl,│
│    13 Transform* classes                        │
│  - Adapts JavaOpenSCAD library to JavaCSG API   │
└─────────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────────┐
│  External: JavaOpenSCAD + OpenSCAD Engine       │
│  - Calls OpenSCAD executable                    │
│  - Returns computed geometries                  │
└─────────────────────────────────────────────────┘
```

### Package Organization

| Package | Purpose |
|---------|---------|
| `org.abstractica.javacsg` | Public interfaces for all API types (Geometry2D/3D, Transform2D/3D, Vector2D/3D, etc.) |
| `org.abstractica.javacsg.impl` | Value type implementations (Vector2DImpl, AngleImpl, ColorImpl, etc.) |
| `org.abstractica.javacsg.impl.baseimpl` | Base interface and abstract behavior (JavaCSGBase) |
| `org.abstractica.javacsg.impl.baseimpl.javaopenscad` | OpenSCAD adapter implementation (JavaCSGBaseOpenSCADImpl) |
| `org.abstractica.javacsg.examples` | Example implementations and tests |

### Key Architectural Patterns

1. **Factory Pattern**: `JavaCSGFactory.createDefault()` and `JavaCSGFactory.createCached(dir)` hide implementation details
2. **Adapter Pattern**: JavaCSGBaseOpenSCADImpl adapts JavaOpenSCAD to JavaCSG API
3. **Strategy Pattern**: JavaCSGBase allows different CSG engine implementations (currently OpenSCAD-based)
4. **Facade Pattern**: JavaCSG interface provides comprehensive facade with 1520+ lines of operations
5. **Immutability**: All operations return new instances, never modify inputs
6. **Value Object Pattern**: Vector2D, Vector3D, Angle, Color are immutable value types
7. **Lazy Initialization**: Geometry bounds (min/max) cached after first access

### 2D and 3D Geometry System

**Parallel Structure:**
- Both 2D and 3D follow identical patterns for operations (union, intersection, difference, transforms)
- 2D operations: `union2D()`, `rotate2D()`, `translate2D()`, etc.
- 3D operations: `union3D()`, `rotate3D()`, `translate3D()`, etc.

**Bridge Operations (2D ↔ 3D):**
- `linearExtrude()` / `rotateExtrude()`: Convert 2D geometry → 3D
- `project()`: Convert 3D geometry → 2D (projection)

**Specialized Types:**
- **Angle**: Supports multiple units (degrees, radians, rotations) with automatic conversion
- **Polar2D**: Polar coordinates for circular patterns (radius + angle)
- **Color**: RGBA in floating-point range [0, 1] (only for 3D rendering)

### Data Flow Example

```java
JavaCSG csg = JavaCSGFactory.createDefault();
  // Returns JavaCSGImpl wrapping JavaCSGBaseOpenSCADImpl

Geometry3D box = csg.box3D(10, 10, 10, true);
  // JavaCSGImpl → base.box3D() → JavaCSGBaseOpenSCADImpl
  // Creates Geometry3DImpl wrapping OpenSCADGeometry3D

Transform3D rotate = csg.rotate3DZ(csg.degrees(45));
  // csg.degrees(45) creates AngleImpl
  // Creates Transform3DRotateZ inner class

Geometry3D rotated = rotate.transform(box);
  // Inner class calls OpenSCAD rotation
  // Returns new Geometry3DImpl with result

Geometry3D result = csg.difference3D(box, rotated);
  // Extracts underlying OpenSCAD objects
  // Performs CSG operation via OpenSCAD
  // Wraps result in new Geometry3DImpl
```

### Immutability Guarantee

All operations guarantee immutability:
- **No setters** in public interfaces
- **All methods return new instances** (never modify input)
- **Private implementations** prevent access to mutable internals
- **Functional programming style**: compose operations without side effects

```java
Geometry3D original = csg.box3D(10, 10, 10, true);
Transform3D t = csg.translate3D(5, 5, 5);
Geometry3D moved = t.transform(original);
// 'original' is completely unchanged
// 'moved' is a brand new Geometry3D instance
```

## Key Classes and Interfaces

### Entry Points
- **JavaCSGFactory**: Factory for creating JavaCSG instances (use `createDefault()` for standard usage)
- **JavaCSG**: Main API interface with 1520+ lines defining all operations

### Public API Interfaces (Never Implement Directly)
- **Geometry**: Base interface for all geometries (minimal API)
- **Geometry2D / Geometry3D**: Extend Geometry, add bounds queries (getMin/getMax)
- **Transform2D / Transform3D**: Transformation objects that can be applied to geometries
- **Vector2D / Vector3D**: Immutable coordinate vectors
- **Angle**: Rotation with automatic unit conversion (degrees, radians, rotations)
- **Polar2D**: Polar coordinates (radius + angle)
- **Color**: RGBA color (floats in [0, 1])

### Implementation Classes (Hidden from Users)
- **JavaCSGImpl**: Implements JavaCSG (delegates to base)
- **Vector2DImpl / Vector3DImpl**: Immutable vector holders
- **AngleImpl**: Stores as rotations internally, converts on demand
- **ColorImpl**: Stores RGBA as doubles
- **Polar2DImpl**: Stores radius and Angle
- **JavaCSGBaseOpenSCADImpl**: Core OpenSCAD adapter with 13 inner Transform classes and 2 inner Geometry classes

## Dependencies

### Primary
- **JavaOpenSCAD** (v0.6.0): Adapter for OpenSCAD functionality
  - Accessed via `com.github.abstractica-org:JavaOpenSCAD:v0.6.0` from JitPack

### Build Plugins
- **maven-source-plugin** (3.2.1): Attaches source JAR
- **maven-javadoc-plugin** (3.5.0): Generates Javadoc (excludes impl packages)

### External Requirement
- **OpenSCAD**: Must be installed and available on system PATH
- **Note**: Must use OpenSCAD development snapshot with manifold engine enabled

## Common Development Workflows

### Adding a New 3D Primitive Shape
1. Add method signature to `JavaCSG` interface (e.g., `Geometry3D myShape3D(...)`)
2. Implement in `JavaCSGImpl` (delegates to `base.myShape3D(...)`)
3. Add implementation in `JavaCSGBaseOpenSCADImpl` (creates and returns `Geometry3DImpl`)
4. The `Geometry3DImpl` wraps the result from JavaOpenSCAD
5. Test with an example in `org.abstractica.javacsg.examples`

### Adding a New Transformation
1. Add method signature to `JavaCSG` interface (e.g., `Transform3D myTransform(...)`)
2. Create new inner class in `JavaCSGBaseOpenSCADImpl` extending appropriate base (follows naming: `Transform2DMyTransform` or `Transform3DMyTransform`)
3. Inner class wraps OpenSCAD transform and implements `transform(Geometry)` method
4. Return from `JavaCSGImpl` delegating to base implementation
5. Test by composing with existing geometries

### Running Examples
```bash
# Compile and run an example (from project root)
mvn compile exec:java -Dexec.mainClass="org.abstractica.javacsg.examples.BoxTest"
mvn compile exec:java -Dexec.mainClass="org.abstractica.javacsg.examples.SphereTest"
```

### Testing Changes
- No automated test suite exists in the repository
- Primary verification: run examples and visually inspect results
- Examples generate OpenSCAD files in `OpenSCAD/` directory for inspection
- Check output files in `STL/` directory for correctness

## Javadoc and Documentation

- **Comprehensive Javadoc**: Available at https://abstractica-org.github.io/JavaCSG
- **Excluded from Javadoc**: Implementation packages (`org.abstractica.javacsg.impl.*`)
- **Documentation Pattern**: Public interfaces are fully documented; implementations are not (intentionally hidden)
- **Building Javadoc locally**: `mvn javadoc:javadoc` generates to `target/site/apidocs/`

## Important Architectural Decisions

1. **Public API as Interfaces**: All user-facing types are interfaces. Users cannot instantiate implementations directly, ensuring API stability and consistency.

2. **Single Factory Method**: `JavaCSGFactory.createDefault()` is the standard entry point. Custom cached instances available via `createCached(path)`.

3. **Lazy Bounds Calculation**: Geometry min/max values are computed on first access and cached (avoiding repeated expensive calculations).

4. **Adapter to JavaOpenSCAD**: The library doesn't implement CSG directly; it adapts the JavaOpenSCAD library, which calls the OpenSCAD executable.

5. **No Direct OpenSCAD Exposure**: Users never interact with OpenSCAD types directly. The adapter layer keeps this detail hidden.

6. **Parallel 2D/3D APIs**: Both systems are independently functional with bridge operations for conversion.

7. **Explicit Geometry Types**: Methods distinguish 2D/3D explicitly (e.g., `union2D()` vs `union3D()`) rather than using overloading, improving discoverability and type safety.

## Common Gotchas and Tips

1. **Must Use Factory**: Always use `JavaCSGFactory.createDefault()`. Direct instantiation is not possible.

2. **Angles Are Unit-Agnostic**: Create angles with `degrees()`, `radians()`, or `rotations()`. They're equivalent internally.

3. **Geometry Bounds Are Cached**: First call to `getMin()` or `getMax()` is expensive; subsequent calls are instant (cached).

4. **Transform Application Creates New Instance**: Transformations don't modify the original geometry; always capture the result: `geometry = transform.transform(geometry)`.

5. **2D Operations Return 2D**: Even if your 2D shapes look 3D visually, the return type is `Geometry2D`. Use extrusion to create true 3D.

6. **Color Is 3D-Only**: Colors only apply to 3D geometries. 2D geometries are rendered without color information.

7. **View Files Generate Incrementally**: `view()` generates `OpenSCAD/view0.scad`, `view1.scad`, etc. Each call creates a new file.

8. **STL/3MF Requires Compilation**: Saving to STL/3MF formats compiles the full geometry via OpenSCAD, which may be slow for complex shapes.
