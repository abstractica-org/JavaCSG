package org.abstractica.javacsg.impl;

import org.abstractica.javacsg.Angle;
import org.abstractica.javacsg.Polar2D;

public class Polar2DImpl implements Polar2D
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
