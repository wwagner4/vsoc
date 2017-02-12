/**
 * $Revision: 1.9 $ $Author: wwan $ $Date: 2005/09/30 15:40:10 $ 
 */

package vsoc.camps;

import java.io.Serializable;


/**
 * Interface for vector function.
 */
public interface VectorFunction extends Serializable {

    double[] apply(double[] in);
    
}