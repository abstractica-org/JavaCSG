package org.abstractica.javacsg.impl.baseimpl.manifold;

import manifold3d.Manifold;
import manifold3d.manifold.MeshGL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class STLWriter
{
	public static void write(String fileName, Manifold manifold) throws IOException
	{
		MeshGL mesh = manifold.getMesh();
		int numProp = mesh.numProp();
		int numTri = mesh.NumTri();

		float[] vertProps = mesh.vertProperties().toFloatArray();
		int[] triVerts = mesh.triVerts().toIntArray();

		File file = new File(fileName);
		File parent = file.getParentFile();
		if (parent != null && !parent.exists())
		{
			parent.mkdirs();
		}

		ByteBuffer buf = ByteBuffer.allocate(80 + 4 + numTri * 50);
		buf.order(ByteOrder.LITTLE_ENDIAN);

		// 80-byte header
		byte[] header = new byte[80];
		byte[] tag = "Binary STL - JavaCSG Manifold".getBytes();
		System.arraycopy(tag, 0, header, 0, Math.min(tag.length, 80));
		buf.put(header);

		// Number of triangles
		buf.putInt(numTri);

		// Each triangle: normal (3 floats) + 3 vertices (9 floats) + attribute (1 short)
		for (int t = 0; t < numTri; t++)
		{
			int i0 = triVerts[t * 3];
			int i1 = triVerts[t * 3 + 1];
			int i2 = triVerts[t * 3 + 2];

			float x0 = vertProps[i0 * numProp];
			float y0 = vertProps[i0 * numProp + 1];
			float z0 = vertProps[i0 * numProp + 2];
			float x1 = vertProps[i1 * numProp];
			float y1 = vertProps[i1 * numProp + 1];
			float z1 = vertProps[i1 * numProp + 2];
			float x2 = vertProps[i2 * numProp];
			float y2 = vertProps[i2 * numProp + 1];
			float z2 = vertProps[i2 * numProp + 2];

			// Compute face normal via cross product
			float e1x = x1 - x0, e1y = y1 - y0, e1z = z1 - z0;
			float e2x = x2 - x0, e2y = y2 - y0, e2z = z2 - z0;
			float nx = e1y * e2z - e1z * e2y;
			float ny = e1z * e2x - e1x * e2z;
			float nz = e1x * e2y - e1y * e2x;
			float len = (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
			if (len > 0)
			{
				nx /= len;
				ny /= len;
				nz /= len;
			}

			buf.putFloat(nx);
			buf.putFloat(ny);
			buf.putFloat(nz);
			buf.putFloat(x0);
			buf.putFloat(y0);
			buf.putFloat(z0);
			buf.putFloat(x1);
			buf.putFloat(y1);
			buf.putFloat(z1);
			buf.putFloat(x2);
			buf.putFloat(y2);
			buf.putFloat(z2);
			buf.putShort((short) 0); // attribute byte count
		}

		buf.flip();
		try (FileOutputStream fos = new FileOutputStream(file))
		{
			fos.getChannel().write(buf);
		}
	}

	public static MeshGL read(String fileName) throws IOException
	{
		byte[] data = Files.readAllBytes(Path.of(fileName));
		ByteBuffer buf = ByteBuffer.wrap(data);
		buf.order(ByteOrder.LITTLE_ENDIAN);

		// Skip 80-byte header
		buf.position(80);
		int numTri = buf.getInt();

		// De-duplicate vertices using exact float bit representation as key
		Map<String, Integer> vertexMap = new HashMap<>();
		List<float[]> uniqueVerts = new ArrayList<>();
		long[] triIndices = new long[numTri * 3];

		for (int t = 0; t < numTri; t++)
		{
			// Skip normal (3 floats)
			buf.getFloat();
			buf.getFloat();
			buf.getFloat();

			for (int v = 0; v < 3; v++)
			{
				float x = buf.getFloat();
				float y = buf.getFloat();
				float z = buf.getFloat();

				String key = Float.floatToIntBits(x) + "," +
						Float.floatToIntBits(y) + "," +
						Float.floatToIntBits(z);
				Integer idx = vertexMap.get(key);
				if (idx == null)
				{
					idx = uniqueVerts.size();
					uniqueVerts.add(new float[]{x, y, z});
					vertexMap.put(key, idx);
				}
				triIndices[t * 3 + v] = idx;
			}

			// Skip attribute byte count
			buf.getShort();
		}

		// Build MeshGL
		int numVert = uniqueVerts.size();
		float[] flatVerts = new float[numVert * 3];
		for (int i = 0; i < numVert; i++)
		{
			float[] v = uniqueVerts.get(i);
			flatVerts[i * 3] = v[0];
			flatVerts[i * 3 + 1] = v[1];
			flatVerts[i * 3 + 2] = v[2];
		}

		MeshGL mesh = new MeshGL();
		mesh.numProp(3);
		mesh.vertProperties(manifold3d.FloatVector.FromArray(flatVerts));
		mesh.triVerts(manifold3d.UIntVector.FromArray(triIndices));

		return mesh;
	}
}
