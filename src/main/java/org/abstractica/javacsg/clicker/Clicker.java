package org.abstractica.javacsg.clicker;

import org.abstractica.javacsg.*;

import java.util.ArrayList;
import java.util.List;

public class Clicker
{
	private final JavaCSG jcsg;
	//Tolerances
	private final double dx = 0.1;
	private final double dy = 0.2;
	//Sizes
	private final double unitHeight = 6.0;
	private final double clickerWidth = 8.0;
	private final double clickerCutoutWidth = 5.0;
	private final double barbSize = 1.0;
	private final double slitWidth = 2.0;
	private final double slitHeight = 8.0;

	//Calculated values
	private final double width = 0.5 * clickerWidth;

	public Clicker(JavaCSG jcsg)
	{
		this.jcsg = jcsg;
	}


	public Geometry3D createClicker(boolean[] units)
	{
		if(units.length < 2)
		{
			throw new IllegalArgumentException("units.length must be at least 2");
		}
		final double height = unitHeight * units.length;
		final double bottom = -0.5*height;
		final double bottomCenter = bottom + 0.5*unitHeight;
		final double top = 0.5*height;
		final double topCenter = top - 0.5*unitHeight;

		List<Vector2D> vertices = new ArrayList<>();
		//Bottom part
		vertices.add(jcsg.vector2D(0, bottom+dy));
		vertices.add(jcsg.vector2D(width-dx, bottom+dy));
		vertices.add(jcsg.vector2D(width-dx, bottomCenter-1.5*barbSize));
		vertices.add(jcsg.vector2D(width-barbSize-dx, bottomCenter-0.5*barbSize));
		//Top part
		vertices.add(jcsg.vector2D(width-barbSize-dx, topCenter+0.5*barbSize));
		vertices.add(jcsg.vector2D(width-dx, topCenter+1.5*barbSize));
		vertices.add(jcsg.vector2D(width-barbSize-dx, top-dy));
		vertices.add(jcsg.vector2D(0, top-dy));

		Geometry2D profile = jcsg.polygon2D(vertices);
		Geometry3D result = jcsg.rotateExtrude(jcsg.rotations(1), 64, profile);

		List<Geometry3D> unionList = new ArrayList<>();
		unionList.add(result);
		double start = bottomCenter;
		Geometry3D ridge = jcsg.rotate3DZ(jcsg.degrees(90)).transform(ridge());
		for(int i = 0; i < units.length; i++)
		{
			if(units[i])
			{
				Transform3D t = jcsg.translate3DZ(start + i * unitHeight);
				unionList.add(t.transform(ridge));
			}
		}
		result = jcsg.union3D(unionList);

		Geometry3D boxCutout = jcsg.boxCorners3D
				(
						-0.5*slitWidth,
						-0.5*clickerWidth-1.0,
						top-slitHeight,
						0.5*slitWidth,
						0.5*clickerWidth+1.0,
						top+1.0
				);
		result = jcsg.difference3D(result, boxCutout);
		Geometry3D constrainBox = jcsg.boxCorners3D
				(
						-0.5*clickerWidth-1.0,
						-0.5*clickerCutoutWidth,
						bottom-1.0,
						0.5*clickerWidth+1.0,
						0.5*clickerCutoutWidth,
						top+1.0
				);
		result = jcsg.intersection3D(result, constrainBox);


		result = jcsg.rotate3DX(jcsg.degrees(-90)).transform(result);
		return result;
	}

	public Geometry3D createClickerCutout()
	{
		double bottom = -0.5*unitHeight;
		double top = 0.5*unitHeight;
		List<Vector2D> vertices = new ArrayList<>();
		//Bottom part
		vertices.add(jcsg.vector2D(0, bottom-1.0));
		vertices.add(jcsg.vector2D(width+dx, bottom-1.0));
		vertices.add(jcsg.vector2D(width+dx, -1.5*barbSize));
		vertices.add(jcsg.vector2D(width-barbSize+dx, -0.5*barbSize));
		//Top part
		vertices.add(jcsg.vector2D(width-barbSize+dx, 0.5*barbSize));
		vertices.add(jcsg.vector2D(width+dx, 1.5*barbSize));
		vertices.add(jcsg.vector2D(width+dx, top+1.0));
		vertices.add(jcsg.vector2D(0, top+1.0));

		Geometry2D profile = jcsg.polygon2D(vertices);
		Geometry3D result = jcsg.rotateExtrude(jcsg.rotations(1), 64, profile);
		result = jcsg.union3D(result, ridge(), jcsg.rotate3DZ(jcsg.degrees(90)).transform(ridge()));
		return result;
	}

	private Geometry3D ridge()
	{
		double ridgeWidth = 0.6;
		double innerRadius = 0.5*clickerWidth - barbSize;
		List<Vector2D> vertices = new ArrayList<>();
		vertices.add(jcsg.vector2D(0.5*ridgeWidth, -innerRadius-ridgeWidth));
		vertices.add(jcsg.vector2D(3.5*ridgeWidth, -innerRadius+2*ridgeWidth));
		vertices.add(jcsg.vector2D(3.5*ridgeWidth, innerRadius-2*ridgeWidth));
		vertices.add(jcsg.vector2D(0.5*ridgeWidth, innerRadius+ridgeWidth));
		vertices.add(jcsg.vector2D(-0.5*ridgeWidth, innerRadius+ridgeWidth));
		vertices.add(jcsg.vector2D(-3.5*ridgeWidth, innerRadius-2*ridgeWidth));
		vertices.add(jcsg.vector2D(-3.5*ridgeWidth, -innerRadius+2*ridgeWidth));
		vertices.add(jcsg.vector2D(-0.5*ridgeWidth, -innerRadius-ridgeWidth));
		Geometry2D profile = jcsg.polygon2D(vertices);
		Geometry3D result = jcsg.linearExtrude(3*barbSize, true, profile);
		return result;
	}
}
