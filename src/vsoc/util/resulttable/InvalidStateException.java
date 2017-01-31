package vsoc.util.resulttable;

public class InvalidStateException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
    public InvalidStateException() {
        super();
    }

    public InvalidStateException(String message) {
        super(message);
    }

    public InvalidStateException(Throwable cause) {
        super(cause);
    }

    public InvalidStateException(String message, Throwable cause) {
        super(message, cause);
    }

}
