package common

import vsoc.util.Vec2D

object CreateDataFormatter {

  implicit val sepAStr = ","

  import Formatter._

  def format(pos: Vec2D, dir: Double, features: Array[Double]): String = {
    s"${formatDense(pos.getX)}$sepAStr${formatDense(pos.getY)}$sepAStr${formatDense(dir)}$sepAStr${formatDense(features)}"
  }

}
