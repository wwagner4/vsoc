package vsoc.ga.trainga.ga.impl.player01.analyse

import java.nio.file.{Files, Path}

import entelijan.viz.{Viz, VizCreator}
import entelijan.viz.creators.VizCreatorGnuplot
import vsoc.ga.trainga.config.ConfigHelper
import vsoc.ga.trainga.analyse.old.smooth.Smoothing._

object AnalyseMain extends App {

  val grpSize = 4

  implicit val wd: Path = ConfigHelper.workDir
  val reader = new CsvReaderDataPlayer01()
  val allDatas = reader.read(s"trainGaPlayer01Simple")

  val gdatas = allDatas.groupBy(d => d.nr).toList

  gdatas.foreach{case (name, datas) =>
    val kdata = datas.map(d => Viz.XY(d.iterations, d.kicks))
    val kicks = Viz.DataRow(
      name = Some("kicks"),
      data = smooth(kdata, grpSize),
    )
    val gdata = datas.map(d => Viz.XY(d.iterations, d.goals * 100))
    val goals = Viz.DataRow(
      name = Some("goals x 100"),
      data = smooth(gdata, grpSize),
    )
    val sdata = datas.map(d => Viz.XY(d.iterations, d.score))
    val score = Viz.DataRow(
      name = Some("score"),
      data = smooth(sdata, grpSize),
    )
    val dia = Viz.Diagram(
      id = s"player01_$name",
      title = s"Player 01 $name",
      dataRows = Seq(kicks, goals, score)
    )

    val scripDir = ConfigHelper.workDir.resolve(".script")
    if (!Files.exists(scripDir)) Files.createDirectories(scripDir)
    val diaDir = ConfigHelper.workDir.resolve("diagram")
    if (!Files.exists(diaDir)) Files.createDirectories(diaDir)
    implicit val creator: VizCreator[Viz.XY] =
      VizCreatorGnuplot(scripDir.toFile, diaDir.toFile, execute = true)
    Viz.createDiagram(dia)
  }



}
