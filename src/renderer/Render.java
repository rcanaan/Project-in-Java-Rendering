package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Intersectable;
import primitives.*;
import primitives.Util;
import scene.Scene;
import geometries.Intersectable.GeoPoint;
//import java.awt.Color;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;
/**
 * render - calculates and makes the picture to happen
 */
public class Render
{
    /**
     * threads- for better running
     */
    private int _threads = 1;
    private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean _print = false; // printing progress percentage
    /**
     * Set multithreading <br>
     * - if the parameter is 0 - number of coress less SPARE (2) is taken
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads)
    {
        if (threads < 0) throw new IllegalArgumentException("Multithreading must be 0 or higher");
        if (threads != 0) _threads = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            _threads = cores <= 2 ? 1 : cores;
        }
        return this;
    }
    /**
     * Set debug printing on
     * @return the Render object itself
     */
    public Render setDebugPrint()
    {
        _print = true; return this;
    }



    private class Pixel
    {
        private long _maxRows = 0; // Ny
        private long _maxCols = 0; // Nx
        private long _pixels = 0; // Total number of pixels: Nx*Ny
        public volatile int row = 0; // Last processed row
        public volatile int col = -1; // Last processed column
        private long _counter = 0; // Total number of pixels processed
        private int _percents = 0; // Percent of pixels processed
        private long _nextCounter = 0; // Next amount of processed pixels for percent progress
        /**
         * The constructor for initializing the main follow up Pixel object
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols)
        {
            _maxRows = maxRows;_maxCols = maxCols; _pixels = maxRows * maxCols;
            _nextCounter = _pixels / 100;
            if (Render.this._print) System.out.printf("\r %02d%%", _percents);
        }
        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel()
        {}

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target)
        {
            int percents = nextP(target);
            if (_print && percents > 0) System.out.printf("\r %02d%%", percents);
            if (percents >= 0) return true;
            if (_print) System.out.printf("\r %02d%%", 100);
            return false;
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
         * critical section for all the threads, and main Pixel object data is the shared data of this critical
         * section.<br/>
         * The function provides next pixel number each call.
         * @param target target secondary Pixel object to copy the row/column of the next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
         * finished, any other value - the progress percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target)
        {
            ++col;
            ++_counter;
            if (col < _maxCols)
            {
                target.row = this.row;
                target.col = this.col;
                if (_print && _counter == _nextCounter)
                {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            ++row;
            if (row < _maxRows)
            {
                col = 0;
                if (_print && _counter == _nextCounter)
                {
                    ++_percents;
                    _nextCounter = _pixels * (_percents + 1) / 100;
                    return _percents;
                }
                return 0;
            }
            return -1;
        }
    }




    /**
     * scene - gathering the all the objects - lights, geometries , camera and "watching screen"
     */
    private Scene _scene;

    /**
     *imageWriter- gets scene and sends it to the screen
     */
    private ImageWriter _imageWriter;

    /**
     * will help us to calculate the shadow rays
     */
  //  private static final double DELTA = 0.1;  - moved to the render class according to the DRY principle

    /***
     * two parameters for breaking point at the recursion - the minimum & maximum level.
     * MAX_CALC_COLOR_LEVEL - will descend  every time we are making the recursion call by 1
     *MIN_CALC_COLOR_K -
     */

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K=0.001;

    private int MAX_ADAPTIVERECURSION = 4;
    /**
     * constructor based on scene
     * @param s - scene
     */
    public Render(Scene s)//constructor
    {
        this._scene = s;
    }

    /**
     * constructor based scene and imageWriter
     * @param imageWriter - its job is -gets scene and sends it to the screen
     * @param scene - it  is gathering the all the objects - lights, geometries , camera and "watching screen"
     */
    public Render(ImageWriter imageWriter, Scene scene)
    {
        this. _imageWriter = imageWriter;
        this. _scene=   scene;
    }

    /**
     * get scene
     * @return the scene itself
     */
    public Scene get_scene()
    {
        return  this._scene;
    }


