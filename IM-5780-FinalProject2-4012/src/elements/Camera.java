//Rinat Canaan 207744012
package elements;

import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.isZero;

public class Camera
{
    Point3D _p0;//location of the camera
    Vector _vUp;//positive vector points up
    Vector _vTo;// vector that its direction towards the screen
    Vector _vRight;// vR =  vto x vup, vector that its side is to the right side of the field

//getters

    /**
     * @return the center of the camera
     */
    public Point3D get_p0()
    {
        return _p0;
    }

    /**
     * @return the axis Y vector
     */
    public Vector get_vUp() {
        return _vUp;
    }

    /**
     * @return the axis z vector
     */
    public Vector get_vTo() {
        return _vTo;
    }

    /**
     * @return the axis x vector
     */
    public Vector get_vRight() {
        return _vRight;
    }

    /**
     * constructor based on point and 2 vectors
     *
     * @param _p0
     * @param _vUp
     * @param _vTo
     * @throws IllegalArgumentException
     */
    public Camera(Point3D _p0, Vector _vTo, Vector _vUp) throws IllegalArgumentException
    {
        if (!(isZero(_vUp.dotProduct(_vTo))))// if vUP * vTO != 0, means not vertical to each other
        {
            throw new IllegalArgumentException("the vectors are not orthogonal to each other");
        }
        this._vUp = _vUp.normalized();//length = 1
        this._vTo = _vTo.normalized();//length = 1
        this._vRight = this._vTo.crossProduct(this._vUp).normalized();// vR =  vto x vup
        this._p0 = _p0;
    }

    /**
     * copy constructor
     *
     * @param cam - camera
     */
    public Camera(Camera cam)
    {
        _p0 = new Point3D(cam._p0);
        _vUp = new Vector(cam._vUp);
        _vTo = new Vector(cam._vTo);
        _vRight = new Vector(cam._vRight);
    }



    /**
     * @param nX             - pixel's num on X
     * @param nY             - pixel's num on Y
     * @param j              - the Y coordinate of the given pixel
     * @param i              - the X coordinate of the given pixel
     * @param screenDistance - the distance from the camera to the screen
     * @param screenWidth
     * @param screenHeight
     * @return the ray that go through a specific pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight)
    {
        if (isZero(screenDistance))//distance can't be 0
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }
        //Pc = P0 + d∙Vto - image center
        Point3D Pc = _p0.add(_vTo.scale(screenDistance));

        //Ratio (pixel width & height)
        double Ry = screenHeight / nY;//Ry = h/Ny
        double Rx = screenWidth / nX;//Rx = w/N

        //Pixel[i,j] center
        double yi = ((i - nY / 2d) * Ry + Ry / 2d);//yi = (i – Ny/2)∙Ry + Ry/2
        double xj = ((j - nX / 2d) * Rx + Rx / 2d);//xj = (j – Nx/2)∙Rx + Rx/2

       // Ray ray = helperRayCalculation(Pc,xj,yi );
        //return ray;
        Point3D Pij = Pc;//  Pi,j = Pc + (Xj∙V_right – Yi∙Vup)

        if (!isZero(xj))
        {
            Pij = Pij.add(_vRight.scale(xj));
        }
        if (!isZero(yi))
        {
            Pij = Pij.add(_vUp.scale(-yi));
        }

        Vector Vij = Pij.subtract(_p0);//Vi,j = Pi,j – P0

        return new Ray(Vij.normalized(), _p0);//Ray: {_p0 = P0, _direction = normalize(Vi,j) }
    }


/*public Ray helperRayCalculation(Point3D Pc, double xj, double yi)
{
    Point3D Pij = Pc;//  Pi,j = Pc + (Xj∙V_right – Yi∙Vup)

    if (!isZero(xj))
    {
        Pij = Pij.add(_vRight.scale(xj));
    }
    if (!isZero(yi))
    {
        Pij = Pij.add(_vUp.scale(-yi));
    }

    Vector Vij = Pij.subtract(_p0);//Vi,j = Pi,j – P0
    return new Ray(Vij.normalized(), _p0);//Ray: {_p0 = P0, _direction = normalize(Vi,j) }
}*/


