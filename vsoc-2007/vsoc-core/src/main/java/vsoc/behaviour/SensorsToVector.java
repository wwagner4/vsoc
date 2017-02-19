package vsoc.behaviour;

public interface SensorsToVector {
	
	/**
	 * Maps the sensor outputs to an array of double
	 */
	double[] apply(Sensors sensors);

}
