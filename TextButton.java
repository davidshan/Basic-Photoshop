import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * A Generic Button to display text that is clickable. Owned by a World, which controls click
 * capturing.
 * <p><b> Modified by David Shan
 * @author Jordan Cohen
 * @version v0.1.5
 */
public class TextButton extends Actor
{
    // Declare private variables
    private GreenfootImage myImage;
    private String buttonText;
    private int textSize;
    private boolean mouseOn;
    private String text;

    /**
     * Construct a TextButton with a given String at the default size
     * 
     * @param text  String value to display
     * 
     */
    public TextButton (String text)
    {
        this(text, 20);
        this.text = text;
    }

    public void act()
    {
        // Check if the user has put their mouse over it
        MouseInfo mouse = Greenfoot.getMouseInfo();
        int xStartPos = getX()-myImage.getWidth()/2;
        int xEndPos = getX()+myImage.getWidth()/2;
        int yStartPos = getY()-myImage.getHeight()/2;
        int yEndPos = getY()+myImage.getHeight()/2;
        
        if (mouse != null) // avoid NullPointerException
        {
            if ( (mouse.getX() > xStartPos) && (mouse.getX() < xEndPos) //If mouse is anywhere on my position
            && (mouse.getY() > yStartPos) && (mouse.getY() < yEndPos) )
            {
                mouseOn = true;
            }
            else mouseOn = false;
            update(text); // change colour accordingly to if mouseOn=true or mouseOn=false
        }
    }

    /**
     * Construct a TextButton with a given String and a specified size
     * 
     * @param text  String value to display
     * @param textSize  size of text, as an integer
     * 
     */
    public TextButton (String text, int textSize)
    {
        buttonText = text;
        GreenfootImage tempTextImage = new GreenfootImage (text, textSize, Color.WHITE, new Color(100,149,237));
        myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
        myImage.setColor (Color.WHITE);
        myImage.fill();
        myImage.drawImage (tempTextImage, 4, 4);

        myImage.setColor (Color.BLACK);
        myImage.drawRect (0,0,tempTextImage.getWidth() + 7, tempTextImage.getHeight() + 7);
        setImage(myImage);
    }

    /**
     * Change the text displayed on this Button
     * 
     * @param   text    String to display
     * 
     */
    public void update (String text)
    {
        if (mouseOn){
            buttonText = text;
            GreenfootImage tempTextImage = new GreenfootImage (text, 20, Color.WHITE, Color.BLUE);
            myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
            myImage.setColor (Color.WHITE);
            myImage.fill();
            myImage.drawImage (tempTextImage, 4, 4);

            myImage.setColor (Color.BLACK);
            myImage.drawRect (0,0,tempTextImage.getWidth() + 7, tempTextImage.getHeight() + 7);
            setImage(myImage);
        }
        else{
            buttonText = text;
            GreenfootImage tempTextImage = new GreenfootImage (text, 20, Color.WHITE, new Color(100,149,237));
            myImage = new GreenfootImage (tempTextImage.getWidth() + 8, tempTextImage.getHeight() + 8);
            myImage.setColor (Color.WHITE);
            myImage.fill();
            myImage.drawImage (tempTextImage, 4, 4);

            myImage.setColor (Color.BLACK);
            myImage.drawRect (0,0,tempTextImage.getWidth() + 7, tempTextImage.getHeight() + 7);
            setImage(myImage);            
        }
    }

}
