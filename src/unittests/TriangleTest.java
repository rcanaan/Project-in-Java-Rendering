package unittests;

import geometries.Polygon;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TriangleTest
{

    @Test
    public void getNormal() {
        Triangle tr = new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        // ============ Equivalence Partitions Tests ==============
        assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), tr.getNormal(new Point3D(0, 0, 1)));

    }

    @Test
    public void findIntersections()
    {
        Triangle triangle = new Triangle(new Point3D(1,0,0), new Point3D(0,1,0), new Point3D(0,0,0));
        // ============ Equivalence Partitions Tests ==============
        //Inside polygon/triangle
        List<GeoPoint> result = triangle.findIntersections(new Ray( new Vector(0,0,-1),new Point3D(0.3, 0.3, 1)));
        assertEquals("not good it is in the triangle" ,1,result.size());
        assertEquals("Ray crosses triangle", List.of(new GeoPoint(triangle,new Point3D(0.3, 0.3, 0))), result);

        //Outside against edge
        result = triangle.findIntersections(new Ray( new Vector(0,0,-1),new Point3D(-0.2, 0.2, 0)));
        assertEquals("not good it is against edge" ,null,result);

        //Outside against vertex
        result = triangle.findIntersections(new Ray( new Vector(0,0,-1),new Point3D(-0.1, 1.2, 0)));
        assertEquals("not good it is against vertex" ,null,result);

        // =============== Boundary Values Tests ==================
        //On edge
        result = triangle.findIntersections(new Ray( new Vector(1,0,0),new Point3D(0.3, 0.2, 0)));
        assertEquals("not good it is on the edge" ,null,result);
        //In vertex
        result = triangle.findIntersections(new Ray( new Vector(1,0,0),new Point3D(-0.1, 1, 0)));
        assertEquals("not good it is on the vertex" ,null,result);
        //On edge's continuation
        result = triangle.findIntersections(new Ray( new Vector(1,0,0),new Point3D(-0.1, 1.3, 0)));
        assertEquals("not good it is on the edges continuation" ,null,result);
    }
}