package pl.rebigo.libs.crawler.Operations;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.itgolo.plugin.helper.Thread.SleepHelper;

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
public class FindElements {

    /**
     * Find elements
     *
     * @param locator      web element
     * @param timeoutMSec     timeout milliseconds
     * @param pollingEvery polling every
     * @param webDriver    web driver
     * @return web elements or null
     */
    public List<WebElement> start(By locator, Integer timeoutMSec, Integer pollingEvery, WebDriver webDriver) {
        for (Integer count = 0; count < timeoutMSec; count += pollingEvery){
            log.debug(String.format("Check exist web elements. Count seconds: %1$s of %2$s.", count / 1000, timeoutMSec / 1000));
            List<WebElement> elements = webDriver.findElements(locator);
            if (elements.size() > 0)
                return elements;
            SleepHelper.sleep(pollingEvery);
        }
        return null;
    }
}
