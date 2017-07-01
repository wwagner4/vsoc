package vsoc.ml;

import org.datavec.api.transform.schema.Schema;

import java.io.File;

/**
 * Read e vsoc dataset
 */
public class DataSetReader {

    public static void main(String... arg) {
        String baseDirName = "/Users/wwagner4/vsoc/data";
        String dataFileName = "random_pos_100.csv";

        new DataSetReader().readDataSet(baseDirName, dataFileName);
    }

    public void readDataSet(String baseDirName, String dataFileName) {

        File dataDir = new File(baseDirName);
        File file = new File(dataDir, dataFileName);

        System.out.println("Reading data from " + file);

        Schema schema = createPlayerposSchema();

        System.out.println("Input data schema details:");
        System.out.println(schema);

    }

    private Schema createPlayerposSchema() {
        Schema.Builder inBuilder = new Schema.Builder()
                .addColumnDouble("nr")
                .addColumnDouble("x")
                .addColumnDouble("y")
                .addColumnDouble("dir");
        for (int i = 0; i < 42; i++) {
            inBuilder = inBuilder.addColumnDouble("flag" + i);
        }
        return inBuilder.build();
    }


}
