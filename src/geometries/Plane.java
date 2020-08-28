package geometries;

import primitives.*;
import static primitives.Util.*;

import java.util.ArrayList;
import java.util.List;

/**
 *class to define a plane
 */
public class Plane extends Geometry
{
    /**
     * plane value
     */
    protected Point3D _p;
    protected Vector _normal;


    //----------constructors - 6 types----------------
    /**
     * constructor based on 3 point - no color and material
     * @param x
     * @param y
     * @param z
     */
    public Plane(Point3D x, Point3D y, Point3D z)
    {
        /*Vector other1 = new Vector( y.subtract(x));
        Vector other2 = new Vector(z.subtract(y));
        _normal = new Vector(other1.crossProduct(other2)).normalize();
        _p = new Point3D(z);*/
        this (Color.BLACK, x, y, z);

    }

    /**
     * A constructor based on point3D and vector normal - no color and material
     * @param x
     * @param normalVector
     */
    public Plane(Point3D x,Vector normalVector)
    {
        /*_normal=new Vector(normalVector);
        _p =new Point3D(x);*/
        this(Color.BLACK, x, normalVector);
    }

    /**
     * constructor based on 3 points and color - no material
     * @param color
     * @param x
     * @param y
     * @param z
     */
    public Plane(Color color, Point3D x, Point3D y, Point3D z)
    {
       /* super(color);
        Vector other1 = new Vector( y.subtract(x));
        Vector other2 = new Vector(z.subtract(y));
        _normal = new Vector(other1.crossProduct(other2)).normalize();
        _p = new Point3D(z);*/
        this(color, new Material(0d, 0d, 0) , x, y, z);

    }

    /**
     * constructor based on color, point and vector - no material
     * @param color
     * @param x
     * @param normalVector
     */
    public Plane (Color color, Point3D x,Vector normalVector)
    {
        /*super(color);
        _normal=new Vector(normalVector);
        _p =new Point3D(x);*/
        this(color, new Material(0d, 0d, 0),x, normalVector );

    }

    /**
     * constructor based on color, material and 3 points
     * @param color
     * @param material
     * @param x
     * @param y
     * @param z
     */
    public Plane(Color color,  Material material, Point3D x, Point3D y, Point3D z)
    {
        super(color, material);
        Vector other1 = new Vector( y.subtract(x));
        Vector other2 = new Vector(z.subtract(y));
        this._normal = new Vector(other1.crossProduct(other2)).normalize();
        this._p = new Point3D(z);

    }

    /**
     * constructor based on color, material , point and vector
     * @param color
     * @param material
     * @param x
     * @param normalVector
     */
    public Plane(Color color,  Material material, Point3D x,Vector normalVector)
    {
        super(color, material);
        this._p = x;
        this._normal = normalVector;
    }


    /**
     *
     * @return the point3D of the plane
     */
    public Point3D get_p()
    {
        return  this._p;
    }

    /**
     *
     * @return the vector of the plane
     */
    public Vector get_normal()//getter
    {
        return  this._normal;
    }



    @java.lang.Override
    public Vector getNormal (Point3D p)//inherit from geometry

    {
        return  this._normal;
    }

    /**
     * Load a function that returns the normal to the plane
     * @return the vector
     */
    public Vector getNormal ()
    {

        return getNormal(_p);
    }

    public @Override String toString()
    {
        return String.format("plane: vector: {}, point: {}",_normal.toString(), _p.toString());
    }
    @Override
    public List<GeoPoint> findIntersections(Ray ray)
    {
      
        //t = n*(Q0-p0)/N*v
        if(isZero( this._normal.dotProduct(ray.get_dir()))||isZero(this._normal.dotProduct(this.get_p().subtract(ray.get_p0()))))
            return null;
        double t = this._normal.dotProduct(this.get_p().subtract(ray.get_p0()))/this._normal.dotProduct(ray.get_dir());
        if(t<0)
            return null;
        //Ray points:
        //p  = p0 + t * v, t>=0
        Point3D p=ray.getPoint(t);

        //refactor
        GeoPoint geop = new GeoPoint(this, p);
        List<GeoPoint> l = new ArrayList<GeoPoint>();
        l.add(geop);
        return l;
    }
}
