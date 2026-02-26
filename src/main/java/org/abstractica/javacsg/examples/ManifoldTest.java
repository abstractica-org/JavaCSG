package org.abstractica.javacsg.examples;

import manifold3d.Manifold;
import org.abstractica.javacsg.*;
import org.abstractica.javacsg.impl.baseimpl.manifold.JavaCSGBaseManifoldImpl;

import java.io.File;

public class ManifoldTest
{
	private static final double TOLERANCE = 0.01;
	private static int passed = 0;
	private static int failed = 0;

	public static void main(String[] args) throws Exception
	{
		JavaCSG csg = JavaCSGFactory.createManifold();

		System.out.println("=== Manifold Backend Tests ===\n");

		// 3D Primitive Tests
		testBoxVolume(csg);
		testSphereVolume(csg);
		testCylinderVolume(csg);

		// 3D Boolean Operation Tests
		testUnionNonOverlapping(csg);
		testDifferenceIdentical(csg);
		testIntersectionOverlapping(csg);

		// 3D Transform Tests
		testTranslation(csg);
		testRotationPreservesVolume(csg);
		testScale(csg);

		// 2D Operation Tests
		testRectangleBounds(csg);
		testCircleArea(csg);
		test2DUnion(csg);
		test2DDifference(csg);

		// Extrusion Tests
		testLinearExtrude(csg);
		testLinearExtrudeCentered(csg);

		// Bounding Box Tests
		testBoundingBox(csg);

		// STL Round-trip Test
		testSTLRoundTrip(csg);

		// Hull Test
		testHull3D(csg);

		// Text Rendering Tests
		testChar2D(csg);
		testChar2DWithHeight(csg);
		testText2D(csg);
		testChar2DSpace(csg);

		System.out.println("\n=== Results: " + passed + " passed, " + failed + " failed ===");
		if (failed > 0)
		{
			System.exit(1);
		}
	}

	// ========== 3D Primitive Tests ==========

	private static void testBoxVolume(JavaCSG csg)
	{
		Geometry3D box = csg.box3D(10, 20, 30, false);
		double volume = getVolume(box);
		double expected = 10 * 20 * 30;
		check("Box volume (10x20x30)", expected, volume, expected * 0.01);
	}

	private static void testSphereVolume(JavaCSG csg)
	{
		double diameter = 10;
		double radius = diameter / 2.0;
		Geometry3D sphere = csg.sphere3D(diameter, 64, true);
		double volume = getVolume(sphere);
		double expected = (4.0 / 3.0) * Math.PI * radius * radius * radius;
		// Sphere approximated by polygons, so allow larger tolerance
		check("Sphere volume (d=10, res=64)", expected, volume, expected * 0.05);
	}

	private static void testCylinderVolume(JavaCSG csg)
	{
		double diameter = 10;
		double height = 20;
		double radius = diameter / 2.0;
		Geometry3D cylinder = csg.cylinder3D(diameter, height, 64, false);
		double volume = getVolume(cylinder);
		double expected = Math.PI * radius * radius * height;
		check("Cylinder volume (d=10, h=20, res=64)", expected, volume, expected * 0.05);
	}

	// ========== 3D Boolean Operation Tests ==========

	private static void testUnionNonOverlapping(JavaCSG csg)
	{
		Geometry3D box1 = csg.box3D(10, 10, 10, false);
		Geometry3D box2 = csg.translate3D(20, 0, 0).transform(csg.box3D(10, 10, 10, false));
		Geometry3D unionResult = csg.union3D(box1, box2);
		double volume = getVolume(unionResult);
		double expected = 2000; // 1000 + 1000
		check("Union of non-overlapping boxes", expected, volume, expected * 0.01);
	}

	private static void testDifferenceIdentical(JavaCSG csg)
	{
		Geometry3D box1 = csg.box3D(10, 10, 10, true);
		Geometry3D box2 = csg.box3D(10, 10, 10, true);
		Geometry3D diff = csg.difference3D(box1, box2);
		double volume = getVolume(diff);
		check("Difference of identical boxes", 0.0, volume, 1.0);
	}

	private static void testIntersectionOverlapping(JavaCSG csg)
	{
		// Two 10x10x10 boxes overlapping by 5 units in X
		Geometry3D box1 = csg.box3D(10, 10, 10, false);
		Geometry3D box2 = csg.translate3D(5, 0, 0).transform(csg.box3D(10, 10, 10, false));
		Geometry3D inter = csg.intersection3D(box1, box2);
		double volume = getVolume(inter);
		double expected = 5 * 10 * 10; // 5-unit overlap in X
		check("Intersection of overlapping boxes", expected, volume, expected * 0.01);
	}

	// ========== 3D Transform Tests ==========

