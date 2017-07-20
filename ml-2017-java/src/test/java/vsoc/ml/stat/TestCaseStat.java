package vsoc.ml.stat;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;

import static org.junit.Assert.*;

public class TestCaseStat {

    @Test
    public void test() {
        INDArray diffs = createDiffs();
        Stat stat = new Stat(diffs);
        System.out.println(stat.toString());
    }

    private INDArray createDiffs() {
        throw new IllegalStateException("NOT YET IMPLEMENTED");
    }

}
