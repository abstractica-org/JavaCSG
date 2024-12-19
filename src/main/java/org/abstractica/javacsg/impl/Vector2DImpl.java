package org.abstractica.javacsg.impl;

import org.abstractica.javacsg.Vector2D;

public class Vector2DImpl implements Vector2D
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
