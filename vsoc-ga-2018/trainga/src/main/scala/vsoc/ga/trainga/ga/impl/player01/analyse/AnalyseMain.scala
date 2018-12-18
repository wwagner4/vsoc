package vsoc.ga.trainga.ga.impl.player01.analyse

import java.nio.file.{Files, Path}

import entelijan.viz.{Viz, VizCreator}
import entelijan.viz.creators.VizCreatorGnuplot
import vsoc.ga.trainga.config.ConfigHelper

object AnalyseMain extends App {

  implicit val wd: Path = ConfigHelper.workDir
  val reader = new CsvReaderDataPlayer01()
  val allDatas = reader.read(s"trainGaPlayer01Simple")

  val gdatas = allDatas.groupBy(d => d.nr).toList

  gdatas.foreach{case (name, datas) =>
    val kicks = Viz.DataRow(
      name = Some("kicks"),
      data = datas.map(d => Viz.XY(d.iterations, d.kicks)),
    )
    val goals = Viz.DataRow(
      name = Some("goals x 100"),
      data = datas.map(d => Viz.XY(d.iterations, d.goals * 100)),
    )
    val score = Viz.DataRow(
      name = Some("score x 2"),
      data = datas.map(d => Viz.XY(d.iterations, d.score * 2)),
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
