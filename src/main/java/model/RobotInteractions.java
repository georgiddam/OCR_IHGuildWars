/**
 * The RobotInteractions class is used to generate basic functions that the computer does
 * It takes the screenshots from the screen to be manipulated and read
 * It performs mouse functionality, based on the final results from the images.
 */
package model;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class RobotInteractions {
	Robot robot;
	public RobotInteractions() throws AWTException {
		robot = new Robot();
	}

//  Screenshot what you currently see on screen
    public BufferedImage screenShot() {
//      Creates a blank rectangle with the size of my current screen.
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
//      Creates an image with the robot screen capturing whatever is on my screen
        return robot.createScreenCapture(screenRect);
    }

    public void mouseClickFight(int rand) throws InterruptedException {
        robot.mouseMove(565, 915);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(7000+ (long)rand);
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
        Thread.sleep(2000);
        mouseClickMatchup();
    }

    public void mouseClickMatchup(){
        robot.mouseMove(1275, 915);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}