    /**
     * to know if the customer wants with the improvement of super sampling or not
     */
    protected boolean withSuperSampling;

    /**
     * get for the super sampling field
     * @return the answer. true = with, false- without
     */
    public boolean isWithSuperSampling()
    {
        return withSuperSampling;
    }

    /**
     * set for the super sampling field
     * @param withSuperSampling
     */
    public void setWithSuperSampling(boolean withSuperSampling)
    {
        this.withSuperSampling = withSuperSampling;
    }


    /**
     * to know if the customer wants with the improvement of  adaptive super sampling or not
     */
    protected boolean adaptiveSuperSampling;
    /**
     * getter for the adaptive super sapmling improvement
     * @return the answer. true = with, false- without
     */
    public boolean isAdaptiveSuperSampling()
    {
        return adaptiveSuperSampling;
    }

    /**
     * set for the super sampling field
     */

    public void setAdaptiveSuperSampling(boolean adaptiveSuperSampling)
    {
        this.adaptiveSuperSampling = adaptiveSuperSampling;
    }



    /**
     *get for num of rays in one beam
     * @return the num of rays that will be calculated in 1 pixel
     */
    public double getnumOfRays_inBeam()
    {
        return numOfRays_inBeam;
    }

    /**
     * set func for how many rays the costumer would want.
     * includes an exeption if the num he provides is less than 1
     * @param numOfRays_inBeam
     */
    public void setnumOfRays_inBeam(double numOfRays_inBeam)
    {
          try
            {
                if(numOfRays_inBeam > 0)//num can't be negative
                    this.numOfRays_inBeam = numOfRays_inBeam;
                else
                    throw new IllegalArgumentException("the number of rays cannot be zero or negative");
            }
            catch(IllegalArgumentException ex)
            {
                System.out.println(ex);
            }
    }

    /**
     * the customer will have the freedom to choose how many rays
     */
    double numOfRays_inBeam;

