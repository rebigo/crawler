package pl.rebigo.libs.crawler;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import pl.rebigo.libs.crawler.Exceptions.CrawlerException;
import pl.rebigo.libs.crawler.Factories.Chrome.ChromeBrowser;
import pl.rebigo.libs.crawler.Factories.Chrome.ChromeDriverBinary;

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
    public void test_chrome_driver() throws Exception {
        ChromeBrowser.validateAllOrDownload();
        CrawlerSettings.chromeHideBrowser = false;
        WebRobot webRobot = new WebRobot();
        webRobot.startNavigateTo("https://www.youtube.com/watch?v=TrDlBPrzvd8&list=RDqa-MpSReuZk&index=11&autoplay=1");
        Assert.assertNotNull(webRobot.getWebDriver().getTitle());
        webRobot.close();
    }

    @Test
    public void test_screenshot() throws Exception {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
        CrawlerSettings.chromeHideBrowser = false;
        WebRobot webRobot = new WebRobot();
        webRobot.startNavigateTo("http://google.pl/");
        webRobot.screenshot("temp");
        webRobot.close();
    }

    @Test
    public void test_functions_crawler() throws CrawlerException {
        WebRobot webRobot = new WebRobot();
        webRobot.startNavigateTo("http://google.pl/");
        webRobot.screenshot("temp");
        webRobot.screenshot(By.className("class"), "temp");
        webRobot.startResize(1024, 600);
        webRobot.startFindElement(By.cssSelector("element"), 2000, 100);
    }

    @Test
    public void test_chrome_driver_for_user() throws CrawlerException {
        CrawlerSettings.chromeHideBrowser = false;
        WebRobot webRobot = new WebRobot("Klikanie");
        webRobot.startNavigateTo("http://google.pl");
        Assert.assertNotNull(webRobot.getWebDriver().getTitle());
        webRobot.close();
    }

    @Test
    public void test_validate_chrome_browser(){
        CrawlerSettings.chromePortableMode = false;
        CrawlerSettings.chromeHideBrowser = false;
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
