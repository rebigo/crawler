package pl.rebigo.libs.crawler.Operations;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.rebigo.libs.crawler.Exceptions.CrawlerException;

import java.io.File;
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
public class Screenshot {

    /**
     * Screenshot
     *
     * @param locator      web element
     *                     @param pathSave      path save
     * @param webDriver    web driver
     * @return web element or null
     */
    public void start(By locator, String pathSave, WebDriver webDriver) throws CrawlerException {
        List<WebElement> elements = webDriver.findElements(locator);
        if (elements.size() == 0){
            log.error(String.format("Can't make screenshot all web site. Element not exist. Locator: %1$s.", locator));
            throw new CrawlerException(String.format("Can't make screenshot all web site. Element not exist. Locator: %1$s.", locator));
        }
         try {
            String abs = new File(pathSave).getAbsolutePath();
            Shutterbug.shootElement(webDriver, elements.get(0)).save(abs);
            log.debug(String.format("Screenshot save to path: %1$s.", abs));
        } catch (Exception e) {
             log.error(String.format("Can't make screenshot all web site."));
             throw new CrawlerException("Can't make screenshot all web site.", e);
        }
    }

    /**
     * Screenshot
     *
     * @param pathSave      path save
     * @param webDriver    web driver
     * @return web element or null
     */
    public void start(String pathSave, WebDriver webDriver) throws CrawlerException {
        try {
            String abs = new File(pathSave).getAbsolutePath();
            Shutterbug.shootPage(webDriver, ScrollStrategy.BOTH_DIRECTIONS).save(abs);
            log.debug(String.format("Screenshot save to path: %1$s.", abs));
        } catch (Exception e) {
            log.error(String.format("Can't make screenshot all web site."));
            throw new CrawlerException("Can't make screenshot all web site.", e);
        }
    }
}
