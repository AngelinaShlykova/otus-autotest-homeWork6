package exceptions;

public class BrowserTypeNotSupportedException extends RuntimeException {

    public BrowserTypeNotSupportedException(String browserType) {
        super(String.format("Browser '%s' is not supported", browserType));
    }
}
