import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class RobotScreenInteraction {
    Robot robot;
    {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

//  Screenshot what you currently see on screen
    public BufferedImage screenShot(String fileExtension) {
//      Creates a blank rectangle with the size of my current screen.
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
//      Creates an image with the robot screen capturing whatever is on my screen
        BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
//      Save the image in root directory.
        return screenFullImage;
    }

    public void mouseClickFight() throws InterruptedException {
        robot.mouseMove(565, 915);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        Thread.sleep(7000);
        robot.keyPress(KeyEvent.VK_ESCAPE);
        robot.keyRelease(KeyEvent.VK_ESCAPE);
        Thread.sleep(2000);
    }

    public void mouseClickMatchup(){
        robot.mouseMove(1275, 915);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }
}
