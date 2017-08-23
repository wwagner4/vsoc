package vsoc.training.genetic

import java.util

import org.datavec.api.records.reader.impl.csv.CSVRecordReader
import org.datavec.api.split.FileSplit
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator
import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.{MultiLayerConfiguration, NeuralNetConfiguration, Updater}
import org.deeplearning4j.nn.conf.layers.{DenseLayer, OutputLayer}
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import org.nd4j.linalg.lossfunctions.LossFunctions
import vsoc.common.Dat
import vsoc.datavec.playerpos.CreateData

object GeneticTryout extends App {

  val delim = ";"


  val nnConf = nnConfiguration()
  val nn: MultiLayerNetwork = new MultiLayerNetwork(nnConf)
  nn.init()
  println("after init")
  val data = readPlayerposXDataSet(Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_A, Dat.Size_1000), 0.1)
  println("after readData")
  printParam(nn)
  nn.fit(data)
  println("after fit")
  printParam(nn)

  private def printParam(_nn: MultiLayerNetwork) = {
    for ((l, i) <- _nn.getLayers.zipWithIndex) {
      println(s"--- layer $i ---")
      println(s"l $i W: ${util.Arrays.toString(l.getParam("W").shape())}")
      println(s"l $i W: ${l.getParam("W")}")
      println(s"l $i b: ${util.Arrays.toString(l.getParam("b").shape())}")
      println(s"l $i b: ${l.getParam("b")}")
    }
  }

  def readPlayerposXDataSet(desc: Dat.DataDesc, batchSizeRelative: Double): DataSetIterator = {
    val infile = CreateData.createDataFile(desc)
    val recordReader = new CSVRecordReader(0, delim)
    recordReader.initialize(new FileSplit(infile))
    new RecordReaderDataSetIterator(recordReader, (desc.size.size * batchSizeRelative).toInt, 42, 42, true)
  }

  private def nnConfiguration(): MultiLayerConfiguration = {
    val numHiddenNodes = 50
    new NeuralNetConfiguration.Builder()
      .seed(-1)
      .iterations(1)
      .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
      .learningRate(0.001)
      .weightInit(WeightInit.XAVIER)
      .updater(Updater.NESTEROVS)
      .regularization(false)
      .momentum(0.9)
      .list
      .layer(0, new DenseLayer.Builder()
        .nIn(42)
        .nOut(numHiddenNodes)
        .activation(Activation.TANH).build)
      .layer(1, new DenseLayer.Builder()
        .nIn(numHiddenNodes)
        .nOut(numHiddenNodes)
        .activation(Activation.TANH).build)
      .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
        .activation(Activation.IDENTITY).nIn(numHiddenNodes)
        .nOut(1)
        .build)
      .pretrain(false)
      .backprop(true)
      .build
  }



}
