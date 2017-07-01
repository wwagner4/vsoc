package vsoc.ml;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.collection.CollectionRecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.transform.TransformProcess;
import org.datavec.api.transform.schema.Schema;
import org.datavec.api.writable.Writable;
import org.datavec.spark.transform.SparkTransformExecutor;
import org.datavec.spark.transform.misc.StringToWritablesFunction;
import org.datavec.spark.transform.misc.WritablesToStringFunction;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.nd4j.linalg.dataset.DataSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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

        // Reorder the data to have 'x' at the end
        Collection<String> reorder = flagNames();
        reorder.add("x");

        TransformProcess tp = new TransformProcess.Builder(playerposSchema)
                .removeColumns("nr", "y", "dir")
                .reorderColumns(reorder.toArray(new String[reorder.size()]))
                .build();

        SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("DataVec Example");

        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> inputRdd = sc.textFile(file.getAbsolutePath());

        //We first need to parse this format. It's comma-delimited (CSV) format, so let's parse it using CSVRecordReader:
        RecordReader recordReader = new CSVRecordReader(0, ",");
        JavaRDD<List<Writable>> parsedInputData = inputRdd.map(new StringToWritablesFunction(recordReader));
        JavaRDD<List<Writable>> processedData = SparkTransformExecutor.execute(parsedInputData, tp);

        writeToStdout(processedData);

        CollectionRecordReader reader = new CollectionRecordReader(processedData.collect());

        RecordReaderDataSetIterator dataSetIterator = new RecordReaderDataSetIterator(reader,10, 42, 42, true);
        log.info("iterator: " + dataSetIterator);
        while (dataSetIterator.hasNext()) {
            DataSet dataSet = dataSetIterator.next();
            log.info("META:" + dataSet.toString());
        }
        log.info("showed iterations");
    }

    void writeToStdout(JavaRDD<List<Writable>> processedData) {
        JavaRDD<String> processedAsString = processedData.map(new WritablesToStringFunction(","));
        writeStringToStdout(processedAsString);
    }

    void writeStringToStdout(JavaRDD<String> stringRdd) {
        // Write processed data to stdout
        List<String> outputLines = stringRdd.collect();
        for (String s : outputLines) System.out.println(s);
    }

    void writeToFile(JavaRDD<List<Writable>> processedData) {
        JavaRDD<String> stringRdd = processedData.map(new WritablesToStringFunction(","));
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        UUID uuid = UUID.randomUUID();
        File outDir = new File(tmpDir, "playerpos-" + uuid);
        stringRdd.saveAsTextFile("file://" + outDir);
        log.info("Wrote processed data to txt-file: " + outDir);
    }

    protected Schema createPlayerposSchema() {
        Schema.Builder inBuilder = new Schema.Builder()
                .addColumnDouble("nr")
                .addColumnDouble("x")
                .addColumnDouble("y")
                .addColumnDouble("dir");
        Collection<String> flagNames = flagNames();
        for (String flagName : flagNames) {
            inBuilder = inBuilder.addColumnDouble(flagName);
        }
        return inBuilder.build();
    }

    protected Collection<String> flagNames() {
        ArrayList<String> re = new ArrayList<>();
        for (int i = 0; i < 42; i++) {
            re.add("flag" + i);
        }
        return re;
    }


}
