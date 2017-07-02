package vsoc.ml;

import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

/**
 * Various utility functions
 */
public class MlUtil {

    public void printDataSetIterator(DataSetIterator dataSetIterator) {
        int cnt = 0;
        while (dataSetIterator.hasNext()) {
            DataSet dataSet = dataSetIterator.next();
            System.out.println("--- DataSet --- " + cnt + " ---\n" + dataSet.toString());
            cnt++;
        }
        System.out.println("-- Finished ---");
    }


}
