package pl.rebigo.libs.crawler.Operations;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import pl.itgolo.plugin.helper.Thread.SleepHelper;
import pl.rebigo.libs.crawler.Exceptions.CrawlerException;

import java.util.Objects;
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
public class ClickReload {

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
    public void start(By locator, Boolean javascript, Integer timeoutMSec, Integer pollingEvery, WebDriver webDriver) throws CrawlerException {
        log.debug(String.format("Begin operation click to element and wait for reload. Locator: %1$s.", locator.toString()));
        webDriver.manage().timeouts().pageLoadTimeout(timeoutMSec, TimeUnit.SECONDS);
        try {
            ((JavascriptExecutor) webDriver).executeScript("window.crawlerReload = false;");
            Click click = new Click();
            click.start(locator, javascript, webDriver);
            for (Integer count = 0; count < timeoutMSec; count += pollingEvery) {
                log.debug(String.format("Check reload web site. Count seconds: %1$s of %2$s.", count / 1000, timeoutMSec / 1000));
                Boolean reloaded = Objects.isNull(((JavascriptExecutor) webDriver).executeScript("return window.crawlerReload;"));
                Boolean completed = ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete");
                if (reloaded && completed) {
                    log.debug(String.format("End operation click to element and wait for reload. Locator: %1$s.", locator.toString()));
                    return;
                }
                SleepHelper.sleep(pollingEvery);
            }
        } catch (Exception e) {
            log.error(String.format("Failed click to element and wait for reload. Locator: %1$s.", locator.toString()), e);
            throw new CrawlerException(String.format("Failed click to element and wait for reload. Locator: %1$s.", locator.toString()), e);
        }
    }
}
