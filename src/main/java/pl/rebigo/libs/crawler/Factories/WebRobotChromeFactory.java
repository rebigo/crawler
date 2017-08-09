package pl.rebigo.libs.crawler.Factories;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pl.rebigo.libs.crawler.Factories.Chrome.ChromeBrowser;
import pl.rebigo.libs.crawler.Exceptions.CrawlerException;
import pl.rebigo.libs.crawler.CrawlerSettings;

import java.io.File;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 08.08.2017
 * Time: 17:13
 * Project name: crawler
 *
 * @author Karol Golec <karol.rebigo@gmail.com>
 */
@Slf4j
public class WebRobotChromeFactory implements IWebRobotFactory {

    /* @var user */
    private String user = "default";

    /**
     * Constructor
     * @throws CrawlerException error web driver
     */
    public WebRobotChromeFactory() throws CrawlerException {
        commonsCreateInstance();
    }

    /**
     * Constructor
     * @param user user
     * @throws CrawlerException error web driver
     */
    public WebRobotChromeFactory(String user) throws CrawlerException {
        this.user = user;
        commonsCreateInstance();
    }

    /**
     * Get web driver
     *
     * @return web driver
     * @throws CrawlerException error web driver
     */
    @Override
    public WebDriver getWebDriver() throws CrawlerException {
        try {
            WebDriver webDriver = new ChromeDriver(this.options());
            log.debug(String.format("Created web driver."));
            return webDriver;
        } catch (Exception e) {
            log.error(String.format("Failed build web driver."));
            throw new CrawlerException("Failed build web driver.", e);
        }
    }

    /**
     * Commons in create instance
     *
     * @throws CrawlerException lock file in web browser
     */
    public void commonsCreateInstance() throws CrawlerException {
        System.setProperty("webdriver.chrome.driver", CrawlerSettings.chromePathDriver);
        lockFile();

    }



    /**
     * Unlock chrome browser
     *
     * @throws CrawlerException lock file in web browser
     */
    public void lockFile() throws CrawlerException {
        File lockFile = new File(CrawlerSettings.chromePathUsers + "/" + this.user + "/lockfile");
        if (lockFile.exists()) {
            log.debug(String.format("Begin destroy web browser processes."));
            if (ChromeBrowser.destroyProcesses() == false)
                throw new CrawlerException(String.format("Can not destroy processes web browser."));
            log.debug(String.format("End destroy web browser processes."));
        }
    }

    /**
     * Create options for web driver
     *
     * @return chrome options
     */
    private ChromeOptions options() {
        ChromeOptions options = new ChromeOptions();
        if (CrawlerSettings.chromeHideBrowser) {
            log.debug(String.format("Web browser is hide mode."));
            options.addArguments("--headless");
        }
        if (CrawlerSettings.chromePortableMode) {
            log.debug(String.format("Web browser is portable mode."));
            options.setBinary(new File(CrawlerSettings.chromePathBinaryPortableExe));
        }
        if (CrawlerSettings.muteAudio){
            log.debug(String.format("Web browser with mute audio."));
            options.addArguments("--mute-audio");
        }
        options.addArguments("user-data-dir=" + new File(CrawlerSettings.chromePathUsers + "/" + this.user).getAbsolutePath());
        options.addArguments("--profile-directory=" + CrawlerSettings.pathProfileDefault);
        return options;
    }
}