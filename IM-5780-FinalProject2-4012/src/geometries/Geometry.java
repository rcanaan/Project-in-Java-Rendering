package geometries;

import primitives.*;
import primitives.Color;

/**
 *
 *  @author rinat
 * an abstruct class of all geometry. all geometry inherits it and it implements the interface 'Intersectable'
 */
public abstract class Geometry implements Intersectable
{
   /**
    * field "emisson" light
    * field "material" of the geometry's
    */
   protected Color _emmision;
   protected Material _material;

   /**
    * default constructor
    */
   public Geometry()
   {
     // _emmision = Color.BLACK;
      this(Color.BLACK);
   }

   /**
    * constructor by color
    * @param emission
    */
   public Geometry (Color emission)
   {
      //_emmision = new Color(emission);
      this(emission, new Material(0d, 0d, 0));
   }

   /**
    * constructor by color and material
    * @param emission
    * @param material
    */
   public Geometry(Color emission, Material material)
   {
      this._emmision = new Color(emission);
     this. _material = material;
   }

   //getters
   /**
    *
    * @return color
    */
   public Color get_emmision()
   {
      return this._emmision;
      //return new Color(_emission);?
   }

   /**
    *
    * @return material
    */
   public Material get_material()
   {
      return this._material;
   }

   /**
    * return the normal in the point
    * @param p - point3d
    * @return vector - the normal
    */
 public abstract Vector getNormal (Point3D p);
}
