package vsoc.ml;

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

        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("DataVec Example");

        JavaSparkContext sc = new JavaSparkContext(conf);

        log.info("Created Spark Context: " + sc);

        JavaRDD<String> inputRdd = sc.textFile(file.getAbsolutePath());

        System.out.println("\n\n---- Original Data ----");
        List<String> inputLines = inputRdd.collect();
        for(String s : inputLines) System.out.println(s);

        //We first need to parse this format. It's comma-delimited (CSV) format, so let's parse it using CSVRecordReader:
        RecordReader recordReader = new CSVRecordReader(0, ",");
        JavaRDD<List<Writable>> parsedInputData = inputRdd.map(new StringToWritablesFunction(recordReader));
        JavaRDD<List<Writable>> processedData = SparkTransformExecutor.execute(parsedInputData, tp);

        // Prepare processed data for output
        JavaRDD<String> processedAsString = processedData.map(new WritablesToStringFunction(","));

        // Write processed data to stdout
        System.out.println("\n\n---- Processed Data ----");
        List<String> outputLines = processedAsString.collect();
        for(String s : outputLines) System.out.println(s);

        // Write processed data to txt-file
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        UUID uuid = UUID.randomUUID();
        File outDir = new File(tmpDir, "playerpos-" + uuid);
        processedAsString.saveAsTextFile("file://" + outDir);
        log.info("Wrote processed data to txt-file: " + outDir);

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
