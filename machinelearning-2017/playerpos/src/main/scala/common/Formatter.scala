package common

import vsoc.util.Vec2D

object Formatter {

  def format(a: Array[Double]): String = {
    a.toList.map { d => f"$d%10.2f" }.mkString(" ")
  }

  def format(pos: Vec2D, dir: Double, a: Array[Double]): String = {
    f"${pos.getX}%10.2f ${pos.getY}%10.2f $dir%10.2f ${format(a)}"
  }

}