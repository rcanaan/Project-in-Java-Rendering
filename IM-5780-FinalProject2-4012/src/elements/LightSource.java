package elements;
import primitives.*;
public interface LightSource
{
    /**
     * returns the intensity of the light
     * @param p - point 3d
     * @return color
     */
    public Color getIntensity(Point3D p);


    /**
     * returns the normalized vector of direction of the light source
     * @param p the lighted point
     * @return Vector
     */
    public Vector getL(Point3D p);

    /**\
     * returns the distance between the light to the intersection point
     * @return double
     */
    public double getDistance(Point3D p);
}
