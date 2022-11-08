translate([0.0, 0.0, 15.0])
{
    linear_extrude(height = 30.0, center = true, twist = 0.0, scale = 1.0, slices = 1, convexity = 1)
    {
        M14();
    }
}

module M14()
{
    scale([10.0, 20.0])
    {
        M8();
    }
}

module M8()
{
    polygon
    (
        points =
        [
            [0.5, -0.5], [0.5, 0.5], [-0.5, 0.5], [-0.5, -0.5]
        ]
        , convexity = 1
    );
}