    /**
     * gets and collects all the data, and for every pixel sets the color.
     *
     * Filling the buffer according to the geometries that are in the scene.
     * This function does not creating the picture file, but create the buffer pf pixels
     *
     * after refactoring -this func includes multiply choices
     * 1.with or without "super sampling" - for the costumer's choice.
     * 2.the number of threads that will run - if 1 - just like before the refactoring. if more - with all the thread's addition
     * 3.the number of ray in a beam - can't be a negative number or equal to 0. if the num is 1 - just like before refactoring
     *
     * the big change/ refactor - instead of writing straight to the pixel with calculating by calcColor there,
     * we will define a parameter color first at "black" and then will sum the amount of each ray into the parameter.
     * at last we will divide by the sum of rays- an average.
     * by that, each pixel will get it's color by the average of the color amount of the rays.
     */
    public void renderImage()
    {
        java.awt.Color background =  this._scene.getBackground().getColor();
        Camera camera=  this._scene.getCamera();
        Intersectable geometries =  this._scene.getGeometries();//the geometries that will be at the picture
        double  distance =  this._scene.getDistance();

        //width and height are the number of pixels in the rows
        //and columns of the view plane
        int width = (int)  this._imageWriter.getWidth();
        int height = (int)  this._imageWriter.getHeight();

        //Nx and Ny are the width and height of the image.
        int Nx =  this._imageWriter.getNx();
        int Ny =  this._imageWriter.getNy();
        Ray ray;

    if (_threads == 1)
    {
        for (int row = 0; row < Ny; row++)
        {
            for (int column = 0; column < Nx; column++)
            {
                if(withSuperSampling == false)//if the customer doesn't want the improvement of "super sampling"
                {
                    ray = camera.constructRayThroughPixel(Nx, Ny, column,row , distance, width, height);//calculates the ray that will go through the pixel , confined  by the parameters we sent.

                    GeoPoint closestPoint = findClosestIntersection(ray);//which geometry is the closest
                    if (closestPoint == null)//if I didn't find- the color of the pixel will be the background color
                        _imageWriter.writePixel(column, row, background);

                    else                    //we will add to the pixel color the color of the specific point
                        _imageWriter.writePixel(column, row, calcColor(closestPoint, ray).getColor());
                }
              //refactor- using the super sampling
            if(withSuperSampling == true)//if the costumer does want the improvement
            {
                List<Ray> rayList = camera.constructRays_ThroughPixel(Nx, Ny, column, row, distance, width, height,numOfRays_inBeam );//calculates the ray that will go through the pixel , confined  by the parameters we sent.
                double counter = 0;//will help to know if there were a few rays, single or non
                Color color = Color.BLACK;//at first, we will start with black color and than will sum it according to the num of rays
                    for (Ray r : rayList)//going all over the rays
                    {
                        GeoPoint closestPoint = findClosestIntersection(r);//which geometry is the closest

                        if (closestPoint == null)//if there isn't any geometry
                            color = color.add(background);//just add the background color - 0
                            //_imageWriter.writePixel(pixel.col, pixel.row, background);
                        else//if we do find "closestPoint"
                        {
                            counter++;//make sure to count
                            color = color.add(calcColor(closestPoint, r));//add it to the sum of color - after will use to average
                            //  _imageWriter.writePixel(pixel.col, pixel.row, calcColor(closestPoint, r).getColor());//before the refactor
                        }

                    }

                    Color currentColor;
                    if (counter == 0)//just in case I couldn't find any intersection point
                        currentColor = new Color(background);//updating the color to be as the background color
                    else//if we did find intersection points after going through all over the rays
                    {
                        currentColor = color;//updating the color = before making the average
                        currentColor = currentColor.scale(1 /numOfRays_inBeam /*100d*/);//making average - divide by the number of rays
                            _imageWriter.writePixel(column, row, currentColor.getColor());//only then we will write to the pixel
                    }
                }
            }
        }
    }
    else//with threads
    {
        final Pixel thePixel = new Pixel(Ny, Nx); // Main pixel management object
        Thread[] threads = new Thread[_threads];
        for (int i = _threads - 1; i >= 0; --i)
        { // create all threads
            threads[i] = new Thread(() ->
            {
                Pixel pixel = new Pixel(); // Auxiliary thread’s pixel object
                //
                while (thePixel.nextPixel(pixel))
                {
                    //for improvement of adaptive super sampling
                    if (adaptiveSuperSampling == true)
                    {
                        //I sent new sizes and divided height of the picture by the num of the pixels and in 2 to get to center of the pixel,
                        // and prevent from calculating the same ray for a couple of times
                        Color result = adaptiveSamplingRecursion(camera, distance, Nx, Ny, width, height, pixel,
                                width / Nx / 2, height / Ny / 2, -width / Nx / 2, height / Ny / 2, MAX_ADAPTIVERECURSION);
                        _imageWriter.writePixel(pixel.col, pixel.row, result.getColor());
                    }
                    else//for the super sampling
                    {
                        List<Ray> rayList = camera.constructRays_ThroughPixel(Nx, Ny,  pixel.col, pixel.row, distance, width, height,numOfRays_inBeam);//calculates the ray that will go through the pixel , confined  by the parameters we sent.
                        Color color = Color.BLACK;
                        double counter = 0;//will help to know if there were a few rays, single or non
                        for (Ray r : rayList)
                        {
                            GeoPoint closestPoint = findClosestIntersection(r);//which geometry is the closest

                            if (closestPoint == null)
                                color = color.add(background);
                                //_imageWriter.writePixel(pixel.col, pixel.row, background);
                            else                    //we will add to the pixel color the color of the specific point
                            {
                                counter++;
                                color = color.add(calcColor(closestPoint, r));
                                //  _imageWriter.writePixel(pixel.col, pixel.row, calcColor(closestPoint, r).getColor());
                            }

                        }

                        Color currentColor;
                        if (counter == 0)
                            currentColor = new Color(background);
                        else
                        {
                            currentColor = color;
                            currentColor = currentColor.scale(1 /numOfRays_inBeam);//divide to 5 - there are 5 rays
                            _imageWriter.writePixel(pixel.col, pixel.row, currentColor.getColor());
                        }
                    }
                }
                } );
            }

        for (Thread thread : threads) thread.start(); // Start all the threads
// Wait for all threads to finish
        for (Thread thread : threads) try { thread.join(); } catch (Exception e) {}
        if (_print) System.out.printf("\r100%%\n"); // Print 100%
    }

    }





