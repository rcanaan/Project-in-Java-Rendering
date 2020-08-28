package unittests;

import org.junit.Test;
import geometries.*;
import primitives.*;

import static org.junit.Assert.*;

public class Point3DTest {

    @Test
    public void subtract()
    {
        Point3D p1 =new Point3D(1,2,3);
        Point3D p2 =new Point3D(4,5,6);
        // ============ Equivalence Partitions Tests ==============
        assertEquals("", p2.subtract(p1), new Vector(3,3,3));
        Point3D p3 =new Point3D(-9,-2.4,3);
        assertEquals(p2.subtract(p3), new Vector(13,7.4,3));

    }

    @Test
    public void add()
    {
        Point3D p3 =new Point3D(-9,-2.4,3);
        Vector v=new Vector(1,5,8);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(p3.add(v),new Point3D(-8,2.6,11));
    }

    @Test
    public void distanceSquared()
    {
        Point3D p1 = new Point3D(1,1,1);
        Point3D p2 = new Point3D(2,2,2);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(p1.distanceSquared(p2),3,0.000001);
        // ============ boundary Tests ==============
        assertEquals(p2.distanceSquared(p2),0,0.000001);
    }

    @Test
    public void distance() {
        Point3D p1 = new Point3D(1,1,1);
        Point3D p2 = new Point3D(2,2,2);
        // ============ boundary Tests ==============
        assertEquals(p2.distance(p2),0,0.000001);
        // ============ Equivalence Partitions Tests ==============
        assertEquals(p1.distance(p2),Math.pow(3,0.5),0.000001);
    }
}