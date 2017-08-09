package pl.rebigo.libs.crawler;

import lombok.Data;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 27.07.2017
 * Time: 07:44
 * Project name: robot
 *
 * @author Karol Golec <karolgolec@itgolo.pl>
 */
@Data
public class CrawlerSettings {

    public static String chromePathDriver = "resources/chrome/chromedriver.exe";

    public static Boolean chromeHideBrowser = true;

    public static Boolean validateSystem = true;

    public static String chromeDriverUrlResourceZipWithExe = "https://chromedriver.storage.googleapis.com/2.31/chromedriver_win32.zip";

    public static Boolean chromePortableMode = false;

    public static String urlTest = "http://itgolo.pl";

    public static String chromePathBinaryPortableExe = "resources/chrome/portable/GoogleChromePortable.exe";

    public static String chromePortableUrlResourceZip = "http://sagrol.pl/itgolo/GoogleChromePortableBeta.zip";

    public static String chromeMinVersion = "60";

    public static Integer defaultHeight = 800;

    public static Integer defaultWidth = 1366;

    public static String pathProfileDefault = "Default";

    public static String chromePathUsers = "resources/chrome/users";

    public static Boolean muteAudio = true;

}
