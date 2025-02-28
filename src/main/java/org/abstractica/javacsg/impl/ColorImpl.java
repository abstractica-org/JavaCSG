package org.abstractica.javacsg.impl;

import org.abstractica.javacsg.Color;

public class ColorImpl implements Color
{
    private final double r;
    private final double g;
    private final double b;
    private final double a;

    public ColorImpl(double r, double g, double b, double a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    @Override
    public String toString()
    {
        return "Color(" + r + ", " + g + ", " + b + ", " + a + ")";
    }

    @Override
    public double r()
    {
        return r;
    }

    @Override
    public double g()
    {
        return g;
    }

    @Override
    public double b()
    {
        return b;
    }

    @Override
    public double a()
    {
        return a;
    }
}