 /*   public List<Ray> constructRays_ThroughPixel (int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight)
    {
        if (isZero(screenDistance))//distance can't be 0
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }
        //Pc = P0 + d∙Vto - image center
        Point3D Pc = _p0.add(_vTo.scale(screenDistance));

        //Ratio (pixel width & height)
        double Ry = screenHeight / nY;//Ry = h/Ny
        double Rx = screenWidth / nX;//Rx = w/N

        //Pixel[i,j] center
        double yi = ((i - nY / 2d) * Ry + Ry / 2d);//yi = (i – Ny/2)∙Ry + Ry/2
        double xj = ((j - nX / 2d) * Rx + Rx / 2d);//xj = (j – Nx/2)∙Rx + Rx/2


        Point3D Pij = Pc;//  Pi,j = Pc + (Xj∙V_right – Yi∙Vup)

        if (!isZero(xj)) {
            Pij = Pij.add(_vRight.scale(xj));
        }
        if (!isZero(yi)) {
            Pij = Pij.add(_vUp.scale(-yi));
        }

        Vector Vij = Pij.subtract(_p0);//Vi,j = Pi,j – P0

        //return new Ray(Vij.normalized(),_p0);//Ray: {_p0 = P0, _direction = normalize(Vi,j) }

        List <Ray> rayListInPixel = new ArrayList<Ray>();

        Ray center_ray = new Ray(Vij.normalized(), _p0);
        Point3D center_point =_p0.add(center_ray.get_dir());//creating the center point - also need to consider p0 for the camera location
        rayListInPixel.add(center_ray);//adding it into the ray's list

        //explanation about the directions - it is not as it really seams in a normal picture.
        //upperLeftSide - is actually lower right side
        //lowerLeftSide - is actually upper right side
        //upperRightSide - is actually lower left side
        //lowerRightSide - is actually upper left side

        //creating the upper left ray. by +Rx, +Ry - it will get the right location
        Point3D upperLeftSidePoint = new Point3D((center_point.get_x().get()+(Rx/4)),//creating the point according to the center point location, and adding it the ratio/ 4 - the num of the rays
                (center_point.get_y().get()+(Ry/4)),
                center_point.get_z().get());
        Ray upperLeftSideRay = new Ray (center_ray.get_dir(),upperLeftSidePoint);
        rayListInPixel.add(upperLeftSideRay);//adding it into the ray's list


        //creating the lower left ray. by +Rx, -Ry - it will get the right location
        Point3D lowerLeftSidePoint = new Point3D((center_point.get_x().get()+(Rx/4)),//creating the point according to the center point location, and adding it the ratio/ 4 - the num of the rays
                (center_point.get_y().get()+(-Ry/4)),
                center_point.get_z().get());
        Ray lowerLeftSideRay = new Ray (center_ray.get_dir(),lowerLeftSidePoint);
        rayListInPixel.add(lowerLeftSideRay);//adding it into the ray's list

        //creating the upper right ray. by -Rx, +Ry - it will get the right location
        Point3D upperRightSidePoint = new Point3D((center_point.get_x().get()+(-Rx/4)),//creating the point according to the center point location, and adding it the ratio/ 4 - the num of the rays
                (center_point.get_y().get()+(Ry/4)),
                center_point.get_z().get());
        Ray upperRightSideRay = new Ray (center_ray.get_dir(),upperRightSidePoint);
        rayListInPixel.add(upperRightSideRay);//adding it into the ray's list

        //creating the lower right ray. by -Rx, -Ry - it will get the right location
        Point3D lowerRightSidePoint = new Point3D((center_point.get_x().get()+(-Rx/4)),//creating the point according to the center point location, and adding it the ratio/ 4 - the num of the rays
                (center_point.get_y().get()+(-Ry/4)),
                center_point.get_z().get());
        Ray lowerRightSideRay = new Ray (center_ray.get_dir(),lowerRightSidePoint);
        rayListInPixel.add(lowerRightSideRay);//adding it into the ray's list

        return rayListInPixel;

    }*/


