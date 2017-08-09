package pl.rebigo.libs.crawler.Exceptions;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 27.07.2017
 * Time: 09:27
 * Project name: crawler
 *
 * @author Karol Golec <karol.rebigo@gmail.com>
 */
public class CrawlerException extends Exception {

    /**
     * Constructor
     */
    public CrawlerException() {

    }

    /**
     * Constructor
     * @param message message exception
     */
    public CrawlerException(String message) {
        super (message);
    }


    /**
     * Constructor
     * @param cause cause exception
     */
    public CrawlerException(Throwable cause) {
        super (cause);
    }

    /**
     * Constructor
     * @param message message exception
     * @param cause cause exception
     */
    public CrawlerException(String message, Throwable cause) {
        super (message, cause);
    }
}
