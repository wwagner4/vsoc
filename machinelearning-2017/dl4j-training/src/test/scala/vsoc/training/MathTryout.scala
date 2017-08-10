package vsoc.training

object MathTryout extends App {

  val learningRates = Map(0.01 -> "10^-2", 0.001 -> "10^-3", 0.0001 -> "10^-4", 0.00001 -> "10^-5", 0.000001 -> "10^-6")
  val x = learningRates.keys.toSeq.sorted.reverse

  println(x)

}
