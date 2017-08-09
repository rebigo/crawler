package pl.rebigo.libs.crawler.Factories.Chrome;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.jutils.jprocesses.JProcesses;
import org.jutils.jprocesses.model.ProcessInfo;
import org.openqa.selenium.remote.RemoteWebDriver;
import pl.rebigo.libs.crawler.CrawlerSettings;
import pl.rebigo.libs.crawler.Services.ValidateSystem;
import pl.rebigo.libs.crawler.WebRobot;
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
@Slf4j
public class ChromeBrowser {

    /* @var temp path zip with chrome driver */
    private static final String PATH_TEMP_ZIP_PORTABLE = "temp/GoogleChromePortableBeta.zip";

    /**
     * Check can start chrome browser
     *
     * @return true if exist
     */
    public static Boolean canStart() {
        try {
            WebRobot webRobot = new WebRobot();
            webRobot.startNavigateTo(CrawlerSettings.urlTest);
            webRobot.close();
            log.debug(String.format("Chrome browser can start."));
            return true;
        } catch (Exception e) {
            log.debug(String.format("Can't start chrome browser.", e));
        }
        return false;
    }

    /**
     * Validate all or download portable for run chrome browser
     *
     * @return true if success
     */
    public static Boolean validateAllOrDownload() {
        if (ValidateSystem.validate()) {
            if (ChromeDriverBinary.existOrInstall()) {
                if (ChromeBrowser.validateOrSwitch()) {
                    if (ChromeBrowser.validateVersion()) {
                        log.debug(String.format("Chrome browser is ready."));
                        return true;
                    } else {
                        CrawlerSettings.chromePortableMode = true;
                        ChromeBrowser.destroyProcesses();
                        ChromeDriverBinary.destroyProcesses();
                        if (forceDownloadPortable()){
                            if (ChromeBrowser.validateVersion()){
                                log.debug(String.format("Download new version and switch to chrome browser in portable mode."));
                                log.debug(String.format("Chrome browser is ready."));
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
            webRobot.startNavigateTo(CrawlerSettings.urlTest);
            String currentVersion = ((RemoteWebDriver) webRobot.getWebDriver()).getCapabilities().getVersion();
            DefaultArtifactVersion minVersion = new DefaultArtifactVersion(CrawlerSettings.chromeMinVersion);
            DefaultArtifactVersion version = new DefaultArtifactVersion(currentVersion);
            if (version.compareTo(minVersion) >= 0)
                validate = true;
            else
                log.error(String.format("This version chrome browser is old."));
            webRobot.close();
            log.debug(String.format("Current version chrome browser: %1$s.", currentVersion));
            return validate;
        } catch (Exception e) {
            log.error(String.format("Failed validate version chrome browser.", e));
        }
        return false;
    }

    /**
     * Check exist portable
     *
     * @return true if exist
     */
    public static Boolean existPortable() {
        if (new File(CrawlerSettings.chromePathBinaryPortableExe).canExecute()) {
            log.debug(String.format("Exist GoogleChromePortable.exe"));
            return ChromeBrowser.canStart();
        } else {
            log.error(String.format("Not exist GoogleChromePortable.exe"));
            return false;
        }
    }

    /**
     * Download portable
     *
     * @return true if success
     */
    public static Boolean downloadPortable() {
        // TODO: Zmienić serwer przeglądarki chrome z tymczasowego na stały
        log.debug(String.format("Download chrome browser portable."));
        if (Download.download(CrawlerSettings.chromePortableUrlResourceZip, PATH_TEMP_ZIP_PORTABLE)) {
            log.debug(String.format("Success download chrome portable."));
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
        log.debug(String.format("Unzip GoogleChromePortableBeta.zip"));
        try {
            File tmp = new File(PATH_TEMP_ZIP_PORTABLE);
            ZipFile zipFile = new ZipFile(tmp);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String dirDest = new File(CrawlerSettings.chromePathBinaryPortableExe).getParent();
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
            log.debug(String.format("Success unzip GoogleChromePortableBeta.zip"));
        } catch (Exception e) {
            log.error(String.format("Failed unzip GoogleChromePortableBeta.zip"), e);
        }
        return success;
    }

    /**
     * Validate chrome browser or switch mode to portable
     *
     * @return true if success
     */
    public static Boolean validateOrSwitch() {
        log.debug(String.format("Validate chrome browser."));
        Boolean validated = false;
        validated = CrawlerSettings.chromePortableMode && existOrDownloadPortable();
        if (validated == false) {
            validated = ChromeBrowser.canStart();
            if (validated == false) {
                CrawlerSettings.chromePortableMode = true;
                validated = ChromeBrowser.canStart();
                if (validated == false) {
                    existOrDownloadPortable();
                    validated = ChromeBrowser.canStart();
                }
                if (validated)
                    log.debug(String.format("Switch to exist chrome browser in portable mode."));
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
            log.debug(String.format("Exist browser chrome in portable mode."));
        } catch (Exception e) {
            log.error(String.format("Failed check exist or download portable chrome browser."), e);
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
            Boolean success = JProcesses.killProcess(Integer.parseInt(processInfo.getPid())).isSuccess();
            if (success)
                destroyed = true;
        }
        return destroyed;
    }
}
