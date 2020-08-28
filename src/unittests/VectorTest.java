package unittests;
import primitives.*;

import static java.lang.System.out;
import static org.junit.Assert.*;
import static primitives.Util.isZero;

public class VectorTest {

    @org.junit.Test
    public void subtract()
    {
        Vector v1 = new Vector(-2,3,4.5);
        Vector v2 = new Vector(1,1,1);
        // ============ Equivalence Partitions Tests ==============
        assertEquals( v1.subtract(v2),new Vector(-3,2,3.5));
        // ============ boundary Tests ==============
        //assertEquals( v1.add(v1.scale(-1)),new Vector(0,0,0));
        //vector-itself
    }

    @org.junit.Test
    public void add() {
        Vector v1 = new Vector(-2,3,4.5);
        Vector v2 = new Vector(1,1,1);
        // ============ Equivalence Partitions Tests ==============
        assertEquals( v1.add(v2),new Vector(-1,4,5.5));
        //vector + the opposite one

    }

    @org.junit.Test
    public void scale()
    {
        Vector v = new Vector(-2,3,4.5);
        // ============ Equivalence Partitions Tests ==============
        assertEquals( v.scale(8),new Vector(-16,24,36));
        try {
            v.scale(0);
            fail("Didn't throw scale by zero exception!");
        } catch (ArithmeticException e) {
            assertTrue(true);
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }

    }

    @org.junit.Test
    public void dotProduct() {
//        Vector v1 = new Vector(-2,3,4.5);
//        Vector v2 = new Vector(1,1,1);
//        assertEquals( v1.dotProduct(v2),5.5);
//        Vector v3 = new Vector(0,1,0);
//        Vector v4 = new Vector(0,0,1);
//        assertEquals( v3.dotProduct(v4),0);
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        // ============ boundary Tests ==============
        assertEquals("ERROR: dotProduct() for orthogonal vectors is not zero",0,v1.dotProduct(v3),0.000001);
        // ============ Equivalence Partitions Tests ==============
        assertEquals("ERROR: dotProduct() wrong value",v1.dotProduct(v2),-28,0.000001);
    }

    @org.junit.Test
    public void crossProduct()
    {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v3.length(), vr.length(), 0.00001);

        // Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v3)));

        // =============== Boundary Values Tests ==================
        // test zero vector from cross-product of co-lined vectors
        try {
            v1.crossProduct(v2);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception e) {}

    }

    @org.junit.Test
    public void lengthSquared() {
//        Vector v = new Vector(3.5,-5,10);
//        assertTrue(v.length() == 12.25 + 25 + 100);
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        // ============ Equivalence Partitions Tests ==============
        assertEquals("ERROR: lengthSquared() wrong value",14,v1.lengthSquared(),0.000005);

    }

    @org.junit.Test
    public void length() {
//        Vector v = new Vector(3.5,-5,10);
//        assertTrue(v.length() ==
//                Math.sqrt(12.25 + 25 + 100));
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        // ============ Equivalence Partitions Tests ==============
        assertEquals("ERROR: length() wrong value",5,new Vector(0, 3, 4).length(),0/000001);
    }

    @org.junit.Test
    public void normalize()
    {
        Vector v = new Vector(3.5,-5,10);
        v.normalize();
        // ============ Equivalence Partitions Tests ==============
        assertEquals(""
                , 1, v.length(),1e-10);

        try {
            v = new Vector(0,0,0);
            v.normalize();
            fail("Didn't throw divide by zero exception!");
        } catch (ArithmeticException e) {
            assertTrue(true);
        }
        catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @org.junit.Test
    public void normalized()
    {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        // ============ Equivalence Partitions Tests ==============
        assertEquals("an error oqured in normalized func",1,v1.normalized().length(),0.000001);
    }
}