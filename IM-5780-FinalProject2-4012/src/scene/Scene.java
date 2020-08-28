package scene;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;
import geometries.Intersectable;
/**
 * scene - gathering all the elements to create a picture
 */
public class Scene
{


    /**
     * name of scene
     */
    String _name;
    /**
     *color of background - black
     */
    Color _background = new Color(0, 0, 0);

    /**
     * the ambient light
     */
    AmbientLight _ambientLight;

    /**
     * the geometries at the scene
     */
    Geometries _geometries;

    /**
     * list of all the lights at the scene
     */
    private List<LightSource> _lights = null;

    /**
     * camera - from its lens tha scene will be seen
     */
    Camera _camera;
    /**
     * distance from the camera
     */
    double _distance;

    /**
     * constructor based on name, geometries. ad list of lights
     * @param name
     */
    public Scene(String name)
    {
        _name = name;
        _geometries = new Geometries();
        _lights = new LinkedList<LightSource>();
    }

    /**
     * copy constructor
     * @param scene
     */
    public Scene(Scene scene)
    {
        _name = scene._name;
        _background = scene._background;
        _ambientLight = scene._ambientLight;
        _geometries = scene._geometries;
        _camera = scene._camera;
        _distance = scene._distance;
    }

    /**
     * getters
     */
    /**
     *
     * @return a list of light sources
     */
    public List<LightSource> getLights()
    {
        List<LightSource> lights = new LinkedList<LightSource>();//I want to add many light sources
        lights.addAll(_lights);

        return lights;
    }

    /**
     * getter for the name of the scene
     * @return the name of the picture
     */
    public String getName()
    {
        return new String(_name);
    }

    /**
     * getter for the ambient light at the scene
     * @return the ambient light
     */
    public AmbientLight getAmbientLight()
    {
        return _ambientLight;
    }

    /**
     * geteer for the camera at the scene
     * @return the camera
     */
    public Camera getCamera()
    {
        return _camera;
    }

    /**
     * getter for the geometries at the scene
     * @return the geometries
     */
    public Geometries getGeometries()
    {
        return _geometries;
    }

    /**
     * getter for the distance at the scene
     * @return the distance between the camera and the scene
     */
    public double getDistance()
    {
        return _distance;
    }

    /**
     * getter for the background at the scene
     * @return the background
     */
    public Color getBackground()
    {
        return this._background;
    }

    /**
     * setters
     */

    /**
     * setter for background
     * @param bg - color of background
     */
    public void setBackground(Color bg)
    {
        _background = new Color(bg);
    }

    /**
     * setter for ambient light
     * @param ambi - ambient light
     */
    public void setAmbientLight(AmbientLight ambi)
    {
        //_ambientLight = new AmbientLight(ambi);
        _ambientLight = ambi;
    }

    /**
     * set the camera
     * @param cam- camera
     */
    public void setCamera(Camera cam)
    {
        _camera = new Camera(cam);
    }

    /**
     * set distance function
     * @param dis - distance from the camera
     */
    public void setDistance(double dis)
    {
        _distance = dis;
    }




    /** adds a collection of intersectable geometries into the scene's list of geometries
     *
     * @param intersectables -
     */
    public void addGeometries(Intersectable... intersectables)
    {
        for (Intersectable i : intersectables)
        {
            _geometries.add(i);
        }
    }

    /**
     * adding lights to the scene
     * @param light - the added light
     */
    public void addLights(LightSource light)
    {
        if (_lights == null)//if the light sources is empty
        {
            _lights = new LinkedList<>();
        }
        _lights.add(light);
    }
}

