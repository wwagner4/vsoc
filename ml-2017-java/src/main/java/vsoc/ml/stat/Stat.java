package vsoc.ml.stat;

import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Calculates some statistics about INDArrays
 */
public class Stat {

    private INDArray data;

    private Optional<INDArray> mean = Optional.empty();

    private Optional<INDArray> stdev = Optional.empty();

    public Stat(INDArray data) {
        this.data = data;
    }

    public List<Pair<String, Number>> stat() {
        List<Pair<String, Number>> re = new ArrayList<>();
        re.add(new Pair("mean", mean()));
        re.add(new Pair("stdev", stdev()));
        return re;
    }

    private Number stdev() {
        return this.stdev.orElse(calcStdev());
    }

    private Number calcStdev() {
        if (data.shape().length == 2 && data.shape()[1] == 1) {
            return data.sumNumber();
        } else {
            throw new IllegalStateException("Currently only NDArrays with shape [n, 1] are supported. " + Arrays.toString(data.shape()));
        }
    }

    private Number mean() {

    }

}
