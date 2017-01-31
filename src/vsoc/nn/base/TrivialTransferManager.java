package vsoc.nn.base;

public class TrivialTransferManager extends TransferManager {

    public Transfer getTransfer(int nSyn) {
        return new TrivialTransfer(1);
    }
}