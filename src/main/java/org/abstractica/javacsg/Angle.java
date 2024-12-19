package org.abstractica.javacsg;

/**
 * Provides an immutable angle measurement accessible in multiple units.
 * <p>
 * This interface allows retrieving an angle in rotations, degrees, or radians,
 * making it straightforward to convert between these units for various
 * geometric and trigonometric applications.
 * </p>
 */
public interface Angle
{
	/**
	 * Returns the angle as a fraction of a full rotation.
	 * <p>
	 * A value of 1.0 represents a complete 360° turn.
	 * </p>
	 *
	 * @return the angle in rotations
	 */
	double asRotations();

	/**
	 * Returns the angle measured in degrees.
	 *
	 * @return the angle in degrees
	 */
	double asDegrees();

	/**
	 * Returns the angle measured in radians.
	 *
	 * @return the angle in radians
	 */
	double asRadians();
}
