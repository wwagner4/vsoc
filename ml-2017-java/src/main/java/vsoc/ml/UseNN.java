package vsoc.ml;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Read a trained net and use it with testdata
 */
public class UseNN {

    private static final Logger log = LoggerFactory.getLogger(UseNN.class);

    public static void main(String... args) throws IOException {
        MlUtil u = new MlUtil();
        File dataDir = u.dataDir();
        File nnFile = new File(dataDir, "nn.ser");
        log.info("NN File " + nnFile);

        MultiLayerNetwork nn = ModelSerializer.restoreMultiLayerNetwork(nnFile);

        log.info("Loaded NN " + nn);
    }

}
