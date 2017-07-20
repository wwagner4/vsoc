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

    private DescriptiveStatistics data;

    public Stat(INDArray data) {
        int[] shape = data.shape();

        log.info("shape of data " + Arrays.toString(shape));


    }


}
