package pl.rebigo.libs.crawler.Operations;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.rebigo.libs.crawler.Exceptions.CrawlerException;

import java.util.List;

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
public class Click {

    /**
     * Click in element
     *
     * @param locator    web element
     * @param javascript click by script of javascript
     * @throws CrawlerException error click
     */
    public void start(By locator, Boolean javascript, WebDriver webDriver) throws CrawlerException {
        List<WebElement> elements = webDriver.findElements(locator);
        if (elements.size()== 0)
            throw new CrawlerException(String.format("Not found web element. Locator: %1$s.", locator.toString()));
        try {
            if (javascript == false)
                elements.get(0).click();
            else
                ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", elements.get(0));
            log.debug(String.format("Clicked in web element. Locator: %1$s.", locator.toString()));
        } catch (Exception e) {
            log.error(String.format("Not clicked in web element. Locator: %1$s.", locator.toString()), e);
            throw new CrawlerException(String.format("Not clicked in web element. Locator: %1$s.", locator.toString()), e);
        }
    }
}