package elements;
import primitives.*;
public class SpotLight extends PointLight
{
    //also a directional and location
    private Vector _direction;

    /**
     * constructor that calls the base class's constructor in order to initialize all fields
     * @param color
     * @param d
     * @param point
     */
    public SpotLight(Color color, Vector d, Point3D point, double _kC, double _kL, double _kQ)
    {
        super(color, point, _kC,_kL,_kQ);
        this._direction = new Vector(d).normalized();
    }
    /**
     * calculates the intensity of a spotlight
     * @return Color
     */
    @Override
    public Color getIntensity(Point3D p)
    {
        Color pointLightIntensity = super.getIntensity(p);
        double projection = _direction.dotProduct(getL(p));

        Color IL = pointLightIntensity.scale(Math.max(0,projection));//if the projection<0 , then multiply by 0
        return IL;
    }

    /**
     * gets the direction of the light source
     * @return Vector
     */
   /* @Override
    public Vector getL(Point3D p)
    {
        return this._direction;
    }*/
}
