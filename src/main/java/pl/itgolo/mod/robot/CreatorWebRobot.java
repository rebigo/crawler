package pl.itgolo.mod.robot;

import lombok.Data;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pl.itgolo.mod.robot.Exceptions.ChromeBrowserException;

import java.io.File;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 27.07.2017
 * Time: 09:13
 * Project name: robot
 *
 * @author Karol Golec <karolgolec@itgolo.pl>
 */
@Data
public class CreatorWebRobot {

    /* @var user name */
    public String userName = "default";

    /**
     * Constructor
     *
     * @throws ChromeBrowserException error unlock chrome browser
     */
    public CreatorWebRobot() throws ChromeBrowserException {
        loadInstance();
    }

    /**
     * Constructor
     *
     * @param userName userName
     * @throws ChromeBrowserException error unlock chrome browser
     */
    public CreatorWebRobot(String userName) throws ChromeBrowserException {
        this.userName = userName;
        loadInstance();
    }

    /**
     * Load instance
     *
     * @throws ChromeBrowserException error unlock chrome browser
     */
    public void loadInstance() throws ChromeBrowserException {
        System.setProperty("webdriver.chrome.driver", GlobalSettings.pathChromeDriver);
        unlock();
    }

    /**
     * Get options for web driver
     *
     * @return chrome options
     */
    private ChromeOptions getOptions() {
        ChromeOptions options = new ChromeOptions();
        if (GlobalSettings.hideChromeBrowser) {
            System.out.println("DEBUG: Browser chrome is hide mode.");
            options.addArguments("--headless");
        }
        if (GlobalSettings.portableMode) {
            System.out.println("DEBUG: Browser chrome is portable mode.");
            options.setBinary(new File(GlobalSettings.pathBinaryPortable));
        }
        options.addArguments("user-data-dir=" + new File(GlobalSettings.pathUsers + "/" + this.userName).getAbsolutePath());
        options.addArguments("--profile-directory=" + GlobalSettings.pathProfileDefault);
        if (GlobalSettings.muteAudio){
            options.addArguments("--mute-audio");
        }
        return options;
    }

    /**
     * Get web driver
     *
     * @return web driver
     * @throws ChromeBrowserException error chrome driver
     */
    public WebDriver getWebDriver() throws ChromeBrowserException {
        try {
            WebDriver webDriver = new ChromeDriver(this.getOptions());
            return webDriver;
        } catch (Exception e) {
            throw new ChromeBrowserException(e.getMessage(), e);
        }
    }

    /**
     * Unlock chrome browser
     *
     * @throws ChromeBrowserException error unlock chrome browser
     */
    private void unlock() throws ChromeBrowserException {
        File lockFile = new File(GlobalSettings.pathUsers + "/" + this.userName + "/lockfile");
        if (lockFile.exists()) {
            System.out.println("DEBUG: Exist lock file.");
            if (ChromeBrowser.destroyProcesses() == false) {
                throw new ChromeBrowserException(String.format("DEBUG: Can not destroy processes chrome browser."));
            }
        }
    }
}
