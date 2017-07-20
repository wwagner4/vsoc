package vsoc.ml.stat;

import org.apache.commons.math.stat.descriptive.DescriptiveStatistics;
import org.junit.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.cpu.nativecpu.NDArray;

import static org.junit.Assert.*;

public class TestCaseStat {

    @Test
    public void test() {
        INDArray diffs = createDiffs();
        Stat stat = new Stat(diffs);
        System.out.println(stat.toString());
    }

    private INDArray createDiffs() {
        double[][] data = {{0.1, 0.2, 0.5, 1.2, -0.6, -1.0}};
        return new NDArray(data).transpose();
    }

}
