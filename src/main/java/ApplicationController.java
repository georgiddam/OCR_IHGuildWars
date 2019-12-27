import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class ApplicationController {

    public static void main(String[] args) throws IOException, InterruptedException {

//      Instantiate stuff
        RobotInteractions rSI = new RobotInteractions();
        ReadImage readImage = new ReadImage();
        CropImage cropImage = new CropImage();
        String[] enemyArray = {"frozenwolves", "rabenhorst"};
        TessSetup tess = new TessSetup();

        Random rand = new Random();

        String enemyFilename = "enemyName";
        String remAtkFilename = "remainingAttacks";

        boolean isRemainingAttacks = true;
        boolean isEnemyAttack = false;
//      wait a bit for this to start.
        Thread.sleep(2000);
        while (isRemainingAttacks) {

//          Create screenshot
            BufferedImage img = rSI.screenShot("png");

//          Get the cropped images
            BufferedImage cropEnemy = cropImage.cropEnemyName(img, enemyFilename);
            BufferedImage cropAttacks = cropImage.cropRemAttacks(img, remAtkFilename);

//          Manipulate cropped images
//          (This step is actually not required anymore but for purposes of doing more with OCR)
            cropEnemy = readImage.invertImage(cropEnemy);
            cropEnemy = readImage.scaleAndBlackWhite(cropEnemy, 2);
            readImage.saveFile(cropEnemy, enemyFilename, "png");

            cropAttacks = readImage.invertImage(cropAttacks);
            cropAttacks = readImage.scaleAndBlackWhite(cropAttacks, 2);
            readImage.saveFile(cropAttacks, remAtkFilename, "png");

//          Wait a bit for stuff to be processed and saved
            Thread.sleep(2000);

            isEnemyAttack = tess.tessScanFile(enemyArray,enemyFilename, remAtkFilename );
            isRemainingAttacks = tess.getHasAttacks();

            if (isEnemyAttack) {
                rSI.mouseClickFight();
            } else {
                rSI.mouseClickMatchup();
            }

//          Wait for the 10 sec to pass
            Thread.sleep(10000 + rand.nextInt(2)*1000);
        }
    }
}
