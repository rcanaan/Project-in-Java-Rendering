package unittests;

import org.junit.Test;
import geometries.*;
import primitives.*;
import geometries.Sphere;
import geometries.Intersectable.GeoPoint;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class SphereTest {

    @Test
    public void getNormal()
    {
        Sphere s = new Sphere(new Point3D(0,0,0),1);
        Point3D p =new Point3D(1,0,0);
        // ============ Equivalence Partitions Tests ==============
        assertEquals("getNormal() result is not good",s.getNormal(p),new Vector(1,0,0));
    }

    @Test
    public void findIntersections()
    {
       /* Sphere sphere = new Sphere( new Point3D(1, 0, 0),1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("Ray's line out of sphere", null, sphere.findIntersections(new Ray( new Vector(1, 1, 0),new Point3D(-1, 0, 0))));
        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<GeoPoint> result = sphere.findIntersections(new Ray( new Vector(3, 1, 0),new Point3D(-1, 0, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        
        if (result.get(0).point.get_x().get() > result.get(1).point.get_x().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p1, p2), result);
        

        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(1, 0.6, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        
        assertEquals("Ray crosses sphere", List.of(new Point3D(1.8, 0.6, 0)), result);


        // TC04: Ray starts after the sphere (0 points)
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(3, 1.5, 0)));
        assertEquals("Wrong number of points", null, result);


        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(0.4, 0.8, 0)));
        assertEquals("Wrong number of points", 1, result.size());

        assertEquals("Ray crosses sphere", List.of(new Point3D(1.6, 0.8, 0)), result);


        // TC12: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(0.4, 0.8, 0)));
        assertEquals("Wrong number of points", 1, result.size());

        assertEquals("Ray crosses sphere", List.of(new Point3D(1.6, 0.8, 0)), result);


        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(-2, 0, 0)));
        assertEquals("Wrong number of points", 2, result.size());

        if (result.get(0).getPoint().get_x().get() > result.get(1).getPoint().get_x().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(new Point3D(0, 0, 0),new Point3D(2,0,0)), result);

        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray( new Vector(0, -1, 0),new Point3D(1, 1, 0)));
        assertEquals("Wrong number of points", 1, result.size());

        assertEquals("Ray crosses sphere", List.of(new Point3D(1, -1, 0)), result);

        // TC15: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(0.4, 0, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of(new Point3D(2, 0, 0)), result);

        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntersections(new Ray( new Vector(0, 1, 0),new Point3D(1, 0, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere", List.of(new Point3D(1, 1, 0)), result);

        // TC17: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(0.4, 0.8, 0)));
        assertEquals("Wrong number of points", 1, result.size());

        assertEquals("Ray crosses sphere", List.of(new Point3D(1.6, 0.8, 0)), result);

        // TC18: Ray starts after sphere (0 points)
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(88, 0, 0)));
        assertEquals("Wrong number of points", null, result);

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(0, -1, 0)));
        assertEquals("Wrong number of points", null, result);

        // TC20: Ray starts at the tangent point
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(1, 1, 0)));
        assertEquals("Wrong number of points", null, result);

        // TC21: Ray starts after the tangent point
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(84, 0, 0)));
        assertEquals("Wrong number of points", null, result);
        
                // **** Group: Special cases
                // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
                result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(0.4, 3, 0)));
        assertEquals("Wrong number of points", null, result);*/


        Sphere sphere = new Sphere( new Point3D(1, 0, 0),1d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("Ray's line out of sphere", null, sphere.findIntersections(new Ray( new Vector(1, 1, 0),new Point3D(-1, 0, 0))));
        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<GeoPoint> result = sphere.findIntersections(new Ray( new Vector(3, 1, 0),new Point3D(-1, 0, 0)));
        assertEquals("Wrong number of points", 2, result.size());

        if (result.get(0).point.get_x().get() > result.get(1).point.get_x().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(new GeoPoint(sphere,p1),new GeoPoint(sphere,p2)), result);


        // TC03: Ray starts inside the sphere (1 point)
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(1, 0.6, 0)));
        assertEquals("Wrong number of points", 1, result.size());

        List <GeoPoint> t = new ArrayList<GeoPoint>();
        t.add(new GeoPoint(sphere,new Point3D(1.8, 0.6, 0)));
        assertEquals("Ray crosses sphere", t, result);


        // TC04: Ray starts after the sphere (0 points)
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(3, 1.5, 0)));
        assertEquals("Wrong number of points", null, result);


        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(0.4, 0.8, 0)));
        assertEquals("Wrong number of points", 1, result.size());


        assertEquals("Ray crosses sphere", List.of(new GeoPoint(sphere,new Point3D(1.6, 0.8, 0))), result);
//List.of(new GeoPoint(sphere,new Point3D(1.6, 0.8, 0)),

        // TC12: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(0.4, 0.8, 0)));
        assertEquals("Wrong number of points", 1, result.size());

        assertEquals("Ray crosses sphere", List.of(new GeoPoint(sphere,new Point3D(1.6, 0.8, 0))), result);
       // List.of(new GeoPoint(sphere,new Point3D(1.6, 0.8, 0))

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(-2, 0, 0)));
        assertEquals("Wrong number of points", 2, result.size());

        if (result.get(0).point.get_x().get() > result.get(1).point.get_x().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere",  List.of(new GeoPoint(sphere,new Point3D(0, 0, 0)),new GeoPoint(sphere,new Point3D(2,0,0))), result);
       // List.of(new GeoPoint(sphere,new Point3D(0, 0, 0)),new GeoPoint(sphere,new Point3D(2,0,0)))
        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray( new Vector(0, -1, 0),new Point3D(1, 1, 0)));
        assertEquals("Wrong number of points", 1, result.size());

        assertEquals("Ray crosses sphere",  List.of(new GeoPoint(sphere,new Point3D(1, -1, 0))), result);
