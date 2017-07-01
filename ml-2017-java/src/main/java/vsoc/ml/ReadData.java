package vsoc.ml;

import java.io.File;
import java.io.IOException;

/**
 * Read e vsoc dataset
 */
public class ReadData {

    public static void main(String... arg) throws IOException {
        String baseDirName = "/Users/wwagner4/vsoc/data";
        String dataFileName = "random_pos_100.csv";

        File dataDir = new File(baseDirName);
        File file = new File(dataDir, dataFileName);

        System.out.println("Reading data from " + file.getCanonicalPath());


    }


}
