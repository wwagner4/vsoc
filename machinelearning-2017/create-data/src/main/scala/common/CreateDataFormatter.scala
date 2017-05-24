package common

import vsoc.util._

object CreateDataFormatter {

  implicit val sepaStr = ","

  import Formatter._

  def format(pos: Vec2D, dir: Double, features: Array[Double]): String = {
    s"${formatDense(pos.getX)}$sepaStr${formatDense(pos.getY)}$sepaStr${formatDense(dir)}$sepaStr${formatLimitatedDense(features)}"
  }

}
