package pl.rebigo.libs.crawler.Services;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 27.07.2017
 * Time: 09:43
 * Project name: robot
 *
 * @author Karol Golec <karolgolec@itgolo.pl>
 */
public class AdmissibleSystem {

    /* @var admissible name */
    private static String admissibleName = "Windows";

    /* @var admissible versions */
    private static String[] admissibleVersions = {"7", "8", "10", "2008", "2012", "2016"};

    /* @var os name */
    public static final String OS_NAME = System.getProperty("os.name");

    /* @var os version */
    public static final String OS_VERSION = System.getProperty("os.version");

    /**
     * Check system is admissible
     *
     * @return true if admissible
     */
    public static Boolean isAdmissible() {
        if (OS_NAME.startsWith(AdmissibleSystem.admissibleName) == false) {
            System.out.println("Error: This system OS is not admissible.");
            return false;
        }
        for (String admissibleVersion : AdmissibleSystem.admissibleVersions) {
            if (OS_VERSION.startsWith(admissibleVersion)) {
                System.out.println("DEBUG: Check admissible OK.");
                return true;
            }
        }
        System.out.println("Error: This system OS is not admissible.");
        return false;
    }
}
