package vsoc.ml;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Read a trained net and use it with test data
 */
public class UseNN {

    private static final Logger log = LoggerFactory.getLogger(UseNN.class);

    private static Random ran = new Random();

    public static void main(String... args) throws IOException {
        MlUtil u = new MlUtil();
        File dataDir = u.dataDir();
        File nnFile = new File(dataDir, "nn.ser");
        log.info("NN File " + nnFile);

        MultiLayerNetwork nn = ModelSerializer.restoreMultiLayerNetwork(nnFile);

        log.info("Loaded NN " + nn);

        INDArray input = createInput();
        List<INDArray> output = nn.feedForward(input, false);

        log.info("in: " + output.get(0));
        log.info("out: " + output.get(output.size() - 1));

    }

    private static INDArray createInput() {
        double[] vals = new double[42];
        for (int i = 0; i < 42; i++) {
            vals[i] = 950 + ran.nextInt(100);
        }
        return Nd4j.create(vals);
    }

}
