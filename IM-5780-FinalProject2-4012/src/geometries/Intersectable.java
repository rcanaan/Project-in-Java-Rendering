package geometries;

import primitives.*;

import java.util.List;

public interface Intersectable
{
    /**
     *Ray-Geometry intersections
     * @param ray
     * @return list of Point3D
     */
    List<GeoPoint> findIntersections(Ray ray);

    /**
     * an helper class that  helps us to calculate the specific color of
     * a geometry based on the color of the geometry itself- the FONG model
     * @author rinat canaan
     */
    public static class GeoPoint
    {
        public Geometry geometry;
        public Point3D point;

        public GeoPoint(Geometry geo, Point3D _point)//constructor
        {
            this.geometry = geo;
            this.point = new Point3D(_point);
        }

        /**
         * get func
         * @return the specific geometry
         */
        public Geometry getGeometry()
        {
            return  this.geometry;
        }

        /**
         * get func
         * @return the specific point
         */
        public Point3D getPoint()
        {
            return  this.point;
        }

        /**
         * set func of geometry
         * @param g
         */
        public void setGeometry(Geometry g)
        {
            geometry = g;
        }
        /**
         * a function that checks the equality of two GeoPoint types
         * @return boolean
         */
        @Override
        public boolean equals(Object obj)
        {

            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            GeoPoint other = (GeoPoint) obj;
            if (geometry == null)
            {
                if (other.geometry != null)
                    return false;
            } else if (!geometry.equals(other.geometry))
                return false;
            if (point == null)
            {
                if (other.point != null)
                    return false;
            } else if (!point.equals(other.point))
                return false;
            return true;
        }
    }


}
