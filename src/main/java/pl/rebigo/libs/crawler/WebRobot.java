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
import pl.rebigo.libs.crawler.Operations.Enums.OperationFill;
import pl.rebigo.libs.crawler.Operations.Enums.OperationSelectDropDown;

import java.util.List;

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

    /* @var find elements */
    private FindElements findElements;

    /* @var screenshot */
    private Screenshot screenshot;

    /* @var click and reload */
    private ClickReload clickReload;

    /* @var fill element */
    private Fill fill;

    /* @var select drop down */
    private SelectDropDown selectDropDown;

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
        this.findElements = new FindElements();
        this.screenshot = new Screenshot();
        this.clickReload = new ClickReload();
        this.fill = new Fill();
        this.selectDropDown = new SelectDropDown();
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
     * Find elements
     *
     * @param locator      web element
     * @param timeoutMSec  wait timeout in milliseconds
     * @param pollingEvery polling every
     * @return web elements or null
     */
    public List<WebElement> startFindElements(By locator, Integer timeoutMSec, Integer pollingEvery) {
        return this.findElements.start(locator, timeoutMSec, pollingEvery, this.webDriver);
    }

    /**
     * Screenshot
     *
     * @param pathSave path save
     * @return web element or null
     */
    public void startScreenshot(String pathSave) throws CrawlerException {
        this.screenshot.start(pathSave, this.webDriver);
    }

    /**
     * Screenshot
     *
     * @param locator  web element
     * @param pathSave path save
     * @return web element or null
     */
    public void startScreenshot(By locator, String pathSave) throws CrawlerException {
        this.screenshot.start(locator, pathSave, this.webDriver);
    }

    /**
     * Click in element and wait for reload
     *
     * @param locator      locator
     * @param javascript   click by java script
     * @param timeoutMSec  timeout in milliseconds
     * @param pollingEvery pooling every
     * @param webDriver    web driver
     * @throws CrawlerException failed reloaded or click
     */
    public void startClickReload(By locator, Boolean javascript, Integer timeoutMSec, Integer pollingEvery, WebDriver webDriver) throws CrawlerException {
        this.clickReload.start(locator, javascript, timeoutMSec, pollingEvery, this.webDriver);
    }

    /**
     * Fill element
     *
     * @param locator         web element
     * @param text            text
     * @param operationFill   method fill
     * @param attempts        attempts fill element
     * @param intervalAttempt interval attempt
     * @throws CrawlerException failed fill
     */
    public void startFill(By locator, String text, OperationFill operationFill, Integer attempts, Integer intervalAttempt) throws CrawlerException {
        this.fill.start(locator, text, operationFill, attempts, intervalAttempt, this.webDriver);
    }

    /**
     * Select option in drop down
     *
     * @param locator                 web element
     * @param value                   value
     * @param operationSelectDropDown method select drop down
     * @throws CrawlerException failed select drop down
     */
    public void startSelectDropDown(By locator, String value, OperationSelectDropDown operationSelectDropDown) throws CrawlerException {
        this.selectDropDown.start(locator, value, operationSelectDropDown, this.webDriver);
    }
}
