package pl.rebigo.libs.crawler.Factories;

import org.openqa.selenium.WebDriver;
import pl.rebigo.libs.crawler.Exceptions.CrawlerException;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 08.08.2017
 * Time: 17:13
 * Project name: crawler
 *
 * @author Karol Golec <karol.rebigo@gmail.com>
 */
public interface IWebRobotFactory {

    /**
     * Get web driver
     *
     * @return web driver
     * @throws CrawlerException error web driver
     */
    WebDriver getWebDriver() throws CrawlerException;

}
