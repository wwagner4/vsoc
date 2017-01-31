package vsoc.reports;

public class VsocReportException extends RuntimeException {

    public VsocReportException() {
        super();
    }

    public VsocReportException(String message) {
        super(message);
    }

    public VsocReportException(Throwable cause) {
        super(cause);
    }

    public VsocReportException(String message, Throwable cause) {
        super(message, cause);
    }

}
