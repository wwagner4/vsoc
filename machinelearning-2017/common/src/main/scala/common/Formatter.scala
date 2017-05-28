package common

/**
  * Formats various data structures to comma separated lines
  */
object Formatter {

  def formatWide(a: Array[Double])(implicit sepaStr: String): String = {
    a.map {
      formatWide
    }.mkString(sepaStr)
  }

  def formatDense(a: Array[Double])(implicit sepaStr: String): String = {
    a.map {
      formatDense
    }.mkString(sepaStr)

  }

  def formatLimitatedWide(a: Array[Double])(implicit sepaStr: String): String = {
    a.map {
      formatLimitatedWide
    }.mkString(sepaStr)
  }

  def formatLimitatedDense(a: Array[Double])(implicit sepaStr: String): String = {
    a.map {
      formatLimitatedDense
    }.mkString(sepaStr)
  }

  def formatLimitatedWide(d: Double): String = {
    if (d < 1.0E-7 && d > -1.0E-7) "         0"
    else if (d > 1.0e7) "        E7"
    else if (d < -1.0e7) "       -E7"
    else formatWide(d)
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

  def formatWide(d: Double): String = {
    f"$d%10.2f"
  }

  def formatDense(l: Long): String = {
    f"$l%5d"
  }

  def formatWide(l: Long): String = {
    f"$l%10d"
  }


}