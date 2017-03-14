package machinelearning

import breeze.linalg._

/**
  * Container for one row of the training set
  */
case class TrainingSet(X: DenseMatrix[Double], y: DenseMatrix[Double])

/**
  * Implementation of the Gradient-Descent algorithm
  */
object GradientDescent {

  /**
    * A function with two input vectors an one output value
    *
    * theta => X => y
    *
    * theta: column vector of parameters (to be optimized)
    * X    : sample values (features). first column must be 1.0
    * y    : result (column vector) of the hypothesis (to be minimized)
    */
  type HypType = DenseMatrix[Double] => DenseMatrix[Double] => DenseMatrix[Double]

  def costFunc(hyp: HypType)(ts: TrainingSet)(theta: DenseMatrix[Double]): Double = {
    val h = hyp(theta)(_)
    val m = ts.y.rows
    sum((h(ts.X) - ts.y) ^:^ 2.0) / (2 * m)
  }

  /**
    * @param alpha step width
    * @param hypo  hypothesis function
    * @param ts    training set
    * @param theta set of parameters
    * @return the next set of parameters
    */
  def gradientDescent(alpha: Double)(hypo: HypType)(ts: TrainingSet)
                     (theta: DenseMatrix[Double]): DenseMatrix[Double] = {
    val m = ts.y.rows.toDouble
    val hf = hypo(theta)(_)

    theta - alpha * ((1/m) * (hf(ts.X) - ts.y).t * ts.X).t
  }

  def initialTheta(ts: TrainingSet): DenseMatrix[Double] = {
    DenseMatrix.zeros(ts.X.cols, ts.y.cols)
  }
}

object HypothesisFunction {

  /**
    * Linear function of type 'HypType'
    */
  def linearFunc: GradientDescent.HypType = {
    case (theta: DenseMatrix[Double]) => (x: DenseMatrix[Double]) => {
      require(theta.rows > 0)
      require(x.rows > 0)
      require(x.cols == theta.rows)

      x * theta

    }
  }


}
