package geometries;
import static java.lang.StrictMath.sqrt;
import static primitives.Util.*;

import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *class to define a sphere
 */
public class Sphere extends RadialGeometry/* implements Geometry*/
{
    /**
     *sphere value
     */
    protected Point3D _center;

    /**
     *A constructor based on point3D and radius
     * @param p
     */
    public Sphere(Point3D p, double radus)
    {
        //super(radus);
        //_center = new Point3D(p);
        this(Color.BLACK,new Material(0,0,0),p,radus);
    }

    /**
     * constructor based on color, rasius and point
     * @param color
     * @param p
     * @param radus
     */
    public Sphere(Color color ,Point3D p, double radus)
    {
        //super(color, radus);
       // _center = new Point3D(p);
        this(Color.BLACK,new Material(0,0,0),p, radus);

    }

    public Sphere(Color color, Material material ,Point3D p, double radius)
    {
        super(color, material, radius);
        this._center = new Point3D(p);

    }

    /**
     *
     * @return point3D of the sphere
     */
    public Point3D get_center()

    {
        return  this._center;
    }
    public @Override String toString()
    {
        return String.format("sphere: point center: {}", _center.toString());
    }


    /**
     * a function to find normal in a given point
     * @param p
     * @return
     */
    @java.lang.Override
    public Vector getNormal(Point3D p)
    {
        return new Vector(p.subtract( this._center).normalize());
    }


    @java.lang.Override
    public List<GeoPoint> findIntersections(Ray ray)
    {
        //u= 0-P0
        Vector u;
        try
        {

         u = new Vector(this.get_center().subtract(ray.get_p0()));
        }
        catch (IllegalArgumentException e)
        {
            //refactoring
            double t=this.getRadius();
            Point3D p= ray.get_p0().add(ray.get_dir().scale(t));

            GeoPoint geop = new GeoPoint(this, p);

            List<GeoPoint> l = new ArrayList<GeoPoint>();
            l.add(geop);
            return l;

        }
        //tm = v * u
        double tm =ray.get_dir().dotProduct(u);
        //d=sqrt(|u|^2-tm^2)
        double d =sqrt( u.lengthSquared()-(tm*tm));
        //if (d>r) there are no intersections
        if(d>this.getRadius())
            return null;
        //th=aqrt(r^2-d^2)
        double th = sqrt((this.getRadius()*this.getRadius())-(d*d));

        //t1,2 = tm +-th
        double t1 =tm+th;
        double t2 = tm-th;
        //Ray points:
        //p = p0 + t * v, t>=0
        Point3D p1=null;
        if(t1>0)
            p1 = ray.getPoint(t1);

        Point3D p2=null;
        if(t2>0)
            p2 = ray.getPoint(t2);


        List<GeoPoint> l = new ArrayList<GeoPoint>();
        if(p1==null&& p2==null|| p1.equals(p2))
            return null;
        if(p1!=null&& !p1.equals(ray.get_p0()))
        {

            GeoPoint geop1 = new GeoPoint(this, p1);
            l.add(geop1);
        }

        if(p2!=null&& !p2.equals(ray.get_p0()))
        {
            GeoPoint geop2 = new GeoPoint(this, p2);
            l.add(geop2);
        }


        return l;
    }
}
