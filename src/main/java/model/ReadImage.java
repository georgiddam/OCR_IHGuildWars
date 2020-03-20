/**
 * The ReadImage class is used to analyse the images given to it.
 * It manipulates the images to make the clearer to read for the Tessaract.
 */
package model;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ReadImage {


//  grayscale + upscale image
    public BufferedImage upscaleAndGrayscale(BufferedImage resizeImg, int scaleValue) {

        int width = resizeImg.getWidth()*scaleValue;
        int height = resizeImg.getHeight()*scaleValue;

//      Creates the scaled image and puts it on a new canvas
        BufferedImage blackAndWhiteImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2d = blackAndWhiteImg.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(resizeImg, 0, 0,width, height, null);
        g2d.dispose();
        return blackAndWhiteImg;
    }

//  Save file
    public void saveFile(BufferedImage img, String name, String extension) throws IOException {
        File outputFile = new File(name + "." + extension);
        ImageIO.write(img, extension, outputFile);
    }

//  Convert to negative
    public BufferedImage invertImage(BufferedImage img) {

        int width = img.getWidth();
        int height = img.getHeight();

//      Look at each pixel rgb, invert it using bit shift and masking
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = img.getRGB(x,y);
//              00000000 00000000 00000000 00000000
                int a = (p>>24)&0xff;
                int r = (p>>16)&0xff;
                int g = (p>>8)&0xff;
                int b = p&0xff;

                //subtract RGB from 255
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;

                //set new RGB value
                p = (a<<24) | (r<<16) | (g<<8) | b;
                img.setRGB(x, y, p);
            }
        }
        return img;
    }
}