	private static void testTranslation(JavaCSG csg)
	{
		Geometry3D box = csg.box3D(10, 10, 10, true);
		Geometry3D translated = csg.translate3D(100, 200, 300).transform(box);
		Vector3D min = translated.getMin();
		Vector3D max = translated.getMax();
		check("Translation minX", 95.0, min.x(), 0.5);
		check("Translation minY", 195.0, min.y(), 0.5);
		check("Translation minZ", 295.0, min.z(), 0.5);
		check("Translation maxX", 105.0, max.x(), 0.5);
		check("Translation maxY", 205.0, max.y(), 0.5);
		check("Translation maxZ", 305.0, max.z(), 0.5);
	}

	private static void testRotationPreservesVolume(JavaCSG csg)
	{
		Geometry3D box = csg.box3D(10, 20, 30, true);
		double originalVolume = getVolume(box);
		Geometry3D rotated = csg.rotate3DZ(csg.degrees(45)).transform(box);
		double rotatedVolume = getVolume(rotated);
		check("Rotation preserves volume", originalVolume, rotatedVolume, originalVolume * 0.01);
	}

	private static void testScale(JavaCSG csg)
	{
		Geometry3D box = csg.box3D(10, 10, 10, true);
		Geometry3D scaled = csg.scale3D(2, 3, 4).transform(box);
		double volume = getVolume(scaled);
		double expected = 10 * 2 * 10 * 3 * 10 * 4; // 24000
		check("Scale 3D volume", expected, volume, expected * 0.01);
	}

	// ========== 2D Operation Tests ==========

	private static void testRectangleBounds(JavaCSG csg)
	{
		Geometry2D rect = csg.rectangle2D(20, 10);
		Vector2D min = rect.getMin();
		Vector2D max = rect.getMax();
		check("Rectangle minX", -10.0, min.x(), 0.5);
		check("Rectangle minY", -5.0, min.y(), 0.5);
		check("Rectangle maxX", 10.0, max.x(), 0.5);
		check("Rectangle maxY", 5.0, max.y(), 0.5);
	}

	private static void testCircleArea(JavaCSG csg)
	{
		double diameter = 20;
		double radius = diameter / 2.0;
		Geometry2D circle = csg.circle2D(diameter, 128);
		double area = getArea(circle);
		double expected = Math.PI * radius * radius;
		check("Circle area (d=20, res=128)", expected, area, expected * 0.02);
	}

	private static void test2DUnion(JavaCSG csg)
	{
		// Two non-overlapping rectangles
		Geometry2D rect1 = csg.rectangle2D(10, 10);
		Geometry2D rect2 = csg.translate2D(20, 0).transform(csg.rectangle2D(10, 10));
		Geometry2D unionResult = csg.union2D(rect1, rect2);
		double area = getArea(unionResult);
		double expected = 200; // 100 + 100
		check("2D union of non-overlapping rects", expected, area, expected * 0.01);
	}

	private static void test2DDifference(JavaCSG csg)
	{
		// Large rect minus small rect (centered)
		Geometry2D outer = csg.rectangle2D(20, 20);
		Geometry2D inner = csg.rectangle2D(10, 10);
		Geometry2D diff = csg.difference2D(outer, inner);
		double area = getArea(diff);
		double expected = 400 - 100; // 300
		check("2D difference (outer - inner)", expected, area, expected * 0.01);
	}

	// ========== Extrusion Tests ==========

	private static void testLinearExtrude(JavaCSG csg)
	{
		Geometry2D rect = csg.rectangle2D(10, 20);
		Geometry3D extruded = csg.linearExtrude(30, false, rect);
		double volume = getVolume(extruded);
		double expected = 10 * 20 * 30;
		check("Linear extrude rect to box", expected, volume, expected * 0.01);
	}

	private static void testLinearExtrudeCentered(JavaCSG csg)
	{
		Geometry2D rect = csg.rectangle2D(10, 10);
		Geometry3D extruded = csg.linearExtrude(20, true, rect);
		Vector3D min = extruded.getMin();
		Vector3D max = extruded.getMax();
		check("Centered extrude minZ", -10.0, min.z(), 0.5);
		check("Centered extrude maxZ", 10.0, max.z(), 0.5);
	}

	// ========== Bounding Box Tests ==========

	private static void testBoundingBox(JavaCSG csg)
	{
		Geometry3D box = csg.box3D(10, 20, 30, false);
		Vector3D min = box.getMin();
		Vector3D max = box.getMax();
		check("Box minX", -5.0, min.x(), 0.5);
		check("Box minY", -10.0, min.y(), 0.5);
		check("Box minZ", 0.0, min.z(), 0.5);
		check("Box maxX", 5.0, max.x(), 0.5);
		check("Box maxY", 10.0, max.y(), 0.5);
		check("Box maxZ", 30.0, max.z(), 0.5);
	}

	// ========== STL Round-trip Test ==========

