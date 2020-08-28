package unittests;

import geometries.Plane;
import geometries.Polygon;
import org.junit.Test;

import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;


/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 *
 */
public class ReflectionRefractionTest
{


    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres()
    {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(Color.BLACK, 0));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0.3, 0),
                        new Point3D(0, 0, 50), 50),
                new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100),  new Point3D(0, 0, 50), 25));

        scene.addLights(new SpotLight(new Color(1000, 600, 0),new Vector(-1, 1, 2), new Point3D(-100, 100, -500),  1,
                0.0004, 0.0000006));

        ImageWriter imageWriter = new ImageWriter("twoSpheres", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(10000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.addGeometries(
                new Sphere(new Color(0, 0, 100), new Material(0.25, 0.25, 20, 0.5, 0),  new Point3D(-950, 900, 1000), 400),
                new Sphere(new Color(100, 20, 20), new Material(0.25, 0.25, 20),  new Point3D(-950, 900, 1000), 200),
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1), new Point3D(1500, 1500, 1500),
                        new Point3D(-1500, -1500, 1500), new Point3D(670, -670, -3000)),
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 0.5), new Point3D(1500, 1500, 1500),
                        new Point3D(-1500, -1500, 1500), new Point3D(-1500, 1500, 2000)));

        scene.addLights(new SpotLight(new Color(1020, 400, 400),new Vector(-1, 1, 4),  new Point3D(-750, 750, 150),
                 1, 0.00001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("twoSpheresMirrored", 2500, 2500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere
     *  producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(Color.BLACK);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.addGeometries( //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
                         new Point3D(60, -50, 50), 30));

        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                new Vector(0, 0, 1),new Point3D(60, -50, 0),  1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("shadow with transparency", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere that has a little sphere in it- that is not transparent and not refracted
     *  producing partial shadow
     */
    @Test
    public void MyTrianglesTransparentSphere()
    {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(1000);
        scene.setBackground(new Color(java.awt.Color.BLACK));
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.LIGHT_GRAY), 0.15));

        scene.addGeometries( //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 2000,0,0.4), //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 115), new Point3D(75, -75, 115)), //

                new Triangle(Color.BLACK, new Material(0.5, 0.5, 200), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 115), new Point3D(75, -75, 115)), //

                new Sphere(new Color(0,0,38), new Material(0.2, 0.2, 30, 0.6, 0), // )the color is dark blue
                        new Point3D(60, 30, 50), 30),//the big purple circle under the small blue circle

                new Sphere(new Color(0,204, 102), new Material(0.2, 0.2, 30,0, 0.5),//the small ball above the bigger.  moving down by moving in the positive direction of y
                        new Point3D(60, 30, 50), 15),//the color is light blue - (0,204, 102)

                new Sphere(new Color(0,76,153), new Material(0.2, 0.2, 30, 0.6, 0),  new Point3D(0, 0, 50), 30),//blue

                new Triangle(new Color (0,153,152), new Material(0.8, 0.8, 200,0,0.7), //the big blue circle under the small triangle
                        new Point3D(15, -15, 15), new Point3D(0, 20, -12), new Point3D(-20, -20, -20)));


               // new Sphere(new Color(0,204, 102), new Material(0.2, 0.2, 30), // moving down by moving in the positive direction of y
                     //         new Point3D(0, 0, -20), 15));

        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                new Vector(0, 0, 1),new Point3D(60, 20, 0),  1, 4E-5, 2E-7));

        scene.addLights(new SpotLight(new Color(255, 204, 229), //
                new Vector(0, 0, 1),new Point3D(0, 20, -25),  1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("My Picture", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);


        render.setMultithreading(1);
        render.setDebugPrint();
        render.setWithSuperSampling(false);
        render.setnumOfRays_inBeam(1);
        render.renderImage();
       // render.printGrid(200, java.awt.Color.WHITE);
        render.writeToImage();
    }


    @Test
    public void myPictureForProject()
    {
        Scene scene = new Scene("Test scene");
        scene.setCamera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.setDistance(10000);
        scene.setBackground(Color.BLACK);
       // scene.setBackground(new Color(204,102,0));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.addGeometries(
                new Sphere(new Color(0, 25, 51), new Material(0.8, 0.8, 200, 0.5, 0.7),  new Point3D(-950, 0, 1100), 400),//the big blue sphere
                new Sphere(new Color(0, 51, 51), new Material(1, 0.8, 600, 0, 1),  new Point3D(0, 300, 900), 300),//the little blue sphere at the right side
                new Sphere(new Color(0, 0, 25), new Material(0.8, 0.25, 200, 0, 0.7),  new Point3D(-700, 300, 640), 300),//the second ball in the row
                new Sphere(new Color(0, 25, 51), new Material(0.8, 0.8, 200, 0.5, 0.7),  new Point3D(-400, 700, 500), 250),//the third sphere in the row
                new Sphere(new Color(0, 51, 51), new Material(1, 1, 300, 0.5, 0.7),  new Point3D(600, 800, 500), 250),//the third sphere in the row

                new Triangle(new Color(30, 30, 30), new Material(0, 0, 0, 0, 1), new Point3D(1500, 1500, 1500),//original triangle- the upper one
                        new Point3D(-1500, -1500, 1500), new Point3D(670, -670, -3000)),
                new Triangle(new Color(30, 30, 39), new Material(0, 0, 0, 0, 0.5), new Point3D(1500, 1500, 1500),//original triangle
                        new Point3D(-1500, -1500, 1500), new Point3D(-1500, 1500, 2000)),

          //      new Triangle(new Color(20, 20, 20), new Material(0.8, 0.8, 0,0.5,0.7), //
             //           new Point3D(-500, 500, 500), new Point3D(-500, -500, 500), new Point3D(500, -500, -1000)), //



                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 0.5), new Point3D(1500, 1500, 0),
                        new Point3D(-1500, -1500, 3850), new Point3D(-1500, 1500, 0)));

              //  new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 0.5), new Point3D(-1500, -1500, 0),
               //         new Point3D(1500, 1500, -3850), new Point3D(1500, -1500, 0)))

        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                new Vector(-1, 1, -10),new Point3D(650, -660, -3000),  1, 4E-5, 2E-7));

        scene.addLights(new SpotLight(new Color(1020, 400, 400),new Vector(-1, 1, 4),  new Point3D(-750, 750, 150),
                1, 0.00001, 0.000005));

   //     scene.addLights(new SpotLight(new Color(700, 400, 400), //
       //         new Vector(0, 0, 1),new Point3D(60, 20, 0),  1, 4E-5, 2E-7));


        ImageWriter imageWriter = new ImageWriter("my picture for project", 2500, 2500, 1000, 1000);
        Render render = new Render(imageWriter, scene);

        render.setMultithreading(6);
        render.setDebugPrint();
        render.setWithSuperSampling(false);
        render.setAdaptiveSuperSampling(true);
        render.setnumOfRays_inBeam(5);

        render.renderImage();
        render.writeToImage();
    }



}
