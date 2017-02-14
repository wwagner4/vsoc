package vsoc.nn.base;

public class TrivialTransferManager extends TransferManager {

	private static final long serialVersionUID = 1L;
	
    public Transfer getTransfer(int nSyn) {
        return new TrivialTransfer(1);
    }
}