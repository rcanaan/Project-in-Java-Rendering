package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Geometries implements Intersectable
{
    /**
     * list of geometries that are intersectable
     */
    protected ArrayList<Intersectable> interList;//more important to get the geometry quiqly than adding one

    /**
     *
     * @return list of intersectable geometries
     */
    public List<Intersectable> getInterList()
    {
        return this.interList;
    }

    /**
     *default constructor
     */
    public Geometries()
    {
        this.interList = new ArrayList<Intersectable>();//i chose an array list because i need to go through the list easily and not to add and delete objects frequently
    }

    /**
     * constructor which gets intersectable geometries
     * @param geometries
     */
    public Geometries(Intersectable... geometries)
    {
        this.add(geometries);
    }

    /**
     * an add func for adding more intersectable geometries to the intersectabel's list
     * @param geometries
     */
    public void add(Intersectable... geometries)
    {
        for (int i=0;i<geometries.length;i++)
        {
            interList.add(geometries[i]);
        }
    }



    /**
     *finding intersections between ray and other geometries in the list
     * @param ray
     * @return
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray)
    {

        List<GeoPoint> l = new ArrayList<GeoPoint>();
        List<GeoPoint> l1 = new ArrayList<GeoPoint>();
        for(int i=0;i<this.interList.size();i++)
        {
            l1 =  this.interList.get(i).findIntersections(ray);
            if(l1!=null)
                for(int j=0;j<l1.size();j++)//copy the list that get
                    l.add(l1.get(j));

        }
        if(l==null)
            return null;
        return l;



    }
}
