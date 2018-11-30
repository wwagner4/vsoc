package vsoc.ga.trainga.ga

object Activation {

  def sigmoid(k: Double)(x: Double): Double = {
    0.5 * (1 + math.tanh((x * k) * 0.5))
  }

  def tanh(k: Double)(x: Double): Double = {
    math.tanh(x * k)
  }

}