    /**
     * calculates the color by using a recursion. the depth of recursion is limited to be 4 - defined at the beginning of the class.
     * in here th pixel will be divided into 4 sub-pixels, dependes on the color of the rays at the bottoms.
     * 2 ways to stop the recursive call - we stopped at level =1, or all 4 rays are like the center ray and therefore there is no need for  more calls.
     *
     *  in here it will creates a list of rays. the returned rays will be not precisely at the edges of the pixel/sub-pixel to prevent multiply calculation for common ray.
     *  therefore - I changed the location of the rays a little bit inside to the pixel
     *
     * @param camera the camera we use for the scene
     * @param distance the distance between the camera and view plane
     * @param nx number of pixels on the x axis
     * @param ny number of pixels on the y axis
     * @param width the width of the picture
     * @param height the height of the picture
     * @param pixel a single pixel which we are currently in
     * @param TX top corner of x
     * @param TY top corner of y
     * @param BX bottom corner of x
     * @param BY bottom corner of y
     * @param level the level of the recursive
     * @return color of the pixel that was sent
     */
    private Color adaptiveSamplingRecursion(Camera camera, double distance, int nx, int ny, double width, double height, Pixel pixel, double TX, double TY, double BX, double BY ,int level)
    {
        //the list of the "moved" rays
        List<Ray> rays = camera.constructAdaptiveRayBeamThroughPixel(nx, ny, pixel.col, pixel.row, distance, width, height, TX, TY, BX, BY);
        Ray center = rays.get(0); // the center
        Color centerColor;//color center
        Color TLColor ;//top left
        Color TRColor ;//top right
        Color BLColor ;//bottom left
        Color BRColor ;//bottom right

        //for the center
        if(findClosestIntersection(center) == null)
        { // if there aren't any intersections - there is no need to calculate
            centerColor = _scene.getBackground();
        }
        else
            { // calculates the color
            centerColor = calcColor(findClosestIntersection(center), center);
        }

        //for the top left
        Ray TL = rays.get(1);
        if(findClosestIntersection(TL) == null)
        {// if there aren't any intersections
            TLColor = _scene.getBackground();
        }
        else {// calculates the color
            TLColor = calcColor(findClosestIntersection(TL), TL);
        }

        //for the top right
        Ray TR = rays.get(2);
        if(findClosestIntersection(TR) == null) {// if there aren't any intersections
            TRColor = _scene.getBackground();
        }
        else
            {// calculates the color
            TRColor = calcColor(findClosestIntersection(TR), TR);
        }

        //for the bottom left
        Ray BL = rays.get(3);
        if(findClosestIntersection(BL) == null) {// if there aren't any intersections
            BLColor = _scene.getBackground();
        }
        else
            {// calculates the color
            BLColor = calcColor(findClosestIntersection(BL), BL);
        }

        //for the bottom right
        Ray BR = rays.get(4);
        if(findClosestIntersection(BR) == null) {// if there aren't any intersections
            BRColor = _scene.getBackground();
        }
        else {// calculates the color
            BRColor = calcColor(findClosestIntersection(BR), BR);
        }

        //the 2 ways to stop the recursion call:
        if (level == 1)
        { // end of recursive
            centerColor = centerColor.add(TLColor, TRColor, BLColor, BRColor);//adding all the colors
            return centerColor.reduce(5); // return the calculated color
        }

        // if the color in all the corners and center are equal-  return the color
        if (TLColor.equals(centerColor) && TRColor.equals(centerColor) && BLColor.equals(centerColor) && BRColor.equals(centerColor))
        {
            return centerColor;
        }

        else//continue with the recursive call. we will check each region with the center, and depens on the result we will divide to sub pixels
            {
            // if the color in each corner isn't equal to the center color - call the recursive function again with that corner
            if (!TLColor.equals(centerColor))//if the top left region is not the same like the center - create subpixel
            {
                TLColor = adaptiveSamplingRecursion(camera, distance, nx, ny, width, height, pixel, TX, TY, (TX+BX) / 2, (TY+BY) / 2, level - 1);
            }
            if (!TRColor.equals(centerColor))//if the top right region is not the same like the center - create subpixel
            {
                TRColor = adaptiveSamplingRecursion(camera, distance, nx, ny, width, height, pixel,  TX,(TY+BY)/2, (TX+BX)/2, BY, level - 1);
            }
            if (!BLColor.equals(centerColor))//if the bottom left region is not the same like the center - create subpixel
            {
                BLColor = adaptiveSamplingRecursion(camera, distance, nx, ny, width, height, pixel, (TX+BX)/2 ,TY, BX, (TY+BY)/2, level - 1);
            }
            if (!BRColor.equals(centerColor))//if the bottom right region is not the same like the center - create subpixel
            {
                BRColor = adaptiveSamplingRecursion(camera, distance, nx, ny, width, height, pixel, (TX+BX) / 2, (TY+BY) / 2, BX, BY, level - 1);
            }
        }
        //when we finished all the recursive calls
        centerColor = centerColor.add(TLColor, TRColor, BLColor, BRColor); // adds the colors
        return centerColor.reduce(5); // returns the calculated color
    }








