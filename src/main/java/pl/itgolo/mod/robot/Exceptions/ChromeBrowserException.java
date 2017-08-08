package pl.itgolo.mod.robot.Exceptions;

/**
 * Created by ITGolo on 25.02.2017.
 */
public class ChromeBrowserException extends Exception {

    /**
     * Constructor
     */
    public ChromeBrowserException() {

    }

    /**
     * Constructor
     * @param message message exception
     */
    public ChromeBrowserException(String message) {
        super (message);
    }


    /**
     * Constructor
     * @param cause cause exception
     */
    public ChromeBrowserException(Throwable cause) {
        super (cause);
    }

    /**
     * Constructor
     * @param message message exception
     * @param cause cause exception
     */
    public ChromeBrowserException(String message, Throwable cause) {
        super (message, cause);
    }
}