// List.of(new GeoPoint(sphere,new Point3D(1, -1, 0))
        // TC15: Ray starts inside (1 points)
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(0.4, 0, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere",   List.of(new GeoPoint(sphere,new Point3D(2, 0, 0))), result);

        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntersections(new Ray( new Vector(0, 1, 0),new Point3D(1, 0, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray crosses sphere",     List.of(new GeoPoint(sphere,new Point3D(1, 1, 0))), result);

        // TC17: Ray starts at sphere and goes outside (0 points)
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(0.4, 0.8, 0)));
        assertEquals("Wrong number of points", 1, result.size());

        assertEquals("Ray crosses sphere",   List.of(new GeoPoint(sphere,new Point3D(1.6, 0.8, 0))), result);

        // TC18: Ray starts after sphere (0 points)
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(88, 0, 0)));
        assertEquals("Wrong number of points", null, result);

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(0, -1, 0)));
        assertEquals("Wrong number of points", null, result);

        // TC20: Ray starts at the tangent point
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(1, 1, 0)));
        assertEquals("Wrong number of points", null, result);

        // TC21: Ray starts after the tangent point
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(84, 0, 0)));
        assertEquals("Wrong number of points", null, result);

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        result = sphere.findIntersections(new Ray( new Vector(1, 0, 0),new Point3D(0.4, 3, 0)));
        assertEquals("Wrong number of points", null, result);


    }



   /* @Test
    public void findIntersections()
    {
        Sphere sp = new Sphere(new Point3D(2, 2, 2), 2);

        //============ Equivalence Partitions Tests

        Ray ray1 = new Ray( new Vector(1, 0, 0),new Point3D(1, 0, 0));
        List<GeoPoint> IntersectionPoints1 = sp.findIntersections(ray1);
        assertEquals("Error, there must not be intersetion points in this case", IntersectionPoints1, null);

        Ray ray2 = new Ray(new Vector(1.5, 1.5, 3), new Point3D(0.5, 0.5, 1));
        List<GeoPoint> IntersectionPoints2 = sp.findIntersections(ray2);
        assertEquals("ERROR, there must be 2 intersection points in this case", IntersectionPoints2.size(), 2 );

        Ray ray3 = new Ray( new Vector(-1, -1, -2), new Point3D(0.5, 0.5, 1));
        List<GeoPoint> IntersectionPoints3 = sp.findIntersections(ray3);
        assertEquals("ERROR, the ray must start from the oposite direction and have no intersection points", IntersectionPoints3, null);

        // the start of the ray is not included
        Ray ray4 = new Ray(new Vector(2, 2, 4), new Point3D(1, 1, 2));
        List<GeoPoint> IntersectionPoints4 = sp.findIntersections(ray4);
        assertEquals("ERROR, there must be not be an intersection point", IntersectionPoints4.size(), 1);

        // =============== Boundary Values Tests ==================

        try
        {
            //the ray starts in the center point
            Ray ray5 = new Ray(new Vector(4, 4, 4), new Point3D(2, 2, 2));
            List<GeoPoint> IntersectionPoints5 = sp.findIntersections(ray5);

            Ray ray6 = new Ray( new Vector(4, 4, 4), new Point3D(3, 3, 3));
            List<GeoPoint> IntersectionPoints6 = sp.findIntersections(ray6);

            Ray ray7 = new Ray( new Vector(0, -1, 0), new Point3D(4, 3, 2));
            List<GeoPoint> IntersectionPoints7 = sp.findIntersections(ray7);

            // the start of ray is not included
            Ray ray8 = new Ray( new Vector(-1, 0, 0), new Point3D(4, 2, 2));
            List<GeoPoint> IntersectionPoints8 = sp.findIntersections(ray8);

            Ray ray9 = new Ray( new Vector(-6, -3, -3), new Point3D(4, 2, 2));
            List<GeoPoint> IntersectionPoints9 = sp.findIntersections(ray9);



        }
        catch(Exception e)
        {
            fail("there must be 1 intersection point");
        }

        try
        {
            Ray ray10 = new Ray( new Vector(-4, -4, -4), new Point3D(0.5, 0.5, 0.5));
            List<GeoPoint> IntersectionPoints10 = sp.findIntersections(ray10);

            Ray ray11 = new Ray(new Vector(0, -1, 0), new Point3D(4, 1, 2));
            List<GeoPoint> IntersectionPoints11 = sp.findIntersections(ray11);

            //the point of the start of the ray
            Ray ray12 = new Ray( new Vector(2, 2, 4), new Point3D(2, 2, 4));
            List<GeoPoint> IntersectionPoints12 = sp.findIntersections(ray12);

            //the point of the start of the ray
            Ray ray13 = new Ray( new Vector(6, 3, 3), new Point3D(4, 2, 2));
            List<GeoPoint> IntersectionPoints13 = sp.findIntersections(ray13);

            //the point of the start of the ray
            Ray ray14 = new Ray( new Vector(0, -1, 0),new Point3D(4, 2, 2));
            List<GeoPoint> IntersectionPoints14 = sp.findIntersections(ray14);
        }
        catch(Exception e)
        {
            fail("there are no intersection points - therefpre it must return null!");
        }

        try
        {
            Ray ray15 = new Ray( new Vector(4, 4, 4), new Point3D(0.5, 0.5, 0.5));
            List<GeoPoint> IntersectionPoints15 = sp.findIntersections(ray15);

        }
        catch(Exception e)
        {
            fail("there must be 2 intersection points");
        }

    }*/









}