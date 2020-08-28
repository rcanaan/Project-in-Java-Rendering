package unittests;

import org.junit.jupiter.api.Test;
import renderer.ImageWriter;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest
{


    @Test
    void writeToImage1()
    {
        String imageName = "ImageWriter_Test1";
        int width = 1600;
        int height = 1000;
        int nx =160;
        int ny =100;
        ImageWriter imageWriter = new ImageWriter(imageName, width, height, nx, ny);
        for (int col = 0; col < ny; col++)
        {
            for (int row = 0; row < nx; row++)
            {
                if (col % 10 == 0 || row % 10 == 0)
                {
                    imageWriter.writePixel(row, col, /*new primitives.Color(*/Color.WHITE);
                }
            }
        }
        imageWriter.writeToImage();
    }
    @Test
    public void writeToImage2()
    {
        String imageName = "ImageWriter_Test2";
        int width = 800;
        int height = 500;
        int nx = 80;
        int ny = 50;
        ImageWriter imageWriter = new ImageWriter(imageName, width, height, nx, ny);
        for (int col = 0; col < ny; col ++)
        {
            for (int row = 0; row < nx; row ++)
            {
                if (col % 10 == 0 || row % 10 == 0)
                {
                    imageWriter.writePixel(row, col, /*new primitives.Color(*/Color.WHITE);
                }
            }
        }
        imageWriter.writeToImage();
    }

  /*  @Test
    void writePixel()
    {
    }*/
}