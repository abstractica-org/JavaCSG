package org.abstractica.javacsg.impl.baseimpl.javaopenscad;

import org.abstractica.javacsg.*;
import org.abstractica.javacsg.impl.baseimpl.JavaCSGBase;
import org.abstractica.javacsg.impl.Vector2DImpl;
import org.abstractica.javacsg.impl.Vector3DImpl;
import org.abstractica.javaopenscad.JavaOpenSCAD;
import org.abstractica.javaopenscad.impl.JavaOpenSCADImpl;
import org.abstractica.javaopenscad.intf.*;
import org.abstractica.javaopenscad.intf.text.OpenSCADTextAlignment;
import org.abstractica.javaopenscad.intf.text.OpenSCADTextAttributes;
import org.abstractica.javaopenscad.intf.text.OpenSCADTextFont;
import org.abstractica.javaopenscad.intf.text.OpenSCADTextSize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JavaCSGBaseOpenSCADImpl implements JavaCSGBase
{
	private static final double DEGREES_TO_ROTATIONS = 1.0 / 360.0;
	private static final double RADIANS_TO_ROTATIONS = 1.0 / (2.0 * Math.PI);

	private final JavaOpenSCAD javaOpenSCAD;
	private final OpenSCADTextAttributes textAttributes;
	private final double textScale;

	public JavaCSGBaseOpenSCADImpl(boolean useCache)
	{
		this(new JavaOpenSCADImpl(useCache));
	}

	public JavaCSGBaseOpenSCADImpl(String cacheDirectory)
	{
		this(new JavaOpenSCADImpl(cacheDirectory));
	}

	private JavaCSGBaseOpenSCADImpl(JavaOpenSCAD javaOpenSCAD)
	{
		this.javaOpenSCAD = javaOpenSCAD;
		OpenSCADTextFont font = javaOpenSCAD.textFont("Consolas", "Regular", "en", "latin");
		OpenSCADTextSize textSize = javaOpenSCAD.textSize(10.0, 1);
		OpenSCADTextAlignment alignment = javaOpenSCAD.textAlignment(
				OpenSCADTextAlignment.Direction.LEFT_TO_RIGHT,
				OpenSCADTextAlignment.Horizontal.LEFT,
				OpenSCADTextAlignment.Vertical.BASELINE);
		this.textAttributes = javaOpenSCAD.textAttributes(font, textSize, alignment);
		this.textScale = 2.0 / 15.0;
		//this.textScaleX = this.textScaleY * (0.75 / 7.4575);
	}
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

	@Override
	public Geometry2D union2D(Iterable<Geometry2D> geometries)
	{
		OpenSCADGeometry2DFrom2D union = javaOpenSCAD.union2D();
		for(Geometry2D geometry : geometries)
		{
			OpenSCADGeometry2D openSCADGeometry = ((Geometry2DImpl) geometry).getOpenSCADGeometry();
			union.add(openSCADGeometry);
		}
		return new Geometry2DImpl(union);
	}

	@Override
	public Geometry2D intersection2D(Iterable<Geometry2D> geometries)
	{
		OpenSCADGeometry2DFrom2D intersection = javaOpenSCAD.intersection2D();
		for(Geometry2D geometry : geometries)
		{
			OpenSCADGeometry2D openSCADGeometry = ((Geometry2DImpl) geometry).getOpenSCADGeometry();
			intersection.add(openSCADGeometry);
		}
		return new Geometry2DImpl(intersection);
	}

	@Override
	public Geometry2D difference2D(Geometry2D filled, Iterable<Geometry2D> cutouts)
	{
		OpenSCADGeometry2DFrom2D difference = javaOpenSCAD.difference2D();
		OpenSCADGeometry2D openSCADFilled = ((Geometry2DImpl) filled).getOpenSCADGeometry();
		difference.add(openSCADFilled);
		for(Geometry2D cutout : cutouts)
		{
			OpenSCADGeometry2D openSCADCutout = ((Geometry2DImpl) cutout).getOpenSCADGeometry();
			difference.add(openSCADCutout);
		}
		return new Geometry2DImpl(difference);
	}

	@Override
	public Geometry2D hull2D(Iterable<Geometry2D> geometries)
	{
		OpenSCADGeometry2DFrom2D hull = javaOpenSCAD.hull2D();
		for(Geometry2D geometry : geometries)
		{
			OpenSCADGeometry2D openSCADGeometry = ((Geometry2DImpl) geometry).getOpenSCADGeometry();
			hull.add(openSCADGeometry);
		}
		return new Geometry2DImpl(hull);
	}

	@Override
	public Geometry2D minkowski2D(Iterable<Geometry2D> geometries)
	{
		OpenSCADGeometry2DFrom2D minkowsky = javaOpenSCAD.minkowsky2D();
		for(Geometry2D geometry : geometries)
		{
			OpenSCADGeometry2D openSCADGeometry = ((Geometry2DImpl) geometry).getOpenSCADGeometry();
			minkowsky.add(openSCADGeometry);
		}
		return new Geometry2DImpl(minkowsky);
	}

	@Override
	public Geometry2D offset2D(double delta, boolean chamfer, Iterable<Geometry2D> geometries)
	{
		OpenSCADGeometry2DFrom2D offset = javaOpenSCAD.offset2D(delta, chamfer);
		for(Geometry2D geometry : geometries)
		{
			OpenSCADGeometry2D openSCADGeometry = ((Geometry2DImpl) geometry).getOpenSCADGeometry();
			offset.add(openSCADGeometry);
		}
		return new Geometry2DImpl(offset);
	}

	@Override
	public Geometry2D offsetRound2D(double radius, int angularResolution, Iterable<Geometry2D> geometries)
	{
		OpenSCADGeometry2DFrom2D offset = javaOpenSCAD.offsetRound2D(radius, angularResolution);
		for(Geometry2D geometry : geometries)
		{
			OpenSCADGeometry2D openSCADGeometry = ((Geometry2DImpl) geometry).getOpenSCADGeometry();
			offset.add(openSCADGeometry);
		}
		return new Geometry2DImpl(offset);
	}

	@Override
	public Geometry2D char2D(char ch, double width, int angularResolution)
	{
		OpenSCADGeometry2D char2D = javaOpenSCAD.text("" + ch, textAttributes, angularResolution);
		OpenSCADGeometry2DFrom2D scale2D = javaOpenSCAD.scale2D(width*textScale, width*textScale);
		scale2D.add(char2D);
		OpenSCADGeometry2D result = javaOpenSCAD.module(scale2D);
		return new Geometry2DImpl(result);
	}

	@Override
	public Geometry2D char2D(char ch, double width, double height, int angularResolution)
	{

		OpenSCADGeometry2D char2D = javaOpenSCAD.text("" + ch, textAttributes, angularResolution);
		OpenSCADGeometry2DFrom2D scale2D = javaOpenSCAD.scale2D(this.textScale*width, this.textScale*0.5*height);
		scale2D.add(char2D);
		OpenSCADGeometry2D result = javaOpenSCAD.module(scale2D);
		return new Geometry2DImpl(result);
	}

	@Override
	public double charHeight2D(double width)
	{
		return 2*width;
	}

	@Override
	public double charBaseline2D(double height)
	{
		return 0.2*height;
	}

	@Override
	public Geometry2D polygon2D(Iterable<Vector2D> vertices)
	{
		List<OpenSCADVector2D> openSCADVertices = new ArrayList<>();
		for(Vector2D vertex : vertices)
		{
			OpenSCADVector2D vector = javaOpenSCAD.vector2D(vertex.x(), vertex.y());
			openSCADVertices.add(vector);
		}
		OpenSCADGeometry2D geometry = javaOpenSCAD.polygon2D(openSCADVertices);
		OpenSCADGeometry2D result = javaOpenSCAD.module(geometry);
		return new Geometry2DImpl(result);
	}

	@Override
	public Geometry2D polygon2D(Iterable<Vector2D> vertices, Iterable<? extends Iterable<Integer>> paths)
	{
		List<OpenSCADVector2D> openSCADVertices = new ArrayList<>();
		for(Vector2D vertex : vertices)
		{
			OpenSCADVector2D vector = javaOpenSCAD.vector2D(vertex.x(), vertex.y());
			openSCADVertices.add(vector);
		}
		OpenSCADGeometry2D geometry = javaOpenSCAD.polygon2D(openSCADVertices, paths);
		OpenSCADGeometry2D result = javaOpenSCAD.module(geometry);
		return new Geometry2DImpl(result);
	}

	@Override
	public Geometry3D polyhedron3D(Iterable<Vector3D> vertices, Iterable<? extends Iterable<Integer>> faces)
	{
		List<OpenSCADVector3D> openSCADVertices = new ArrayList<>();
		for(Vector3D vertex : vertices)
		{
			OpenSCADVector3D vector = javaOpenSCAD.vector3D(vertex.x(), vertex.y(), vertex.z());
			openSCADVertices.add(vector);
		}
		OpenSCADGeometry3D geometry = javaOpenSCAD.polyhedron3D(openSCADVertices, faces);
		return new Geometry3DImpl(geometry);
	}

	@Override
	public Geometry2D project(boolean cutAtZeroZ, Geometry3D geometry)
	{
		OpenSCADGeometry3D openSCADGeometry = ((Geometry3DImpl) geometry).getOpenSCADGeometry();
		OpenSCADGeometry2DFrom3D projection = javaOpenSCAD.project(cutAtZeroZ);
		projection.add(openSCADGeometry);
		return new Geometry2DImpl(projection);
	}

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

	@Override
	public Geometry3D union3D(Iterable<Geometry3D> geometries)
	{
		OpenSCADGeometry3DFrom3D union = javaOpenSCAD.union3D();
		for(Geometry3D geometry : geometries)
		{
			OpenSCADGeometry3D openSCADGeometry = ((Geometry3DImpl) geometry).getOpenSCADGeometry();
			union.add(openSCADGeometry);
		}
		return new Geometry3DImpl(union);
	}

	@Override
	public Geometry3D intersection3D(Iterable<Geometry3D> geometries)
	{
		OpenSCADGeometry3DFrom3D intersection = javaOpenSCAD.intersection3D();
		for(Geometry3D geometry : geometries)
		{
			OpenSCADGeometry3D openSCADGeometry = ((Geometry3DImpl) geometry).getOpenSCADGeometry();
			intersection.add(openSCADGeometry);
		}
		return new Geometry3DImpl(intersection);
	}

	@Override
	public Geometry3D difference3D(Geometry3D solid, Iterable<Geometry3D> cutouts)
	{
		OpenSCADGeometry3D openSCADGeometry = ((Geometry3DImpl) solid).getOpenSCADGeometry();
		OpenSCADGeometry3DFrom3D difference = javaOpenSCAD.difference3D();
		difference.add(openSCADGeometry);
		for(Geometry3D cutout : cutouts)
		{
			OpenSCADGeometry3D openSCADCutout = ((Geometry3DImpl) cutout).getOpenSCADGeometry();
			difference.add(openSCADCutout);
		}
		return new Geometry3DImpl(difference);
	}

	@Override
	public Geometry3D hull3D(Iterable<Geometry3D> geometries)
	{
		OpenSCADGeometry3DFrom3D hull = javaOpenSCAD.hull3D();
		for(Geometry3D geometry : geometries)
		{
			OpenSCADGeometry3D openSCADGeometry = ((Geometry3DImpl) geometry).getOpenSCADGeometry();
			hull.add(openSCADGeometry);
		}
		return new Geometry3DImpl(hull);
	}

	@Override
	public Geometry3D minkowski3D(Iterable<Geometry3D> geometries)
	{
		OpenSCADGeometry3DFrom3D minkowsky = javaOpenSCAD.minkowsky3D();
		for(Geometry3D geometry : geometries)
		{
			OpenSCADGeometry3D openSCADGeometry = ((Geometry3DImpl) geometry).getOpenSCADGeometry();
			minkowsky.add(openSCADGeometry);
		}
		return new Geometry3DImpl(minkowsky);
	}

	@Override
	public Geometry3D color3D(Color color, Geometry3D geometry)
	{
		OpenSCADGeometry3DFrom3D coloredGeometry = javaOpenSCAD.color3D(color.r(), color.g(), color.b(), color.a());
		OpenSCADGeometry3D openSCADGeometry = ((Geometry3DImpl) geometry).getOpenSCADGeometry();
		coloredGeometry.add(openSCADGeometry);
		return new Geometry3DImpl(coloredGeometry);
	}

	@Override
	public Geometry3D linearExtrude(double height,
	                                Angle twist,
	                                double scale,
	                                int slices,
	                                boolean centerZ,
	                                Geometry2D geometry)
	{
		return linearExtrude(height, twist.asDegrees(), scale, slices, centerZ, geometry);
	}

	@Override
	public Geometry3D linearExtrude(double height, boolean centerZ, Geometry2D geometry)
	{
		return linearExtrude(height, 0, 1, 1, centerZ, geometry);
	}

	private Geometry3D linearExtrude(
			double height,
			double twistDegrees,
			double scale,
			int slices,
			boolean centerZ,
			Geometry2D geometry
	)
	{
		OpenSCADGeometry2D openSCADGeometry = ((Geometry2DImpl) geometry).getOpenSCADGeometry();
		OpenSCADGeometry3DFrom2D linearExtrude =
				javaOpenSCAD.linearExtrude(height, twistDegrees, scale, slices, centerZ);
		linearExtrude.add(openSCADGeometry);
		return new Geometry3DImpl(linearExtrude);
	}

	@Override
	public Geometry3D rotateExtrude(Angle angle, int angularResolution, Geometry2D geometry)
	{
		OpenSCADGeometry2D openSCADGeometry = ((Geometry2DImpl) geometry).getOpenSCADGeometry();
		OpenSCADGeometry3DFrom2D rotateExtrude =
				javaOpenSCAD.rotateExtrude(angle.asDegrees(), angularResolution);
		rotateExtrude.add(openSCADGeometry);
		return new Geometry3DImpl(rotateExtrude);
	}

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
		try
		{
			OpenSCADGeometry2D openSCADGeometry = ((Geometry2DImpl) geometry).getOpenSCADGeometry();
			javaOpenSCAD.generateOpenSCADFile("OpenSCAD/View" + windowID + ".scad", openSCADGeometry);
		} catch (IOException e)
		{
			throw new RuntimeException("Could not view geometry!", e);
		}
	}

	@Override
	public void view(Geometry3D geometry, int windowID)
	{
		try
		{
			OpenSCADGeometry3D openSCADGeometry = ((Geometry3DImpl) geometry).getOpenSCADGeometry();
			javaOpenSCAD.generateOpenSCADFile("OpenSCAD/View" + windowID + ".scad", openSCADGeometry);
		} catch (IOException e)
		{
			throw new RuntimeException("Could not view geometry!", e);
		}
	}

	@Override
	public Geometry2D cache(Geometry2D geometry)
	{
		OpenSCADGeometry2D openSCADGeometry = ((Geometry2DImpl) geometry).getOpenSCADGeometry();
		OpenSCADGeometry2D cached = javaOpenSCAD.module(openSCADGeometry);
		return new Geometry2DImpl(cached);
	}

	@Override
	public Geometry3D cache(Geometry3D geometry)
	{
		OpenSCADGeometry3D openSCADGeometry = ((Geometry3DImpl) geometry).getOpenSCADGeometry();
		try
		{
			OpenSCADGeometry3D cached = javaOpenSCAD.cacheGeometry3D(openSCADGeometry);
			return new Geometry3DImpl(cached);
		} catch (IOException e)
		{
			throw new RuntimeException("Could not cache geometry!", e);
		}

	}

	@Override
	public Geometry3D loadSTL(String fileName) throws IOException
	{
		return new Geometry3DImpl(javaOpenSCAD.loadSTL(fileName));
	}

	@Override
	public void saveSTL(String fileName, Geometry3D geometry) throws IOException
	{
		OpenSCADGeometry3D openSCADGeometry = ((Geometry3DImpl) geometry).getOpenSCADGeometry();
		javaOpenSCAD.saveSTL(fileName, openSCADGeometry);
	}

	@Override
	public Geometry3D load3MF(String fileName) throws IOException
	{
		return new Geometry3DImpl(javaOpenSCAD.load3MF(fileName));
	}

	@Override
	public void save3MF(String fileName, Geometry3D geometry) throws IOException
	{
		OpenSCADGeometry3D openSCADGeometry = ((Geometry3DImpl) geometry).getOpenSCADGeometry();
		javaOpenSCAD.save3MF(fileName, openSCADGeometry);
	}

	private static class Transform2DComposed implements Transform2D
	{
		private List<Transform2D> list;

		public Transform2DComposed(List<Transform2D> children)
		{
			for(Transform2D child : children)
			{
				if(child instanceof Transform2DIdentity)
				{
					continue;
				}
				if(child instanceof Transform2DComposed composed)
				{
					if(composed.list == null)
					{
						continue;
					}
					if(list == null)
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
					if(list == null)
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
			if(list == null)
			{
				return this;
			}
			List<Transform2D> inverseList = new ArrayList<>(list.size());
			for(Transform2D t : list.reversed())
			{
				inverseList.add(t.inverse());
			}
			return new Transform2DComposed(inverseList);
		}

		@Override
		public Vector2D transformPoint(Vector2D vector)
		{
			if(list == null)
			{
				return vector;
			}
			for(Transform2D transform : list.reversed())
			{
				vector = transform.transformPoint(vector);
			}
			return vector;
		}

		@Override
		public Vector2D transformDirection(Vector2D vector)
		{
			if(list == null)
			{
				return vector;
			}
			for(Transform2D transform : list.reversed())
			{
				vector = transform.transformDirection(vector);
			}
			return vector;
		}

		@Override
		public Geometry2D transform(Geometry2D geometry)
		{
			if(list == null)
			{
				return geometry;
			}
			for(Transform2D transform : list.reversed())
			{
				geometry = transform.transform(geometry);
			}
			return geometry;
		}

		@Override
		public Transform3D asTransform3D()
		{
			List<Transform3D> transform3DList = new ArrayList<>();
			for(Transform2D transform2D : list)
			{
				transform3DList.add(transform2D.asTransform3D());
			}
			return new Transform3DComposed(transform3DList);
		}
	}

	private class Transform2DTranslate implements Transform2D
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
			OpenSCADGeometry2DFrom2D translate = javaOpenSCAD.translate2D(x, y);
			translate.add(((Geometry2DImpl) geometry).getOpenSCADGeometry());
			return new Geometry2DImpl(translate);
		}

		@Override
		public Transform3D asTransform3D()
		{
			return translate3D(x, y, 0);
		}
	}

	private class Transform2DRotate implements Transform2D
	{
		private static final double RAD_TO_DEG = 180.0 / Math.PI;
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
			double x = vector.x();
			double y = vector.y();
			return new Vector2DImpl(x * cos - y * sin, x * sin + y * cos);
		}

		@Override
		public Vector2D transformDirection(Vector2D vector)
		{
			return transformPoint(vector);
		}

		@Override
		public Geometry2D transform(Geometry2D geometry)
		{
			OpenSCADGeometry2DFrom2D rotate = javaOpenSCAD.rotate2D(rad * RAD_TO_DEG);
			rotate.add(((Geometry2DImpl) geometry).getOpenSCADGeometry());
			return new Geometry2DImpl(rotate);
		}

		@Override
		public Transform3D asTransform3D()
		{
			return new Transform3DRotateZ(rad);
		}
	}

	private class Transform2DScale implements Transform2D
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
			OpenSCADGeometry2DFrom2D scale = javaOpenSCAD.scale2D(sx, sy);
			scale.add(((Geometry2DImpl) geometry).getOpenSCADGeometry());
			return new Geometry2DImpl(scale);
		}

		@Override
		public Transform3D asTransform3D()
		{
			return scale3D(sx, sy, 1.0);
		}
	}

	private static class Transform2DIdentity implements Transform2D
	{
		public static final Transform2DIdentity INSTANCE = new Transform2DIdentity();

		private Transform2DIdentity()
		{
		}

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

	private class Transform2DMirror implements Transform2D
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
			double x = vector.x();
			double y = vector.y();
			double d = x * normX + y * normY;
			return new Vector2DImpl(x - 2 * d * normX, y - 2 * d * normY);
		}

		@Override
		public Vector2D transformDirection(Vector2D vector)
		{
			return transformPoint(vector);
		}

		@Override
		public Geometry2D transform(Geometry2D geometry)
		{
			OpenSCADGeometry2DFrom2D mirror = javaOpenSCAD.mirror2D(normX, normY);
			mirror.add(((Geometry2DImpl) geometry).getOpenSCADGeometry());
			return new Geometry2DImpl(mirror);
		}

		@Override
		public Transform3D asTransform3D()
		{
			return mirror3D(normX, normY, 0);
		}
	}

	private static class Transform3DComposed implements Transform3D
	{
		private List<Transform3D> list;

		public Transform3DComposed(List<Transform3D> children)
		{
			for(Transform3D child : children)
			{
				if(child instanceof Transform3DIdentity)
				{
					continue;
				}
				if(child instanceof Transform3DComposed composed)
				{
					if(composed.list == null)
					{
						continue;
					}
					if(list == null)
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
					if(list == null)
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
			if(list == null)
			{
				return this;
			}
			List<Transform3D> inverseList = new ArrayList<>(list.size());
			for(Transform3D t : list.reversed())
			{
				inverseList.add(t.inverse());
			}
			return new Transform3DComposed(inverseList);
		}

		@Override
		public Vector3D transformPoint(Vector3D vector)
		{
			if(list == null)
			{
				return vector;
			}
			for(Transform3D transform : list.reversed())
			{
				vector = transform.transformPoint(vector);
			}
			return vector;
		}

		@Override
		public Vector3D transformDirection(Vector3D vector)
		{
			if(list == null)
			{
				return vector;
			}
			for(Transform3D transform : list.reversed())
			{
				vector = transform.transformDirection(vector);
			}
			return vector;
		}

		@Override
		public Geometry3D transform(Geometry3D geometry)
		{
			if(list == null)
			{
				return geometry;
			}
			for(Transform3D transform : list.reversed())
			{
				geometry = transform.transform(geometry);
			}
			return geometry;
		}
	}

	private class Transform3DTranslate implements Transform3D
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
			OpenSCADGeometry3DFrom3D translate = javaOpenSCAD.translate3D(x, y, z);
			translate.add(((Geometry3DImpl) geometry).getOpenSCADGeometry());
			return new Geometry3DImpl(translate);
		}
	}

	private class Transform3DRotateX implements Transform3D
	{
		private static final double RAD_TO_DEG = 180.0 / Math.PI;

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
			double y = vector.y();
			double z = vector.z();
			return new Vector3DImpl(vector.x(), y * cos - z * sin, y * sin + z * cos);
		}

		@Override
		public Vector3D transformDirection(Vector3D vector)
		{
			return transformPoint(vector);
		}

		@Override
		public Geometry3D transform(Geometry3D geometry)
		{
			OpenSCADGeometry3DFrom3D rotate = javaOpenSCAD.rotate3D(rad * RAD_TO_DEG, 0, 0);
			rotate.add(((Geometry3DImpl) geometry).getOpenSCADGeometry());
			return new Geometry3DImpl(rotate);
		}
	}

	private class Transform3DRotateY implements Transform3D
	{
		private static final double RAD_TO_DEG = 180.0 / Math.PI;

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
			double x = vector.x();
			double z = vector.z();
			return new Vector3DImpl(x * cos + z * sin, vector.y(), -x * sin + z * cos);
		}

		@Override
		public Vector3D transformDirection(Vector3D vector)
		{
			return transformPoint(vector);
		}

		@Override
		public Geometry3D transform(Geometry3D geometry)
		{
			OpenSCADGeometry3DFrom3D rotate = javaOpenSCAD.rotate3D(0, rad * RAD_TO_DEG, 0);
			rotate.add(((Geometry3DImpl) geometry).getOpenSCADGeometry());
			return new Geometry3DImpl(rotate);
		}
	}

	private class Transform3DRotateZ implements Transform3D
	{
		private static final double RAD_TO_DEG = 180.0 / Math.PI;

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
			double x = vector.x();
			double y = vector.y();
			return new Vector3DImpl(x * cos - y * sin, x * sin + y * cos, vector.z());
		}

		@Override
		public Vector3D transformDirection(Vector3D vector)
		{
			return transformPoint(vector);
		}

		@Override
		public Geometry3D transform(Geometry3D geometry)
		{
			OpenSCADGeometry3DFrom3D rotate = javaOpenSCAD.rotate3D(0, 0, rad * RAD_TO_DEG);
			rotate.add(((Geometry3DImpl) geometry).getOpenSCADGeometry());
			return new Geometry3DImpl(rotate);
		}
	}

	private class Transform3DScale implements Transform3D
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
			OpenSCADGeometry3DFrom3D scale = javaOpenSCAD.scale3D(sx, sy, sz);
			scale.add(((Geometry3DImpl) geometry).getOpenSCADGeometry());
			return new Geometry3DImpl(scale);
		}
	}

	private static class Transform3DIdentity implements Transform3D
	{
		public static final Transform3DIdentity INSTANCE = new Transform3DIdentity();

		private Transform3DIdentity()
		{
		}

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

	private class Transform3DMirror implements Transform3D
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
			double x = vector.x();
			double y = vector.y();
			double z = vector.z();
			double d = x * normX + y * normY + z * normZ;
			return new Vector3DImpl(x - 2 * d * normX, y - 2 * d * normY, z - 2 * d * normZ);
		}

		@Override
		public Vector3D transformDirection(Vector3D vector)
		{
			return transformPoint(vector);
		}

		@Override
		public Geometry3D transform(Geometry3D geometry)
		{
			OpenSCADGeometry3DFrom3D mirror = javaOpenSCAD.mirror3D(normX, normY, normZ);
			mirror.add(((Geometry3DImpl) geometry).getOpenSCADGeometry());
			return new Geometry3DImpl(mirror);
		}
	}


	private class Geometry2DImpl implements Geometry2D
	{
		private final OpenSCADGeometry2D geometry;
		private Vector2D min;
		private Vector2D max;

		public Geometry2DImpl(OpenSCADGeometry2D geometry)
		{
			this.geometry = geometry;
			this.min = null;
			this.max = null;
		}

		public OpenSCADGeometry2D getOpenSCADGeometry()
		{
			return geometry;
		}

		@Override
		public void debugMark()
		{
			geometry.debugMark();
		}

		@Override
		public void disable()
		{
			geometry.disable();
		}

		@Override
		public Vector2D getMin()
		{
			if(min == null)
			{
				calculateMinMax();
			}
			return min;
		}

		@Override
		public Vector2D getMax()
		{
			if(max == null)
			{
				calculateMinMax();
			}
			return max;
		}

		private void calculateMinMax()
		{
			OpenSCADVector2D minOSC = javaOpenSCAD.getMin2D(this.geometry);
			OpenSCADVector2D maxOSC = javaOpenSCAD.getMax2D(this.geometry);
			min = new Vector2DImpl(minOSC.x(), minOSC.y());
			max = new Vector2DImpl(maxOSC.x(), maxOSC.y());
		}
	}

	private class Geometry3DImpl implements Geometry3D
	{
		private final OpenSCADGeometry3D geometry;
		private Vector3D min;
		private Vector3D max;

		public Geometry3DImpl(OpenSCADGeometry3D geometry)
		{
			this.geometry = geometry;
			this.min = null;
			this.max = null;
		}

		public OpenSCADGeometry3D getOpenSCADGeometry()
		{
			return geometry;
		}

		@Override
		public void debugMark()
		{
			geometry.debugMark();
		}

		@Override
		public void disable()
		{
			geometry.disable();
		}

		@Override
		public Vector3D getMin()
		{
			if(min == null)
			{
				calculateMinMax();
			}
			return min;
		}

		@Override
		public Vector3D getMax()
		{
			if(max == null)
			{
				calculateMinMax();
			}
			return max;
		}

		private void calculateMinMax()
		{
			OpenSCADVector3D minOSC = javaOpenSCAD.getMin3D(this.geometry);
			OpenSCADVector3D maxOSC = javaOpenSCAD.getMax3D(this.geometry);
			min = new Vector3DImpl(minOSC.x(), minOSC.y(), minOSC.z());
			max = new Vector3DImpl(maxOSC.x(), maxOSC.y(), maxOSC.z());
		}
	}
}