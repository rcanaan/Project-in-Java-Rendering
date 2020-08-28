package unittests;

import org.junit.Test;
import primitives.Coordinate;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;
/**
 *
 *  unit test for primitives.ray class
 * @author Rinat Canaan
 */
public class RayTest
{


    @Test
    public void testEqualsObject()
    {
        Coordinate c1 = new Coordinate(1.0);
        Coordinate c2 = new Coordinate(1.0);
        Coordinate c3 = new Coordinate(1.0);

        Point3D p1 = new Point3D(c1,c2,c3);
        Vector direction1 = new Vector(p1);
        Ray r1 = new Ray(direction1,p1);

        Point3D p2 = new Point3D(c1,c2,c3);
        Vector direction2 = new Vector(p2);
        Ray r2 = new Ray(direction2,p2);

        assertEquals(r1, r2);
    }
}