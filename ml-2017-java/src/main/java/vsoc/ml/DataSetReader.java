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

        //We first need to parse this format. It's comma-delimited (CSV) format, so let's parse it using CSVRecordReader:
        RecordReader rr = new CSVRecordReader();
        JavaRDD<List<Writable>> parsedInputData = stringData.map(new StringToWritablesFunction(rr));

        log.info("Parsed input Data " + parsedInputData);


        //Now, let's execute the transforms we defined earlier:
        JavaRDD<List<Writable>> processedData = SparkTransformExecutor.execute(parsedInputData, tp);

        //For the sake of this example, let's collect the data locally and print it:
        JavaRDD<String> processedAsString = processedData.map(new WritablesToStringFunction(","));
        //processedAsString.saveAsTextFile("file://your/local/save/path/here");   //To save locally
        //processedAsString.saveAsTextFile("hdfs://your/hdfs/save/path/here");   //To save to hdfs

        List<String> processedCollected = processedAsString.collect();
        List<String> inputDataCollected = stringData.collect();


        System.out.println("\n\n---- Original Data ----");
        for(String s : inputDataCollected) System.out.println(s);

        System.out.println("\n\n---- Processed Data ----");
        for(String s : processedCollected) System.out.println(s);


        System.out.println("\n\nDONE");

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
