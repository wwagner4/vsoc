package vsoc.ga.trainga.analyse.old.kickout

import entelijan.viz.{Viz, VizCreator, VizCreators}
import vsoc.ga.common.UtilPath
import vsoc.ga.common.config.ConfigHelper

object KickoutFunctionAnalyse extends App {

  val wd = UtilPath.workDir.toFile

  implicit val creator: VizCreator[Viz.XY] = VizCreators.gnuplot(wd, wd, execute = true, classOf[Viz.XY])

  def now(n: Int): Double = n.toDouble * 10

  def newLog(n: Int): Double = 10000 * math.log10(n + 1)

  def newExp(n: Int): Double = 10000 * (1.0 - math.exp(n * -0.003))

  val xs = 0 to(2000, 10)

  val nowData = xs.map(x => Viz.XY(x, now(x)))
  val newLogData = xs.map(x => Viz.XY(x, newLog(x)))
  val newExpData = xs.map(x => Viz.XY(x, newExp(x)))

  val _dataRows: Seq[Viz.DataRow[Viz.XY]] = Seq(
    Viz.DataRow(name = Some("now"), data = nowData),
    Viz.DataRow(name = Some("exp"), data = newExpData),
    Viz.DataRow(name = Some("log"), data = newLogData),
  )

  val dia = Viz.Diagram[Viz.XY]("ko", "Analyse Kickout Function", dataRows = _dataRows)

  Viz.createDiagram(dia)

}
