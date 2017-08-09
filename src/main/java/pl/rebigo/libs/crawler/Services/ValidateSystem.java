package pl.rebigo.libs.crawler.Services;

import lombok.extern.slf4j.Slf4j;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 27.07.2017
 * Time: 09:43
 * Project name: robot
 *
 * @author Karol Golec <karolgolec@itgolo.pl>
 */
@Slf4j
public class ValidateSystem {

    /* @var validate name */
    private static String validateName = "Windows";

    /* @var admissible versions */
    private static String[] validateVersions = {"7", "8", "10", "2008", "2012", "2016"};

    /* @var os name */
    public static final String OS_NAME = System.getProperty("os.name");

    /* @var os version */
    public static final String OS_VERSION = System.getProperty("os.version");

    /**
     * Check system is validate
     *
     * @return true if validate
     */
    public static Boolean validate() {
        if (OS_NAME.startsWith(ValidateSystem.validateName) == false) {
            log.error(String.format("This system OS is not validate. System: %1$s, version: %2$s.", OS_NAME, OS_VERSION));
            return false;
        }
        for (String admissibleVersion : ValidateSystem.validateVersions) {
            if (OS_VERSION.startsWith(admissibleVersion)) {
                log.debug(String.format("Validate system. System: %1$s, version: %2$s.", OS_NAME, OS_VERSION));
                return true;
            }
        }
        log.error(String.format("This system OS is not validate. System: %1$s, version: %2$s.", OS_NAME, OS_VERSION));
        return false;
    }
}
