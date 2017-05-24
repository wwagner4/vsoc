package common

/**
  * Formats various data structures to comma separated lines
  */
object Formatter {

  def format(a: Array[Double])(implicit sepaStr: String): String = {
    a.map { d => f"$d%10.2f" }.mkString(sepaStr)
  }

  def formatLimitated(a: Array[Double])(implicit sepaStr: String): String = {
    a.map {
      formatLimitated
    }.mkString(sepaStr)

  }

  def formatLimitatedDense(a: Array[Double])(implicit sepaStr: String): String = {
    a.map {
      formatLimitated
    }.mkString(sepaStr)
  }

  def formatLimitated(d: Double): String = {
    if (d < 1.0E-7 && d > -1.0E-7) "         0"
    else if (d > 1.0e7) "        E7"
    else if (d < -1.0e7) "       -E7"
    else format(d)
  }

  def formatLimitatedDense(d: Double): String = {
    if (d < 1.0E-7 && d > -1.0E-7) "    0"
    else if (d > 1.0e7) "   E7"
    else if (d < -1.0e7) "  -E7"
    else formatDense(d)
  }

  def formatDense(d: Double): String = {
    f"$d%5.2f"
  }

  def format(d: Double): String = {
    f"$d%10.2f"
  }


}