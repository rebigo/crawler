package pl.rebigo.libs.crawler;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pl.itgolo.plugin.helper.Thread.SleepHelper;
import pl.rebigo.libs.crawler.Exceptions.CrawlerException;
import pl.rebigo.libs.crawler.Operations.Enums.OperationFill;
import pl.rebigo.libs.crawler.Operations.Enums.OperationSelectDropDown;

import java.util.List;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 09.08.2017
 * Time: 10:42
 * Project name: crawler
 *
 * @author Karol Golec <karol.rebigo@gmail.com>
 */
public class TestWebDriverOperations {

    @Test
    public void test_navigate_to_screenshot_resize_and_find_element() throws CrawlerException {
        WebRobot webRobot = new WebRobot();
        webRobot.startNavigateTo("http://google.pl/");
        webRobot.startScreenshot("temp");
        webRobot.startScreenshot(By.className("class"), "temp");
        webRobot.startResize(1024, 600);
        webRobot.startFindElement(By.cssSelector("element"), 2000, 100);
    }

    @Test
    public void test_operation_find_elements() throws CrawlerException {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
        CrawlerSettings.chromeHideBrowser = false;
        WebRobot webRobot = new WebRobot();
        webRobot.startNavigateTo("https://github.com/");
        List<WebElement> elements = webRobot.startFindElements(By.xpath("//a[@data-ga-click='Header, click, Nav menu - item:explore']"), 2000, 100);
        Assert.assertTrue(elements.size()>0);
        webRobot.close();
    }

    @Test
    public void test_operation_click_and_reload() throws CrawlerException {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
        CrawlerSettings.chromeHideBrowser = false;
        WebRobot webRobot = new WebRobot();
        webRobot.startNavigateTo("https://github.com/");
        webRobot.startClickReload(By.xpath("//a[@data-ga-click='Header, click, Nav menu - item:explore']"), false, 40000, 100, webRobot.getWebDriver());
        webRobot.close();
    }

    @Test
    public void test_operation_fill_element() throws CrawlerException {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
        CrawlerSettings.chromeHideBrowser = false;
        WebRobot webRobot = new WebRobot();
        webRobot.startNavigateTo("https://google.com/");
        webRobot.startFill(By.cssSelector(".gsfi"), "Example text", OperationFill.JAVASCRIPT, 2, 100);
        SleepHelper.sleep(40000);
        webRobot.close();
    }

    @Test
    public void test_operation_select_drop_down() throws CrawlerException {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
        CrawlerSettings.chromeHideBrowser = false;
        WebRobot webRobot = new WebRobot();
        webRobot.startNavigateTo("https://select2.github.io/examples.html");
        webRobot.startSelectDropDown(By.xpath("//select[@class='js-states form-control']"), "Nevada", OperationSelectDropDown.SELECT_BY_TEXT);
        SleepHelper.sleep(40000);
        webRobot.close();
    }
}
