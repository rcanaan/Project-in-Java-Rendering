package elements;
import primitives.Color;

/**
 * the light class
 * @author rinatcanaan
 */
abstract class Light
{
    /**
     * the intensity of the light
     */
    protected Color _intensity;



    /**
     * constructor
     * @param color
     */
    public Light(Color color)
    {
        this._intensity = new Color(color);
    }

    /**
     * gets the intensity of the light
     * @return Color
     */
    public Color getIntensity()
    {
        return new Color(_intensity);
    }
}