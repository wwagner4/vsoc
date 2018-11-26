package vsoc.ga.trainga.ga.common

class TC extends ValuesByIndexCollector[Double] {

  override def sumBase(value1: Double, value2: Double): Double = value1 + value2

  override def divideBase(value: Double, divisor: Double): Double = value / divisor

  override def unitValue: Double = 0

}

