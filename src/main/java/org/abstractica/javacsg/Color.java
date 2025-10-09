package org.abstractica.javacsg;

/**
 * Provides an immutable color.
 * <p>
 * This interface represents a color.
 * </p>
 */
public interface Color
{
    /**
     * Returns the r-component of this color.
     *
     * @return the r-component as a double in the range [0, 1]
     */
    double r();

    /**
     * Returns the g-component of this color.
     *
     * @return the g-component as a double in the range [0, 1]
     */
    double g();

    /**
     * Returns the b-component of this color.
     *
     * @return the b-component as a double in the range [0, 1]
     */
    double b();

    /**
     * Returns the a-component of this color.
     *
     * @return the a-component as a double in the range [0, 1]
     */
    double a();
}
