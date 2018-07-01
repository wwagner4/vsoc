package vsoc.behaviour;

import java.io.Serializable;


/**
 * Interface for vector function.
 */
public interface VectorFunction extends Serializable {

    double[] apply(double[] in);
    
}