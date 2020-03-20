/**
*  This program helps with guild wars. It's designed to
*  allow users to select teams to fight and keeps refreshing fights until
*  desired team comes or run out of stamina to fight.
*
* @author  G.Dam
* @version 1.0
* @since   10/01/2020
*/
package controller;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Scanner;

import model.CropImage;
import model.ReadImage;
import model.RobotInteractions;
import model.TessSetup;

public class ApplicationController {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the amount of teams you want to input::");

//      nextInt does not consume return line character from commandline so I use nextLine
        String strSize = sc.nextLine();
        int size = Integer.parseInt(strSize);
        String[] enemyArray = new String[size];

        for(int i=0; i<size; i++) {
            enemyArray[i] = sc.nextLine();
        }
        
        sc.close();

//      Instantiate everything required
        RobotInteractions rI = new RobotInteractions();
        ReadImage readImage = new ReadImage();
        CropImage cropImage = new CropImage();
        TessSetup tess = new TessSetup();

        Random rand = new Random();

        String enemyFilename = "enemyName";
        String remAtkFilename = "remainingAttacks";

        boolean isRunningMainLoop = true;
        boolean isEnemyAttack = false;
        
//      wait a bit for this to start and reposition to game window.
        Thread.sleep(2000);
        while (isRunningMainLoop) {

//          Create screenshot
            BufferedImage img = rI.screenShot();

//          Get the cropped images
            BufferedImage cropEnemy = cropImage.cropEnemyName(img);
            BufferedImage cropAttacks = cropImage.cropRemAttacks(img);

//          Manipulate cropped images
//          (This step is actually not required anymore but for purposes of doing more with OCR)
            cropEnemy = readImage.invertImage(cropEnemy);
            cropEnemy = readImage.upscaleAndGrayscale(cropEnemy, 2);
            readImage.saveFile(cropEnemy, enemyFilename, "png");

            cropAttacks = readImage.invertImage(cropAttacks);
            cropAttacks = readImage.upscaleAndGrayscale(cropAttacks, 2);
            readImage.saveFile(cropAttacks, remAtkFilename, "png");


//          Using the tesseract to scan the images
            isEnemyAttack = tess.tessScanFile(enemyArray,enemyFilename, remAtkFilename );
            isRunningMainLoop = tess.isRunning();

            if (isEnemyAttack) {
                rI.mouseClickFight(rand.nextInt((1)*1000));
            } else {
                rI.mouseClickMatchup();
            }

//          Wait for the 10 sec to pass
            Thread.sleep(10000 + (long)rand.nextInt(2)*1000);
        }
    }
}
