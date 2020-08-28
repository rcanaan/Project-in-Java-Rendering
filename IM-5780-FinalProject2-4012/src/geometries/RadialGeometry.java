package geometries;

import primitives.Color;
import primitives.Material;

/**
 * abstract class to define radial geometry' contains a radius
 */
public abstract class RadialGeometry extends Geometry
{
    /**
     * radial geometry value
     */
    protected double _radius;


    /**
     * constructor based on color , material, and radius
     * @param emissionLight
     * @param material
     * @param radius
     */
    public RadialGeometry (Color emissionLight, Material material, double radius)
    {
        super(emissionLight, material);
        if (primitives.Util.isZero(radius) || (radius < 0.0))
            throw new IllegalArgumentException("radius "+ _radius +" is not valid");

        this._radius = radius;
    }

    /**
     * constructor based on color and radius - using the constructor above
     * @param emissionLight
     * @param radius
     */
    public RadialGeometry(Color emissionLight, double radius)
    {
        super(emissionLight);

        if (primitives.Util.isZero(radius) || (radius < 0.0))
            throw new IllegalArgumentException("radius "+ _radius +" is not valid");

        this._radius = radius;
    }

    /**
     *A constructor that gets radius
     * @param r
     */
    RadialGeometry(double r)
    {
        super();
        this._radius= r;
    }

    /**
     *A constractor that gets Radial Geometry
     * @param radGeo
     */
    RadialGeometry(RadialGeometry radGeo)
    {
        this._radius= radGeo.getRadius();
    }

    /**
     *
     * @return the radius
     */
    public double getRadius() {
        return  this._radius;
    }
}
