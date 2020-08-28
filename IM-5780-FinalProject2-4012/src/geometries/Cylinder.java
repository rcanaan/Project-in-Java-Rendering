package geometries;
import primitives.*;

import java.util.List;

/**
 *class cylinder to define a cylinder
 */
public class Cylinder extends Tube
{
    /**
     *cylinder value
     */
    double _height;


    /**
     *A constructor that receives height ,ray and radius
     * @param heightX
     * @param axisRayX
     * @param radius
     */
    public Cylinder(double heightX ,Ray axisRayX, double radius)
    {
       // super(axisRayX,radius);
        //_height =heightX;
        this(Color.BLACK, heightX,radius,  axisRayX);
    }

    /**
     *
     * @param color
     * @param material
     * @param heightX
     * @param axisRayX
     * @param radius
     */
    public Cylinder(Color color,Material material,  double heightX, Ray axisRayX,double radius)
    {
        super(color, axisRayX,radius);
        this._material = material;
        try
        {
            if(heightX > 0)//height can't be negative
                _height =heightX;
            else
                throw new IllegalArgumentException("the height of a Cylinder cannot be zero or nagitive");
        }
        catch(IllegalArgumentException ex)
        {
            System.out.println(ex);
        }

    }

    /**
     *
     * @param color
     * @param h
     * @param radius
     * @param ray
     */
    public Cylinder(Color color,double h, double radius, Ray ray)
    {
        this(color, new Material(0d, 0d, 0), h,ray, radius);
    }


    /**
     *
     * @return the height of the cylinder
     */
    public double get_height()
    {
        return this._height;
    }


    /**
     * a function to find normal in a given point
     * @param point
     * @return
     */
    @Override
    public Vector getNormal(Point3D point)
    {

        return super.getNormal(point);
    }

    /**
     * to string func
     * @return
     */
    public @Override String toString()
    {
        return String.format("Cylinder: height", _height);
    }
    @Override
    public List <GeoPoint> findIntersections(Ray ray)
    {
        return null;
    }


}
