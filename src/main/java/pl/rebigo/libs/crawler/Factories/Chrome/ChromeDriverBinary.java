package pl.rebigo.libs.crawler.Factories.Chrome;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jutils.jprocesses.JProcesses;
import org.jutils.jprocesses.model.ProcessInfo;
import pl.rebigo.libs.crawler.CrawlerSettings;
import pl.rebigo.libs.download.Download;

import java.io.*;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 27.07.2017
 * Time: 10:01
 * Project name: robot
 *
 * @author Karol Golec <karolgolec@itgolo.pl>
 */
@Slf4j
public class ChromeDriverBinary {

    /* @var temp path zip with chrome driver */
    private static final String PATH_ZIP = "temp/chromedriver_win32.zip";

    /**
     * Check exist or install
     * @return true if exist
     */
    public static Boolean existOrInstall(){
        if (new File(CrawlerSettings.chromePathDriver).canExecute()) {
            log.debug(String.format("Exist chrome driver."));
            return true;
        } else {
            log.error(String.format("Not exist chrome driver."));
            return install();
        }
    }

    /**
     * Install chrome driver
     * @return true if success
     */
    public static Boolean install(){
        if (download() && unzip()) {
            log.debug(String.format("Success install chrome driver."));
            return true;
        } else {
            log.error(String.format("Not installed chrome driver."));
            return false;
        }
    }

    /**
     *
     * @return
     */
    public static Boolean download() {
        if (Download.download(CrawlerSettings.chromeDriverUrlResourceZipWithExe, PATH_ZIP)) {
            System.out.println("DEBUG: ");
            log.debug(String.format("Success download chrome driver."));
            return true;
        } else {
            log.error(String.format("Failed download chrome driver."));
        }
        return false;
    }

    /**
     * Unzip chrome driver
     * @return true if success
     */
    public static Boolean unzip(){
        Boolean success = false;
        try {
            log.debug(String.format("Unzip chrome driver."));
            File tmp = new File(PATH_ZIP);
            ZipFile zipFile = new ZipFile(tmp);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String dirDestination = new File(CrawlerSettings.chromePathDriver).getParent();
                File entryDestination = new File(dirDestination, entry.getName());
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
            log.debug(String.format("Success unzip chrome driver."));
            success = true;
        } catch (IOException e) {
            log.error(String.format("Failed unzip chrome driver."), e);
        }
        return success;
    }

    public static Boolean destroyProcesses(){
        List<ProcessInfo> processesList = JProcesses.getProcessList("chromedriver.exe");
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
