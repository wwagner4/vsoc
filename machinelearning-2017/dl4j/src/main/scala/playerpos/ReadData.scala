package playerpos

import java.io.{File, FileOutputStream}
import java.util

import common.Util
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
  * Read data from the playerpos datasets
  */
object ReadData extends App {

  import Util._

  private val log = LoggerFactory.getLogger("vsoc.ReadData")
  private val delim = ";"
  private val flagNames: Seq[String] = (0 until 42).map(i => "flag" + i)
  private val qualis = List("10", "100", "1000")

  use(new JavaSparkContext(sparkConfSimple)) { sparkContext =>
    for (quali <- qualis) {
      convert(quali, sparkContext)
    }
  }

  private def convert(quali: String, sparkContext: JavaSparkContext): Unit = {

    val inputFileName: String = f"random_pos_$quali%s.csv"
    val outputFileName: String = f"random_pos_$quali%s_xval01.csv"
    val inputFile: File = Util.dataFile(inputFileName)
    val outputFile: File = Util.dataFile(outputFileName)

    val transformed = transformToX(inputFile, sparkContext)
    writeToFile(outputFile, transformed)
    log.info(s"transformed $inputFileName to $outputFileName")
  }


  private def sparkConfSimple: SparkConf = {
    val conf = new SparkConf
    conf.setMaster("local[*]")
    conf.setAppName("DataVec Example")
    conf
  }

  private def transformToX(file: File, sparkContext: JavaSparkContext): RDD[String] = {
    val recordReader = new CSVRecordReader(0, delim)
    val inData: JavaRDD[util.List[Writable]] = sparkContext
      .textFile(file.getAbsolutePath)
      .map(new StringToWritablesFunction(recordReader))

    val schema = createPlayerposSchema
    val tp = new TransformProcess.Builder(schema)
      .removeColumns("nr", "y", "dir")
      .reorderColumns(flagNames :+ "x" :_*)
      .build

    SparkTransformExecutor
      .execute(inData, tp)
      .map(new WritablesToStringFunction(delim))
  }

  private def createPlayerposSchema: Schema = {
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

  private def writeToFile(outFile: File, lines: RDD[String]) = {
    val tmpDir = createTempDirectory
    lines.saveAsTextFile(tmpDir.getAbsolutePath)
    use(new FileOutputStream(outFile)) { pw =>
      for (file <- tmpDir.listFiles) {
        if (file.getName.startsWith("part")) {
          FileUtils.copyFile(file, pw)
        }
      }
    }
  }

}

