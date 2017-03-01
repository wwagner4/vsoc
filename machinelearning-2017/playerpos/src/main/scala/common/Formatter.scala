package common

import vsoc.util.Vec2D

/**
  * Formats various data structures to comma separated lines
  */
object Formatter {

  private val sepa = ','
  private val sepaStr =  "" + sepa

  def format(a: Array[Double]): String = {
    a.toList.map { d => f"$d%10.2f" }.mkString(sepaStr)
  }

  def format(pos: Vec2D, dir: Double, a: Array[Double]): String = {
    f"${pos.getX}%10.2f$sepaStr${pos.getY}%10.2f$sepaStr$dir%10.2f$sepaStr${format(a)}"
  }

}