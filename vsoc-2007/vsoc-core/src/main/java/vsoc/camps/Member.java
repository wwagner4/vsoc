package vsoc.camps;

import atan.model.Controller;

public interface Member<C extends Controller> {

	void setController(C cs);

	C getController();
	
	void reset();

}