package vsoc.genetic;

import java.io.Serializable;

public interface CrossableFactory extends Serializable {
    
    Crossable createNewCrossableWithRandomAttributes();

}
