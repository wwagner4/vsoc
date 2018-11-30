package vsoc.ga.analyse.old.iterations.iter04

import entelijan.viz.Viz
import vsoc.ga.analyse.old.dia.DataDia.FDia
import vsoc.ga.analyse.old.dia.DiaFactories
import vsoc.ga.analyse.old.smooth.Smoothing
import vsoc.ga.common.data.Data02

object DiaFactoriesB03 extends DiaFactories[Data02] {

  override def trainGaId: String = "trainGaB03"

  def diaFactories: Seq[FDia[Data02]] = Seq(
    Seq(scores),
    cats,
  ).flatten

  def smoothProp(data: Seq[Data02], f: Data02 => Double, grpSize: Int): Seq[Viz.XY] = {
    val xy = data.map(d => Viz.XY(d.iterations, f(d)))
    Smoothing.smooth(xy, grpSize)
  }

  def scores: FDia[Data02] =
    (trainingId: String, data: Seq[Data02]) => {

      val title = "score for " + trainingId

      def createVizData(rowData: Seq[Data02]): Seq[Viz.XY] = {
        for (d <- rowData) yield {
          Viz.XY(d.iterations, d.score)
        }
      }

      require(data.size > 4, "Cannot handle empty dataset")
      val rows: Map[String, Seq[Data02]] = data.groupBy(d => d.trainGaNr)
      val vizDataRows =
        for (pop <- rows.keys.toList.sorted) yield {
          val rowData = rows(pop)
          require(rowData.size > 4, s"Row data must contain at least 3 records. $pop")
          val vizData = Smoothing.smooth(createVizData(rowData), 40)
          Viz.DataRow(
            name = Some(pop),
            data = vizData
          )
        }
      Viz.Diagram(
        id = trainingId,
        title = title,
        imgWidth = 1000,
        imgHeight = 1000,
        dataRows = vizDataRows
      )
    }

  val categories = Seq(
    ("One Kicker [OK]", "OK", Seq("work001", "work006")),
    ("All Kickers [AL]", "AL", Seq("bob001", "bob004", "work003")),
    ("One Goalgetter [OG]", "OG", Seq("bob002", "bob003", "work002", "work004", "work005")),
  )

  def cats: Seq[FDia[Data02]] =
    for ((title, diaId, trainGaNrs) <- categories) yield {
      cat(title, diaId, trainGaNrs) _
    }

  private def cat(title: String, diaId: String, trainGaNrs: Seq[String])
                 (name: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {

    val grpSize = 50

    def diagram(diaId: String, name: String, diaData: Seq[Data02]): Viz.Diagram[Viz.XY] = {
      require(diaData.nonEmpty, "Cannot handle empty dataset")

      val rows = Seq(
        Viz.DataRow(Some("kicks max x 2"), data = smoothProp(diaData, d => d.kicksMax * 2, grpSize)),
        Viz.DataRow(Some("kicks min x 100"), data = smoothProp(diaData, d => d.kicksMin * 100, grpSize)),
        Viz.DataRow(Some("goals max x 1000"), data = smoothProp(diaData, d => d.otherGoalsMax * 1000, grpSize)),
        Viz.DataRow(Some("score"), data = smoothProp(diaData, d => d.score, grpSize))
      )

      Viz.Diagram(
        id = diaId,
        title = name,
        yRange = Some(Viz.Range(Some(0), Some(15000))),
        xRange = Some(Viz.Range(Some(0), Some(5000))),
        dataRows = rows
      )
    }

    def mdiagram(gdata: Seq[(String, Seq[Data02])]): Viz.Dia[Viz.XY] = {
      val dias = for ((nr, data) <- gdata) yield diagram(nr, nr, data)
      Viz.MultiDiagram[Viz.XY](
        id = diaId + name,
        columns = 5,
        title = Some(s"$title $name"),
        imgWidth = 2000,
        imgHeight = 400,
        diagrams = dias
      )
    }

    require(data.nonEmpty, "Cannot handle empty dataset")

    val gdata = data
      .filter(d => trainGaNrs.contains(d.trainGaNr))
      .groupBy(d => d.trainGaNr)
      .toSeq.sortBy { case (k, _) => k }
    mdiagram(gdata)

  }


}
