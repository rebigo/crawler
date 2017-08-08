package pl.rebigo.libs.crawler.Commands;

import org.openqa.selenium.WebDriver;
import pl.rebigo.libs.crawler.Exceptions.ChromeBrowserException;

import java.util.concurrent.TimeUnit;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 27.07.2017
 * Time: 09:27
 * Project name: robot
 *
 * @author Karol Golec <karolgolec@itgolo.pl>
 */
public class CommandNavigateTo {

    /**
     * Run navigate to
     *
     * @throws ChromeBrowserException error navigate to
     */
    public void run(String url, WebDriver webDriver, Integer timeout) throws ChromeBrowserException {
        Boolean success = false;
        try {
            webDriver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
            webDriver.navigate().to(url);
            success = true;
        } catch (Exception e){
            System.out.println("ERROR: Robot not navigate to: " + url);
            throw new ChromeBrowserException(String.format("Can not navigate to site: %1$s.", url));
        }
    }
}
