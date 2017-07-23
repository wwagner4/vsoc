package playerpos

import java.io.{Closeable, File, FileOutputStream}
import java.util
import java.util.UUID

import common.Util
import org.apache.commons.io.FileUtils
import org.apache.spark.api.java.{JavaRDD, JavaSparkContext}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.datavec.api.records.reader.impl.csv.CSVRecordReader
import org.datavec.api.transform.TransformProcess
import org.datavec.api.transform.schema.Schema
import org.datavec.api.writable.Writable
import org.datavec.spark.transform.SparkTransformExecutor
import org.datavec.spark.transform.misc.{StringToWritablesFunction, WritablesToStringFunction}

/**
  * Read data from the playerpos datasets
  */
object ReadData extends App {

  val delim = ";"

  val w2s: WritablesToStringFunction = new WritablesToStringFunction(delim)

  def readData(): Unit = {

    val sparkCtx = new JavaSparkContext(sparkConfSimple)
    use(sparkCtx) { sparkContext =>
      val qualis = List("10", "100", "1000")
      for (quali <- qualis) {
        convert(quali, sparkContext)
      }
    }


  }

  private def convert(quali: String, sparkContext: JavaSparkContext): Unit = {
    val inputFileName: String = f"random_pos_$quali%s.csv"
    val outputFileName: String = f"random_pos_$quali%s_xval.csv"
    val inputFile: File = Util.dataFile(inputFileName)
    val outputFile: File = Util.dataFile(outputFileName)
    val listRDD: JavaRDD[util.List[Writable]] = transformToX(inputFile, sparkContext)
    //    val processedAsString: JavaRDD[String] = listRDD.{lw => ""}   (lw => w2s.call(lw))
    val processedAsString = listRDD.map(w2s)
    // Write processed data to CSV-File
    writeToFile(outputFile, processedAsString)
  }


  def sparkConfSimple: SparkConf = {
    val conf = new SparkConf
    conf.setMaster("local[*]")
    conf.setAppName("DataVec Example")
    conf
  }

  def use[T <: Closeable](closeable: T)(f: T => Unit): Unit = {
    try {
      f(closeable)
    }
    finally {
      closeable.close()
    }
  }

  private def transformToX(file: File, sparkContext: JavaSparkContext): JavaRDD[util.List[Writable]] = {
    val playerposSchema = createPlayerposSchema
    // Reorder the data to have 'x' at the end
    val reorder: Seq[String] = flagNamesFunc :+ "x"

    println("reorder:" + reorder)

    val tp = new TransformProcess.Builder(playerposSchema)
      .removeColumns("nr", "y", "dir")
      .reorderColumns(reorder: _*)
      .build
    val inputRdd: JavaRDD[String] = sparkContext.textFile(file.getAbsolutePath)
    //We first need to parse this format. It's comma-delimited (CSV) format, so let's parse it using CSVRecordReader:
    val recordReader = new CSVRecordReader(0, delim)
    val parsedInputData = inputRdd.map(new StringToWritablesFunction(recordReader))
    SparkTransformExecutor.execute(parsedInputData, tp)
  }

  private def writeToFile(outFile: File, lines: RDD[String]) = {
    val tmp = createTempDirectory
    lines.saveAsTextFile(tmp.getAbsolutePath)
    val pw = new FileOutputStream(outFile)
    try {
      val files = tmp.listFiles
      for (file <- files) {
        if (file.getName.startsWith("part")) {
          FileUtils.copyFile(file, pw)
        }
      }
    } finally {
      pw.close()
    }
  }

  private def createTempDirectory = {
    val tmpDirName = System.getProperty("java.io.tmpdir")
    val uuid = UUID.randomUUID
    new File(new File(tmpDirName), "ml" + uuid.getMostSignificantBits)
  }


  protected def createPlayerposSchema: Schema = {
    var inBuilder = new Schema.Builder()
      .addColumnDouble("nr")
      .addColumnDouble("x")
      .addColumnDouble("y")
      .addColumnDouble("dir")
    val flagNames = flagNamesFunc
    for (flagName <- flagNames) {
      inBuilder = inBuilder.addColumnDouble(flagName)
    }
    inBuilder.build
  }

  protected def flagNamesFunc: Seq[String] = {
    (0 until 42).map(i => "flag" + i)
  }

  readData()


}

