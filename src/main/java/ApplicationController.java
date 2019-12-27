import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ApplicationController {

    public static void main(String[] args) throws IOException, InterruptedException {

        String[] enemyToAttack = {"frozenwolves"};

        boolean isRemainingAttacks = true;
        boolean isEnemyAttack = false;
//      wait a bit for this to start.
        Thread.sleep(3000);
        while (isRemainingAttacks) {
//          Wait for the 10 sec to pass
            Thread.sleep(10000);

//          Instantiate stuff
            RobotScreenInteraction rSI = new RobotScreenInteraction();
            ReadImage readImage = new ReadImage();
            CropImage cropImage = new CropImage();
//          Create screenshot
            BufferedImage img = rSI.screenShot("png");
            String enemyNameFilename = "enemyName";
            String remainingAttacksFilename = "remainingAttacks";
//          Get the cropped images
            BufferedImage cropEnemy = cropImage.cropEnemyName(img, enemyNameFilename);
            BufferedImage cropAttacks = cropImage.cropRemAttacks(img, remainingAttacksFilename);

//          Manipulate cropped images (This step is actually not required anymore but for purposes of doing more with OCR)
            cropEnemy = readImage.invertImage(cropEnemy);
            cropEnemy = readImage.scaleAndBlackWhite(cropEnemy, 2);
            readImage.saveFile(cropEnemy, enemyNameFilename, "png");

            cropAttacks = readImage.invertImage(cropAttacks);
            cropAttacks = readImage.scaleAndBlackWhite(cropAttacks, 2);
            readImage.saveFile(cropAttacks, remainingAttacksFilename, "png");

//          Setup tesseract
            Tesseract tess = new Tesseract();
            tess.setDatapath("tessdata");
            tess.setLanguage("eng");
            System.out.println("Tesseract set up, start analysing image");
            try {
                String enemyName = tess.doOCR(new File(enemyNameFilename + ".png"));
                String remainingAttacks = tess.doOCR(new File(remainingAttacksFilename + ".png"));
                System.out.println("Enemy Name: " + enemyName + " Remaining Attacks: " + remainingAttacks);
                if(remainingAttacks.equals("0/40")) {
                    System.out.println("Out of attacks, stop program");
                    isRemainingAttacks = false;
                } else {
                    for(String e : enemyToAttack) {
//                      TODO some function to call these stuff on the array variables as well
                        e = e.toLowerCase();
                        e = e.trim();
                        e = e.replace(" ", "");
                        if(enemyName.contains(e)) {
                            isEnemyAttack = true;
                        }
                    }
                }
            } catch (TesseractException e) {
                System.out.println("Error with reading data: /n " + e.getMessage());
            } finally {
                if(isEnemyAttack) {
                    rSI.mouseClickFight();
                    isEnemyAttack = false;
                } else {
                    rSI.mouseClickMatchup();
                }
            }
        }
    }
}
