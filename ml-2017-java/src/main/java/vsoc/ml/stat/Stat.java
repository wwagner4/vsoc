package vsoc.ml.stat;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Calculates some statistics about INDArrays
 */
public class Stat {

    private static final Logger log = LoggerFactory.getLogger(Stat.class);

    private DescriptiveStatistics statistics;

    public Stat(INDArray data) {
        int[] shape = data.shape();
        log.debug("shape of data " + Arrays.toString(shape));
        if (shape.length != 2) {
            throw new IllegalArgumentException("Dimension of INDArray not 2. " + shape.length);
        }
        if (shape[1] != 1) {
            throw new IllegalArgumentException("INDArray not a vector. " + shape.length);
        }
        statistics = new DescriptiveStatistics(shape[0]);
        for (int i = 0; i < shape[0]; i++) {
            statistics.addValue(data.getDouble(i, 0));
        }
    }

    @Override
    public String toString() {
        return statistics.toString();
    }
}
