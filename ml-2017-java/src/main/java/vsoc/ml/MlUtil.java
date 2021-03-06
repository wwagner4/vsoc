package vsoc.ml;

import org.apache.spark.SparkConf;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.io.File;

/**
 * Various utility functions
 */
public class MlUtil {

    /**
     *
     * @param dataSetIterator A dataset iterator to be displayed in stdout
     */
    public static void printDataSetIterator(DataSetIterator dataSetIterator) {
        int cnt = 0;
        while (dataSetIterator.hasNext()) {
            DataSet dataSet = dataSetIterator.next();
            System.out.println("--- DataSet --- " + cnt + " ---\n" + dataSet.toString());
            cnt++;
        }
        System.out.println("-- Finished ---");
    }

    /**
     * @return The data-directory. Creates the directory if it does not exist
     */
    public static File dataDir() {
        String homeDirName = System.getProperty("user.home");
        File homeDir = new File(homeDirName);
        File vsocDir = subdir(homeDir, "vsoc");
        return subdir(vsocDir, "data");
    }

    private static File subdir(File parentDir, String name) {
        File dataDir = new File(parentDir, name);
        if (!dataDir.exists()) {
            boolean ok = dataDir.mkdir();
            if (!ok) {
                throw new IllegalStateException("Could not create directory. " + dataDir);
            }
        }
        return dataDir;
    }

    public static String delim() {
        return ";";
    }

    public static String f(String format, Object... params) {
        return String.format(format, params);
    }

    public static SparkConf sparkConfSimple() {
        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("DataVec Example");
        return conf;
    }


}