	private static void testSTLRoundTrip(JavaCSG csg) throws Exception
	{
		Geometry3D box = csg.box3D(10, 20, 30, true);
		double originalVolume = getVolume(box);

		new File("STL").mkdirs();
		String stlFile = "STL/test_roundtrip.stl";
		csg.saveSTL(stlFile, box);

		Geometry3D loaded = csg.loadSTL(stlFile);
		double loadedVolume = getVolume(loaded);

		check("STL round-trip volume", originalVolume, loadedVolume, originalVolume * 0.05);

		// Cleanup
		new File(stlFile).delete();
	}

	// ========== Hull Test ==========

	private static void testHull3D(JavaCSG csg)
	{
		// Hull of two small boxes should be larger than either
		Geometry3D box1 = csg.box3D(2, 2, 2, true);
		Geometry3D box2 = csg.translate3D(10, 0, 0).transform(csg.box3D(2, 2, 2, true));
		Geometry3D hull = csg.hull3D(box1, box2);
		double hullVolume = getVolume(hull);
		double box1Volume = getVolume(box1);
		double box2Volume = getVolume(box2);
		boolean hullLarger = hullVolume > (box1Volume + box2Volume);
		if (hullLarger)
		{
			System.out.println("  PASS: Hull 3D is larger than sum of parts (" +
					String.format("%.2f", hullVolume) + " > " +
					String.format("%.2f", box1Volume + box2Volume) + ")");
			passed++;
		}
		else
		{
			System.out.println("  FAIL: Hull 3D should be larger than sum of parts (" +
					String.format("%.2f", hullVolume) + " <= " +
					String.format("%.2f", box1Volume + box2Volume) + ")");
			failed++;
		}
	}

	// ========== Text Rendering Tests ==========

	private static void testChar2D(JavaCSG csg)
	{
		Geometry2D charA = csg.char2D('A', 5.0, 64);
		double area = getArea(charA);
		if (area > 0)
		{
			System.out.println("  PASS: char2D('A') area = " + String.format("%.4f", area));
			passed++;
		}
		else
		{
			System.out.println("  FAIL: char2D('A') area should be > 0, got " + String.format("%.4f", area));
			failed++;
		}
	}

	private static void testChar2DWithHeight(JavaCSG csg)
	{
		Geometry2D charB = csg.char2D('B', 5.0, 10.0, 64);
		double area = getArea(charB);
		if (area > 0)
		{
			System.out.println("  PASS: char2D('B', w=5, h=10) area = " + String.format("%.4f", area));
			passed++;
		}
		else
		{
			System.out.println("  FAIL: char2D('B', w=5, h=10) area should be > 0, got " + String.format("%.4f", area));
			failed++;
		}
	}

	private static void testText2D(JavaCSG csg)
	{
		Geometry2D text = csg.text2D("Hello", 5, 64);
		double area = getArea(text);
		Vector2D min = text.getMin();
		Vector2D max = text.getMax();
		double textWidth = max.x() - min.x();
		boolean areaOk = area > 0;
		boolean widthOk = textWidth > 20 && textWidth < 30; // 5 chars * 5 width ≈ 25
		if (areaOk && widthOk)
		{
			System.out.println("  PASS: text2D(\"Hello\") area=" + String.format("%.4f", area) +
					", width=" + String.format("%.2f", textWidth));
			passed++;
		}
		else
		{
			System.out.println("  FAIL: text2D(\"Hello\") area=" + String.format("%.4f", area) +
					", width=" + String.format("%.2f", textWidth) +
					" (expected area>0, width in [20,30])");
			failed++;
		}
	}

	private static void testChar2DSpace(JavaCSG csg)
	{
		Geometry2D space = csg.char2D(' ', 5.0, 64);
		double area = getArea(space);
		if (area == 0)
		{
			System.out.println("  PASS: char2D(' ') area = 0 (empty glyph)");
			passed++;
		}
		else
		{
			System.out.println("  FAIL: char2D(' ') area should be 0, got " + String.format("%.4f", area));
			failed++;
		}
	}

	// ========== Helper Methods ==========

	private static double getVolume(Geometry3D geometry)
	{
		Manifold m = ((JavaCSGBaseManifoldImpl.Geometry3DImpl) geometry).getManifold();
		return m.volume();
	}

	private static double getArea(Geometry2D geometry)
	{
		manifold3d.manifold.CrossSection cs =
				((JavaCSGBaseManifoldImpl.Geometry2DImpl) geometry).getCrossSection();
		return cs.area();
	}

	private static void check(String name, double expected, double actual, double tolerance)
	{
		double diff = Math.abs(expected - actual);
		if (diff <= tolerance)
		{
			System.out.println("  PASS: " + name + " (expected=" +
					String.format("%.4f", expected) + ", actual=" +
					String.format("%.4f", actual) + ")");
			passed++;
		}
		else
		{
			System.out.println("  FAIL: " + name + " (expected=" +
					String.format("%.4f", expected) + ", actual=" +
					String.format("%.4f", actual) + ", diff=" +
					String.format("%.4f", diff) + ", tolerance=" +
					String.format("%.4f", tolerance) + ")");
			failed++;
		}
	}
}
