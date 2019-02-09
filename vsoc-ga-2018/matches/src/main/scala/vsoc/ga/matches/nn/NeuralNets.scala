package vsoc.ga.matches.nn

import java.lang

import org.deeplearning4j.nn.conf.layers.recurrent.SimpleRnn
import org.deeplearning4j.nn.conf.layers.{DenseLayer, OutputLayer, RnnOutputLayer}
import org.deeplearning4j.nn.conf.{MultiLayerConfiguration, NeuralNetConfiguration}
import org.deeplearning4j.nn.weights.WeightInit
import org.nd4j.linalg.activations.{Activation, IActivation}
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.lossfunctions.{ILossFunction, LossFunctions}
import org.nd4j.linalg.primitives
import vsoc.ga.matches.nn.impl.NnWrapperAbstract

object NeuralNets {

  def default: NeuralNet = new NnWrapperAbstract {

    // id must be the name of the method creating the neural net
    def id = "default"

    def numInputNodes = 2

    def numHiddenNodes = 5

    def numOutputNodes = 3

    protected def nnConfiguration(): MultiLayerConfiguration = {
      new NeuralNetConfiguration.Builder()
        .maxNumLineSearchIterations(1)
        .weightInit(WeightInit.XAVIER)
        .list
        .layer(0, new DenseLayer.Builder()
          .nIn(numInputNodes)
          .nOut(numHiddenNodes)
          .activation(Activation.TANH)
          .build)
        .layer(1, new DenseLayer.Builder()
          .nIn(numHiddenNodes)
          .nOut(numHiddenNodes)
          .activation(Activation.TANH)
          .build)
        .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
          .activation(Activation.IDENTITY).nIn(numHiddenNodes)
          .nOut(numOutputNodes)
          .build)
        .build
    }

    override def fullDesc: String = s"'$id' - Default Neural Net. Used for testing purposes"

