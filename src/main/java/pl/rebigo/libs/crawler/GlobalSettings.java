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
public class GlobalSettings {

    public static String pathChromeDriver = "resources/chrome/chromedriver.exe";

    public static Boolean hideChromeBrowser = true;

    public static Boolean admissibleSystem = true;

    public static String urlChromeDriver = "https://chromedriver.storage.googleapis.com/2.31/chromedriver_win32.zip";

    public static Boolean portableMode = false;

    public static String urlTest = "http://itgolo.pl";

    public static String pathBinaryPortable = "resources/chrome/portable/GoogleChromePortable.exe";

    public static String urlChromeBrowserPortable = "http://sagrol.pl/itgolo/GoogleChromePortableBeta.zip";

    public static String minVersionChromeBrowser = "60";

    public static Integer defaultHeight = 800;

    public static Integer defaultWidth = 1366;

    public static String pathProfileDefault = "Default";

    public static String pathUsers = "resources/chrome/users";

    public static Boolean muteAudio = true;
}
