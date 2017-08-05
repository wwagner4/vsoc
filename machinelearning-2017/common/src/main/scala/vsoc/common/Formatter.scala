package vsoc.common

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
    formatNumber("%5.2f", d)
  }

  def formatWide(d: Double): String = {
    formatNumber("%10.2f", d)
  }

  def formatDense(l: Long): String = {
    formatNumber("%5d", l)
  }

  def formatWide(l: Long): String = {
    formatNumber("%10d", l)
  }

  def formatNumber(format: String, num: Number): String = {
    format.formatLocal(locale, num)
  }
}