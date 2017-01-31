package vsoc;

/**
 * Indicates a programming or configuration problem.
 *
 */
public class VsocInvalidDataException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;
	
    public VsocInvalidDataException() {
        super();
    }

    public VsocInvalidDataException(String message) {
        super(message);
    }

    public VsocInvalidDataException(Throwable cause) {
        super(cause);
    }

    public VsocInvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }

}
