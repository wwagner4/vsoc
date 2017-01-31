package vsoc;

public class VsocNotYetImplementedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
    public VsocNotYetImplementedException() {
        super();
    }

    public VsocNotYetImplementedException(String message) {
        super(message);
    }

    public VsocNotYetImplementedException(Throwable cause) {
        super(cause);
    }

    public VsocNotYetImplementedException(String message, Throwable cause) {
        super(message, cause);
    }

}
