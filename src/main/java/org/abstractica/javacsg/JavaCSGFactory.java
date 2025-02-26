package org.abstractica.javacsg;

import org.abstractica.javacsg.impl.baseimpl.JavaCSGBase;
import org.abstractica.javacsg.impl.JavaCSGImpl;
import org.abstractica.javacsg.impl.baseimpl.javaopenscad.JavaCSGBaseOpenSCADImpl;

/**
 * A factory class for creating {@link JavaCSG} instances configured in various ways.
 * <p>
 * Use this class to obtain ready-to-use {@link JavaCSG} objects for geometric modeling tasks.
 */
public class JavaCSGFactory
{
	private JavaCSGFactory()
	{
	}

	/**
	 * Creates a default-configured {@link JavaCSG} instance.
	 * <p>
	 * Use this method to obtain a {@link JavaCSG} object that that caches geometries
	 * to the file system for reuse in future runs.
	 *
	 * @return a newly created {@link JavaCSG} instance with default configuration
	 */
	public static JavaCSG createDefault()
	{
		JavaCSGBase base = new JavaCSGBaseOpenSCADImpl(true);
		JavaCSG javaCSG = new JavaCSGImpl(base);
		return javaCSG;
	}

	/**
	 * Creates a cache-configured {@link JavaCSG} instance with a custom cache directory.
	 * <p>
	 * Use this method to obtain a {@link JavaCSG} object that that caches geometries
	 * to the provided directory.
	 *
	 * @return a newly created {@link JavaCSG} instance with caching configuration
	 */
	public static JavaCSG createCached(String cacheDirectory)
	{
		JavaCSGBase base = new JavaCSGBaseOpenSCADImpl(cacheDirectory);
		JavaCSG javaCSG = new JavaCSGImpl(base);
		return javaCSG;
	}

	/**
	 * Creates a {@link JavaCSG} instance without caching.
	 * <p>
	 * Use this method when you prefer to disable caching, This is mainly useful
	 * for smaller projects or when you want to avoid writing files to the file system.
	 *
	 * @return a newly created {@link JavaCSG} instance with caching disabled
	 */
	public static JavaCSG createNoCaching()
	{
		JavaCSGBase base = new JavaCSGBaseOpenSCADImpl(false);
		JavaCSG javaCSG = new JavaCSGImpl(base);
		return javaCSG;
	}
}