    /**
     *
     * // no longer relevant- instead we use- findClosestIntersection- "refactor" (because we don't calculate just light rays- but more..)
     * finds the closest point
     * @param intersectionPoints - list of all the geometries and their intersectioned points
     * @return the closest point to the lens of the camera
     */
    private GeoPoint getClosestPoint(List<GeoPoint> intersectionPoints)
    {
        GeoPoint result = null;
        double mindist = Double.MAX_VALUE;

        Point3D p0 = this._scene.getCamera().get_p0();

        for (GeoPoint geo: intersectionPoints )//going through all over the list to find what is the closest point
        {
            Point3D pt = geo.getPoint();
            double distance = p0.distance(pt);
            if (distance < mindist)//if the distance of the point and camera < from mindist -
            {
                mindist = distance; //it means that this point has the smallest distance- therefore it is the closest
                result = geo;
            }
        }
        return  result;//the closest point
    }

    /**
     * a shell func that calls the "write to image" func through the _image-writer field
     */
    public void writeToImage()
    {
        this._imageWriter.writeToImage();
    }

    private Color calcColor (List<Ray> inRay)
    {
        Color background = _scene.getBackground();
        Color color = Color.BLACK;
        for(Ray ray: inRay)
        {
            GeoPoint gp = findClosestIntersection(ray);
            color = color.add(gp ==null ? background : calcColor (gp, ray, MAX_CALC_COLOR_LEVEL,1d));

        }
        color = color.add(_scene.getAmbientLight().getIntensity());
        int size = inRay.size();
        return (size ==1) ? color : color.reduce(size);
    }

