package vsoc.nn.base;


/**
* Implements the transfer function for neurons. The values are calculated at
* initialization time and stored in an array. If a value has to be calculated
* it is taken from this array.
*/
public class TrivialTransfer extends Transfer {

	private static final long serialVersionUID = 1L;
	
    public TrivialTransfer(int stretch) {
        super(stretch);
    }

    public short getValue(int in) {
        if (in >= Params.maxValue)
            return Params.maxValue - 1;
        else if (in <= -Params.maxValue)
            return - (Params.maxValue - 1);
        else
            return (short) in;
    }

}
