package elements;
import primitives.*;
import primitives.Point3D;

public class PointLight extends Light implements LightSource
{//there is no directional, there is location- lights to everywhere

    protected Point3D _position;// the location
    //helps to calculate the landing - bacause of the distance. if it is in the same distance and in othr location -- will be at the same intensity
    protected double _kC;
    protected double _kL;
    protected double _kQ;

    /**
     * constructor that calls the base class's constructor in order to initialize all fields
     * @param color
     * @param point
     */
    public PointLight(Color color, Point3D point, double _kC, double _kL, double _kQ)
    {
        super(color);//light's constuctor

        //--------------
        //this._intensity = color;//check the duplications
        //----------------

        _position = new Point3D(point);
        this._kC = _kC;
        this._kL = _kL;
        this._kQ = _kQ;

    }

    /**
     * calculates the intensity of a point light
     * @return Color
     */
    @Override
    public Color getIntensity(Point3D p)
    {
        double dsquared = p.distanceSquared(_position);
        double d = p.distance(_position);

        Color IL = _intensity.reduce(_kC + d*_kL +dsquared* _kQ);

        return IL;
    }

    /**
     * calculates the vector from the position point to the intersection point
     * @return Vector
     */
    @Override
    public Vector getL(Point3D p)
    {
        if (p.equals(_position))//if the geometry is on the light's source
        {
            return null;
        }
        else
        {
            return p.subtract(_position).normalize();//towards the geometry
        }
    }

    /**
     *
     * @param p
     * @return
     */
    @Override
    public double getDistance(Point3D p)
    {
        return _position.distance(p);
    }

}