    /** the recursive calcColor.
     * we will use it to calculate everything that connected to the point - if there are any transparency/reflection/refraction next to it.
     *Calculating the intensity of a color in a certain point
     * @param intersection - the intersected point and it's geometry
     * @param inRay - the ray that intersects with the geopoint
     * @param level - one of the parameters to stop the recursion. begins in 10 and with every recursive call decrease in 1 level.
     * @param k -one of the parameters to stop the recursion. we will check if it is smaller than the minimal k we set (MIN_CALC_COLOR_K) and with the calculation of kkt and kkr
     * @return the color of the specific intersected point (after taking into consideration (and calculation) all the elements)
     */
    private Color calcColor(GeoPoint intersection, Ray inRay, int level, double k)
    {
        if (level == 1 || k < MIN_CALC_COLOR_K)//the stop condition for the recursion
            return Color.BLACK;

        //collecting all the data
        Color resultColor = intersection.getGeometry().get_emmision();//the color of the geometry
        Point3D pointGeo = intersection.getPoint();//the point on the geometry

        Vector v = pointGeo.subtract(_scene.getCamera().get_p0()).normalize();//vector from the camera to the geometry
        Vector n = intersection.getGeometry().getNormal(pointGeo);//the normal to the intersection point

        Material material = intersection.getGeometry().get_material();

        int nShininess = material.get_nShininess();
        double kd = material.get_kD();//"gorem harchaka" of the material- helps to know how much the diffuse light is strong
        double ks = material.get_kS();//"gorem harchaka" of the material - helps to know the amount of the specular light
        double kr = material.get_kR();//for the reflection. kr=1 represents perfect mirror, kr = 0 represents matt
        double kt = material.get_kT();//for the refraction. kt=1 represents complete transparency, kt = 0 represents sealed

        double kkr = k * kr;//for some limit -reflection ray- on of the things to stop the recursion call
        double kkt = k * kt;//for some limit - refraction ray- on of the things to stop the recursion call

        List<LightSource> lights = _scene.getLights();

        if (lights != null)//if there are lights
        {
            for (LightSource lightSource : _scene.getLights())//for each light source in the scene's light sources
            {
                Vector l = lightSource.getL(intersection.point);//the lights direction from geopoint
                if (n.dotProduct(l)*n.dotProduct(v) > 0)//if the dot product (scalar multiplication) between the normal and the light direction times the dot product between the normal and the normal vector between the camera and geopoint
                    //for the same direction
                {
                    double ktr = transparency(lightSource, l, n, intersection);//instead of  using the boolean "unshaded", in case of  partial transparent geometry- it will be half shadowed. as long ktr is small-  it means the geometry is more shadowed
                    //if (unshaded(lightSource,l, n, intersection))//if the geopoint isn't shaded by the light
                    if(ktr* k > MIN_CALC_COLOR_K)//if it is not so shadowed- add it (big ktr - less shadow, small ktr - more shadow)
                    {
                        primitives.Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);//intensity color of the geopoint. scale ktr for the shadow action and it will affect the light intensity
                        resultColor = resultColor.add(calcDiffusive(kd,l.dotProduct(n), lightIntensity),
                                calcSpecular(ks,l,n, l.dotProduct(n), v, nShininess, lightIntensity));//adds the specular and diffuse lights to the color. the phong model.
                    }
                }

            }
        }

        if (kkr > MIN_CALC_COLOR_K)//if we can calculate more reflection points
        {
            Ray reflectedRay = constructReflectedRay(pointGeo, inRay, n);//recalculate ray
            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);//recalculate point

