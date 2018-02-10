package vsoc.ga.trainga.retina.impl

import vsoc.behaviour.{DistDirVision, Sensors}
import vsoc.ga.trainga.retina.Retina

abstract class AbstractRetina extends Retina {

  def see(sens: Sensors): Array[Double] = {

    def fill(array: Array[Double], vision: DistDirVision): Unit = {
      for ((act, i) <- activation(vision)) {
        array(i) += act
      }
    }

    /**
      * Prepare the linpeak functions for each index
      */
    lazy val linpeakMap: Map[Int, Double => Double] = (0 until resolution).map { i =>
      val d = 90.0 / (resolution * 2)
      val centerOffset = (resolution - 1) * d
      val centers = Stream.iterate(-centerOffset)(center => center + 2 * d)
      val widts = 4.0 * d
      (i, Functions.linpeak(centers(i), widts)(_))
    }.toMap


    def activation(vision: DistDirVision): Seq[(Double, Int)] = {
      (0 until resolution).flatMap { i =>
        val dirVal = linpeakMap(i)(vision.getDirection)
        if (dirVal != 0.0) {
          val distVal = 10.0 - vision.getDistance * 0.1
          val act = dirVal * distVal
          Some((act, i))
        }
        else None
      }
    }

    val re = Array.fill(resolution)(0.0)
    look(sens).foreach(v => fill(re, v))
    re
  }

  /**
    * Defines the resolution of the retina
    */
  def resolution: Int

  /**
    * Defines which object is relevant for the retina
    * and if it is currently visible
    */
  def look(sens: Sensors): Option[DistDirVision]
}
