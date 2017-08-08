package pl.itgolo.mod.robot.Commands;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import pl.itgolo.mod.robot.Exceptions.ChromeBrowserException;
import pl.itgolo.mod.robot.GlobalSettings;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 27.07.2017
 * Time: 09:27
 * Project name: robot
 *
 * @author Karol Golec <karolgolec@itgolo.pl>
 */
public class CommandResize {

    /**
     * Browser resize
     * @param webDriver web driver
     * @return true if success
     * @throws ChromeBrowserException error resize
     */
    public void run(Integer width, Integer height, WebDriver webDriver) throws ChromeBrowserException {
        Integer poolingEvery = 1000;
        Boolean resized = false;
        Integer timeMilliseconds = 0;
        Integer timeoutResize = 45;
        while(true){
            webDriver.manage().window().setSize(new Dimension(GlobalSettings.defaultWidth, GlobalSettings.defaultHeight));
            try {
                Thread.sleep(poolingEvery);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Dimension size = webDriver.manage().window().getSize();
            if (size.getWidth() == GlobalSettings.defaultWidth && size.getHeight() == GlobalSettings.defaultHeight){
                resized = true;
                break;
            }
            timeMilliseconds += poolingEvery;
            if (timeMilliseconds >= timeoutResize * 1000)
                break;
        }
        if (resized == false){
            throw new ChromeBrowserException(String.format("Can not resize chrome browser."));
        } else
            System.out.println("DEBUG: Chrome browser is resized.");
    }
}