            if(reflectedPoint != null)//if we found geometry
                resultColor = resultColor.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));//add its color

        }

        if (kkt > MIN_CALC_COLOR_K)//if we can calculate more refraction points
        {
            Ray refractedRay = constructRefractedRay(pointGeo, inRay, n);//recalculate ray
            GeoPoint refractedPoint = findClosestIntersection(refractedRay);//recalculate point

            if (refractedPoint != null)//if we found geometry
                resultColor = resultColor.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));//add its color

        }

        return resultColor;//of the point
    }


    /**
     * a shell function of the "calc_color" function - calls the recursive "calcColor" .
     * we don't want the costumer/no one besides the programmer to control the level of the recursion
     * @param geoPoint - the geometry with its intersected point
     * @param inRay - the intersected ray
     * @return the finite color calculation (after using the recursive CalcColor)
     */
    private Color calcColor(GeoPoint geoPoint, Ray inRay)
    {
        Color color = calcColor(geoPoint, inRay, MAX_CALC_COLOR_LEVEL, 1.0);//if k is too small we will break he recursion. calculates the color at the point
        color = color.add(_scene.getAmbientLight().getIntensity());//adding the ambient light to the background

        return color;

    }

    /**
     * calculating the reflected ray - very similar to the specular light calculation
     * @param geopoint- the intersected point with its geometry
     * @param inRay - the ray
     * @param n - the normal to the point
     * @return the reflected ray
     */
    public Ray constructReflectedRay(Point3D geopoint, Ray inRay, Vector n)
    {

        //r =v-2*v*n*n
        Vector normal = new Vector(n);
        normal.normalize();

        Vector direction = new Vector(inRay.get_dir());//the vector v in the equation
        direction.normalize();

        if(direction.dotProduct(normal) == 0)//if v*n ==0 - there is no reflect
            return null;

        Vector r = normal.scale((-2)* direction.dotProduct(normal));
        r = r.add(direction);//adding v



        Ray reflectedRay = new Ray(geopoint, r, n);

        return reflectedRay;
    }

    /**
     * calculating the refracted ray  by the geopoint, the vector direction and the normal to the  point
     * @param geopoint -  the intersected point with its geometry
     * @param inRay - thr ray from the light
     * @param n - the normal to the point
     * @return the refracted ray
     */
    public Ray constructRefractedRay(Point3D geopoint, Ray inRay, Vector n)
    {
        Vector normal = new Vector(n);
        normal.normalize();

        Vector direction = new Vector(inRay.get_dir());
        direction.normalize();

        Ray refractedRay = new Ray(geopoint, direction, normal);

        return refractedRay;


    }



    /**
     * Printing the grid with a fixed interval between lines
     * @param interval - The interval between the lines.
     * @param colorsep - the color of the printed grid
     */
   public void printGrid(int interval,/*Color*/ java.awt.Color colorsep)
    {
        double rows = this._imageWriter.getNx();
        double collumns =  this._imageWriter.getNy();
        //Writing the lines.

        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < collumns; col++)
            {
                if (col % interval == 0 || row % interval == 0)
                {
                    this._imageWriter.writePixel(row, col, colorsep);
                }
            }
        }
    }



    /**
     * help function -checking if a number is positive. very useful at the program. support the DRY principle.
     * @param val - double number
     * @return if it is positive or not
     */
    private boolean sign(double val)
    {
        return (val > 0d);
    }


    /**
     * calculating the diffuse light = the spread of the light and its influence on the geometry's color when the light hits it from specific location
     * @param kd - "material affect"
     * @param nl  - multiplication of 2 normalized vectors - gives the cos of their angle. as long the angle smaller - the intensity stronger.
     *           l - the light ray that hits the point, n - the normal to the point.
     * @param ip - the intensity of the color
     * @return the color after calculating the diffusing of the light
     */
    private Color calcDiffusive(double kd, double nl, Color ip)
    {
        if(nl < 0)
            nl = nl*(-1);//absolute value

        double res = nl * kd;//the diffuse of the light
        Color diffuse = ip.scale(res);//adding it to the color -

        return diffuse;

    }

    /**
     * calculating the specular light - the returned light from the material of the geometry
     * @param ks -mattrial affect
     * @param l - the light ray
     * @param n the normal
     * @param nl - multiplication of 2 normalized vectors - gives the cos of their angle. as long the angle smaller - the intensity stronger.
     *      *           l - the light ray that hits the point, n - the normal to the point.
     * @param v - vector from the camera
     * @param nShininess - the amount of Shininess of the geometry. as long it is more shiny, this parameter would increase and the returned light will be bigger
     * @param ip - the intensity of the color
     * @return
     */
    private Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, Color ip)
    {
        double p = nShininess;
        if (isZero(nl))
        {
            throw new IllegalArgumentException("nl cannot be Zero for scaling the normal vector");
        }

        Vector R = l.add(n.scale(-2 * nl)); // nl must not be zero!. R = the vector of the returned light
        //R = L-2*(L*N)*N

        double VR = alignZero(v.dotProduct(R));//the dot product gives the angle cosinus between them - the amount of the viewer's watching of the returning light
        if (VR >= 0 )
        {
            return Color.BLACK; // view from direction opposite to r vector
        }
        return ip.scale(ks * Math.pow(-1d *VR, p));
    }

    /**was used before- but after refactor we uses transparency.
     * knowing if the point is shaded/not is not enough, we want to get partial shadow in case the geometry that blocks the point has some transparency feature
     * checks if there is any geometry that shades the intersection point
     * @param light - the light source
     * @param l -  the light ray from the light source towards the geometry
     * @param n - the normal
     * @param geopoint - the point that is shaded
     * @return if the point is shaded by some geometry or not
     */
    private boolean unshaded(LightSource light,Vector l, Vector n, GeoPoint geopoint)
    {

        Vector lightDirection = l.scale(-1); // from point to light source to check if someone shades me
        Point3D point = geopoint.getPoint();//.add(delta);// the delta moved to the ray class - DRY
        Vector normal = new Vector(n).normalized();
        Ray lightRay = new Ray(point, lightDirection, normal);//calculates the light ray
        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);//finding if there are any geometries with the light ray

        if (intersections.size() == 0)//if there is non - the point in unshaded
            return true;

        double lightDistance = light.getDistance(geopoint.getPoint());// the distance between the point and the light source

        for (GeoPoint gp : intersections)
        {
            double temp = gp.getPoint().distance(geopoint.getPoint()) - lightDistance;//calculates if the distance between ,e and the other geomtery is bigger- it doesnt shades me
            if (primitives.Util.alignZero(temp) <= 0 && gp.getGeometry().get_material().get_kT() == 0) //if (lightDistance - temp <=0)- someone shades me, and alos the geometry that shades me- is not transpernt clearlry
                return false;
        }
        return true;

    }

    /**
     *this func calculates the intersections and also calculates the closest intersection to the head of the ray.
     * "refactor" - instead of "getClosestPoint"
     * (if there won't be any intersection points- it will return "null")
     * @param ray - intersects the scene
     * @return the closest point from all
     */
    private GeoPoint findClosestIntersection(Ray ray)
    {
        if (ray == null)//if the ray is undefined
        {
            return null;
        }
        GeoPoint closestPoint = null;
        double closestDistance = Double.MAX_VALUE;
        Point3D ray_p0 = ray.get_p0();

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);//gets all the list of geometries that have intersection point with the ray
        if (intersections == null)//if there is no intersection points between the ray and any of the geometries
            return null;

        for (GeoPoint geoPoint : intersections)//going through the list
        {
            double distance = ray_p0.distance(geoPoint.getPoint());//the distance between the head point of the ray and the suspected intersected point
            if (distance < closestDistance)//if the distance is smaller than the determined "closestDistance"
            {
                closestPoint = geoPoint;
                closestDistance = distance;
            }
        }
        return closestPoint;//the closest intersected point from all
    }

    /** knowing if the point is shaded/not is not enough, we want to get partial shadow in case the geometry that blocks the point has some transparency feature
     * this function calculates the transparency of the geometry that shadows the point, and returns the degree of transparency....
     * @param light -  the light source
     * @param l -  the light ray from the light source towards the geometry
     * @param n -  the normal
     * @param geopoint - the point that is shaded
     * @return the amount of transparency - and according to that we will put partial shadow
     */
    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint)
    {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.getPoint(), lightDirection, n); // goes to the c-tor that adds the delta value
        Point3D pointGeo = geopoint.getPoint();

        List<GeoPoint> intersections = _scene.getGeometries().findIntersections(lightRay);
        if (intersections == null)//it means no geometry is between the point and the light source
            return 1d; // 1.0//there is non

        double lightDistance = light.getDistance(pointGeo);
        double ktr = 1d;
        for (GeoPoint gp : intersections)
        {
            if (primitives.Util.alignZero(gp.getPoint().distance(pointGeo) - lightDistance) <= 0)//does it shades me? - ktr will be how much it shades me. as long it will be more transparent ktr will be bigger
            {
                ktr *= gp.getGeometry().get_material().get_kT();//calculating the amount of transparency
                if (ktr < MIN_CALC_COLOR_K)
                    return 0.0;//shaded completly

            }
        }
        return ktr;
    }

}
