package vsoc.ml;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Trains a NN to learn the values of the playerpos datasets
 */
public class Training {

    private static final Logger log = LoggerFactory.getLogger(Training.class);

    public static void main(String... args) {

        final String baseDirName = "/Users/wwagner4/vsoc/data";
        final String dataFileNameTransformed = "random_pos_200000_xval.csv";

        MlUtil util = new MlUtil();
        PlayerposReader datasetReader = new PlayerposReader();
        Training training = new Training();

        log.info("Start read data");
        File dataDir = new File(baseDirName);
        DataSetIterator dataSetIterator = datasetReader.readPlayerposXDataSet(new File(dataDir, dataFileNameTransformed), 5000);
        log.info("Start training");
        training.train(dataSetIterator);
        log.info("Finished training");
    }

    private void train(DataSetIterator dataSetIterator) {
        MultiLayerConfiguration nnConf = nnConfiguration();
        // System.out.printf("nn conf: %s%n", nnConfiguration);

        //Create the network
        final MultiLayerNetwork net = new MultiLayerNetwork(nnConf);
        net.init();
        net.setListeners(new ScoreIterationListener(1));

        net.fit(dataSetIterator);
    }

    /**
     * Returns the network configuration, 2 hidden DenseLayers
     */
    private static MultiLayerConfiguration nnConfiguration() {
        final int numHiddenNodes = 50;
        final double learningRate = 0.005;
        final int iterations = 1;
        return new NeuralNetConfiguration.Builder()
                .seed(29847298437L)
                .iterations(iterations)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .learningRate(learningRate)
                .weightInit(WeightInit.XAVIER)
                .updater(Updater.NESTEROVS)
                .momentum(0.9)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(42)
                        .nOut(numHiddenNodes)
                        .activation(Activation.TANH)
                        .build())
                .layer(1, new DenseLayer.Builder()
                        .nIn(numHiddenNodes)
                        .nOut(numHiddenNodes)
                        .activation(Activation.TANH)
                        .build()
                )
                .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
                        .activation(Activation.IDENTITY)
                        .nIn(numHiddenNodes)
                        .nOut(1)
                        .build()
                )
                .pretrain(false)
                .backprop(true)
                .build();
    }
}
