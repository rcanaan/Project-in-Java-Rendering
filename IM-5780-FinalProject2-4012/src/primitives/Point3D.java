package primitives;
/**
 * A Point3D class is a basic class and it contains a 3D point.
 * The class is based on Coordinate class.
 */
public class Point3D
{
    /**
     * Point3D value
     */
    protected Coordinate _x;
    protected Coordinate _y;
    protected Coordinate _z;
    public static final Point3D zero = new Point3D(0, 0, 0);

    /**
     * A constructor that gets three coordinates
     * @param a
     * @param b
     * @param c
     */
    public Point3D(Coordinate a, Coordinate b, Coordinate c) {
        _x = new Coordinate(a);
        _y = new Coordinate(b);
        _z = new Coordinate(c);
    }

    /**
     * A constructor gets three numbers of type double
     * @param a
     * @param b
     * @param c
     */
    public Point3D(double a, double b, double c) {
        _x = new Coordinate(a);
        _y = new Coordinate(b);
        _z = new Coordinate(c);
    }

    /**
     * A constructor that gets a 3D point
     * @param p
     */
    public Point3D(Point3D p) {
        _x = new Coordinate( p.get_x());
        _y = new Coordinate(p.get_y());
        _z = new Coordinate(p.get_z());
    }

    /**
     *
     * @return Coordinate in the first place on the point
     */
    public Coordinate get_x() {
        return _x;
    }

    /**
     *
     * @return Coordinate in the second place on the point
     */
    public Coordinate get_y() {
        return _y;
    }

    /**
     *
     * @return Coordinate in the three place on the point
     */
    public Coordinate get_z() {
        return _z;
    }

    /**
     * func to get a vector between two points
     *Vector Subtraction - Gets a second point in the parameter,
     * @param p
     * @return a vector from the second point to the point at which the action is performed
     */
    public Vector subtract(Point3D p)
    {
        return new Vector(_x.get()-p.get_x().get() ,_y.get()- p.get_y().get(), _z.get()-p.get_z().get());
    }

    /**
     * Adding Vector to a Point
     * @param v
     * @return a new point
     */
    public Point3D add(Vector v)
    {
        Point3D p = new Point3D(v.get_head());
        return new Point3D(p.get_x().get()+ _x.get(),p.get_y().get()+ _y.get(),p.get_z().get()+ _z.get());
    }

    /**
     * func to calculate The distance between two points squared
     * @param p
     * @return
     */
    public double distanceSquared(Point3D p)
    {
        return (p.get_x().get()- _x.get())*(p.get_x().get()- _x.get())+(p.get_y().get()- _y.get())*(p.get_y().get()- _y.get())+(p.get_z().get()- _z.get())*(p.get_z().get()- _z.get());
    }

    /**
     * func to calculate Distance between 2 points
     * @param p
     * @return
     */
    public double distance(Point3D p)
    {
        return Math.sqrt(distanceSquared(p));/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Point3D)) return false;
        Point3D oth = (Point3D) obj;
        return _x.equals(oth.get_x()) && _y.equals(oth.get_y()) && _z.equals(oth.get_z());
    }

    public @Override String toString ()
    {
        return String.format("(%s,%s,%s)", _x, _y, _z);
    }

}

