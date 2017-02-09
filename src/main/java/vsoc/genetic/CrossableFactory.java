package vsoc.genetic;

import java.io.Serializable;

public interface CrossableFactory<T> extends Serializable {
    
    T createNewCrossableWithRandomAttributes();

}
