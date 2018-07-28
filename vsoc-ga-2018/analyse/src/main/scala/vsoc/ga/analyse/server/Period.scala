package vsoc.ga.analyse.server

import scala.concurrent.duration.TimeUnit

case class Period(period: Long, unit: TimeUnit) {

  override def toString: String = s"$period ${unit.name()}"

}
