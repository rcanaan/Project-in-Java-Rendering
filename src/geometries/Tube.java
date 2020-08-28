package geometries;

import primitives.*;
import primitives.Util;

import java.util.List;

/**
 *class to define a tube
 */
public class Tube extends RadialGeometry /*implements Geometry*/
{
    /**
     * tube value
     */
    protected Ray _axisRay;
    /**
     * A constructor that gets ray and radius
     * @param r
     * @param radius
     */
    public Tube(Ray r,double radius)
    {
        //super(radius);
        //_axisRay =new Ray(r);
        this(Color.BLACK, new Material(0, 0, 0), r, radius);
    }
    public Tube(Color color, Ray r,double radius)
    {
      //  super(color, radius);
        this(color, new Material(0, 0, 0), r, radius);
       // _axisRay =new Ray(r);
    }

    public Tube(Color color, Material material, Ray r, double radius)
    {
        super(color, radius);
        this._material = material;
        this._axisRay = new Ray(r);
    }
    /**
     *
     * @return the ray in the tube
     */
    public Ray get_axisRay()
    {
        return  this._axisRay;
    }



    @Override

    public Vector getNormal(Point3D p)
    {

        Point3D p0 = _axisRay.get_p0();
        Vector v = _axisRay.get_dir();
        //t = v (P – P0)
        double t = p.subtract(p0).dotProduct(v);
        //System.out.println(t);
        // O = P0 + tv
        Point3D o=null;
        if (!Util.isZero(t))// if it's close to 0, we'll get ZERO vector exception
            o = p0.add(v.scale(t));
        //System.out.println(o);
        Vector n = p.subtract(o).normalize();
        //System.out.println(n);
        return n;

    }
    public List<GeoPoint> findIntersections(Ray ray)
    {
        return null;
    }
}
