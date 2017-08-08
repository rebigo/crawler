package pl.rebigo.libs.crawler;

import org.apache.commons.io.IOUtils;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.jutils.jprocesses.JProcesses;
import org.jutils.jprocesses.model.ProcessInfo;
import org.openqa.selenium.remote.RemoteWebDriver;
import pl.rebigo.libs.crawler.Exceptions.ChromeBrowserException;
import pl.rebigo.libs.crawler.Services.AdmissibleSystem;
import pl.rebigo.libs.download.Download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 27.07.2017
 * Time: 10:41
 * Project name: robot
 *
 * @author Karol Golec <karolgolec@itgolo.pl>
 */
public class ChromeBrowser {

    /* @var temp path zip with chrome driver */
    private static final String PATH_ZIP_PORTABLE = "temp/GoogleChromePortableBeta.zip";

    /**
     * Check exist chrome browser
     *
     * @return true if exist
     */
    public static Boolean exist() {
        try {
            WebRobot webRobot = new WebRobot();
            webRobot.runNavigateTo(GlobalSettings.urlTest);
            webRobot.close();
            System.out.println("DEBUG: Exist chrome browser.");
            return true;
        } catch (ChromeBrowserException e) {
            System.out.println("WARNING: Not exist chrome browser.");
        }
        return false;
    }

    /**
     * Validate all or download portable for run chrome browser
     *
     * @return true if success
     */
    public static Boolean validateAllOrDownload() {
        if (AdmissibleSystem.isAdmissible()) {
            if (ChromeDriverBinary.existOrInstall()) {
                if (ChromeBrowser.validateOrSwitch()) {
                    if (ChromeBrowser.validateVersion()) {
                        System.out.println("DEBUG: Chrome browser is OK.");
                        return true;
                    } else {
                        GlobalSettings.portableMode = true;
                        ChromeBrowser.destroyProcesses();
                        ChromeDriverBinary.destroyProcesses();
                        if (forceDownloadPortable()){
                            if (ChromeBrowser.validateVersion()){
                                System.out.println("DEBUG: Download new version and switch to chrome browser in portable mode.");
                                System.out.println("DEBUG: Chrome browser is OK.");
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Validate version or switch and download portable
     *
     * @return true if success
     */
    public static Boolean validateVersion() {
        Boolean validate = false;
        try {
            WebRobot webRobot = new WebRobot();
            webRobot.runNavigateTo(GlobalSettings.urlTest);
            String currentVersion = ((RemoteWebDriver) webRobot.getWebDriver()).getCapabilities().getVersion();
            DefaultArtifactVersion minVersion = new DefaultArtifactVersion(GlobalSettings.minVersionChromeBrowser);
            DefaultArtifactVersion version = new DefaultArtifactVersion(currentVersion);
            if (version.compareTo(minVersion) >= 0) {
                validate = true;
            } else {
                System.out.println("WARNING: This version chrome browser is old.");
            }
            webRobot.close();
            System.out.println("DEBUG: Current version chrome browser: " + currentVersion);
            return validate;
        } catch (ChromeBrowserException e) {
            System.out.println("ERROR: Validate version chrome browser.");
        }
        return false;
    }

    /**
     * Check exist portable
     *
     * @return true if exist
     */
    public static Boolean existPortable() {
        Boolean exist = false;
        if (new File(GlobalSettings.pathBinaryPortable).canExecute()) {
            System.out.println("DEBUG: Exist GoogleChromePortable.exe");
            exist = ChromeBrowser.exist();
        } else {
            System.out.println("DEBUG: Not exist GoogleChromePortable.exe");
            exist = false;

        }
        return exist;
    }

    /**
     * Download portable
     *
     * @return true if success
     */
    public static Boolean downloadPortable() {
        // TODO: Zmienić serwer przeglądarki chrome z tymczasowego na stały
        System.out.println("DEBUG: Download chrome browser portable.");
        if (Download.download(GlobalSettings.urlChromeBrowserPortable, PATH_ZIP_PORTABLE)) {
            System.out.println("DEBUG: Success download chrome portable.");
            return true;
        }
        return false;
    }

    /**
     * Unzip portable
     *
     * @return true if success
     */
    public static Boolean unzip() {
        Boolean success = false;
        System.out.println("DEBUG: Unzip GoogleChromePortableBeta.zip");
        try {
            File tmp = new File(PATH_ZIP_PORTABLE);
            ZipFile zipFile = new ZipFile(tmp);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String dirDest = new File(GlobalSettings.pathBinaryPortable).getParent();
                File entryDestination = new File(dirDest, entry.getName());
                if (entry.isDirectory()) {
                    entryDestination.mkdirs();
                } else {
                    entryDestination.getParentFile().mkdirs();
                    InputStream in = zipFile.getInputStream(entry);
                    OutputStream out = new FileOutputStream(entryDestination);
                    IOUtils.copy(in, out);
                    IOUtils.closeQuietly(in);
                    out.close();
                }
            }
            zipFile.close();
            success = true;
            System.out.println("DEBUG: Success unzip GoogleChromePortableBeta.zip");
        } catch (Exception e) {
            System.out.println("Error: Failed unzip GoogleChromePortableBeta.zip");
        }
        return success;
    }

    /**
     * Validate chrome browser or switch mode to portable
     *
     * @return true if success
     */
    public static Boolean validateOrSwitch() {
        System.out.println("DEBUG: Validate chrome browser.");
        Boolean validated = false;
        validated = GlobalSettings.portableMode && existOrDownloadPortable();
        if (validated == false) {
            validated = ChromeBrowser.exist();
            if (validated == false) {
                GlobalSettings.portableMode = true;
                validated = ChromeBrowser.exist();
                if (validated == false) {
                    existOrDownloadPortable();
                    validated = ChromeBrowser.exist();
                }
                if (validated)
                    System.out.println("DEBUG: Switch to exist chrome browser in portable mode.");
            }
        }
        return validated;
    }

    /**
     * Force download portable
     * @return true if success
     */
    public static Boolean forceDownloadPortable(){
        if (ChromeBrowser.downloadPortable()) {
            return ChromeBrowser.unzip();
        }
        return false;
    }

    /**
     * Check exist chrome browser portable or download
     *
     * @return true if success
     */
    public static Boolean existOrDownloadPortable() {
        Boolean exist = false;
        try {
            if (ChromeBrowser.existPortable() == false) {
                if (ChromeBrowser.downloadPortable()) {
                    exist = ChromeBrowser.unzip();
                }
            } else {
                exist = true;
            }
            System.out.println("DEBUG: Exist browser chrome in portable mode.");
        } catch (Exception e) {
            System.out.println("Error: Failed check exist or download portable chrome browser.");
        }
        return exist;
    }

    /**
     * Destroy all processes of Chrome.exe in system
     *
     * @return true if success
     */
    public static Boolean destroyProcesses() {
        List<ProcessInfo> processesList = JProcesses.getProcessList("Chrome.exe");
        if (processesList.size() == 0)
            return true;
        Boolean destroyed = false;
        for (final ProcessInfo processInfo : processesList) {
            System.out.println(processInfo.getPid());
            Boolean success = JProcesses.killProcess(Integer.parseInt(processInfo.getPid())).isSuccess();
            if (success)
                destroyed = true;
        }
        return destroyed;
    }
}
