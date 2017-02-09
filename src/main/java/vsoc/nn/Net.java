/**
 * $Revision: 1.9 $ $Author: wwan $ $Date: 2005/09/30 15:40:10 $ 
 */

package vsoc.nn;

import java.io.Serializable;


/**
 * Interface for a neural net.
 */
public interface Net extends Serializable {

    void setInputValue(int index, short val);

    short getOutputValue(int index);

    void calculate();
    
}