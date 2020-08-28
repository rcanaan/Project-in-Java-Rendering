package elements;

import primitives.Color;

public class AmbientLight extends Light
{
  //  protected Color _intensity;
    //private double  _kA;

    /**
     * constructor
     * @param Ka - the distance factor - coefficient landing. as long is bigger- the bigger intensity
     * @param Ia - color, the intensity of the original light
     */
    public AmbientLight(Color Ia, double Ka)
    {
        //_intensity = Ia.scale(Ka);
        super(Ia.scale(Ka));//final power of ambient light
    }
   /* public AmbientLight(AmbientLight ambLight)
    {
        _intensity = ambLight._intensity;
    }*/
  /* public Color GetIntensity()
    {
        return new Color(_intensity);
    }*/
}
