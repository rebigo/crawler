package pl.itgolo.mod.robot;

import org.apache.commons.io.IOUtils;
import org.jutils.jprocesses.JProcesses;
import org.jutils.jprocesses.model.ProcessInfo;
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
public class ChromeDriverBinary {

    /* @var temp path zip with chrome driver */
    private static final String PATH_ZIP = "temp/chromedriver_win32.zip";

    /**
     * Check exist or install
     * @return true if exist
     */
    public static Boolean existOrInstall(){
        if (new File(GlobalSettings.pathChromeDriver).canExecute()) {
            System.out.println("DEBUG: Exist chrome driver.");
            return true;
        } else {
            System.out.println("WARNING: Not exist chrome driver.");
            return install();
        }
    }

    /**
     * Install chrome driver
     * @return true if success
     */
    public static Boolean install(){
        if (download() && unzip()) {
            System.out.println("DEBUG: Success install chrome driver.");
            return true;
        } else {
            System.out.println("ERROR: Not installed chrome driver.");
            return false;
        }
    }

    /**
     *
     * @return
     */
    public static Boolean download() {
        if (Download.download(GlobalSettings.urlChromeDriver, PATH_ZIP)) {
            System.out.println("DEBUG: Success download chrome driver.");
            return true;
        } else {
            System.out.println("ERROR: Failed download chrome driver.");
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
            System.out.println("DEBUG: Unzip chrome driver.");
            File tmp = new File(PATH_ZIP);
            ZipFile zipFile = new ZipFile(tmp);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String dirDestination = new File(GlobalSettings.pathChromeDriver).getParent();
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
            System.out.println("DEBUG: Success unzip chrome driver.");
            success = true;
        } catch (IOException e) {
            System.out.println("ERROR: Failed unzip chrome driver.");
        }
        return success;
    }

    public static Boolean destroyProcesses(){
        List<ProcessInfo> processesList = JProcesses.getProcessList("chromedriver.exe");
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
