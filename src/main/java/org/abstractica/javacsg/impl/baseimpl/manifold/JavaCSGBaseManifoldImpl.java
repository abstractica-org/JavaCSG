package org.abstractica.javacsg.impl.baseimpl.manifold;

import manifold3d.Manifold;
import manifold3d.ManifoldVector;
import manifold3d.FloatVector;
import manifold3d.UIntVector;
import manifold3d.manifold.CrossSection;
import manifold3d.manifold.CrossSectionVector;
import manifold3d.manifold.MeshGL;
import manifold3d.pub.OpType;
import manifold3d.pub.SimplePolygon;
import manifold3d.pub.Polygons;
import manifold3d.linalg.DoubleVec2;
import manifold3d.linalg.DoubleVec3;

import org.abstractica.javacsg.*;
import org.abstractica.javacsg.impl.baseimpl.JavaCSGBase;
import org.abstractica.javacsg.impl.Vector2DImpl;
import org.abstractica.javacsg.impl.Vector3DImpl;

import java.awt.Font;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class JavaCSGBaseManifoldImpl implements JavaCSGBase
{
	private static final double RAD_TO_DEG = 180.0 / Math.PI;

	private final Font awtFont;
	private final FontRenderContext frc;
	private final double textScale;

	// Pre-load libmanifold.so.3 before any Manifold/CrossSection classes are touched.
	// The manifold3d JAR bundles it as "libmanifold.so" at the JAR root, but the JNI
	// libraries link against "libmanifold.so.3" (the SONAME). We extract it to a temp
	// file with the correct name and load it, so it's already in the process when the
	// JNI libraries are loaded by JavaCPP.
	static
	{
		try
		{
			InputStream is = JavaCSGBaseManifoldImpl.class.getClassLoader()
					.getResourceAsStream("libmanifold.so");
			if (is == null)
			{
				// Try platform-specific name for macOS
				is = JavaCSGBaseManifoldImpl.class.getClassLoader()
						.getResourceAsStream("libmanifold.dylib");
			}
			if (is != null)
			{
				String suffix = System.getProperty("os.name", "").toLowerCase().contains("mac")
						? ".dylib" : ".so.3";
				Path tempLib = Files.createTempFile("libmanifold", suffix);
				tempLib.toFile().deleteOnExit();
				Files.copy(is, tempLib, StandardCopyOption.REPLACE_EXISTING);
				is.close();
				System.load(tempLib.toAbsolutePath().toString());
			}
		}
		catch (IOException | UnsatisfiedLinkError e)
		{
			// Library may already be installed system-wide; continue and let
			// JavaCPP's normal loading try to find it.
			System.err.println("JavaCSG Manifold: Could not pre-load native library (" +
					e.getMessage() + "). Falling back to system library path.");
		}
	}

	public JavaCSGBaseManifoldImpl()
	{
		Font candidate = new Font("Consolas", Font.PLAIN, 100);
		if (!"Consolas".equalsIgnoreCase(candidate.getFamily()))
		{
			candidate = new Font(Font.MONOSPACED, Font.PLAIN, 100);
		}
		this.awtFont = candidate;
		this.frc = new FontRenderContext(null, true, true);
		GlyphVector gv = awtFont.createGlyphVector(frc, "M");
		double advanceWidth = gv.getGlyphMetrics(0).getAdvance();
		this.textScale = 1.0 / advanceWidth;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// 2D Polygon
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Geometry2D polygon2D(Iterable<Vector2D> vertices)
	{
		SimplePolygon poly = new SimplePolygon();
		for (Vector2D v : vertices)
		{
			poly.pushBack(new DoubleVec2(v.x(), v.y()));
		}
		CrossSection cs = new CrossSection(poly, 0); // EvenOdd fill rule
		return new Geometry2DImpl(cs);
	}

	@Override
	public Geometry2D polygon2D(Iterable<Vector2D> vertices, Iterable<? extends Iterable<Integer>> paths)
	{
		List<Vector2D> vertList = new ArrayList<>();
		for (Vector2D v : vertices)
		{
			vertList.add(v);
		}

		Polygons polygons = new Polygons();
		for (Iterable<Integer> path : paths)
		{
			SimplePolygon poly = new SimplePolygon();
			for (int idx : path)
			{
				Vector2D v = vertList.get(idx);
				poly.pushBack(new DoubleVec2(v.x(), v.y()));
			}
			polygons.pushBack(poly);
		}
		CrossSection cs = new CrossSection(polygons, 0); // EvenOdd fill rule
		return new Geometry2DImpl(cs);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// 2D Transformations
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Transform2D identity2D()
	{
		return Transform2DIdentity.INSTANCE;
	}

	@Override
	public Transform2D compose2D(List<Transform2D> transforms)
	{
		return new Transform2DComposed(transforms);
	}

	@Override
	public Transform2D translate2D(double x, double y)
	{
		return new Transform2DTranslate(x, y);
	}

	@Override
	public Transform2D rotate2D(Angle angle)
	{
		return new Transform2DRotate(angle);
	}

	@Override
	public Transform2D scale2D(double x, double y)
	{
		return new Transform2DScale(x, y);
	}

	@Override
	public Transform2D mirror2D(double normX, double normY)
	{
		return new Transform2DMirror(normX, normY);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// 2D Operations
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Geometry2D union2D(Iterable<Geometry2D> geometries)
	{
		List<CrossSection> sections = new ArrayList<>();
		for (Geometry2D g : geometries)
		{
			sections.add(((Geometry2DImpl) g).getCrossSection());
		}
		if (sections.isEmpty())
		{
			return new Geometry2DImpl(new CrossSection());
		}
		if (sections.size() == 1)
		{
			return new Geometry2DImpl(sections.get(0));
		}
		CrossSectionVector vec = new CrossSectionVector();
		for (CrossSection cs : sections)
		{
			vec.pushBack(cs);
		}
		return new Geometry2DImpl(CrossSection.BatchBoolean(vec, OpType.Add));
	}

	@Override
	public Geometry2D intersection2D(Iterable<Geometry2D> geometries)
	{
		List<CrossSection> sections = new ArrayList<>();
		for (Geometry2D g : geometries)
		{
			sections.add(((Geometry2DImpl) g).getCrossSection());
		}
		if (sections.isEmpty())
		{
			return new Geometry2DImpl(new CrossSection());
		}
		if (sections.size() == 1)
		{
			return new Geometry2DImpl(sections.get(0));
		}
		CrossSectionVector vec = new CrossSectionVector();
		for (CrossSection cs : sections)
		{
			vec.pushBack(cs);
		}
		return new Geometry2DImpl(CrossSection.BatchBoolean(vec, OpType.Intersect));
	}

	@Override
	public Geometry2D difference2D(Geometry2D filled, Iterable<Geometry2D> cutouts)
	{
		CrossSection filledCS = ((Geometry2DImpl) filled).getCrossSection();
		List<CrossSection> cutoutList = new ArrayList<>();
		for (Geometry2D g : cutouts)
		{
			cutoutList.add(((Geometry2DImpl) g).getCrossSection());
		}
		if (cutoutList.isEmpty())
		{
			return new Geometry2DImpl(filledCS);
		}
		// Union all cutouts, then subtract from filled
		CrossSection cutoutUnion;
		if (cutoutList.size() == 1)
		{
			cutoutUnion = cutoutList.get(0);
		}
		else
		{
			CrossSectionVector vec = new CrossSectionVector();
			for (CrossSection cs : cutoutList)
			{
				vec.pushBack(cs);
			}
			cutoutUnion = CrossSection.BatchBoolean(vec, OpType.Add);
		}
		return new Geometry2DImpl(filledCS.subtract(cutoutUnion));
	}

	@Override
	public Geometry2D hull2D(Iterable<Geometry2D> geometries)
	{
		CrossSectionVector vec = new CrossSectionVector();
		for (Geometry2D g : geometries)
		{
			vec.pushBack(((Geometry2DImpl) g).getCrossSection());
		}
		return new Geometry2DImpl(CrossSection.ConvexHull(vec));
	}

	@Override
	public Geometry2D minkowski2D(Iterable<Geometry2D> geometries)
	{
		throw new UnsupportedOperationException(
				"minkowski2D is not supported by the Manifold backend. " +
				"Use the OpenSCAD backend for Minkowski sum operations.");
	}

	@Override
	public Geometry2D offset2D(double delta, boolean chamfer, Iterable<Geometry2D> geometries)
	{
		// Union all geometries first
		CrossSection combined = unionCrossSections(geometries);
		// chamfer=false → Miter (sharp corners), chamfer=true → Square (chamfered)
		int joinType = chamfer ? 0 : 2; // Square=0, Miter=2
		return new Geometry2DImpl(combined.offset(delta, joinType, 2.0, 0));
	}

	@Override
	public Geometry2D offsetRound2D(double radius, int angularResolution, Iterable<Geometry2D> geometries)
	{
		CrossSection combined = unionCrossSections(geometries);
		// Round=1
		return new Geometry2DImpl(combined.offset(radius, 1, 2.0, angularResolution));
	}

	private CrossSection unionCrossSections(Iterable<Geometry2D> geometries)
	{
		List<CrossSection> sections = new ArrayList<>();
		for (Geometry2D g : geometries)
		{
			sections.add(((Geometry2DImpl) g).getCrossSection());
		}
		if (sections.isEmpty())
		{
			return new CrossSection();
		}
		if (sections.size() == 1)
		{
			return sections.get(0);
		}
		CrossSectionVector vec = new CrossSectionVector();
		for (CrossSection cs : sections)
		{
			vec.pushBack(cs);
		}
		return CrossSection.BatchBoolean(vec, OpType.Add);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// 2D Text
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Geometry2D char2D(char ch, double width, int angularResolution)
	{
		double flatness = angularResolutionToFlatness(angularResolution);
		CrossSection glyph = glyphToCrossSection(ch, flatness);
		double scale = width * textScale;
		CrossSection scaled = glyph.scale(new DoubleVec2(scale, scale));
		return new Geometry2DImpl(scaled);
	}

	@Override
	public Geometry2D char2D(char ch, double width, double height, int angularResolution)
	{
		double flatness = angularResolutionToFlatness(angularResolution);
		CrossSection glyph = glyphToCrossSection(ch, flatness);
		double scaleX = textScale * width;
		double scaleY = textScale * 0.5 * height;
		CrossSection scaled = glyph.scale(new DoubleVec2(scaleX, scaleY));
		return new Geometry2DImpl(scaled);
	}

	@Override
	public double charHeight2D(double width)
	{
		return 2 * width;
	}

	@Override
	public double charBaseline2D(double height)
	{
		return 0.2 * height;
	}

	private double angularResolutionToFlatness(int angularResolution)
	{
		if (angularResolution < 3) angularResolution = 3;
		double referenceRadius = 50.0;
		double flatness = referenceRadius * (1.0 - Math.cos(Math.PI / angularResolution));
		return Math.max(0.01, Math.min(flatness, 10.0));
	}

	private CrossSection glyphToCrossSection(char ch, double flatness)
	{
		GlyphVector gv = awtFont.createGlyphVector(frc, new char[]{ch});
		Shape outline = gv.getOutline();

		AffineTransform yFlip = new AffineTransform(1, 0, 0, -1, 0, 0);
		PathIterator pi = outline.getPathIterator(yFlip, flatness);

		Polygons polygons = new Polygons();
		SimplePolygon currentPoly = null;
		double[] coords = new double[6];

		while (!pi.isDone())
		{
			int type = pi.currentSegment(coords);
			switch (type)
			{
				case PathIterator.SEG_MOVETO:
					if (currentPoly != null && currentPoly.size() > 0)
					{
						polygons.pushBack(currentPoly);
					}
					currentPoly = new SimplePolygon();
					currentPoly.pushBack(new DoubleVec2(coords[0], coords[1]));
					break;

				case PathIterator.SEG_LINETO:
					if (currentPoly != null)
					{
						currentPoly.pushBack(new DoubleVec2(coords[0], coords[1]));
					}
					break;

				case PathIterator.SEG_CLOSE:
					if (currentPoly != null && currentPoly.size() > 0)
					{
						polygons.pushBack(currentPoly);
						currentPoly = null;
					}
					break;

				case PathIterator.SEG_QUADTO:
					if (currentPoly != null)
					{
						currentPoly.pushBack(new DoubleVec2(coords[2], coords[3]));
					}
					break;

				case PathIterator.SEG_CUBICTO:
					if (currentPoly != null)
					{
						currentPoly.pushBack(new DoubleVec2(coords[4], coords[5]));
					}
					break;
			}
			pi.next();
		}

		if (currentPoly != null && currentPoly.size() > 0)
		{
			polygons.pushBack(currentPoly);
		}

		if (polygons.size() == 0)
		{
			return new CrossSection();
		}

		return new CrossSection(polygons, 0);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// 3D Polyhedron
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Geometry3D polyhedron3D(Iterable<Vector3D> vertices, Iterable<? extends Iterable<Integer>> faces)
	{
		// Collect vertices
		List<Vector3D> vertList = new ArrayList<>();
		for (Vector3D v : vertices)
		{
			vertList.add(v);
		}

		// Flatten vertices into float array
		float[] flatVerts = new float[vertList.size() * 3];
		for (int i = 0; i < vertList.size(); i++)
		{
			Vector3D v = vertList.get(i);
			flatVerts[i * 3] = (float) v.x();
			flatVerts[i * 3 + 1] = (float) v.y();
			flatVerts[i * 3 + 2] = (float) v.z();
		}

		// Fan-triangulate faces and collect triangle indices
		List<int[]> triangles = new ArrayList<>();
		for (Iterable<Integer> face : faces)
		{
			List<Integer> faceVerts = new ArrayList<>();
			for (int idx : face)
			{
				faceVerts.add(idx);
			}
			// Fan triangulation: first vertex connects to all other consecutive pairs
			for (int i = 1; i < faceVerts.size() - 1; i++)
			{
				triangles.add(new int[]{faceVerts.get(0), faceVerts.get(i), faceVerts.get(i + 1)});
			}
		}

		long[] flatTris = new long[triangles.size() * 3];
		for (int i = 0; i < triangles.size(); i++)
		{
			int[] tri = triangles.get(i);
			flatTris[i * 3] = tri[0];
			flatTris[i * 3 + 1] = tri[1];
			flatTris[i * 3 + 2] = tri[2];
		}

		MeshGL mesh = new MeshGL();
		mesh.numProp(3);
		mesh.vertProperties(FloatVector.FromArray(flatVerts));
		mesh.triVerts(UIntVector.FromArray(flatTris));

		return new Geometry3DImpl(new Manifold(mesh));
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// 2D to 3D Operations
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Geometry3D linearExtrude(double height, Angle twist, double scale, int slices, boolean centerZ, Geometry2D geometry)
	{
		CrossSection cs = ((Geometry2DImpl) geometry).getCrossSection();
		Polygons polys = cs.toPolygons();
		Manifold extruded = Manifold.Extrude(polys, (float) height, slices,
				(float) twist.asDegrees(), new DoubleVec2(scale, scale));
		if (centerZ)
		{
			extruded = extruded.translate(0, 0, -height / 2.0);
		}
		return new Geometry3DImpl(extruded);
	}

	@Override
	public Geometry3D linearExtrude(double height, boolean centerZ, Geometry2D geometry)
	{
		CrossSection cs = ((Geometry2DImpl) geometry).getCrossSection();
		Polygons polys = cs.toPolygons();
		Manifold extruded = Manifold.Extrude(polys, (float) height, 1, 0.0f,
				new DoubleVec2(1.0, 1.0));
		if (centerZ)
		{
			extruded = extruded.translate(0, 0, -height / 2.0);
		}
		return new Geometry3DImpl(extruded);
	}

	@Override
	public Geometry3D rotateExtrude(Angle angle, int angularResolution, Geometry2D geometry)
	{
		CrossSection cs = ((Geometry2DImpl) geometry).getCrossSection();
		Polygons polys = cs.toPolygons();
		Manifold revolved = Manifold.Revolve(polys, angularResolution, (float) angle.asDegrees());
		return new Geometry3DImpl(revolved);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// 3D to 2D Operations
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Geometry2D project(boolean cutAtZeroZ, Geometry3D geometry)
	{
		Manifold m = ((Geometry3DImpl) geometry).getManifold();
		Polygons polys;
		if (cutAtZeroZ)
		{
			polys = m.slice();
		}
		else
		{
			polys = m.project();
		}
		CrossSection cs = new CrossSection(polys, 0); // EvenOdd fill rule
		return new Geometry2DImpl(cs);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// 3D Transformations
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Transform3D identity3D()
	{
		return Transform3DIdentity.INSTANCE;
	}

	@Override
	public Transform3D compose3D(List<Transform3D> transforms)
	{
		return new Transform3DComposed(transforms);
	}

	@Override
	public Transform3D translate3D(double x, double y, double z)
	{
		return new Transform3DTranslate(x, y, z);
	}

	@Override
	public Transform3D rotate3DX(Angle angle)
	{
		return new Transform3DRotateX(angle.asRadians());
	}

	@Override
	public Transform3D rotate3DY(Angle angle)
	{
		return new Transform3DRotateY(angle.asRadians());
	}

	@Override
	public Transform3D rotate3DZ(Angle angle)
	{
		return new Transform3DRotateZ(angle.asRadians());
	}

	@Override
	public Transform3D scale3D(double x, double y, double z)
	{
		return new Transform3DScale(x, y, z);
	}

	@Override
	public Transform3D mirror3D(double normX, double normY, double normZ)
	{
		return new Transform3DMirror(normX, normY, normZ);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// 3D Operations
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Geometry3D union3D(Iterable<Geometry3D> geometries)
	{
		List<Manifold> manifolds = new ArrayList<>();
		for (Geometry3D g : geometries)
		{
			manifolds.add(((Geometry3DImpl) g).getManifold());
		}
		if (manifolds.isEmpty())
		{
			return new Geometry3DImpl(new Manifold());
		}
		if (manifolds.size() == 1)
		{
			return new Geometry3DImpl(manifolds.get(0));
		}
		ManifoldVector vec = new ManifoldVector();
		for (Manifold m : manifolds)
		{
			vec.pushBack(m);
		}
		return new Geometry3DImpl(Manifold.BatchBoolean(vec, OpType.Add));
	}

	@Override
	public Geometry3D intersection3D(Iterable<Geometry3D> geometries)
	{
		List<Manifold> manifolds = new ArrayList<>();
		for (Geometry3D g : geometries)
		{
			manifolds.add(((Geometry3DImpl) g).getManifold());
		}
		if (manifolds.isEmpty())
		{
			return new Geometry3DImpl(new Manifold());
		}
		if (manifolds.size() == 1)
		{
			return new Geometry3DImpl(manifolds.get(0));
		}
		ManifoldVector vec = new ManifoldVector();
		for (Manifold m : manifolds)
		{
			vec.pushBack(m);
		}
		return new Geometry3DImpl(Manifold.BatchBoolean(vec, OpType.Intersect));
	}

	@Override
	public Geometry3D difference3D(Geometry3D solid, Iterable<Geometry3D> cutouts)
	{
		Manifold filledM = ((Geometry3DImpl) solid).getManifold();
		List<Manifold> cutoutList = new ArrayList<>();
		for (Geometry3D g : cutouts)
		{
			cutoutList.add(((Geometry3DImpl) g).getManifold());
		}
		if (cutoutList.isEmpty())
		{
			return new Geometry3DImpl(filledM);
		}
		Manifold cutoutUnion;
		if (cutoutList.size() == 1)
		{
			cutoutUnion = cutoutList.get(0);
		}
		else
		{
			ManifoldVector vec = new ManifoldVector();
			for (Manifold m : cutoutList)
			{
				vec.pushBack(m);
			}
			cutoutUnion = Manifold.BatchBoolean(vec, OpType.Add);
		}
		return new Geometry3DImpl(filledM.subtract(cutoutUnion));
	}

	@Override
	public Geometry3D hull3D(Iterable<Geometry3D> geometries)
	{
		ManifoldVector vec = new ManifoldVector();
		for (Geometry3D g : geometries)
		{
			vec.pushBack(((Geometry3DImpl) g).getManifold());
		}
		return new Geometry3DImpl(Manifold.ConvexHull(vec));
	}

	@Override
	public Geometry3D minkowski3D(Iterable<Geometry3D> geometries)
	{
		throw new UnsupportedOperationException(
				"minkowski3D is not supported by the Manifold backend. " +
				"Use the OpenSCAD backend for Minkowski sum operations.");
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// Color
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Geometry3D color3D(Color color, Geometry3D geometry)
	{
		// Manifold doesn't support color rendering; pass through the geometry
		return geometry;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// View
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void view(Geometry2D geometry)
	{
		view(geometry, 0);
	}

	@Override
	public void view(Geometry3D geometry)
	{
		view(geometry, 0);
	}

	@Override
	public void view(Geometry2D geometry, int windowID)
	{
		// Extrude 2D to thin 3D shape for visualization
		CrossSection cs = ((Geometry2DImpl) geometry).getCrossSection();
		Polygons polys = cs.toPolygons();
		Manifold extruded = Manifold.Extrude(polys, 1.0f, 1, 0.0f, new DoubleVec2(1.0, 1.0));
		try
		{
			STLWriter.write("STL/View" + windowID + ".stl", extruded);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Could not view geometry!", e);
		}
	}

	@Override
	public void view(Geometry3D geometry, int windowID)
	{
		Manifold m = ((Geometry3DImpl) geometry).getManifold();
		try
		{
			STLWriter.write("STL/View" + windowID + ".stl", m);
		}
		catch (IOException e)
		{
			throw new RuntimeException("Could not view geometry!", e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// Cache
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Geometry2D cache(Geometry2D geometry)
	{
		// Manifold operates in-memory; no external caching needed
		return geometry;
	}

	@Override
	public Geometry3D cache(Geometry3D geometry)
	{
		// Manifold operates in-memory; no external caching needed
		return geometry;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// STL I/O
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Geometry3D loadSTL(String fileName) throws IOException
	{
		MeshGL mesh = STLWriter.read(fileName);
		return new Geometry3DImpl(new Manifold(mesh));
	}

	@Override
	public void saveSTL(String fileName, Geometry3D geometry) throws IOException
	{
		Manifold m = ((Geometry3DImpl) geometry).getManifold();
		STLWriter.write(fileName, m);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// 3MF I/O
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Geometry3D load3MF(String fileName) throws IOException
	{
		throw new UnsupportedOperationException(
				"load3MF is not supported by the Manifold backend.");
	}

	@Override
	public void save3MF(String fileName, Geometry3D geometry) throws IOException
	{
		throw new UnsupportedOperationException(
				"save3MF is not supported by the Manifold backend.");
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// Inner Geometry Wrapper Classes
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class Geometry2DImpl implements Geometry2D
	{
		private final CrossSection crossSection;
		private Vector2D min;
		private Vector2D max;

		public Geometry2DImpl(CrossSection crossSection)
		{
			this.crossSection = crossSection;
			this.min = null;
			this.max = null;
		}

		public CrossSection getCrossSection()
		{
			return crossSection;
		}

		@Override
		public void debugMark()
		{
			// No-op for Manifold backend
		}

		@Override
		public void disable()
		{
			// No-op for Manifold backend
		}

		@Override
		public Vector2D getMin()
		{
			if (min == null)
			{
				calculateMinMax();
			}
			return min;
		}

		@Override
		public Vector2D getMax()
		{
			if (max == null)
			{
				calculateMinMax();
			}
			return max;
		}

		private void calculateMinMax()
		{
			manifold3d.manifold.Rect bounds = crossSection.bounds();
			DoubleVec2 center = bounds.Center();
			DoubleVec2 size = bounds.Size();
			double halfW = size.x() / 2.0;
			double halfH = size.y() / 2.0;
			min = new Vector2DImpl(center.x() - halfW, center.y() - halfH);
			max = new Vector2DImpl(center.x() + halfW, center.y() + halfH);
		}
	}

	public static class Geometry3DImpl implements Geometry3D
	{
		private final Manifold manifold;
		private Vector3D min;
		private Vector3D max;

		public Geometry3DImpl(Manifold manifold)
		{
			this.manifold = manifold;
			this.min = null;
			this.max = null;
		}

		public Manifold getManifold()
		{
			return manifold;
		}

		@Override
		public void debugMark()
		{
			// No-op for Manifold backend
		}

		@Override
		public void disable()
		{
			// No-op for Manifold backend
		}

		@Override
		public Vector3D getMin()
		{
			if (min == null)
			{
				calculateMinMax();
			}
			return min;
		}

		@Override
		public Vector3D getMax()
		{
			if (max == null)
			{
				calculateMinMax();
			}
			return max;
		}

		private void calculateMinMax()
		{
			manifold3d.pub.Box bbox = manifold.boundingBox();
			DoubleVec3 center = bbox.Center();
			DoubleVec3 size = bbox.Size();
			double halfX = size.x() / 2.0;
			double halfY = size.y() / 2.0;
			double halfZ = size.z() / 2.0;
			min = new Vector3DImpl(center.x() - halfX, center.y() - halfY, center.z() - halfZ);
			max = new Vector3DImpl(center.x() + halfX, center.y() + halfY, center.z() + halfZ);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// Inner Transform2D Classes
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	private static class Transform2DIdentity implements Transform2D
	{
		public static final Transform2DIdentity INSTANCE = new Transform2DIdentity();

		private Transform2DIdentity() {}

		@Override
		public Transform2D inverse()
		{
			return this;
		}

		@Override
		public Vector2D transformPoint(Vector2D vector)
		{
			return vector;
		}

		@Override
		public Vector2D transformDirection(Vector2D vector)
		{
			return vector;
		}

		@Override
		public Geometry2D transform(Geometry2D geometry)
		{
			return geometry;
		}

		@Override
		public Transform3D asTransform3D()
		{
			return Transform3DIdentity.INSTANCE;
		}
	}

	private static class Transform2DComposed implements Transform2D
	{
		private List<Transform2D> list;

		public Transform2DComposed(List<Transform2D> children)
		{
			for (Transform2D child : children)
			{
				if (child instanceof Transform2DIdentity)
				{
					continue;
				}
				if (child instanceof Transform2DComposed composed)
				{
					if (composed.list == null)
					{
						continue;
					}
					if (list == null)
					{
						list = new ArrayList<>(composed.list);
					}
					else
					{
						list.addAll(composed.list);
					}
				}
				else
				{
					if (list == null)
					{
						list = new ArrayList<>();
					}
					list.add(child);
				}
			}
		}

		@Override
		public Transform2D inverse()
		{
			if (list == null)
			{
				return this;
			}
			List<Transform2D> inverseList = new ArrayList<>(list.size());
			for (Transform2D t : list.reversed())
			{
				inverseList.add(t.inverse());
			}
			return new Transform2DComposed(inverseList);
		}

		@Override
		public Vector2D transformPoint(Vector2D vector)
		{
			if (list == null)
			{
				return vector;
			}
			for (Transform2D transform : list.reversed())
			{
				vector = transform.transformPoint(vector);
			}
			return vector;
		}

		@Override
		public Vector2D transformDirection(Vector2D vector)
		{
			if (list == null)
			{
				return vector;
			}
			for (Transform2D transform : list.reversed())
			{
				vector = transform.transformDirection(vector);
			}
			return vector;
		}

		@Override
		public Geometry2D transform(Geometry2D geometry)
		{
			if (list == null)
			{
				return geometry;
			}
			for (Transform2D transform : list.reversed())
			{
				geometry = transform.transform(geometry);
			}
			return geometry;
		}

		@Override
		public Transform3D asTransform3D()
		{
			List<Transform3D> transform3DList = new ArrayList<>();
			if (list != null)
			{
				for (Transform2D transform2D : list)
				{
					transform3DList.add(transform2D.asTransform3D());
				}
			}
			return new Transform3DComposed(transform3DList);
		}
	}

	private static class Transform2DTranslate implements Transform2D
	{
		private final double x;
		private final double y;

		public Transform2DTranslate(double x, double y)
		{
			this.x = x;
			this.y = y;
		}

		@Override
		public Transform2D inverse()
		{
			return new Transform2DTranslate(-x, -y);
		}

		@Override
		public Vector2D transformPoint(Vector2D vector)
		{
			return new Vector2DImpl(vector.x() + x, vector.y() + y);
		}

		@Override
		public Vector2D transformDirection(Vector2D vector)
		{
			return vector;
		}

		@Override
		public Geometry2D transform(Geometry2D geometry)
		{
			CrossSection cs = ((Geometry2DImpl) geometry).getCrossSection();
			return new Geometry2DImpl(cs.translate(new DoubleVec2(x, y)));
		}

		@Override
		public Transform3D asTransform3D()
		{
			return new Transform3DTranslate(x, y, 0);
		}
	}

	private static class Transform2DRotate implements Transform2D
	{
		private final double rad;
		private final double cos;
		private final double sin;

		public Transform2DRotate(double rad)
		{
			this.rad = rad;
			cos = Math.cos(rad);
			sin = Math.sin(rad);
		}

		public Transform2DRotate(Angle angle)
		{
			this(angle.asRadians());
		}

		@Override
		public Transform2D inverse()
		{
			return new Transform2DRotate(-rad);
		}

		@Override
		public Vector2D transformPoint(Vector2D vector)
		{
			double vx = vector.x();
			double vy = vector.y();
			return new Vector2DImpl(vx * cos - vy * sin, vx * sin + vy * cos);
		}

		@Override
		public Vector2D transformDirection(Vector2D vector)
		{
			return transformPoint(vector);
		}

		@Override
		public Geometry2D transform(Geometry2D geometry)
		{
			CrossSection cs = ((Geometry2DImpl) geometry).getCrossSection();
			return new Geometry2DImpl(cs.rotate((float) (rad * RAD_TO_DEG)));
		}

		@Override
		public Transform3D asTransform3D()
		{
			return new Transform3DRotateZ(rad);
		}
	}

	private static class Transform2DScale implements Transform2D
	{
		private final double sx;
		private final double sy;

		public Transform2DScale(double sx, double sy)
		{
			this.sx = sx;
			this.sy = sy;
		}

		@Override
		public Transform2D inverse()
		{
			return new Transform2DScale(1.0 / sx, 1.0 / sy);
		}

		@Override
		public Vector2D transformPoint(Vector2D vector)
		{
			return new Vector2DImpl(vector.x() * sx, vector.y() * sy);
		}

		@Override
		public Vector2D transformDirection(Vector2D vector)
		{
			return new Vector2DImpl(vector.x() * sx, vector.y() * sy);
		}

		@Override
		public Geometry2D transform(Geometry2D geometry)
		{
			CrossSection cs = ((Geometry2DImpl) geometry).getCrossSection();
			return new Geometry2DImpl(cs.scale(new DoubleVec2(sx, sy)));
		}

		@Override
		public Transform3D asTransform3D()
		{
			return new Transform3DScale(sx, sy, 1.0);
		}
	}

	private static class Transform2DMirror implements Transform2D
	{
		private final double normX;
		private final double normY;

		public Transform2DMirror(double normX, double normY)
		{
			this.normX = normX;
			this.normY = normY;
		}

		@Override
		public Transform2D inverse()
		{
			return this;
		}

		@Override
		public Vector2D transformPoint(Vector2D vector)
		{
			double vx = vector.x();
			double vy = vector.y();
			double d = vx * normX + vy * normY;
			return new Vector2DImpl(vx - 2 * d * normX, vy - 2 * d * normY);
		}

		@Override
		public Vector2D transformDirection(Vector2D vector)
		{
			return transformPoint(vector);
		}

		@Override
		public Geometry2D transform(Geometry2D geometry)
		{
			CrossSection cs = ((Geometry2DImpl) geometry).getCrossSection();
			return new Geometry2DImpl(cs.mirror(new DoubleVec2(normX, normY)));
		}

		@Override
		public Transform3D asTransform3D()
		{
			return new Transform3DMirror(normX, normY, 0);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/// Inner Transform3D Classes
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	private static class Transform3DIdentity implements Transform3D
	{
		public static final Transform3DIdentity INSTANCE = new Transform3DIdentity();

		private Transform3DIdentity() {}

		@Override
		public Transform3D inverse()
		{
			return this;
		}

		@Override
		public Vector3D transformPoint(Vector3D vector)
		{
			return vector;
		}

		@Override
		public Vector3D transformDirection(Vector3D vector)
		{
			return vector;
		}

		@Override
		public Geometry3D transform(Geometry3D geometry)
		{
			return geometry;
		}
	}

	private static class Transform3DComposed implements Transform3D
	{
		private List<Transform3D> list;

		public Transform3DComposed(List<Transform3D> children)
		{
			for (Transform3D child : children)
			{
				if (child instanceof Transform3DIdentity)
				{
					continue;
				}
				if (child instanceof Transform3DComposed composed)
				{
					if (composed.list == null)
					{
						continue;
					}
					if (list == null)
					{
						list = new ArrayList<>(composed.list);
					}
					else
					{
						list.addAll(composed.list);
					}
				}
				else
				{
					if (list == null)
					{
						list = new ArrayList<>();
					}
					list.add(child);
				}
			}
		}

		@Override
		public Transform3D inverse()
		{
			if (list == null)
			{
				return this;
			}
			List<Transform3D> inverseList = new ArrayList<>(list.size());
			for (Transform3D t : list.reversed())
			{
				inverseList.add(t.inverse());
			}
			return new Transform3DComposed(inverseList);
		}

		@Override
		public Vector3D transformPoint(Vector3D vector)
		{
			if (list == null)
			{
				return vector;
			}
			for (Transform3D transform : list.reversed())
			{
				vector = transform.transformPoint(vector);
			}
			return vector;
		}

		@Override
		public Vector3D transformDirection(Vector3D vector)
		{
			if (list == null)
			{
				return vector;
			}
			for (Transform3D transform : list.reversed())
			{
				vector = transform.transformDirection(vector);
			}
			return vector;
		}

		@Override
		public Geometry3D transform(Geometry3D geometry)
		{
			if (list == null)
			{
				return geometry;
			}
			for (Transform3D transform : list.reversed())
			{
				geometry = transform.transform(geometry);
			}
			return geometry;
		}
	}

	private static class Transform3DTranslate implements Transform3D
	{
		private final double x;
		private final double y;
		private final double z;

		public Transform3DTranslate(double x, double y, double z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public Transform3D inverse()
		{
			return new Transform3DTranslate(-x, -y, -z);
		}

		@Override
		public Vector3D transformPoint(Vector3D vector)
		{
			return new Vector3DImpl(vector.x() + x, vector.y() + y, vector.z() + z);
		}

		@Override
		public Vector3D transformDirection(Vector3D vector)
		{
			return vector;
		}

		@Override
		public Geometry3D transform(Geometry3D geometry)
		{
			Manifold m = ((Geometry3DImpl) geometry).getManifold();
			return new Geometry3DImpl(m.translate(new DoubleVec3(x, y, z)));
		}
	}

	private static class Transform3DRotateX implements Transform3D
	{
		private final double rad;
		private final double sin;
		private final double cos;

		public Transform3DRotateX(double rad)
		{
			this.rad = rad;
			this.sin = Math.sin(rad);
			this.cos = Math.cos(rad);
		}

		@Override
		public Transform3D inverse()
		{
			return new Transform3DRotateX(-rad);
		}

		@Override
		public Vector3D transformPoint(Vector3D vector)
		{
			double vy = vector.y();
			double vz = vector.z();
			return new Vector3DImpl(vector.x(), vy * cos - vz * sin, vy * sin + vz * cos);
		}

		@Override
		public Vector3D transformDirection(Vector3D vector)
		{
			return transformPoint(vector);
		}

		@Override
		public Geometry3D transform(Geometry3D geometry)
		{
			Manifold m = ((Geometry3DImpl) geometry).getManifold();
			return new Geometry3DImpl(m.rotate((float) (rad * RAD_TO_DEG), 0, 0));
		}
	}

	private static class Transform3DRotateY implements Transform3D
	{
		private final double rad;
		private final double sin;
		private final double cos;

		public Transform3DRotateY(double rad)
		{
			this.rad = rad;
			this.sin = Math.sin(rad);
			this.cos = Math.cos(rad);
		}

		@Override
		public Transform3D inverse()
		{
			return new Transform3DRotateY(-rad);
		}

		@Override
		public Vector3D transformPoint(Vector3D vector)
		{
			double vx = vector.x();
			double vz = vector.z();
			return new Vector3DImpl(vx * cos + vz * sin, vector.y(), -vx * sin + vz * cos);
		}

		@Override
		public Vector3D transformDirection(Vector3D vector)
		{
			return transformPoint(vector);
		}

		@Override
		public Geometry3D transform(Geometry3D geometry)
		{
			Manifold m = ((Geometry3DImpl) geometry).getManifold();
			return new Geometry3DImpl(m.rotate(0, (float) (rad * RAD_TO_DEG), 0));
		}
	}

	private static class Transform3DRotateZ implements Transform3D
	{
		private final double rad;
		private final double sin;
		private final double cos;

		public Transform3DRotateZ(double rad)
		{
			this.rad = rad;
			this.sin = Math.sin(rad);
			this.cos = Math.cos(rad);
		}

		@Override
		public Transform3D inverse()
		{
			return new Transform3DRotateZ(-rad);
		}

		@Override
		public Vector3D transformPoint(Vector3D vector)
		{
			double vx = vector.x();
			double vy = vector.y();
			return new Vector3DImpl(vx * cos - vy * sin, vx * sin + vy * cos, vector.z());
		}

		@Override
		public Vector3D transformDirection(Vector3D vector)
		{
			return transformPoint(vector);
		}

		@Override
		public Geometry3D transform(Geometry3D geometry)
		{
			Manifold m = ((Geometry3DImpl) geometry).getManifold();
			return new Geometry3DImpl(m.rotate(0, 0, (float) (rad * RAD_TO_DEG)));
		}
	}

	private static class Transform3DScale implements Transform3D
	{
		private final double sx;
		private final double sy;
		private final double sz;

		public Transform3DScale(double sx, double sy, double sz)
		{
			this.sx = sx;
			this.sy = sy;
			this.sz = sz;
		}

		@Override
		public Transform3D inverse()
		{
			return new Transform3DScale(1.0 / sx, 1.0 / sy, 1.0 / sz);
		}

		@Override
		public Vector3D transformPoint(Vector3D vector)
		{
			return new Vector3DImpl(vector.x() * sx, vector.y() * sy, vector.z() * sz);
		}

		@Override
		public Vector3D transformDirection(Vector3D vector)
		{
			return new Vector3DImpl(vector.x() * sx, vector.y() * sy, vector.z() * sz);
		}

		@Override
		public Geometry3D transform(Geometry3D geometry)
		{
			Manifold m = ((Geometry3DImpl) geometry).getManifold();
			return new Geometry3DImpl(m.scale(new DoubleVec3(sx, sy, sz)));
		}
	}

	private static class Transform3DMirror implements Transform3D
	{
		private final double normX;
		private final double normY;
		private final double normZ;

		public Transform3DMirror(double normX, double normY, double normZ)
		{
			this.normX = normX;
			this.normY = normY;
			this.normZ = normZ;
		}

		@Override
		public Transform3D inverse()
		{
			return this;
		}

		@Override
		public Vector3D transformPoint(Vector3D vector)
		{
			double vx = vector.x();
			double vy = vector.y();
			double vz = vector.z();
			double d = vx * normX + vy * normY + vz * normZ;
			return new Vector3DImpl(vx - 2 * d * normX, vy - 2 * d * normY, vz - 2 * d * normZ);
		}

		@Override
		public Vector3D transformDirection(Vector3D vector)
		{
			return transformPoint(vector);
		}

		@Override
		public Geometry3D transform(Geometry3D geometry)
		{
			Manifold m = ((Geometry3DImpl) geometry).getManifold();
			return new Geometry3DImpl(m.mirror(new DoubleVec3(normX, normY, normZ)));
		}
	}
}
