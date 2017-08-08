package pl.rebigo.libs.crawler;

import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.rebigo.libs.crawler.Commands.CommandClick;
import pl.rebigo.libs.crawler.Commands.CommandFindElement;
import pl.rebigo.libs.crawler.Commands.CommandNavigateTo;
import pl.rebigo.libs.crawler.Commands.CommandResize;
import pl.rebigo.libs.crawler.Exceptions.ChromeBrowserException;

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
public class WebRobot {

    /* @var creator web robot */
    private CreatorWebRobot creatorWebRobot;

    /* @var Command navigate to */
    private CommandNavigateTo commandNavigateTo;

    /* @var Command resize */
    private CommandResize commandResize;

    /* @var click */
    private CommandClick commandClick;

    /* @var find element */
    private CommandFindElement commandFindElement;

    /* @var web driver */
    private WebDriver webDriver;

    /**
     * Constructor
     *
     * @throws ChromeBrowserException error chrome driver
     */
    public WebRobot() throws ChromeBrowserException {
        this.creatorWebRobot = new CreatorWebRobot();
        loadInstance();
    }

    /**
     * Constructor
     *
     * @param userName user name
     * @throws ChromeBrowserException error chrome driver
     */
    public WebRobot(String userName) throws ChromeBrowserException {
        this.creatorWebRobot = new CreatorWebRobot(userName);
        loadInstance();
    }

    /**
     * Load instance
     *
     * @throws ChromeBrowserException error chrome driver
     */
    public void loadInstance() throws ChromeBrowserException {
        this.webDriver = this.creatorWebRobot.getWebDriver();
        this.commandNavigateTo = new CommandNavigateTo();
        this.commandResize = new CommandResize();
        this.commandClick = new CommandClick();
        this.commandFindElement = new CommandFindElement();
        this.runResize(GlobalSettings.defaultWidth, GlobalSettings.defaultHeight);
    }

    /**
     * Close
     */
    public void close() {
        this.webDriver.close();
        this.webDriver.quit();
    }

    /**
     * Navigate to
     *
     * @param url url
     * @throws ChromeBrowserException error navigate to
     */
    public void runNavigateTo(String url) throws ChromeBrowserException {
        this.commandNavigateTo.run(url, this.webDriver, 30);
    }

    /**
     * Resize
     *
     * @param width  width
     * @param height height
     * @throws ChromeBrowserException error navigate to
     */
    public void runResize(Integer width, Integer height) throws ChromeBrowserException {
        this.commandResize.run(width, height, this.webDriver);
    }

    /**
     * Click
     *
     * @param locator    locator
     * @param javascript java script
     * @throws ChromeBrowserException error navigate to
     */
    public void runClick(By locator, Boolean javascript) throws ChromeBrowserException {
        this.commandClick.run(locator, javascript, this.webDriver);
    }

    /**
     * Find element
     *
     * @param locator      web element
     * @param waitTimeout  wait timeout
     * @param pollingEvery polling every
     * @return web element or null
     */
    public WebElement runFindElement(By locator, Integer waitTimeout, Integer pollingEvery) {
        return this.commandFindElement.run(locator, waitTimeout, pollingEvery, this.webDriver);
    }
}
