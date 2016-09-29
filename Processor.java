import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.Graphics;

import java.util.ArrayList;
import javax.swing.JOptionPane;

import java.lang.ArrayIndexOutOfBoundsException;

/**
 * Processor manipulates Java BufferedImages, which are effectively 2d arrays
 * of pixels. Each pixel is a single integer packed with 4 values inside it.
 * The user can provide a BufferedImage for the methods, and it will make changes to
 * the BufferedImage according to the method's function. This class does not need to
 * be instantiated, nor is it a child of Greenfoot.Actor or Greenfoot.World.
 * 
 * <p>Example of using Processor: greyScale method on a BufferedImage turns it grey.
 *
 * @author David Shan
 * @version November 2014
 */
public class Processor  
{
    private static ArrayList<BufferedImage> imageList = new ArrayList<BufferedImage>();

    /**
     * Example colour altering method by Mr. Cohen. This method will
     * increase the blue value while reducing the red and green values.
     * 
     * Demonstrates use of packagePixel() and unpackPixel() methods.
     * 
     * @param bi    The BufferedImage (passed by reference) to change.
     */
    public static void blueify (BufferedImage bi)
    {
        addImage(bi);
        // Get image size to use in for loops
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Using array size as limit
        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                // Calls method in BufferedImage that returns R G B and alpha values
                // encoded together in an integer
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make the pic BLUE-er
                if (blue <= 255)
                    blue += 2;
                if (red >= 50)
                    red--;
                if (green >= 50)
                    green--;

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }

    }

    /**
     * Horizontally flips a BufferedImage
     * 
     * @param   bi  The BufferedImage to be flipped
     */
    public static void flipHorizontal (BufferedImage bi)
    {
        addImage(bi);
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        // Temp image, to store pixels as we reverse everything
        BufferedImage newBi = new BufferedImage (xSize, ySize, 1);

        // Copy last column of original onto the first of new buffered image
        // Second last onto second
        // Third last onto second, so on so forth
        for (int i = 0; i < xSize; i++)
        {            
            for (int j = 0; j < ySize; j++)
            {
                int pixel = bi.getRGB(xSize-i-1, j);
                newBi.setRGB(i, j, pixel);
            }
        }
        // Copy newBi back onto bi pixel by pixel
        for (int i = 0; i < xSize; i++)
        {
            for (int j = 0; j < ySize; j++)
            {
                int pixel = newBi.getRGB(i,j);
                bi.setRGB(i,j,pixel);
            }
        }
    }

    /**
     * Method that vertically flips a BufferedImage
     * 
     * @param   bi  The BufferedImage to be flipped
     */
    public static void flipVertical (BufferedImage bi)
    {
        addImage(bi);
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        BufferedImage newBi = new BufferedImage(xSize, ySize, 1);

        // Same concept as horizontal, but this time dealing with rows
        // First row of newBi = last row of original bi
        // Second row of newBi = second last row of original bi
        for (int i = 0; i < xSize; i++)
        {
            for(int j = 0; j < ySize; j++)
            {
                int pixel = bi.getRGB(i, ySize-j-1);
                newBi.setRGB(i,j,pixel);
            }
        }
        // Copy newBI back onto bi pixel by pixel
        for (int i = 0; i < xSize; i++)
        {
            for (int j = 0; j < ySize; j++)
            {
                int pixel = newBi.getRGB(i,j);
                bi.setRGB(i,j,pixel);
            }
        }
    }

    /**
     * Method that takes a BufferedImage and has the Grey-Scaling effect applied on it
     * 
     * @param   bi  The BufferedImage to be grey-scaled
     */
    public static void greyScale (BufferedImage bi)
    {
        addImage(bi);
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                int rgb = bi.getRGB(x, y);

                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // average out all the colours
                int newP = (red + green + blue) / 3;
                
                int newColour = packagePixel (newP, newP, newP, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }

    /**
     * Method that takes a BufferedImage and has the sepia colour-effect applied on it
     * 
     * Taken from <b>http://stackoverflow.com/questions/5132015/how-to-convert-image-to-sepia-in-java
     * 
     * @param   bi  The BufferedImage to be manipulated
     * 
     */
    public static void sepia (BufferedImage bi)
    {
        addImage(bi);
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        int sepiaDepth = 20;

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                int rgb = bi.getRGB(x, y);

                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];
                int grey = (red+green+blue) / 3;
                
                // the algorithm
                red = green = blue = grey;
                red += (sepiaDepth * 2);
                green += sepiaDepth;
                blue -= sepiaDepth;

                // Ensure no saturation/ weird colours
                if (red > 255)
                {
                    red = 255;
                }
                if (green > 255)
                {
                    green = 255;
                }
                if (blue > 255)
                {
                    blue = 255;
                }
                else if (blue < 0)
                {
                    blue = 0;
                }

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }
    }

    /**
     * Method that takes a BufferedImage and turns it's RGB values into it's negatives
     * 
     * @param   bi  The BufferedImage to be manipulated with
     */
    public static void turnToNegative(BufferedImage bi)
    {
        addImage(bi);
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                int rgb = bi.getRGB(x, y);

                // Call the unpackPixel method to retrieve the four integers for
                // R, G, B and alpha and assign them each to their own integer
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // 255 - colour RGB value = 'negative' of that colour

                int newColour = packagePixel (255-red, 255-green, 255-blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }       
    }

    /**
     * Increases the brightness of a provided BufferedImage
     * 
     * @param   bi  The BufferedImage to be brightened
     */
    public static void brighten(BufferedImage bi)
    {
        addImage(bi);
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                int rgb = bi.getRGB(x, y);

                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make sure some weird colours don't pop up when brightening
                // avoids saturation too
                if (red+10 < 225)
                {
                    red+=10;
                }
                if (green+10 < 225)
                {
                    green+=10;
                }
                if (blue+10 < 225)
                {
                    blue+=10;
                }

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }       
    }


    /**
     * Decreases the brightness of a provided BufferedImage
     * 
     * @param   bi  The BufferedImage to be darkened
     */
    public static void darken(BufferedImage bi)
    {
        addImage(bi);
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int x = 0; x < xSize; x++)
        {
            for (int y = 0; y < ySize; y++)
            {
                int rgb = bi.getRGB(x, y);
                
                int[] rgbValues = unpackPixel (rgb);
                int alpha = rgbValues[0];
                int red = rgbValues[1];
                int green = rgbValues[2];
                int blue = rgbValues[3];

                // make sure some weird colours don't pop up when darkening
                // avoids saturation too
                if (red-10 > 35)
                {
                    red-=10;
                }
                if (green-10 > 35)
                {
                    green-=10;
                }
                if (blue-10 > 35)
                {
                    blue-=10;
                }

                int newColour = packagePixel (red, green, blue, alpha);
                bi.setRGB (x, y, newColour);
            }
        }       
    }    
    
    /**
     * Applies a warm-colour effect on a provided BufferedImage
     * 
     * @param   bi  The BufferedImage to be affected
     */
    public static void warm(BufferedImage bi)
    {
        addImage(bi);
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int i = 0; i < xSize; i++)
        {
            for(int j = 0; j < ySize; j++)
            {
                int[] pixel = unpackPixel(bi.getRGB(i, j));
                int alpha = pixel[0];
                int red = pixel[1];
                int green = pixel[2];
                int blue = pixel[3];

                // Warm effect increases the Red colouring in a pixel
                // Makes sure it will not saturate
                if (red + 10 < 255)
                {
                    red+=10;
                }

                int newColour = packagePixel(red, green, blue, alpha);
                bi.setRGB(i, j, newColour);
            }
        }
    }

    /**
     * Applies a colour-cooling effect on a provided BufferedImage
     * 
     * @param   bi  The BufferedImage to be affected
     */
    public static void cool(BufferedImage bi)
    {
        addImage(bi);
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();

        for (int i = 0; i < xSize; i++)
        {
            for(int j = 0; j < ySize; j++)
            {
                int[] pixel = unpackPixel(bi.getRGB(i, j));
                int alpha = pixel[0];
                int red = pixel[1];
                int green = pixel[2];
                int blue = pixel[3];

                // Cool effect reduces the Red colouring in a pixel
                // Makes sure it will not saturate
                if (red - 10 > 0)
                {
                    red-=10;
                }

                int newColour = packagePixel(red, green, blue, alpha);
                bi.setRGB(i, j, newColour);
            }
        }
    }

    /**
     * Method that takes a BufferedImage and rotates it 90 degrees clockwise
     * 
     * @param   bi  The BufferedImage to be rotated
     * @return  Returns a new BufferedImage that is the input BufferedImage rotated 90 degrees CW
     */
    public static BufferedImage rotateCW(BufferedImage bi)
    {
        addImage(bi);
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        BufferedImage newBi = new BufferedImage(ySize, xSize, 1); // turn it 90, so side lengths are now switched

        for (int x = 0; x < xSize; x++){
            for (int y = 0; y < ySize; y++){
                int pixel = bi.getRGB(x, y);
                newBi.setRGB(ySize-y-1, x, pixel); // as it flips, the new x becomes the original max y size - the y coord of the of the original pixel
            }                                       // while new y becomes the old x coordinate
        }

        return newBi;
        //         Graphics g = bi.createGraphics();
        //         g.drawImage(newBi, 0, 0, xSize, ySize, null);
        //         g.dispose();        

        //         for (int x = 0; x < ySize; x++){
        //             for (int y = 0; y < xSize; y++){
        //                 int pixel = newBi.getRGB(x, y);
        //                 //bi.setRGB(x,y,pixel);
        //             }
        //         }        
    }

    /**
     * Method that takes a BufferedImage and rotates it 90 degrees counter-clockwise
     * 
     * @param   bi  The BufferedImage to be rotated
     * @return  Returns a new BufferedImage that is the input BufferedImage rotated 90 degrees CCW
     */
    public static BufferedImage rotateCCW(BufferedImage bi)
    {
        addImage(bi);
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        BufferedImage newBi = new BufferedImage(ySize, xSize, 1); // turn it 90, so side lengths are now switched

        for (int x = 0; x < xSize; x++){
            for (int y = 0; y < ySize; y++){
                int pixel = bi.getRGB(x, y);
                newBi.setRGB(y, xSize-x-1, pixel); // as it flips, the new X becomes the original Y of the of the original pixel
            }                                       // while new y becomes the old xSize - x coordinate
        }

        return newBi;     
    }    

    /**
     * Applies a Gaussian Blur onto a provided BufferedImage
     *
     * source: http://www.jhlabs.com/ip/blurring.html
     * 
     * @param   bi  The BufferedImage to be manipulated
     */
    public static void gaussianBlur(BufferedImage bi)
    {
        addImage(bi);
        float[] kernel = {
                0.111f, 0.111f, 0.111f, 
                0.111f, 0.111f, 0.111f, 
                0.111f, 0.111f, 0.111f, 
            };

        // Create a copy of BufferedImage, run it through the 'mill' (BufferedImageOp's Convolution of biCopy to bi with the float[] kernel)
        BufferedImage biCopy = copy(bi);
        BufferedImageOp op = new ConvolveOp( new Kernel(3, 3, kernel) );
        op.filter(biCopy,bi);
    }

    /**
     * Applies an embossing effect on a provided BufferedImage
     * 
     * @param   bi  The BufferedImage to be manipulated
     */
    public static void emboss(BufferedImage bi)
    {
        addImage(bi);
        float[] kernel = {
                -2, 0, 0, 
                0, 1, 0, 
                0, 0, 2
            };

        BufferedImage biCopy = copy(bi);
        BufferedImageOp op = new ConvolveOp( new Kernel(3, 3, kernel) );
        op.filter(biCopy,bi);
    }    
    
    
    /**
     * Detects the edges of a BufferedImage and emphasizes it
     * <p> http://blancosilva.wordpress.com/teaching/mathematical-imaging/edge-detection-the-convolution-approach/
     * @param   bi  The BufferedImage to be manipulated
     */
    public static void edgeDetect(BufferedImage bi)
    {
        addImage(bi);        
//         float[] kernel = {
//                 0, 1, 0,
//                 1, -4, 1,
//                 0, 1, 0
//             };
        float[] kernel = {
                1, 2, 1,
                1, 0, -1,
                -1, -2, -1
            };
            
        BufferedImage biCopy = copy(bi);
        BufferedImageOp op = new ConvolveOp( new Kernel( 3, 3, kernel) );
        op.filter(biCopy, bi);
    
    }

    /**
     * Adds a copy of a provided BufferedImage to the ArrayList of BufferedImage
     * 
     * @param   bi  The BufferedImage to be added into the ArrayList
     */
    public static void addImage(BufferedImage bi)
    {
        imageList.add( copy(bi) );
    }

    /**
     * Returns the last image in the BufferedImage ArrayList, and removes it from the ArrayList.
     * User needs to provide a BufferedImage (particularly the one that is being manipulated on the screen)
     * 
     * @param   bi  The current BufferedImage
     * @return  Returns the last BufferedImage in the ArrayList (or if none are available, returns bi)
     */
    public static BufferedImage undo(BufferedImage bi)
    {
        JOptionPane msgBox = new JOptionPane();
        BufferedImage previousImage = bi;

        try{
            previousImage = imageList.get(imageList.size()-1); // Gets the previous image from the arrayList
            imageList.remove(imageList.size()-1); // and then remove that image
        }
        catch(ArrayIndexOutOfBoundsException e){
            msgBox.showMessageDialog(null,"No more pictures to choose from!"); // Only if you undid to the first image
        }

        return previousImage;
        //         int xSize = bi.getWidth();
        //         int ySize = bi.getHeight();
        // 
        //         for (int x = 0; x < xSize; x++)
        //         {
        //             for (int y = 0; y < ySize; y++)
        //             {
        //                 int pixel = previousImage.getRGB(x, y);
        //                 bi.setRGB(x, y, pixel);
        //             }
        //         }
    }

    /**
     * Copies a provided BufferedImage, and returns it
     * 
     * @param   bi  The BufferedImage to be copied
     */
    public static BufferedImage copy(BufferedImage bi)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        BufferedImage copiedBi = new BufferedImage(xSize, ySize, 1);
        
        // Copy pixel by pixel onto the new BufferedImage
        for (int i = 0; i < xSize; i++)
        {
            for (int j = 0; j < ySize; j++)
            {
                int pixel = bi.getRGB(i, j);
                copiedBi.setRGB(i, j, pixel);
            }
        }

        return copiedBi;
    }

    /**
     * Clears the BufferedImage ArrayList used for the undo function
     * when for example the user opens a new picture
     */
    public static void clearImageList()
    {
        imageList.clear();
    }
    
    /**
     * Sets the alpha value of the pixels of a provided BufferedImage to 0
     * 
     * @param   bi  BufferedImage to have it's alpha value set to 0
     * @return  The provided BufferedImage with alpha value of 0
     */
    public static BufferedImage removeAlpha(BufferedImage bi)
    {
        int xSize = bi.getWidth();
        int ySize = bi.getHeight();
        BufferedImage copiedBi = new BufferedImage(xSize, ySize, 1);
        // go through pixel by pixel and set alpha = 0
        for (int i = 0; i < xSize; i++)
        {
            for (int j = 0; j < ySize; j++)
            {
                int pixel = bi.getRGB(i, j);
                int[] unpackedPixel = unpackPixel(pixel);
                unpackedPixel[0] = 0; // Set all pixels alpha value to 0
                pixel = packagePixel(unpackedPixel[1], unpackedPixel[2], unpackedPixel[3], unpackedPixel[0]);
                copiedBi.setRGB(i, j, pixel);
            }
        }

        return copiedBi;        
    }
    /**
     * Takes in an rgb value - the kind that is returned from BufferedImage's
     * getRGB() method - and returns 4 integers for easy manipulation.
     * 
     * By Jordan Cohen
     * Version 0.2
     * 
     * @param rgbaValue The value of a single pixel as an integer, representing<br>
     *                  8 bits for red, green and blue and 8 bits for alpha:<br>
     *                  <pre>alpha   red     green   blue</pre>
     *                  <pre>00000000000000000000000000000000</pre>
     * @return int[4]   Array containing 4 shorter ints<br>
     *                  <pre>0       1       2       3</pre>
     *                  <pre>alpha   red     green   blue</pre>
     */
    public static int[] unpackPixel (int rgbaValue)
    {
        int[] unpackedValues = new int[4];
        // alpha
        unpackedValues[0] = (rgbaValue >> 24) & 0xFF;
        // red
        unpackedValues[1] = (rgbaValue >> 16) & 0xFF;
        // green
        unpackedValues[2] = (rgbaValue >>  8) & 0xFF;
        // blue
        unpackedValues[3] = (rgbaValue) & 0xFF;

        return unpackedValues;
    }

    /**
     * Takes in a red, green, blue and alpha integer and uses bit-shifting
     * to package all of the data into a single integer.
     * 
     * @param   int red value (0-255)
     * @param   int green value (0-255)
     * @param   int blue value (0-255)
     * @param   int alpha value (0-255)
     * 
     * @return int  Integer representing 32 bit integer pixel ready
     *              for BufferedImage
     */
    public static int packagePixel (int r, int g, int b, int a)
    {
        int newRGB = (a << 24) | (r << 16) | (g << 8) | b;
        return newRGB;
    }
}