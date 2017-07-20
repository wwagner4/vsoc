package common

import java.util.Locale

/**
  * Formats various data structures to comma separated lines
  */
object Formatter {

  val locale = Locale.ENGLISH

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
    "%5.2f".formatLocal(locale, d)
  }

  def formatWide(d: Double): String = {
    "%10.2f".formatLocal(locale, d)
  }

  def formatDense(l: Long): String = {
    "%5d".formatLocal(locale, l)
  }

  def formatWide(l: Long): String = {
    "%10d".formatLocal(locale, l)
  }

}