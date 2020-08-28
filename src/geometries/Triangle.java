package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.isZero;

/**
 *class to define a triangle
 */
public class Triangle extends Polygon /*extends Geometry*/
{
    /**
     *A constructor that gets 3 point3D
     * @param a - first point
     * @param b - second point
     * @param c - third point
     */
    public Triangle(Point3D a,Point3D b,Point3D c)
    {
        super(a,b,c);//sending to the father's constructor
    }

    /**
     *constructor based on color, and 3 points
     * @param emission - the intensity of the color - the light that emerge from the geometry
     * @param a - first point
     * @param b - second point
     * @param c - third point
     */
    public Triangle(Color emission, Point3D a, Point3D b, Point3D c)
    {
        super(emission, a, b, c);//sending to the father's constructor
    }


    /**
     * constructor based on color, material and 3 points
     * @param emission - the intensity of the color - the light that emerge from the geometry
     * @param material
     * @param a
     * @param b
     * @param c
     */
    public Triangle(Color emission,Material material, Point3D a, Point3D b, Point3D c)
    {
        super(emission,material, a, b, c);//sending to the father's constructor
    }
    public @Override String toString ()
    {
        return String.format("point 1: {}\npoint2: {}\npoint3: {})", _vertices.get(0),_vertices.get(1),_vertices.get(2));
    }

    /**
     * a function to find normal in a given point
     * @param point
     * @return vector
     */
    @Override
    public Vector getNormal(Point3D point)
    {
        return super.getNormal(point);
        //return null;
    }

    /***
     * finding intersections between ray and triangle. based on plane
     * therefore- we will check first with the plane's function , and than we will check if there is point inside the
     * triangle itself. this we will do by constructing more 3 triangles that lay on the original triangle's sides,
     * and will check their projection - if for every side of the original triangle all their projection are equal by sign - the point is inside the triangle.
     *
     * @param ray -
     * @return
     */
    @java.lang.Override
    public List<GeoPoint> findIntersections(Ray ray)
    {
        List<GeoPoint> intersections = _plane.findIntersections(ray);//the triangle is 2 dimension - this is sort of plane
        if (intersections == null)//if there are no intersection point with the ray and the plane - therefore with the triangle
            return null;

        Point3D p0 = ray.get_p0();
        Vector v = ray.get_dir();

        Vector v1 = _vertices.get(0).subtract(p0);
        Vector v2 = _vertices.get(1).subtract(p0);
        Vector v3 = _vertices.get(2).subtract(p0);

        double s1 = v.dotProduct(v1.crossProduct(v2));//v*N1 = (v*(v1 vectorial mult v2))
        if (isZero(s1))//if the projection is 0
            return null;
        double s2 = v.dotProduct(v2.crossProduct(v3));
        if (isZero(s2))
            return null;
        double s3 = v.dotProduct(v3.crossProduct(v1));
        if (isZero(s3))
            return null;

        for (int i=0;i<intersections.size();i++)//setting all the intersection point from plane to triangle
            intersections.get(i).setGeometry(this);//triangle


        return ((s1 > 0 && s2 > 0 && s3 > 0) || (s1 < 0 && s2 < 0 && s3 < 0)) ? intersections : null;//if both of all the signs are positive - it means there is intersection point
    }
}
