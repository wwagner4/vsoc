package vsoc.ml;

import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

/**
 * Trains a NN to learn the values of the playerpos datasets
 */
public class Training {

    public static void main(String... args) {

        String baseDirName = "/Users/wwagner4/vsoc/data";
        String dataFileName = "random_pos_100.csv";

        MlUtil util = new MlUtil();
        PlayerposReader datasetReader = new PlayerposReader();

        DataSetIterator dataSetIterator = datasetReader.readPlayerposXDataSet(baseDirName, dataFileName);
        util.printDataSetIterator(dataSetIterator);
    }

}
