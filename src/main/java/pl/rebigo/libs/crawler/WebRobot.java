package pl.rebigo.libs.crawler;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.rebigo.libs.crawler.Exceptions.CrawlerException;
import pl.rebigo.libs.crawler.Factories.IWebRobotFactory;
import pl.rebigo.libs.crawler.Factories.WebRobotChromeFactory;
import pl.rebigo.libs.crawler.Operations.*;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 27.07.2017
 * Time: 09:11
 * Project name: robot
 *
 * @author Karol Golec <karolgolec@itgolo.pl>
 */
@Data
@Slf4j
public class WebRobot {

    /* @var creator web robot */
    private IWebRobotFactory webRobotFactory;

    /* @var Command navigate to */
    private NavigateTo navigateTo;

    /* @var Command resize */
    private Resize resize;

    /* @var click */
    private Click click;

    /* @var find element */
    private FindElement findElement;

    /* @var screenshot */
    private Screenshot screenshot;

    /* @var web driver */
    private WebDriver webDriver;

    /**
     * Constructor
     *
     * @throws CrawlerException error chrome driver
     */
    public WebRobot() throws CrawlerException {
        this.webRobotFactory = new WebRobotChromeFactory();
        commonsCreateInstance();
    }

    /**
     * Constructor
     *
     * @param user user
     * @throws CrawlerException error chrome driver
     */
    public WebRobot(String user) throws CrawlerException {
        this.webRobotFactory = new WebRobotChromeFactory(user);
        commonsCreateInstance();
    }

    /**
     * Commons with create instance
     *
     * @throws CrawlerException error chrome driver
     */
    public void commonsCreateInstance() throws CrawlerException {
        log.debug("Create web robot.");
        this.webDriver = this.webRobotFactory.getWebDriver();
        this.navigateTo = new NavigateTo();
        this.resize = new Resize();
        this.click = new Click();
        this.findElement = new FindElement();
        this.screenshot = new Screenshot();
        this.startResize(CrawlerSettings.defaultWidth, CrawlerSettings.defaultHeight);
    }

    /**
     * Close
     */
    public void close() {
        this.webDriver.close();
        this.webDriver.quit();
        log.debug("Call to close web driver.");
    }

    /**
     * Navigate to
     *
     * @param url url
     * @throws CrawlerException error navigate to
     */
    public void startNavigateTo(String url) throws CrawlerException {
        this.navigateTo.start(url, this.webDriver, 30);
    }

    /**
     * Resize
     *
     * @param width  width
     * @param height height
     * @throws CrawlerException error resize
     */
    public void startResize(Integer width, Integer height) throws CrawlerException {
        this.resize.start(width, height, this.webDriver);
    }

    /**
     * Click
     *
     * @param locator    locator
     * @param javascript java script
     * @throws CrawlerException error click
     */
    public void startClick(By locator, Boolean javascript) throws CrawlerException {
        this.click.start(locator, javascript, this.webDriver);
    }

    /**
     * Find element
     *
     * @param locator      web element
     * @param timeoutMSec  wait timeout in milliseconds
     * @param pollingEvery polling every
     * @return web element or null
     */
    public WebElement startFindElement(By locator, Integer timeoutMSec, Integer pollingEvery) {
        return this.findElement.start(locator, timeoutMSec, pollingEvery, this.webDriver);
    }

    /**
     * Screenshot
     *
     * @param pathSave  path save
     * @return web element or null
     */
    public void screenshot(String pathSave) throws CrawlerException {
        this.screenshot.start(pathSave, this.webDriver);
    }

    /**
     * Screenshot
     *
     * @param locator   web element
     * @param pathSave  path save
     * @return web element or null
     */
    public void screenshot(By locator, String pathSave) throws CrawlerException {
        this.screenshot.start(locator, pathSave, this.webDriver);
    }
}
