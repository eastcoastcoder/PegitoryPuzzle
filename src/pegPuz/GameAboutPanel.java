package pegPuz;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameAboutPanel extends JPanel {

	public final static String ABOUT_IMAGE = "aboutImg";
	
	public GameAboutPanel(){
		
		super.setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		
		JLabel imageLabel = new JLabel( new ImageIcon( loadImage( ABOUT_IMAGE ) ) );
		imageLabel.setAlignmentX( Component.CENTER_ALIGNMENT );
		
		JTextArea txtArea = new JTextArea();
		String someText = "Rules: Moves must start on a peg, contain a peg in the middle, and end on an empty location.\n" +
				"The ending location must be 2-away from Starting location in a valid direction.\n" +
				"Developed By: Window Group!\n" +
				"CS305 Software Development Practices\n" +
				"Fall 2014, Dr. M. Wainer, SIU Carbondale";
		
		txtArea.setText( someText );
		txtArea.setEditable( false );
		txtArea.setAlignmentX( Component.CENTER_ALIGNMENT );
		
		super.add( imageLabel );
		super.add( txtArea );
		
	}
	
	/** 	BREAKING JAR COMPILATION
	 * Reads in a jpg image from application's image directory within src folder
     * @param name of an image file (with jpg extension)
     * @return reference to the image from the file
     */
	private BufferedImage loadImage( String name ) {

		String imgFileName = "images/" + name + ".jpg";
        URL url = getClass().getResource( imgFileName );
        BufferedImage img = null;
        try {
            img =  ImageIO.read( url );
        } catch (Exception e) {
            System.err.println( "Failed attempt to read image: " + imgFileName );
        }
        return img;
    }
	
	
}
