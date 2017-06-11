package playerpos

import org.nd4j.linalg.dataset.DataSet
import org.datavec.api.records.reader.impl.csv.CSVRecordReader
import org.datavec.api.split._
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator



/**
  * Read data from the playerpos datasets
  */
object ReadData {

  def readData(fileName: String): Unit = {

    val file = new java.io.File(fileName)
    val fileSplit = new FileSplit(file)

    val recordReader = new CSVRecordReader(0, ",")
    recordReader.initialize(fileSplit)
    val iterator: RecordReaderDataSetIterator = new RecordReaderDataSetIterator(recordReader, 4, 3, 1)

    while(iterator.hasNext) {
      val set: DataSet = iterator.next()
      println(set.toString)
    }

  }

}

object Tryout extends App {

  ReadData.readData("/Users/wwagner4/prj/vsoc/machinelearning-2017/create-data/src/main/doc/random-pos-001/data/random_pos_10.csv")
  println("ready")

}
