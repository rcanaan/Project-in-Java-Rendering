package primitives;
import java.lang.IllegalArgumentException;

/**
 * A Vector class is a basic class and it contains a 3D point.
 *  The class is based on point3D class.
 */
public class Vector
{
    /**
     * vector values
     */
    final static int Zero=0;
    protected Point3D  _head;

    /**
     *
     * @return the point in the vector
     */
    public Point3D get_head()
    {
        return _head;
    }

    /**
     * A constructor that gets three coordinates
     * @param x
     * @param y
     * @param z
     * @throws IllegalArgumentException
     */
    public Vector(Coordinate x,Coordinate y,Coordinate z) throws IllegalArgumentException
    {
      if(x.equals(Zero) && y.equals(Zero) && z.equals(Zero))
            throw new IllegalArgumentException("the value is zero!!!");
        _head = new Point3D(x,y,z);
    }

    /**
     *A constructor gets three numbers of type double
     * @param x
     * @param y
     * @param z
     * @throws IllegalArgumentException
     */
    public Vector(double x, double y,double z) throws IllegalArgumentException
    {
      if(x==Zero && y==Zero && z==Zero)
            throw new IllegalArgumentException("the value is zero!!!");
        _head = new Point3D(x,y,z);
    }

    /**
     *A constructor that gets a 3D point
     * @param myPoint
     * @throws IllegalArgumentException
     */
    public Vector(Point3D myPoint) throws IllegalArgumentException
    {
      if(myPoint.equals(Point3D.zero))
            throw new IllegalArgumentException("point is zero!!!");
        _head =new Point3D(myPoint);
    }

    /**
     *A constructor that gets a vector
     * @param myVector
     */
    public Vector(Vector myVector)
    {
        _head =new Point3D(myVector.get_head());
    }
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Vector))
            return false;
        Vector oth = (Vector)obj;
        return _head.equals(oth.get_head());
    }
    public @Override String toString()
    {
        return String.format("(%s,%s,%s)", _head.get_x(), _head.get_y(), _head.get_z());
    }

    /**
     *Vector subtraction
     * @param other
     * @return vector
     */
    public Vector subtract(Vector other)
    {
        Vector newVector = new Vector(this.get_head().subtract(other.get_head()));
        return newVector;
    }

    /**
     * vector add
     * @param other
     * @return vector
     */
    public Vector add(Vector other)
    {
        Vector newVector = new Vector(this.get_head().add(other));
        return newVector;
    }

    /**
     *Multiple vector multiples - scalar
     * @param a
     * @return vector
     */
    public Vector scale(double a)
    {
        Vector newVector= new Vector(a*this.get_head().get_x().get(),a*this.get_head().get_y().get(),a*this.get_head().get_z().get());
        return newVector;
    }

    /**
     * Multiple dot Product
     * @param other
     * @return double
     */
    public double dotProduct(Vector other)//scalar multiplication
    {
        return (this.get_head().get_x().get()*other.get_head().get_x().get()+
                this.get_head().get_y().get()*other.get_head().get_y().get()+
                this.get_head().get_z().get()*other.get_head().get_z().get());
    }

    /**
     * Multiple cross Product
     * @param other
     * @return vector
     */
    public Vector crossProduct(Vector other)//vectorial multiplication
    {
        return new Vector(this.get_head().get_y().get()*other.get_head().get_z().get()-this.get_head().get_z().get()*other.get_head().get_y().get(),
                this.get_head().get_z().get()*other.get_head().get_x().get()-this.get_head().get_x().get()*other.get_head().get_z().get(),
                this.get_head().get_x().get()*other.get_head().get_y().get()-this.get_head().get_y().get()*other.get_head().get_x().get());
    }

    /**
     *Calculate vector length squared
     * @return double
     */
     public double lengthSquared()
     {
         return (_head.get_x().get()* _head.get_x().get())+(_head.get_y().get()* _head.get_y().get())+(_head.get_z().get()* _head.get_z().get());
     }

    /**
     *Calculate vector length
     * @return double
     */
     public double length()
     {
         return Math.sqrt(lengthSquared());
     }

    /**
     *A vector normalization action that will change a vector itself
     * @return this vector
     */
     public Vector normalize()//normalsizes the self vector
     {
         Point3D other= new Point3D(this._head.get_x().get()/length(),this._head.get_y().get()/length(),this._head.get_z().get()/length());
        this._head = other;
        return this;
     }

    /**
     *Normalization returns a new vector normalized in the same direction as the original vector
     * @return vector
     */
     public Vector normalized()//returns a copy of the original vector- but normalized
     {
        return new Vector(normalize());
     }
}
