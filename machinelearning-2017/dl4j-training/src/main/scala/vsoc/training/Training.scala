package vsoc.training

import java.io.{File, IOException}
import java.util

import common.Util._
import common.{Viz, VizCreatorGnuplot}
import org.datavec.api.records.reader.impl.csv.CSVRecordReader
import org.datavec.api.split.FileSplit
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator
import org.deeplearning4j.nn.api.OptimizationAlgorithm
import org.deeplearning4j.nn.conf.layers.{DenseLayer, OutputLayer}
import org.deeplearning4j.nn.conf.{MultiLayerConfiguration, NeuralNetConfiguration, Updater}
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.nn.weights.WeightInit
import org.deeplearning4j.optimize.listeners.ScoreIterationListener
import org.nd4j.linalg.activations.Activation
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.dataset.DataSet
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator
import org.nd4j.linalg.factory.Nd4j
import org.nd4j.linalg.lossfunctions.LossFunctions
import org.slf4j.{Logger, LoggerFactory}

import scala.util.Random


case class MetaParam(
                      learningRate: Double = 0.001,
                      sizeTrainingData: Int = 500000,
                      batchSizeTrainingData: Int = 10000,
                      sizeTestData: Int = 1000,
                      batchSizeTestData: Int = 1000,
                      iterations: Int = 3,
                      seed: Long = 1L
                    )


object Training extends App {
  val log: Logger = LoggerFactory.getLogger(classOf[Training])
  val dia: Viz.Dia[Viz.XY] = new Training(log).train()
  Viz.createDiagram(dia)(VizCreatorGnuplot[Viz.XY](dataDir, execute=true))
}

class Training(log: Logger) {

  import common.Util._

  val delim = ";"

  def train(): Viz.Dia[Viz.XY] = {
    val metas = Seq(1, 3, 10).map{iter =>
      MetaParam(
        seed = Random.nextLong(),
        iterations = iter)}
    log.info("start training")
    val dias: Seq[Viz.Diagram[Viz.XY]] = metas.map(mparam => train(mparam))

    Viz.MultiDiagram[Viz.XY](id = "playerpos_iter_02",
      imgWidth = 2000,
      columns= 3,
      diagrams = dias)
  }

  def test(nn: MultiLayerNetwork, metaParam: MetaParam): Viz.Diagram[Viz.XY] = {

    log.info("test for metaparameter: " + metaParam)

    import common.Formatter._

    val dataFileName = s"random_pos_${metaParam.sizeTestData}_xval.csv"
    val dataSet: DataSet = readPlayerposXDataSet(new File(dataDir, dataFileName), metaParam.sizeTestData).next()

    val features: INDArray = dataSet.getFeatures
    val labels: INDArray = dataSet.getLabels

    val output: util.List[INDArray] = nn.feedForward(features, false)

    val out: INDArray = output.get(output.size - 1)
    val diff: INDArray = labels.sub(out)
    val all: INDArray = Nd4j.hstack(labels, diff)

    val _data: Seq[Viz.XY] = UtilTraining.convertXY(all, (0, 1))
    val dr: Viz.DataRow[Viz.XY] = Viz.DataRow(style = Viz.Style_POINTS,
      data = _data)

    Viz.Diagram(id = "playerpos_x",
      title =  formatNumber("iterations: %d", metaParam.iterations),
      xLabel = Some("x"),
      yLabel = Some("diff"),
      dataRows = Seq(dr))
  }

  def train(mparam: MetaParam): Viz.Diagram[Viz.XY] = {

    require(mparam.sizeTestData != mparam.sizeTrainingData, "Test- and training data must be different")

    val trainingDataFileName = s"random_pos_${mparam.sizeTrainingData}_xval.csv"

    log.info("Start read data")
    val trainingData = readPlayerposXDataSet(new File(dataDir, trainingDataFileName), mparam.batchSizeTrainingData)
    log.info("Start training")
    val configuration = nnConfiguration(mparam)
    log.info("Training net with the following configuration: \n" + configuration)
    val nn = train(trainingData, configuration)
    log.info("Start testing")
    val re = test(nn, mparam)
    log.info("Finished")
    re
  }


  /**
    * @return DataSetIterator from the data of a file with a certain batch size
    */
  def readPlayerposXDataSet(inFile: File, batchSize: Int): DataSetIterator = try {
    val recordReader = new CSVRecordReader(0, delim)
    recordReader.initialize(new FileSplit(inFile))
    new RecordReaderDataSetIterator(recordReader, batchSize, 42, 42, true)
  } catch {
    case e@(_: IOException | _: InterruptedException) =>
      throw new IllegalStateException("Error in 'readPlayerposXDataSet'. " + e.getMessage, e)
  }


  private def train(dataSetIterator: DataSetIterator, nnConf: MultiLayerConfiguration): MultiLayerNetwork = {
    // create the net
    val net: MultiLayerNetwork = new MultiLayerNetwork(nnConf)
    net.init()
    net.setListeners(new ScoreIterationListener(40))
    // train the net
    net.fit(dataSetIterator)
    // return the trained net
    net
  }


  /**
    * Returns the network configuration, 2 hidden DenseLayers
    */
  private def nnConfiguration(mparam: MetaParam): MultiLayerConfiguration = {
    val numHiddenNodes = 50
    val learningRate = mparam.learningRate
    val iterations = mparam.iterations
    new NeuralNetConfiguration.Builder()
      .seed(mparam.seed)
      .iterations(iterations)
      .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
      .learningRate(learningRate)
      .weightInit(WeightInit.XAVIER)
      .updater(Updater.NESTEROVS)
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