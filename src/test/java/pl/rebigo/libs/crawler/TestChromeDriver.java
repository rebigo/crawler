package pl.rebigo.libs.crawler;

import org.junit.Assert;
import org.junit.Test;
import pl.rebigo.libs.crawler.Exceptions.ChromeBrowserException;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 26.07.2017
 * Time: 14:07
 * Project name: robot
 *
 * @author Karol Golec <karolgolec@itgolo.pl>
 */
public class TestChromeDriver {

    @Test
    public void test_chrome_driver() throws ChromeBrowserException, InterruptedException {
        ChromeBrowser.validateAllOrDownload();
        GlobalSettings.hideChromeBrowser = false;
        WebRobot webRobot = new WebRobot();
        webRobot.runNavigateTo("https://www.youtube.com/watch?v=TrDlBPrzvd8&list=RDqa-MpSReuZk&index=11&autoplay=1");
        Assert.assertNotNull(webRobot.getWebDriver().getTitle());
        //webRobot.close();
        Thread.sleep(100000);
    }

    @Test
    public void test_chrome_driver_for_user() throws ChromeBrowserException {
//        GlobalSettings.hideChromeBrowser = false;
//        WebRobot webRobot = new WebRobot("Klikanie");
//        webRobot.runNavigateTo("http://google.pl");
//        Assert.assertNotNull(webRobot.getWebDriver().getTitle());
        //webRobot.close();
    }

    @Test
    public void test_validate_chrome_browser(){
        //GlobalSettings.portableMode = false;
        //GlobalSettings.hideChromeBrowser = false;
        ChromeBrowser.validateAllOrDownload();
    }

    @Test
    public void off_all_chrome_drivers() {
        ChromeDriverBinary.destroyProcesses();
    }

    @Test
    public void off_all_chrome_browsers() {
        ChromeBrowser.destroyProcesses();
    }
}
