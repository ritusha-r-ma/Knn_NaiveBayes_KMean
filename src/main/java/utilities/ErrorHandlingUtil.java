package utilities;

/**
 * This class will be invoked in case of any error
 */
public class ErrorHandlingUtil {

    public ErrorHandlingUtil(String errorMessage) {
        errorOccurred(errorMessage);
    }

    /**
     * If any error comes up, show on console and exit
     * @param message
     */
    public static void errorOccurred(String message) {
        System.out.println(message);
        System.exit(0);
    }
}