    override def historyLength: Int = 1
  }

  /**
    * Used in testcases should not be changed
    */
  def test: NeuralNet = new NnWrapperAbstract {

    // id must be the name of the method creating the neural net
    def id = "test"

    override def numInputNodes: Int = 2

    private def numHiddenNodes: Int = 5

    override def numOutputNodes: Int = 3

    override protected def nnConfiguration(): MultiLayerConfiguration = {
      new NeuralNetConfiguration.Builder()
        .maxNumLineSearchIterations(1)
        .weightInit(WeightInit.XAVIER)
        .list
        .layer(0, new DenseLayer.Builder()
          .nIn(numInputNodes)
          .nOut(numHiddenNodes)
          .activation(Activation.TANH)
          .build)
        .layer(1, new DenseLayer.Builder()
          .nIn(numHiddenNodes)
          .nOut(numHiddenNodes)
          .activation(Activation.TANH)
          .build)
        .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
          .activation(Activation.IDENTITY).nIn(numHiddenNodes)
          .nOut(numOutputNodes)
          .build)
        .build
    }

    override def fullDesc: String = s"'$id' - Standard Neural Net. Used for testing purposes"

    override def historyLength: Int = 1

  }

  def team01: NeuralNet = new NnWrapperAbstract {

    // id must be the name of the method creating the neural net
    def id = "team01"

    override def numInputNodes: Int = 140

    private def numHiddenNodes: Int = 150

    override def numOutputNodes: Int = 4

    override protected def nnConfiguration(): MultiLayerConfiguration = {
      new NeuralNetConfiguration.Builder()
        .maxNumLineSearchIterations(1)
        .weightInit(WeightInit.XAVIER)
        .list
        .layer(0, new DenseLayer.Builder()
          .nIn(numInputNodes)
          .nOut(numHiddenNodes)
          .activation(Activation.TANH)
          .build)
        .layer(1, new DenseLayer.Builder()
          .nIn(numHiddenNodes)
          .nOut(numHiddenNodes)
          .activation(Activation.TANH)
          .build)
        .layer(2, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
          .activation(Activation.IDENTITY).nIn(numHiddenNodes)
          .nOut(numOutputNodes)
          .build)
        .build
    }

    override def fullDesc: String = {
      s"""'$id' - Feedforward NN with one hidden layers
         |140 (in) 100 (1) 100 (out) 4 TANH
         |""".stripMargin

    }

    override def historyLength: Int = 1

  }

  def team02: NeuralNet = new NnWrapperAbstract {

    // id must be the name of the method creating the neural net
    def id = "team02"

    override def numInputNodes: Int = 140

    private def numHiddenNodes1: Int = 100

    private def numHiddenNodes2: Int = 50

    private def numHiddenNodes3: Int = 10

    override def numOutputNodes: Int = 4


    override protected def nnConfiguration(): MultiLayerConfiguration = {
      new NeuralNetConfiguration.Builder()
        .list
        .layer(0, new DenseLayer.Builder()
          .nIn(numInputNodes)
          .nOut(numHiddenNodes1)
          .activation(Activation.TANH)
          .build)
        .layer(1, new DenseLayer.Builder()
          .nIn(numHiddenNodes1)
          .nOut(numHiddenNodes2)
          .activation(Activation.TANH)
          .build)
        .layer(2, new DenseLayer.Builder()
          .nIn(numHiddenNodes2)
          .nOut(numHiddenNodes3)
          .activation(Activation.TANH)
          .build)
        .layer(3, new OutputLayer.Builder(LossFunctions.LossFunction.MSE)
          .activation(Activation.IDENTITY).nIn(numHiddenNodes3)
          .nOut(numOutputNodes)
          .build)
        .build
    }

    override def fullDesc: String = {
      s"""'$id' - Feedforward NN with two hidden layers
         |140 (in) 100 (1) 50 (2) 10 (out) 4 TANH
         |""".stripMargin

    }

    override def historyLength: Int = 1
  }

  def rnn01: NeuralNet = new NnWrapperAbstract {
    override def id: String = "rnn01"

    override def fullDesc: String = "Simple recurrent neural Net"

    override def numInputNodes: Int = 140

    override def numOutputNodes: Int = 4

    val numHiddenNodes1 = 100

    val numHiddenNodes2 = 50

    val numHiddenNodes3 = 10

    override protected def nnConfiguration(): MultiLayerConfiguration =
      new NeuralNetConfiguration.Builder()
        .list
        .layer(0, new SimpleRnn.Builder()
          .nIn(numInputNodes)
          .nOut(numHiddenNodes1)
          .activation(Activation.TANH)
          .build)
        .layer(1, new SimpleRnn.Builder()
          .nIn(numHiddenNodes1)
          .nOut(numHiddenNodes2)
          .activation(Activation.TANH)
          .build)
        .layer(2, new SimpleRnn.Builder()
          .nIn(numHiddenNodes2)
          .nOut(numHiddenNodes3)
          .activation(Activation.TANH)
          .build)
        .layer(3, new RnnOutputLayer.Builder()
          .lossFunction(new LF)
          .activation(Activation.TANH)
          .nIn(numHiddenNodes3)
          .nOut(numOutputNodes)
          .build)
        .build

    override def historyLength: Int = 5

  }

  class LF extends ILossFunction {
    override def computeScore(labels: INDArray, preOutput: INDArray, activationFn: IActivation, mask: INDArray, average: Boolean): Double =
      throw new IllegalStateException("should never be called")

    override def computeScoreArray(labels: INDArray, preOutput: INDArray, activationFn: IActivation, mask: INDArray): INDArray =
      throw new IllegalStateException("should never be called")

    override def computeGradient(labels: INDArray, preOutput: INDArray, activationFn: IActivation, mask: INDArray): INDArray =
      throw new IllegalStateException("should never be called")

    override def computeGradientAndScore(labels: INDArray, preOutput: INDArray, activationFn: IActivation, mask: INDArray, average: Boolean): primitives.Pair[lang.Double, INDArray] =
      throw new IllegalStateException("should never be called")

    override def name(): String = "LF"
  }

}
