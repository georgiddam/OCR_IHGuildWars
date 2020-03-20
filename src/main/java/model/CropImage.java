/**
 * The CropImage class is used to set bounds for specific images
 * It uses the main image to create the bounds. 
 * Currently is only set for 1080 resolution screens
 */
package model;
import java.awt.image.BufferedImage;

public class CropImage {
	
    public BufferedImage cropEnemyName (BufferedImage img) {
        return img.getSubimage(970, 290, 400,  85);
    }
    
    public BufferedImage cropRemAttacks (BufferedImage img) {
    	return img.getSubimage(865, 890, 130,  35);
    }

}
