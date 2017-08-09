package pl.rebigo.libs.crawler.Operations;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import pl.rebigo.libs.crawler.Exceptions.CrawlerException;

import java.util.concurrent.TimeUnit;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 27.07.2017
 * Time: 09:27
 * Project name: crawler
 *
 * @author Karol Golec <karol.rebigo@gmail.com>
 */
@Slf4j
public class NavigateTo {

    /**
     * Run operation
     *
     * @param site url web site
     * @param webDriver  web driver
     * @param timeout    timeout page load
     * @throws CrawlerException error operation
     */
    public static void start(String site, WebDriver webDriver, Integer timeout) throws CrawlerException {
        try {
            log.debug(String.format("Begin navigate to web site: %1$s.", site));
            webDriver.manage().timeouts().pageLoadTimeout(timeout, TimeUnit.SECONDS);
            webDriver.navigate().to(site);
            log.debug(String.format("End navigate to web site: %1$s.", site));
        } catch (Exception e) {
            log.error(String.format("Failed navigate to web site: %1$s.", site), e);
            throw new CrawlerException(String.format("Failed navigate to web site: %1$s.", site), e);
        }
    }
}