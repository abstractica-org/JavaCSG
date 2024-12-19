package org.abstractica.javacsg.impl;

import org.abstractica.javacsg.Angle;

public class AngleImpl implements Angle
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
