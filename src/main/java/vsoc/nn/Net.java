/**
 * $Revision: 1.9 $ $Author: wwan $ $Date: 2005/09/30 15:40:10 $ 
 */

package vsoc.nn;

import java.io.Serializable;

import vsoc.genetic.Crossable;

/**
 * Interface for a neuronal net.
 */
public interface Net extends Crossable<Net>, Serializable {

    public abstract void setInputValue(int index, short val);

    public abstract short getOutputValue(int index);

    public abstract void calculate();

    public abstract double distance(Net n);
    
}