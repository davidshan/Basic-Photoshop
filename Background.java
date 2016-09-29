import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.File;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics2D;

/**
 * Holds all the buttons and the image to be manipulated,
 * and lays it out onto the world.
 * 
 * @author David Shan
 * @version November 2014
 */
public class Background extends World
{
    // Constants:
    private final String STARTING_FILE = "space_battle.jpg";
    // Objects and Variables:
    private ImageHolder image;

    private TextButton sepiaButton;
    private TextButton hRevButton;
    private TextButton vRevButton;
    private TextButton rotateCWButton;
    private TextButton rotateCCWButton;
    private TextButton undoButton;
    private TextButton greyScaleButton;
    private TextButton negativeButton;
    private TextButton brightenButton;
    private TextButton darkenButton;
    private TextButton blurButton;    
    private TextButton warmButton;    
    private TextButton coolButton;    
    private TextButton embossButton;
    private TextButton edgeDetectButton;

    private TextButton openFile;
    private TextButton savePNGFile;
    private TextButton saveJPGFile;

    private String fileName;
    /**
     * Constructor for objects of class Background.
     * 
     */
    public Background()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(800, 600, 1); 

        // Initialize buttons and the image
        image = new ImageHolder(STARTING_FILE);
        sepiaButton = new TextButton(" [ Sepia ] ");
        hRevButton = new TextButton(" [ Flip Horizontal ] ");
        vRevButton = new TextButton(" [ Flip Vertical ] ");
        rotateCWButton = new TextButton(" [ Rotate 90 Degrees ] ");
        undoButton = new TextButton( " [ Undo ] ");        
        greyScaleButton = new TextButton (" [ GreyScale ] ");
        negativeButton = new TextButton (" [ Negative Colours ] ");
        brightenButton = new TextButton (" [ Brighten ] ");
        darkenButton = new TextButton (" [ Darken ] ");
        blurButton = new TextButton (" [ Blur Image ] ");
        warmButton = new TextButton (" [ Warm Effect ] ");
        coolButton = new TextButton (" [ Cool Effect ] ");
        rotateCCWButton = new TextButton (" [ Rotate 90 Degrees Counter-Clockwise ] ");
        embossButton = new TextButton (" [ Emboss Image ] ");
        edgeDetectButton = new TextButton (" [ Edge Detect ] " );
        
        openFile = new TextButton(" [ Open File: " + STARTING_FILE + " ] ");
        savePNGFile = new TextButton (" [ Save File As .png ] ");
        saveJPGFile = new TextButton (" [ Save File As .jpg ] ");

        // Add objects to the screen
        int leftStartXPos = 100;
        int leftPosY = 200;
        int rightStartXPos = 700;
        int rightPosY = 200;
        int offset = 35;

        // LOAD UP ALL THE STUFF
        // LAYOUT BELOW WITHOUT setLayout() IS IN ORDER TO EASILY IMPLEMENT FOR TESTING THE FUNCTIONALITIES
        
        // Image and right side of the screen
        addObject (image, 400, 300);
        addObject (sepiaButton, rightStartXPos, rightPosY);
        addObject (hRevButton, rightStartXPos, rightPosY+=offset);
        addObject (vRevButton, rightStartXPos, rightPosY+=offset);
        addObject (rotateCWButton, rightStartXPos, rightPosY+=offset);
        addObject (rotateCCWButton, rightStartXPos, rightPosY+=offset);
        addObject (undoButton, rightStartXPos, rightPosY+=offset);
        addObject (greyScaleButton, rightStartXPos, rightPosY+=offset); 
        addObject (edgeDetectButton, rightStartXPos, rightPosY+=offset);

        // Left side of the screen
        addObject (negativeButton, leftStartXPos, leftPosY);
        addObject (brightenButton, leftStartXPos, leftPosY+=offset);
        addObject (darkenButton, leftStartXPos, leftPosY+=offset);
        addObject (embossButton, leftStartXPos, leftPosY+=offset);        
        addObject (blurButton, leftStartXPos, leftPosY+=offset);
        addObject (warmButton, leftStartXPos, leftPosY+=offset);
        addObject (coolButton, leftStartXPos, leftPosY+=offset);

        addObject (openFile, 300, 24);
        addObject (savePNGFile, 500, 24);
        addObject (saveJPGFile, 500, 48);

