package vsoc.ml;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static vsoc.ml.MlUtil.dataDir;

/**
 * Read a trained net and use it with test data
 */
public class UseNN {

    private static final Logger log = LoggerFactory.getLogger(UseNN.class);

    private static Random ran = new Random();

    public static void main(String... args) throws IOException {
        File nnFile = new File(dataDir(), "nn.ser");
        log.info("NN File " + nnFile);

        MultiLayerNetwork nn = ModelSerializer.restoreMultiLayerNetwork(nnFile);

        log.info("Loaded NN " + nn);

        final DataHandler datasetReader = new DataHandler();

        final String dataFileNameTransformed = "random_pos_100_xval.csv";
        final File dataFileTransformed = new File(dataDir(), dataFileNameTransformed);
        DataSet dataSet = datasetReader.readPlayerposXDataSet(new File(dataDir(), dataFileNameTransformed), 100).next();

        INDArray features = dataSet.getFeatures();
        INDArray labels = dataSet.getLabels();

        List<INDArray> output = nn.feedForward(features, false);



        INDArray out = output.get(output.size() - 1);
        INDArray diff = labels.sub(out);
        log.info("out:          " + out);
        log.info("labels:       " + labels);
        log.info("diff:         " + diff);

        log.info("diff shape:   " + Arrays.toString(diff.shape()));

    }

}
