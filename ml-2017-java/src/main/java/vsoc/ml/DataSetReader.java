package vsoc.ml;

import org.datavec.api.transform.schema.Schema;

import java.io.File;
import java.io.IOException;

/**
 * Read e vsoc dataset
 */
public class DataSetReader {

    public static void main(String... arg) throws IOException {
        String baseDirName = "/Users/wwagner4/vsoc/data";
        String dataFileName = "random_pos_100.csv";

        File dataDir = new File(baseDirName);
        File file = new File(dataDir, dataFileName);

        System.out.println("Reading data from " + file.getCanonicalPath());

        Schema.Builder inBuilder = new Schema.Builder()
                .addColumnDouble("nr")
                .addColumnDouble("x")
                .addColumnDouble("y")
                .addColumnDouble("dir");
        for (int i = 0; i < 42; i++) {
            inBuilder = inBuilder.addColumnDouble("flag" + i);
        }

        Schema inputDataSchema = inBuilder.build();

        System.out.println("Input data schema details:");
        System.out.println(inputDataSchema);

        System.out.println("\n\nOther information obtainable from schema:");
        System.out.println("Number of columns: " + inputDataSchema.numColumns());
        System.out.println("Column names: " + inputDataSchema.getColumnNames());
        System.out.println("Column types: " + inputDataSchema.getColumnTypes());


    }


}
