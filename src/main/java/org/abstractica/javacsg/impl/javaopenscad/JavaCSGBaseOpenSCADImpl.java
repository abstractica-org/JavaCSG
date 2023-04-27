package org.abstractica.javacsg.impl.javaopenscad;

import org.abstractica.javacsg.*;
import org.abstractica.javaopenscad.JavaOpenSCAD;
import org.abstractica.javaopenscad.impl.JavaOpenSCADImpl;
import org.abstractica.javaopenscad.intf.*;
import org.abstractica.javaopenscad.intf.text.OpenSCADTextAlignment;
import org.abstractica.javaopenscad.intf.text.OpenSCADTextAttributes;
import org.abstractica.javaopenscad.intf.text.OpenSCADTextFont;
import org.abstractica.javaopenscad.intf.text.OpenSCADTextSize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
		this.javaOpenSCAD = new JavaOpenSCADImpl(useCache);
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
	public Angle rotations(double rotations)
	{
		return new AngleImpl(rotations);
	}

	@Override
	public Angle degrees(double degrees)
	{
		return new AngleImpl(degrees * DEGREES_TO_ROTATIONS);
	}

	@Override
	public Angle radians(double radians)
	{
		return new AngleImpl(radians * RADIANS_TO_ROTATIONS);
	}

	@Override
	public Vector2D vector2D(double x, double y)
	{
		return new Vector2DImpl(x, y);
	}

	@Override
	public double sqrLength(Vector2D vector)
	{
		return vector.x() * vector.x() + vector.y() * vector.y();
	}

	@Override
	public double length(Vector2D vector)
	{
		return Math.sqrt(sqrLength(vector));
	}

	@Override
	public double dist(Vector2D vector1, Vector2D vector2)
	{
		return length(sub(vector1, vector2));
	}

	@Override
	public Vector2D normalize(Vector2D vector)
	{
		double len = length(vector);
		return vector2D(vector.x() / len, vector.y() / len);
	}

	@Override
	public Vector2D add(Vector2D vector1, Vector2D vector2)
	{
		return vector2D(vector1.x() + vector2.x(), vector1.y() + vector2.y());
	}

	@Override
	public Vector2D sub(Vector2D vector1, Vector2D vector2)
	{
		return vector2D(vector1.x() - vector2.x(), vector1.y() - vector2.y());
	}

	@Override
	public Vector2D mul(Vector2D vector, double scalar)
	{
		return vector2D(vector.x() * scalar, vector.y() * scalar);
	}

	@Override
	public Vector2D div(Vector2D vector, double scalar)
	{
		return vector2D(vector.x() / scalar, vector.y() / scalar);
	}

	@Override
	public double dot(Vector2D vector1, Vector2D vector2)
	{
		return vector1.x() * vector2.x() + vector1.y() * vector2.y();
	}

	@Override
	public Vector2D fromTo(Vector2D from, Vector2D to)
	{
		return sub(to, from);
	}

	@Override
	public Polar2D polar2D(double r, Angle phi)
	{
		return new Polar2DImpl(r, phi);
	}

	@Override
	public Polar2D asPolar2D(Vector2D vector)
	{
		return polar2D(length(vector), radians(Math.atan2(vector.y(), vector.x())));
	}

	@Override
	public Vector2D asVector2D(Polar2D polar)
	{
		return vector2D(polar.r() * Math.cos(polar.phi().asRadians()), polar.r() * Math.sin(polar.phi().asRadians()));
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
	public Transform2D identity2D()
	{
		return Transform2DIdentity.INSTANCE;
	}

	@Override
	public Transform2D compose3D(Transform2D... transforms)
	{
		return new Transform2DComposed(Arrays.asList(transforms));
	}

	@Override
	public Transform2D translate2D(double x, double y)
	{
		return new Transform2DTranslate(x, y);
	}

	@Override
	public Transform2D translate2DX(double x)
	{
		return new Transform2DTranslate(x, 0.0);
	}

	@Override
	public Transform2D translate2DY(double y)
	{
		return new Transform2DTranslate(0.0, y);
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
	public Geometry2D resize2D(double x, double y, boolean autoX, boolean autoY, Geometry2D geometry)
	{
		OpenSCADGeometry2DFrom2D resize = javaOpenSCAD.resize2D(x, y, autoX, autoY);
		OpenSCADGeometry2D openSCADGeometry = ((Geometry2DImpl) geometry).getOpenSCADGeometry();
		resize.add(openSCADGeometry);
		return new Geometry2DImpl(resize);
	}

	@Override
	public Geometry2D union2D(Geometry2D... geometries)
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
	public Geometry2D intersection2D(Geometry2D... geometries)
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
	public Geometry2D difference2D(Geometry2D filled, Geometry2D... cutouts)
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
	public Geometry2D hull2D(Geometry2D... geometries)
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
	public Geometry2D minkowski2D(Geometry2D... geometries)
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
	public Geometry2D offset2D(double delta, boolean chamfer, Geometry2D... geometries)
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
	public Geometry2D offsetRound2D(double radius, int angularResolution, Geometry2D... geometries)
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
	public Geometry2D color2D(double r, double g, double b, double a, Geometry2D... geometries)
	{
		OpenSCADGeometry2DFrom2D color = javaOpenSCAD.color2D(r, g, b, a);
		for(Geometry2D geometry : geometries)
		{
			OpenSCADGeometry2D openSCADGeometry = ((Geometry2DImpl) geometry).getOpenSCADGeometry();
			color.add(openSCADGeometry);
		}
		return new Geometry2DImpl(color);
	}

	@Override
	public Geometry2D color2D(double r, double g, double b, double a, Iterable<Geometry2D> geometries)
	{
		OpenSCADGeometry2DFrom2D color = javaOpenSCAD.color2D(r, g, b, a);
		for(Geometry2D geometry : geometries)
		{
			OpenSCADGeometry2D openSCADGeometry = ((Geometry2DImpl) geometry).getOpenSCADGeometry();
			color.add(openSCADGeometry);
		}
		return new Geometry2DImpl(color);
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
	public Geometry2D text2D(String text, double letterWidth, int angularResolution)
	{
		List<Geometry2D> chars = new ArrayList<>();
		for(int i = 0; i < text.length(); i++)
		{
			Transform2D t = translate2D(i*letterWidth, 0);
			chars.add(t.transform(char2D(text.charAt(i), letterWidth, angularResolution)));
		}
		return union2D(chars);
	}

	@Override
	public Geometry2D text2D(String text, double letterWidth, double letterHeight, int angularResolution)
	{
		List<Geometry2D> chars = new ArrayList<>();
		for(int i = 0; i < text.length(); i++)
		{
			Transform2D t = translate2D(i*letterWidth, 0);
			chars.add(t.transform(char2D(text.charAt(i), letterWidth, letterHeight, angularResolution)));
		}
		return union2D(chars);
	}

	@Override
	public Vector3D vector3D(double x, double y, double z)
	{
		return new Vector3DImpl(x, y, z);
	}

	@Override
	public double sqrLength(Vector3D vector)
	{
		return vector.x() * vector.x() + vector.y() * vector.y() + vector.z() * vector.z();
	}

	@Override
	public double length(Vector3D vector)
	{
		return Math.sqrt(sqrLength(vector));
	}

	@Override
	public double dist(Vector3D vector1, Vector3D vector2)
	{
		return length(sub(vector1, vector2));
	}

	@Override
	public Vector3D normalize(Vector3D vector)
	{
		double length = length(vector);
		return vector3D(vector.x() / length, vector.y() / length, vector.z() / length);
	}

	@Override
	public Vector3D add(Vector3D vector1, Vector3D vector2)
	{
		return vector3D(vector1.x() + vector2.x(), vector1.y() + vector2.y(), vector1.z() + vector2.z());
	}

	@Override
	public Vector3D sub(Vector3D vector1, Vector3D vector2)
	{
		return vector3D(vector1.x() - vector2.x(), vector1.y() - vector2.y(), vector1.z() - vector2.z());
	}

	@Override
	public Vector3D mul(Vector3D vector, double scalar)
	{
		return vector3D(vector.x() * scalar, vector.y() * scalar, vector.z() * scalar);
	}

	@Override
	public Vector3D div(Vector3D vector, double scalar)
	{
		return vector3D(vector.x() / scalar, vector.y() / scalar, vector.z() / scalar);
	}

	@Override
	public double dot(Vector3D vector1, Vector3D vector2)
	{
		return vector1.x() * vector2.x() + vector1.y() * vector2.y() + vector1.z() * vector2.z();
	}

	@Override
	public Vector3D fromTo(Vector3D from, Vector3D to)
	{
		return sub(to, from);
	}

	@Override
	public Geometry3D multMatrix3D(double m00, double m01, double m02, double m03,
	                               double m10, double m11, double m12, double m13,
	                               double m20, double m21, double m22, double m23,
	                               Geometry3D geometry  )
	{
		OpenSCADGeometry3D openSCADGeometry = ((Geometry3DImpl) geometry).getOpenSCADGeometry();
		OpenSCADGeometry3DFrom3D multMatrix = javaOpenSCAD.multMatrix3D(
				m00, m01, m02, m03,
				m10, m11, m12, m13,
				m20, m21, m22, m23);
		multMatrix.add(openSCADGeometry);
		return new Geometry3DImpl(multMatrix);
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
	public Transform3D compose3D(Transform3D... transforms)
	{
		return new Transform3DComposed(Arrays.asList(transforms));
	}

	@Override
	public Transform3D translate3D(double x, double y, double z)
	{
		return new Transform3DTranslate(x, y, z);
	}

	@Override
	public Transform3D translate3DX(double x)
	{
		return new Transform3DTranslate(x, 0, 0);
	}

	@Override
	public Transform3D translate3DY(double y)
	{
		return new Transform3DTranslate(0, y, 0);
	}

	@Override
	public Transform3D translate3DZ(double z)
	{
		return new Transform3DTranslate(0, 0, z);
	}

	@Override
	public Transform3D rotate3D(Angle angleX, Angle angleY, Angle angleZ)
	{
		Transform3D rotX = new Transform3DRotateX(angleX.asRadians());
		Transform3D rotY = new Transform3DRotateY(angleY.asRadians());
		Transform3D rotZ = new Transform3DRotateZ(angleZ.asRadians());
		return new Transform3DComposed(Arrays.asList(rotX, rotY, rotZ));
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
	public Geometry3D union3D(Geometry3D... geometries)
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
	public Geometry3D intersection3D(Geometry3D... geometries)
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
	public Geometry3D difference3D(Geometry3D solid, Geometry3D... cutouts)
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
	public Geometry3D hull3D(Geometry3D... geometries)
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
	public Geometry3D minkowski3D(Geometry3D... geometries)
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
	public Geometry3D linearExtrude(double height,
	                                Angle twist,
	                                double scale,
	                                int slices,
	                                boolean centerZ,
	                                Geometry2D geometry)
	{
		OpenSCADGeometry2D openSCADGeometry = ((Geometry2DImpl) geometry).getOpenSCADGeometry();
		OpenSCADGeometry3DFrom2D linearExtrude =
				javaOpenSCAD.linearExtrude(height, twist.asDegrees(), scale, slices, centerZ);
		linearExtrude.add(openSCADGeometry);
		return new Geometry3DImpl(linearExtrude);
	}

	@Override
	public Geometry3D linearExtrude(double height, boolean centerZ, Geometry2D geometry)
	{
		return linearExtrude(height, degrees(0), 1, 1, centerZ, geometry);
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
	public Geometry3D loadSTL(String name) throws IOException
	{
		return new Geometry3DImpl(javaOpenSCAD.loadSTL(name));
	}

	@Override
	public void saveSTL(String name, Geometry3D geometry) throws IOException
	{
		OpenSCADGeometry3D openSCADGeometry = ((Geometry3DImpl) geometry).getOpenSCADGeometry();
		javaOpenSCAD.saveSTL(name, openSCADGeometry);
	}

	private static class Transform2DComposed implements Transform2D
	{
		private List<Transform2D> list;

		public Transform2DComposed(List<Transform2D> children)
		{
			for(Transform2D child : children)
			{
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
					if(!(child instanceof Transform2DIdentity))
					{
						list.add(child);
					}
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
			for(int i = list.size() - 1; i >= 0; i--)
			{
				inverseList.add(list.get(i).inverse());
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
			for(Transform2D transform : list)
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
			for(Transform2D transform : list)
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
			for(Transform2D transform : list)
			{
				geometry = transform.transform(geometry);
			}
			return geometry;
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
	}

	private static class Transform3DComposed implements Transform3D
	{
		private List<Transform3D> list;

		public Transform3DComposed(List<Transform3D> children)
		{
			for(Transform3D child : children)
			{
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
					if(!(child instanceof Transform3DIdentity))
					{
						list.add(child);
					}
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
			for(int i = list.size() - 1; i >= 0; i--)
			{
				inverseList.add(list.get(i).inverse());
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
			for(Transform3D transform : list)
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
			for(Transform3D transform : list)
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
			for(Transform3D transform : list)
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


	private static class Vector2DImpl implements Vector2D
	{
		private final double x;
		private final double y;

		public Vector2DImpl(double x, double y)
		{
			this.x = x;
			this.y = y;
		}

		@Override
		public double x()
		{
			return x;
		}

		@Override
		public double y()
		{
			return y;
		}

		@Override
		public String toString()
		{
			return "Vector2D(" + x + ", " + y + ")";
		}
	}

	private static class Polar2DImpl implements Polar2D
	{
		private final double r;
		private final Angle phi;

		public Polar2DImpl(double r, Angle phi)
		{
			this.r = r;
			this.phi = phi;
		}

		@Override
		public double r()
		{
			return r;
		}

		@Override
		public Angle phi()
		{
			return phi;
		}

		@Override
		public String toString()
		{
			return "Polar2D(" + r + ", " + phi.asDegrees() + " deg)";
		}
	}

	private static class Vector3DImpl implements Vector3D
	{
		private final double x;
		private final double y;
		private final double z;

		public Vector3DImpl(double x, double y, double z)
		{
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public double x()
		{
			return x;
		}

		@Override
		public double y()
		{
			return y;
		}

		@Override
		public double z()
		{
			return z;
		}

		@Override
		public String toString()
		{
			return "Vector3D(" + x + ", " + y + ", " + z + ")";
		}
	}

	private static class AngleImpl implements Angle
	{
		private static final double ROTATIONS_TO_DEGREES = 360.0;
		private static final double ROTATIONS_TO_RADIANS = 2.0 * Math.PI;
		private final double rotations;

		public AngleImpl(double rotations)
		{
			this.rotations = rotations;
		}

		@Override
		public double asRotations()
		{
			return rotations;
		}

		@Override
		public double asDegrees()
		{
			return rotations * ROTATIONS_TO_DEGREES;
		}

		@Override
		public double asRadians()
		{
			return rotations * ROTATIONS_TO_RADIANS;
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