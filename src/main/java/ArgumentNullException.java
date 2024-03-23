
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.rmi.server.LoaderHandler;

public class ArgumentNullException extends Exception {
    /**
     * @description The error set when an ArgumentNullException is thrown.
     */
    public static final String ARGUMENT_NULL_EXCEPTION_ERROR = "Unexpected null argument: %s.";

    /**
     * @description Constructor of the ArgumentNullException class that accepts in a string parameter containing the error message.
     * @param errorMessage The error message to apply to this exception.
     */
    public ArgumentNullException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * @description Constructor of the ArgumentNullException class that accepts no parameters and leaves the message blank.
     */
    public ArgumentNullException() {
        super();
    }

    /**
     * @description Tests whether the object passed in is null, and throws an ArgumentNullException if it is.
     * @param o The object to test.
     * @param param The string parameter name of the object being tested.
     * @throws ArgumentNullException if o is null.
     */
    public static void throwIfNull(Object o, String param) throws ArgumentNullException {
        if (o == null) {
            // Get the stack trace from the current thread - we know that thrown exceptions aren't going to
            // cross threads, so we can reliably get it from there
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            // Dig out the specific element containing the class name and method.
            StackTraceElement element = stackTrace[2];

            // Auto-log when this exception is thrown
            // LoggerFactory.getLogger(element.getClass()).error("An exception was thrown by method: " + element.getMethodName());

            Logger logger = LogManager.getLogger(element.getClass());
            logger.error("An exception was thrown by method: " + element.getMethodName());

            throw new ArgumentNullException(String.format(ARGUMENT_NULL_EXCEPTION_ERROR, param));
        }
    }
}
