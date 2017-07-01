package vsoc.ml;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.transform.TransformProcess;
import org.datavec.api.transform.schema.Schema;
import org.datavec.api.writable.Writable;
import org.datavec.spark.transform.SparkTransformExecutor;
import org.datavec.spark.transform.misc.StringToWritablesFunction;
import org.datavec.spark.transform.misc.WritablesToStringFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * Read e vsoc dataset
 */
public class DataSetReader {

    private static final Logger log = LoggerFactory.getLogger(DataSetReader.class);

    public static void main(String... arg) {
        String baseDirName = "/Users/wwagner4/vsoc/data";
        String dataFileName = "random_pos_100.csv";

        new DataSetReader().readPlayerposXDataSet(baseDirName, dataFileName);
    }

    public void readPlayerposXDataSet(String baseDirName, String dataFileName) {

        File dataDir = new File(baseDirName);
        File file = new File(dataDir, dataFileName);

        log.info("Reading data from " + file);

        Schema playerposSchema = createPlayerposSchema();

        log.debug("Playerpos Schema:\n");
        log.debug("" + playerposSchema);

        TransformProcess tp = new TransformProcess.Builder(playerposSchema)
                .removeColumns("nr", "y", "dir")
                .build();

        Schema playerposXSchema = tp.getFinalSchema();

        log.debug("Playerpos Schema X-Values:\n");
        log.debug("" + playerposXSchema);

        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("DataVec Example");

        JavaSparkContext sc = new JavaSparkContext(conf);

        log.info("Created Spark Context: " + sc);

        JavaRDD<String> stringData = sc.textFile(file.getAbsolutePath());
        List<String> inputDataCollected = stringData.collect();

        System.out.println("\n\n---- Original Data ----");
        for(String s : inputDataCollected) System.out.println(s);

        //We first need to parse this format. It's comma-delimited (CSV) format, so let's parse it using CSVRecordReader:
        RecordReader rr = new CSVRecordReader();
        JavaRDD<List<Writable>> parsedInputData = stringData.map(new StringToWritablesFunction(rr));

        JavaRDD<List<Writable>> processedData = SparkTransformExecutor.execute(parsedInputData, tp);

        // Write processed data to stdout
        JavaRDD<String> processedAsString = processedData
                .map(new WritablesToStringFunction(","));
        List<String> processedCollected = processedAsString.collect();

        System.out.println("\n\n---- Processed Data ----");
        for(String s : processedCollected) System.out.println(s);


        // Write processed data to txt-file
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        UUID uuid = UUID.randomUUID();
        File outTxt = new File(tmpDir, "playerpos-" + uuid);
        processedAsString.saveAsTextFile("file://" + outTxt);
        log.info("Wrote processed data to txt-file: " + outTxt);

    }

    protected Schema createPlayerposSchema() {
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
