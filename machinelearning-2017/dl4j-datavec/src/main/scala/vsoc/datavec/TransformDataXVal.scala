package vsoc.datavec

import java.io.{File, FileOutputStream, OutputStream}
import java.util

import vsoc.common.Util
import org.apache.commons.io.FileUtils
import org.apache.spark.SparkConf
import org.apache.spark.api.java.{JavaRDD, JavaSparkContext}
import org.apache.spark.rdd.RDD
import org.datavec.api.records.reader.impl.csv.CSVRecordReader
import org.datavec.api.transform.TransformProcess
import org.datavec.api.transform.schema.Schema
import org.datavec.api.writable.Writable
import org.datavec.spark.transform.SparkTransformExecutor
import org.datavec.spark.transform.misc.{StringToWritablesFunction, WritablesToStringFunction}
import org.slf4j.LoggerFactory

/**
  * Read data from the datavec datasets and converts them to be used for training
  */
object TransformDataXVal extends App {

  import Util._

  private val log = LoggerFactory.getLogger("vsoc.ReadData")
  private val delim = ";"
  private val flagNames: Seq[String] = (0 until 42).map(i => "flag" + i)
  private val sizes = List("10", "100", "1000", "50000", "200000", "500000", "1000000")
  private val filenames = sizes.map{size => (f"random_pos_$size%s.csv", f"random_pos_$size%s_xval.csv")}


  use(new JavaSparkContext(sparkConfSimple)) { sparkContext =>
    for (filename <- filenames) {
      convert(filename, sparkContext)
    }
  }

  private def convert(filename: (String, String), sparkContext: JavaSparkContext): Unit = {

    val (inputFileName, outputFileName) = filename
    val inputFile: File = new File(Util.dirData, inputFileName)
    val outputFile: File = new File(Util.dirData, outputFileName)

    val data = readFile(inputFile, sparkContext)
    val transformed = transform(data)
    writeToFile(outputFile, transformed)
    log.info(s"transformed $inputFileName to $outputFileName")

  }

  private def readFile(file: File, sparkContext: JavaSparkContext): RDD[util.List[Writable]] = {
    val recordReader = new CSVRecordReader(0, delim)
    sparkContext
      .textFile(file.getAbsolutePath)
      .map(new StringToWritablesFunction(recordReader))
  }

  private def transform(inData: RDD[util.List[Writable]]): RDD[util.List[Writable]] = {

    def createPlayerposSchema: Schema = {
      var inBuilder = new Schema.Builder()
        .addColumnDouble("nr")
        .addColumnDouble("x")
        .addColumnDouble("y")
        .addColumnDouble("dir")
      for (flagName <- flagNames) {
        inBuilder = inBuilder.addColumnDouble(flagName)
      }
      inBuilder.build
    }

    val tp = new TransformProcess.Builder(createPlayerposSchema)
      .removeColumns("nr", "y", "dir")
      .reorderColumns(flagNames :+ "x" :_*)
      .build

    SparkTransformExecutor
      .execute(inData, tp)
  }

  private def writeToFile(outFile: File, lines: JavaRDD[util.List[Writable]]) = {
    val tmpDir = dirTmp

    lines
      .map(new WritablesToStringFunction(delim))
      .saveAsTextFile(tmpDir.getAbsolutePath)

    use(new FileOutputStream(outFile)) { (pw: OutputStream) =>
      for (file <- tmpDir.listFiles) {
        if (file.getName.startsWith("part")) {
          FileUtils.copyFile(file, pw)
        }
      }
    }
  }

  private def sparkConfSimple: SparkConf = {
    val conf = new SparkConf
    conf.setMaster("local[*]")
    conf.setAppName("DataVec Example")
    conf
  }

}

