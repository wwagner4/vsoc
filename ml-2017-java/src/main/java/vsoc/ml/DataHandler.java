package vsoc.ml;

import org.apache.commons.io.FileUtils;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.collection.CollectionRecordReader;
import org.datavec.api.records.reader.impl.csv.CSVRecordReader;
import org.datavec.api.split.FileSplit;
import org.datavec.api.transform.TransformProcess;
import org.datavec.api.transform.schema.Schema;
import org.datavec.api.writable.Writable;
import org.datavec.spark.transform.SparkTransformExecutor;
import org.datavec.spark.transform.misc.StringToWritablesFunction;
import org.datavec.spark.transform.misc.WritablesToStringFunction;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static vsoc.ml.MlUtil.*;

/**
 * Read a vsoc dataset and converts it in a form
 * that can be used for training NNs
 */
public class DataHandler {

    private static final Logger log = LoggerFactory.getLogger(DataHandler.class);

    public static void main(String... arg) {

        try (JavaSparkContext sc = new JavaSparkContext(sparkConfSimple())) {
            DataHandler dh = new DataHandler();
            List<String> qualis = Arrays.asList("10", "100", "1000", "50000", "200000");
            qualis.forEach(q -> dh.convert(q, sc));
        }
    }

    private void convert(String quali, JavaSparkContext sparkContext) {

        String inputFileName = f("random_pos_%s.csv", quali);
        String outputFileName = f("random_pos_%s_xval.csv", quali);


        File dataDir = dataDir();
        File inputFile = new File(dataDir, inputFileName);
        File outputFile = new File(dataDir, outputFileName);
        JavaRDD<List<Writable>> listJavaRDD = transformToX(inputFile, sparkContext);

        JavaRDD<String> processedAsString = listJavaRDD
                .map(new WritablesToStringFunction(delim()));

        // Write processed data to CSV-File
        writeToFile(outputFile, processedAsString);
        log.info("wrote transformed data to: " + outputFile);

    }

    private void writeToFile(File outFile, JavaRDD<String> lines) {
        File tmp = createTempDirectory();
        lines.saveAsTextFile(tmp.getAbsolutePath());
        log.info("Collecting in temporary directory: " + tmp.getAbsolutePath());

        try {
            try (final FileOutputStream pw = new FileOutputStream(outFile)) {
                File[] files = tmp.listFiles();
                if (files == null || files.length == 0) {
                    throw new IllegalStateException("No data found");
                }
                for (File file : files) {
                    if (file.getName().startsWith("part")) {
                        log.info("Reading part: " + file);
                        FileUtils.copyFile(file, pw);
                    }
                }
            }
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private File createTempDirectory() {
        String tmpDirName = System.getProperty("java.io.tmpdir");
        UUID uuid = UUID.randomUUID();
        return new File(new File(tmpDirName), "ml" + uuid.getMostSignificantBits());
    }

    private JavaRDD<List<Writable>> transformToX(File file, JavaSparkContext sparkContext) {
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


        JavaRDD<String> inputRdd = sparkContext.textFile(file.getAbsolutePath());

        //We first need to parse this format. It's comma-delimited (CSV) format, so let's parse it using CSVRecordReader:
        RecordReader recordReader = new CSVRecordReader(0, delim());
        JavaRDD<List<Writable>> parsedInputData = inputRdd.map(new StringToWritablesFunction(recordReader));
        return SparkTransformExecutor.execute(parsedInputData, tp);
    }

    public DataSetIterator readPlayerposDataSetWithTransform(File inFile, int batchSize, JavaSparkContext sc) {
        JavaRDD<List<Writable>> processedData = transformToX(inFile, sc);
        CollectionRecordReader reader = new CollectionRecordReader(processedData.collect());
        return new RecordReaderDataSetIterator(reader, batchSize, 42, 42, true);
    }

    public DataSetIterator readPlayerposXDataSet(File inFile, int batchSize) {
        try {
            RecordReader recordReader = new CSVRecordReader(0, delim());
            recordReader.initialize(new FileSplit(inFile));
            return new RecordReaderDataSetIterator(recordReader, batchSize, 42, 42, true);
        } catch (IOException | InterruptedException e) {
            throw new IllegalStateException("Error in 'readPlayerposXDataSet'. " + e.getMessage(), e);
        }
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
