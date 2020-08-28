package unittests;

import org.junit.Test;

import static org.junit.Assert.*;
import static primitives.Util.isZero;

import geometries.*;
import primitives.*;

public class TubeTest {

    @Test
    public void getNormal()
    {
        Tube t = new Tube(new Ray(new Vector(0,1,0),new Point3D(0,0,1)),1.0);
        // ============ Equivalence Partitions Tests ==============
        assertEquals("getNormal() result is not good",new Vector(0,0,1),t.getNormal(new Point3D(0,0.5,2)));
    }

//    @Test
//    public void findIntersections()
//    {
//
//    }
}