        // final product layout
        setLayout();
        Processor.clearImageList(); // past undos are all gone when reset is clicked
    }

    /**
     * Act() method just checks for mouse input... Not going to do anything else here
     */
    public void act ()
    {
        checkMouse();
    }

    /**
     * Check for user clicking on a button
     */
    private void checkMouse ()
    {
        // Avoid excess mouse checks - only check mouse if somethething is clicked.
        if (Greenfoot.mouseClicked(null))
        {
            BufferedImage img = image.getBufferedImage();

            if (Greenfoot.mouseClicked(sepiaButton)){                
                Processor.sepia(img);
            }
            else if (Greenfoot.mouseClicked(hRevButton)){
                Processor.flipHorizontal(img);
            }
            else if (Greenfoot.mouseClicked(vRevButton)){
                Processor.flipVertical(img);
            }
            else if (Greenfoot.mouseClicked(rotateCWButton)){
                image.setImage(createGreenfootImageFromBI (Processor.rotateCW(img)));
            }     
            else if (Greenfoot.mouseClicked(rotateCCWButton)){
                image.setImage(createGreenfootImageFromBI (Processor.rotateCCW(img)));
            }   
            else if (Greenfoot.mouseClicked(undoButton))
            {
                image.setImage(createGreenfootImageFromBI (Processor.undo(img)));
            }
            else if (Greenfoot.mouseClicked(greyScaleButton))
            {
                Processor.greyScale(img);
            }
            else if (Greenfoot.mouseClicked(negativeButton))
            {
                Processor.turnToNegative(img);
            }
            else if (Greenfoot.mouseClicked(brightenButton))
            {
                Processor.brighten(img);
            }
            else if (Greenfoot.mouseClicked(darkenButton))
            {
                Processor.darken(img);
            }            
            else if (Greenfoot.mouseClicked(embossButton))
            {
                Processor.emboss(img);
            }            
            else if (Greenfoot.mouseClicked(blurButton))
            {
                Processor.gaussianBlur(img);
            }
            else if (Greenfoot.mouseClicked(warmButton))
            {
                Processor.warm(img);
            }
            else if (Greenfoot.mouseClicked(coolButton))
            {
                Processor.cool(img);
            }            
            else if (Greenfoot.mouseClicked(edgeDetectButton))
            {
                Processor.edgeDetect(img);
            }
            else if (Greenfoot.mouseClicked(openFile))
            {
                openFile ();
            }
            else if (Greenfoot.mouseClicked(savePNGFile))
            {
                try{
                    savePNGFile();
                }
                catch(IOException e)
                {
                    JOptionPane.showMessageDialog(null,"Error: " + e);
                }
            }
            else if (Greenfoot.mouseClicked(saveJPGFile))
            {
                try{
                    saveJPGFile();
                }
                catch(IOException e)
                {
                    JOptionPane.showMessageDialog(null,"Error: " + e);
                }
            }            
        }
    }

    /**
     * Takes in a BufferedImage and returns a GreenfootImage.
     * 
     * @param newBi The BufferedImage to convert.
     * 
     * @return GreenfootImage   A GreenfootImage built from the BufferedImage provided.
     */
    public static GreenfootImage createGreenfootImageFromBI (BufferedImage newBi)
    {
        GreenfootImage returnImage = new GreenfootImage (newBi.getWidth(), newBi.getHeight());
        BufferedImage backingImage = returnImage.getAwtImage();
        Graphics2D backingGraphics = (Graphics2D)backingImage.getGraphics();
        backingGraphics.drawImage(newBi, null, 0, 0);

        return returnImage;
    }

    /**
     * Allows the user to open a new image file.
     */
    private void openFile ()
    {
        // Use a JOptionPane to get file name from user
        String fileName = JOptionPane.showInputDialog("Please input a value");

        // If the file opening operation is successful, update the text in the open file button
        if (image.openFile (fileName))
        {
            String display = " [ Open File: " + fileName + " ] ";
            openFile.update (display);
            JOptionPane.showMessageDialog(null, "File opened successfully!");
            Processor.clearImageList(); // get rid of past image undos

        }
        else{
            JOptionPane.showMessageDialog(null, "There was an error. Make sure you are referencing an existing image.");
        }
    }

    /**
     * Lets the user save their file as a .png
     */
    private void savePNGFile() throws IOException
    {
        try{
            String fileName = JOptionPane.showInputDialog("Input file name (no extension)");
            fileName+= ".png";
            File f = new File (fileName);
            ImageIO.write(image.getImage().getAwtImage(), "png", f);
            JOptionPane.showMessageDialog(null, "File saved successfully!");
        }
        catch(NullPointerException e)
        {
            JOptionPane.showMessageDialog(null, "Invalid file name! Please enter a valid name!");
        }
        catch(FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Invalid file name! Please enter a valid name!");
        }   

    }

    /**
     * Lets the user save their file as a .jpg
     */
    private void saveJPGFile() throws IOException
    {
        try{
            String fileName = JOptionPane.showInputDialog("Input file name (no extension)");
            fileName+= ".jpg";
            File f = new File (fileName);

            ImageIO.write(Processor.removeAlpha(image.getImage().getAwtImage()), "jpg", f);
            JOptionPane.showMessageDialog(null, "File saved successfully!");
        }
        catch(NullPointerException e)
        {
            JOptionPane.showMessageDialog(null, "Invalid file name! Please enter a valid name!");
        }
        catch(FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(null, "Invalid file name! Please enter a valid name!");
        }        

    }

    /**
     * Lays out the world by putting the buttons in custom locations
     */
    private void setLayout()
    {
        // Top of the world
        openFile.setLocation(113, 19);
        savePNGFile.setLocation(319, 18);
        saveJPGFile.setLocation(471, 18);
        
        brightenButton.setLocation(136, 88);
        darkenButton.setLocation(132, 119);
        negativeButton.setLocation(256, 88);
        embossButton.setLocation(250, 120);
        greyScaleButton.setLocation(381,88);
        blurButton.setLocation(370, 120);
        sepiaButton.setLocation(469, 88);
        edgeDetectButton.setLocation(480, 120);
        warmButton.setLocation(568, 88);
        coolButton.setLocation(595, 120);
        
        // Bottom of the world
        rotateCWButton.setLocation(201, 496);
        rotateCCWButton.setLocation(264, 529);
        hRevButton.setLocation(530, 495);
        vRevButton.setLocation(521, 528);
        undoButton.setLocation(668, 425);
        
        // Position of Image        
        image.setLocation(400, 283);
    }
}

