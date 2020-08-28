package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource
{
    /**
     * the direction of the light - sun. there is no lacation. evetywhere is the same intensity
     */
    private Vector _direction;

    /**
     * constructor that calls the base class's constructor in order to initialize all fields
     * @param color
     * @param d
     */
    public DirectionalLight(Color color, Vector d)
    {
        super(color);//light's constructor
        this._direction = d.normalized();
    }

    /**
     * @param p the lighted point is not used and is mentioned
     *          only for compatibility with LightSource
     * @return fixed intensity of the directionLight
     */
    @Override
    public Color getIntensity(Point3D p)
    {
        return this._intensity;
    }

    /**
     * like a get function of direction - towards the geometry
     * @return Vector
     */
    @Override
    public Vector getL(Point3D p)
    {
        return this._direction;
    }

    /**
     *
     * @param p - point
     * @return
     */
    @Override
    public double getDistance(Point3D p)
    {
        return Double.POSITIVE_INFINITY;// there is no location to the directional light source
    }
}
