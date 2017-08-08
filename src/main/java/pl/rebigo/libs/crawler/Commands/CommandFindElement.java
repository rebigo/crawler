package pl.rebigo.libs.crawler.Commands;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by ITGolo on 03.03.2017.
 */
public class CommandFindElement {

    /**
     * Find element
     *
     * @param locator      web element
     * @param waitTimeout  wait timeout
     * @param pollingEvery polling every
     * @param webDriver    web driver
     * @return web element or null
     */
    public WebElement run(By locator, Integer waitTimeout, Integer pollingEvery, WebDriver webDriver) {
        List<WebElement> elements = null;
        Integer timeMilliseconds = 0;
        while (true) {
            elements = webDriver.findElements(locator);
            if (elements.size() > 0)
                break;
            try {
                Thread.sleep(pollingEvery);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timeMilliseconds += pollingEvery;
            if (timeMilliseconds >= waitTimeout * 1000)
                break;
        }
        if (elements.size() > 0) {
            return elements.get(0);
        }
        return null;
    }
}
