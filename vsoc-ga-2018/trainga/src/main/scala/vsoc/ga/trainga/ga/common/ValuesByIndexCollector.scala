package vsoc.ga.trainga.ga.common

abstract class ValuesByIndexCollector[T] {

  private var values = Map.empty[Int, List[T]]

  def sumBase(value1: T, value2: T): T

  def divideBase(value: T, divisor: Double): T

  def unitValue: T

  def sum(index: Int): T = {
    if (!values.contains(index)) unitValue else sumOfList(values(index))
  }

  private def sumOfList(values: List[T]): T = {
    values match {
      case Nil => unitValue
      case a :: rest => sumBase(a, sumOfList(rest))
    }
  }

  def mean(index: Int): T = {
    if (!values.contains(index)) unitValue else {
      val s = sumOfList(values(index))
      val size = values(index).size
      divideBase(s, size)
    }
  }

  def putValue(index: Int, value: T):Unit = {
    if (values.contains(index)) {
      values = values + (index -> (value :: values(index)))
    } else {
      values = values + (index -> List(value))
    }
  }

}
