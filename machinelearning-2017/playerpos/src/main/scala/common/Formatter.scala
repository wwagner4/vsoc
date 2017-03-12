package common

import vsoc.util.Vec2D

/**
  * Formats various data structures to comma separated lines
  */
object Formatter {

  private val sepa = ','
  private val sepaStr = "" + sepa

  def format(a: Array[Double]): String = {
    a.map { d => f"$d%10.2f" }.mkString(sepaStr)
  }

  def formatLimitated(a: Array[Double]): String = {
    a.map { d =>
      if (d < 1.0E-7 && d > -1.0E-7) "         0"
      else if (d > 1.0e7)            "        E7"
      else if (d < -1.0e7)           "       -E7"
      else f"$d%10.2f"
    }.mkString(sepaStr)

  }

  def formatLimitatedDense(a: Array[Double]): String = {
    a.map { d =>
      if (d < 1.0E-7 && d > -1.0E-7) "    0"
      else if (d > 1.0e7) "   E7"
      else if (d < -1.0e7) "  -E7"
      else f"$d%5.2f"
    }.mkString(sepaStr)

  }

  def format(pos: Vec2D, dir: Double, a: Array[Double]): String = {
    f"${pos.getX}%10.2f$sepaStr${pos.getY}%10.2f$sepaStr$dir%10.2f$sepaStr${format(a)}"
  }

  def format(x: Double, y: Double): String = {
    f"$x%10.2f$sepaStr$y%10.2f"
  }

}