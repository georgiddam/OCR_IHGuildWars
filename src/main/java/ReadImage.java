import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ReadImage {


//  grayscale + upscale image
    public BufferedImage scaleAndBlackWhite(BufferedImage resizeImg, int scaleValue) {

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
        System.out.println("Finished saving file");
    }

//  Convert to negative
    public BufferedImage invertImage(BufferedImage img) {

        System.out.println("Start Inversing");
        int width = img.getWidth();
        int height = img.getHeight();

//      Go through each pixel in the image
//      Look at its current rgb, invert it using bit shift and bit and
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int p = img.getRGB(x,y);
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
