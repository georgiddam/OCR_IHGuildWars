import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CropImage {

    public BufferedImage cropEnemyName (BufferedImage img, String name) throws IOException {

        BufferedImage getName = img.getSubimage(970, 290, 400,  85);
        File getNameOutput = new File(name+".png");
        ImageIO.write(getName, "png", getNameOutput);
//        System.out.println("Finished saving name");

        return getName;

    }
    public BufferedImage cropRemAttacks (BufferedImage img, String remAttacks) throws IOException {

        BufferedImage getRemainingAttacks = img.getSubimage(865, 890, 130,  35);
        File getRemainingAttacksOutput = new File(remAttacks+".png");
        ImageIO.write(getRemainingAttacks, "png", getRemainingAttacksOutput);
//        System.out.println("Finished saving remaining attacks");
        return getRemainingAttacks;
    }

}
