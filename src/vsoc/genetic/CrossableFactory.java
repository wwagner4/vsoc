package vsoc.genetic;

import java.io.Serializable;

public interface CrossableFactory<T extends Crossable<?>> extends Serializable {
    
    T createNewCrossableWithRandomAttributes();

}
