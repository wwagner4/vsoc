package vsoc.ga.trainga.ga.impl.player01.analyse

import java.nio.file.{Files, Path}

import entelijan.viz.creators.VizCreatorGnuplot
import entelijan.viz.{Viz, VizCreator}
import vsoc.ga.common.viz.Smoothing._
import vsoc.ga.trainga.config.ConfigHelper
import vsoc.ga.trainga.ga.impl.player01.DataPlayer01

object AnalyseMainPlayer01 extends App {

  kicksAndGoals(Seq("Simple", "B", "C"), 50)
  meanGoals(Seq("Simple", "B", "C"), 100)

  def meanGoals(trainIds: Seq[String], grpSize: Int): Unit = {
    def meanGoals(data: Seq[DataPlayer01]): Double = {
      require(data.nonEmpty)
      val sum = data.map(_.goals).sum
      sum / data.size
    }

    val allData = trainIds.flatMap { trainId =>
      implicit val wd: Path = ConfigHelper.workDir
      val reader = new CsvReaderDataPlayer01()
      reader.read(s"trainGaPlayer01$trainId")
    }

    val dataRows = allData
      .groupBy(d => (d.id, d.iterations))
      .toSeq
      .map { case ((id, iter), data) => (id, iter, meanGoals(data)) }
      .groupBy { case (id, _, _) => id }
      .map { case (id, d) =>
        val all = d.map { case (_, x, y) => Viz.XY(x, y) }
          .sortBy(xy => xy.x.intValue())
        val sm = smooth(all, grpSize)
        Viz.DataRow(
          name = Some(id),
          data = sm)
      }.toSeq

    val dia = Viz.Diagram(
      id = s"player01_goals",
      title = s"Player 01 goals per player per match",
      //xRange = Some(Viz.Range(Some(0), Some(4000))),
      //yRange = Some(Viz.Range(Some(0), Some(500))),
      dataRows = dataRows
    )
    writeDia(dia)
  }



  def kicksAndGoals(trainIds: Seq[String], grpSize: Int): Unit = {
    trainIds.foreach { trainId =>
      implicit val wd: Path = ConfigHelper.workDir
      val reader = new CsvReaderDataPlayer01()
      val allDatas = reader.read(s"trainGaPlayer01$trainId")

      val gdatas = allDatas.groupBy(d => d.nr).toList

      gdatas.foreach { case (nr, datas) =>
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
          id = s"player01_${trainId}_$nr",
          title = s"Player 01 $trainId $nr",
          xRange = Some(Viz.Range(Some(0), Some(6000))),
          yRange = Some(Viz.Range(Some(0), Some(500))),
          dataRows = Seq(kicks, goals, score)
        )
        writeDia(dia)
      }

    }
  }

  def writeDia(dia: Viz.Diagram[Viz.XY]): Unit = {
    val scripDir = ConfigHelper.workDir.resolve(".script")
    if (!Files.exists(scripDir)) Files.createDirectories(scripDir)
    val diaDir = ConfigHelper.workDir.resolve("diagram")
    if (!Files.exists(diaDir)) Files.createDirectories(diaDir)
    implicit val creator: VizCreator[Viz.XY] =
      VizCreatorGnuplot(scripDir.toFile, diaDir.toFile, execute = true)
    Viz.createDiagram(dia)
  }

}
