package pl.rebigo.libs.crawler.Operations;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import pl.itgolo.plugin.helper.Thread.SleepHelper;
import pl.rebigo.libs.crawler.Exceptions.CrawlerException;
import pl.rebigo.libs.crawler.CrawlerSettings;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 27.07.2017
 * Time: 09:27
 * Project name: robot
 *
 * @author Karol Golec <karolgolec@itgolo.pl>
 */
@Slf4j
public class Resize {

    /* @var pooling every check resized */
    private Integer poolingEvery = 1000;

    /* @var timeout resize in milliseconds */
    private Integer timeoutMSec = 45000;


    /**
     * Browser resize
     *
     * @param webDriver web driver
     * @throws CrawlerException error resize
     */
    public void start(Integer width, Integer height, WebDriver webDriver) throws CrawlerException {
        for (Integer count = 0 ; count < timeoutMSec ; count += poolingEvery){
            webDriver.manage().window().setSize(new Dimension(CrawlerSettings.defaultWidth, CrawlerSettings.defaultHeight));
            SleepHelper.sleep(poolingEvery);
            Dimension size = webDriver.manage().window().getSize();
            if (size.getWidth() == CrawlerSettings.defaultWidth
                    && size.getHeight() == CrawlerSettings.defaultHeight) {
                log.debug(String.format("Resized web browser to size: %1$s x %2$s.", width, height));
                return;
            }
        }
        log.error(String.format("Can not resize web browser."));
        throw new CrawlerException(String.format("Can not resize web browser."));
    }
}