   /* public List<Ray> constructRays_ThroughPixel (int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight)
    {
        List<Ray> rayListInPixel = new ArrayList<Ray>();

            if (isZero(screenDistance))//distance can't be 0
            {
                throw new IllegalArgumentException("distance cannot be 0");
            }
            //Pc = P0 + d∙Vto - image center
            Point3D Pc = _p0.add(_vTo.scale(screenDistance));
            //List<Ray> rayListInPixel = new ArrayList<Ray>();
            //Ratio (pixel width & height)
            double Ry = screenHeight / nY;//Ry = h/Ny
            double Rx = screenWidth / nX;//Rx = w/N

            //Pixel[i,j] center
            double yi = ((i - nY / 2d) * Ry + Ry / 2d);//yi = (i – Ny/2)∙Ry + Ry/2
            double xj = ((j - nX / 2d) * Rx + Rx / 2d);//xj = (j – Nx/2)∙Rx + Rx/2
            Point3D Pij = Pc;//  Pi,j = Pc + (Xj∙V_right – Yi∙Vup)
            if (!isZero(xj))
                Pij = Pij.add(_vRight.scale(xj));

            if (!isZero(yi))
                Pij = Pij.add(_vUp.scale(-yi));

        for (int k = 0; k < 80; k++)
        {
            //will define the limits of the pixel
            Random random = new Random();
            double randX = -Rx / 2 + (Rx) * random.nextDouble();
            double randY = -Rx / 2 + (Ry) * random.nextDouble();

            Pij = Pij.add(new Vector(randX, randY, 0));
            Vector Vij = new Vector(Pij.subtract(_p0));
            Ray ray = new Ray(Vij.normalize(), _p0);

            rayListInPixel.add(ray);
        }


        return rayListInPixel;
    }
}*/
    /**
     * instead of returning 1 ray that will return to the pixel's calculation,
     * now i will create a beam of random rays - spread all over the pixel, by random places.
     * this will solute the problem of "sharp edges"
     *
     * how?
     * first I will define the wanted limits, calculate the center ray of the pixel
     * and by than will create a loop that will create the random rays.
     *
     * @param nX             - pixel's num on X
     * @param nY             - pixel's num on Y
     * @param j              - the Y coordinate of the given pixel
     * @param i              - the X coordinate of the given pixel
     * @param screenDistance - the distance from the camera to the screen
     * @param screenWidth
     * @param screenHeight
     * @return the ray that go through a specific pixel
     */
    public List<Ray> constructRays_ThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight, double numOfRays)
    {
        //the refactor - i will set min number and maximuo number for x and y as limits for randomising
        //the difference between the min and the max - the max is xj/yi regular calculation, and the min is for the starting point of the pixel
        //when we add (screenHeight / nY) it allow us to go to the end of the pixel limit
        //the min - for the start
        //the max - for the end
        double rang_minY = (i - nY / 2d) * (screenHeight / nY);///
        double rang_maxY = (i - nY / 2d) * ((screenHeight / nY)) + ((screenHeight / nY));//yi = (i – Ny/2)∙Ry + Ry/2

        double rang_minX = (j - nY / 2d) * (screenWidth / nX);
        double rang_maxX = (j - nY / 2d) * (screenWidth / nX) + (screenWidth / nX);
        Random r = new Random();
        List<Ray> rayListInPixel = new ArrayList<Ray>();

        //the old code
        if (isZero(screenDistance))//distance can't be 0
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }
        //Pc = P0 + d∙Vto - image center
        Point3D Pc = _p0.add(_vTo.scale(screenDistance));
        Point3D Pij = Pc;//  Pi,j = Pc + (Xj∙V_right – Yi∙Vup)
        //List<Ray> rayListInPixel = new ArrayList<Ray>();
        //Ratio (pixel width & height)
        double Ry = screenHeight / nY;//Ry = h/Ny
        double Rx = screenWidth / nX;//Rx = w/N

        //Pixel[i,j] center
        double yi = ((i - nY / 2d) * Ry + Ry / 2d);//yi = (i – Ny/2)∙Ry + Ry/2
        if (!isZero(yi))
            Pij = Pij.add(_vUp.scale(-yi));//allocating the point by the y

        double xj = ((j - nX / 2d) * Rx + Rx / 2d);//xj = (j – Nx/2)∙Rx + Rx/2
        if (!isZero(xj))
            Pij = Pij.add(_vRight.scale(xj));//allocating the point by the x

        Vector Vij = new Vector(Pij.subtract(_p0));//the vector of the specific pixel

        //till here everything was similar just like constructing a single ray
        //from here the refactoring

        Ray center_ray = new Ray(Vij.normalized(), _p0);//getting the center ray based on the point of camera and the vector of the pixel

        //Point3D center_point = _p0.add(center_ray.get_dir());//creating the center point - also need to consider p0 for the camera location
        rayListInPixel.add(center_ray);//adding it into the ray's list
        for (int w = 0; w < numOfRays; w++)
        {
            Pij = Pc;

            double currentY = rang_minY + (rang_maxY - rang_minY) * r.nextDouble();
        //    if (!isZero(yi))
            if (!isZero(currentY))
                Pij = Pij.add(_vUp.scale(-currentY));

            double currentX = rang_minX + (rang_maxX - rang_minX) * r.nextDouble();
            if (!isZero(currentX))
                Pij = Pij.add(_vRight.scale(currentX));

            Vij = Pij.subtract(_p0);
            Ray ray = new Ray(Vij, _p0);
            rayListInPixel.add(ray);
        }
        return rayListInPixel;
    }

    /** a helper function for the improvement of "adaptive super sampling"
     * calculates the point after we moved it
     *
     * during the recursive division - for more and more pixels (each time one pixel splits to 4) - we will send 4 rays for each pixel ,
     * ans sometimes it can be the same ray for some different pixels.
     * therefore, I wanted to avoid from sending multiply and unnecessary rays -> and I moved the rays a little bit inside the pixel, to make sure each
     * ray that is sent, it is not an extra ray for another pixel.
     *
     * @param pc the point that is the middle of the pixel
     * @param yi the place we need to move the y value
     * @param xj the place we need to move the x value
     * @param offsetX the offset for the x
     * @param offsetY the offset for the y
     * @return point3D value in the new place
     */

    private Point3D newPoint(Point3D pc, double yi, double xj, double offsetX, double offsetY)
    {

        Point3D point = pc;//the point the is the middle of the pixel

        //the amount we are moving the point on the x axis
        double x = xj + offsetX;
        //the amount we are moving the point on the y axis
        double y = -yi - offsetY; // this is the opposite side because we want to "go down" by the pixel (actually go up)
        //if the x isn't a zero we will move the Pij point in the direction
        if (!isZero(x))
        {
            point = point.add(get_vRight().scale(x));
        }
        //if the y isn't a zero we will move the Pij point in the direction
        if (!isZero(y))
        {
            point = point.add(get_vUp().scale(y));
        }
        return point;
    }

    /**the improvement for "adaptive super sampling". taking 5 rays - 4 at the edges and one at the center.
     * this is based on the improvement of the "super sampling", but instead of sending a beam of 50 rays -  I want to prevebt spare calculation,
     * therefore there will 4 rays in each edge of the pixel - and one at the center for the calculation.
     * constructs adaptive ray beam through a given pixel
     * @param nX number of pixels on the x axis
     * @param nY number of pixels on the y axis
     * @param j the given line
     * @param i the given column
     * @param screenDistance distance between the camera and the view plane
     * @param screenWidth the width of the screen
     * @param screenHeight the height of the screen
     * @param cornerTopX the double value for the top value of x
     * @param cornerTopY the double value for the top value of y
     * @param cornerBottomX the double value for the bottom value of x
     * @param cornerBottomY the double value for the bottom value of y
     * @return list of rays
     */
    public LinkedList<Ray> constructAdaptiveRayBeamThroughPixel(int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight,
                                                                double cornerTopX, double cornerTopY,double cornerBottomX,double cornerBottomY)
    {
        if (isZero(screenDistance))//if the distance = 0
        {
            throw new IllegalArgumentException("distance cannot be 0");
        }

        LinkedList<Ray> rays = new LinkedList<>();
        //creating a point for the middle of the view plane
        Point3D Pc = get_p0().add(get_vTo().scale(screenDistance));

        double Ry = screenHeight / nY;//the height of each pixel
        double Rx = screenWidth / nX;//the width of each pixel

        //the value on the y axis we moved to
        double yi = ((i - nY / 2d) * Ry + Ry / 2d);
        //the value on the x axis we moved to
        double xj = ((j - nX / 2d) * Rx + Rx / 2d);

        Point3D Pij = Pc;//allocating the point to start from the camera
        //if the xj isn't a zero we will move the Pij point in the direction
        if (!isZero(xj))
        {
            Pij = Pij.add(get_vRight().scale(xj));
        }
        //if the yi isn't a zero we will move the Pij point in the direction
        if (!isZero(yi))
        {
            Pij = Pij.add(get_vUp().scale(-1 * yi));
        }
        //the refactor from here:

        //calculates the center of the pixel by doing a mean between the top and bottom values of x and y
        Point3D Center =  newPoint( Pc, yi, xj,(cornerTopX + cornerBottomX)/2,(cornerTopY + cornerBottomY)/2);
        //calculating the values for the topRightCorner, topLeftCorner, bottomRightCorner, bottomLeftCorner:
        Point3D TL = newPoint( Pc, yi, xj,cornerTopX,cornerTopY);//top left region (bottom right)
        Point3D TR = newPoint( Pc, yi, xj,cornerTopX,cornerBottomY);//top right region
        Point3D BR = newPoint( Pc, yi, xj,cornerBottomX,cornerBottomY);//bottom left region
        Point3D BL = newPoint( Pc, yi, xj,cornerBottomX,cornerTopY);//bottom right region (top left)
        //adding the rays to the list we will return, the rays will be those from the center of the camera to the topLeft, topRight, bottomLeft, bottomRight
        rays.add(new Ray(new Vector(Center.subtract(get_p0())), get_p0()));
        rays.add(new Ray(new Vector(TL.subtract(get_p0())), get_p0()));
        rays.add(new Ray(new Vector(TR.subtract(get_p0())), get_p0()));
        rays.add(new Ray(new Vector(BL.subtract(get_p0())), get_p0()));
        rays.add(new Ray(new Vector(BR.subtract(get_p0())), get_p0()));
        return rays;

    }

}


