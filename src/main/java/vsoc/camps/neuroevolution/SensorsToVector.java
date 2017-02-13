package vsoc.camps.neuroevolution;

import vsoc.behaviour.Sensors;


public interface SensorsToVector {
	
	/**
	 * Maps the sensor outputs to an array of double
	 */
	double[] apply(Sensors sensors);

}
