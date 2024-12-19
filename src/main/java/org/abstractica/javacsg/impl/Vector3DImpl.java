package org.abstractica.javacsg.impl;

import org.abstractica.javacsg.Vector3D;

public class Vector3DImpl implements Vector3D
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
