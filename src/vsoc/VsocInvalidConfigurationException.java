package vsoc;

/**
 * Wrong configuration.
 */
public class VsocInvalidConfigurationException extends RuntimeException {

    public VsocInvalidConfigurationException() {
        super();
    }

    public VsocInvalidConfigurationException(String msg) {
        super(msg);
    }

    public VsocInvalidConfigurationException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public VsocInvalidConfigurationException(Throwable ex) {
        super(ex);
    }

}
