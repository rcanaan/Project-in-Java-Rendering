package primitives;

public class Material
{
    /**
     *
     */
    private double _kD;
    private double _kS;
    private int _nShininess;

    private double _kT;//transparency
    private double _kR;//reflectance

    /**
     * constructor
     * @param kd
     * @param ks
     * @param nShine
     */
    public Material(double kd, double ks, int nShine)
    {
        this(kd,ks,nShine,0.0,0.0);
    }

    /**
     * copy constructor
     * @param mat
     */
    public Material(Material mat)
    {
        this(mat._kD, mat._kS, mat._nShininess, mat._kT, mat._kR);
    }

    /**
     *
     * @param kd
     * @param ks
     * @param nShine
     * @param kt
     * @param kr
     */
    public Material(double kd, double ks, int nShine, double kt, double kr)
    {
        this._kD = kd;
        this._kS = ks;
        this._nShininess = nShine;
        this._kT = kt;
        this._kR = kr;
    }


    //getters

    /**
     * gets _kD
     * @return double
     */
    public double get_kD()
    {
        return _kD;
    }

    /**
     * gets _kS
     * @return double
     */
    public double get_kS()
    {
        return _kS;
    }

    /**
     * gets _nShininess
     * @return int
     */
    public int get_nShininess()
    {
        return _nShininess;
    }

    /**
     * gets _kT
     * @return double
     */
    public double get_kT()
    {
        return _kT;
    }

    /**
     * gets _kR
     * @return double
     */
    public double get_kR()
    {
        return _kR;
    }

}
