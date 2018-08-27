package vsoc.ga.analyse

import entelijan.viz.Viz
import vsoc.ga.analyse.smooth.Smoothing
import vsoc.ga.common.data.Data02

object DiaFactoriesB03  extends DiaFactories[Data02]{

  override def trainGaId: String = "trainGaB03"

  def diaFactories: Seq[DiaFactory[Data02]] = Seq(
    scoreGroupedByPopulation,
    scoreComposition,
    kicks,
    goals,
  )

  def scoreGroupedByPopulation: DiaFactory[Data02] =
    (id: String, data: Seq[Data02]) => {

      val title = "score for app populations B03"

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
        id = id,
        title = title,
        dataRows = vizDataRows
      )
    }

  def scoreComposition: DiaFactory[Data02] = {

    (name: String, data: Seq[Data02]) => {

      val grpSize = 50
      val diaId = "scorecomp"
      val title = "score composition"

      def diagram(diaId: String, name: String, diaData: Seq[Data02]): Viz.Diagram[Viz.XY] = {
        require(diaData.nonEmpty, "Cannot handle empty dataset")
        val rows = Seq(
          ("kicks", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.kicksMax)), grpSize)),
          ("kick out", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.kickOutMax)), grpSize)),
          ("goals x 5000", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.otherGoalsMax * 5000)), grpSize)),
          ("own goals x 5000", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.ownGoalsMax * 5000)), grpSize)),
          ("score", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.score)), grpSize))
        )

        val vizDataRows =
          for ((nam, dat) <- rows) yield {
            Viz.DataRow(
              name = Some(nam),
              data = dat
            )
          }
        Viz.Diagram(
          id = diaId,
          title = name,
          //yRange = Some(Viz.Range(Some(0), Some(10000))),
          dataRows = vizDataRows
        )
      }

      require(data.nonEmpty, "Cannot handle empty dataset")
      val rows: Map[String, Seq[Data02]] = data.groupBy(d => d.trainGaNr)
      val _dias = for (nr <- rows.keys.toSeq.sorted) yield {
        diagram(nr, nr, rows(nr))
      }
      Viz.MultiDiagram[Viz.XY](
        id = diaId + name,
        columns = 2,
        title = Some(s"$title $name"),
        imgWidth = 1500,
        imgHeight = 1000,
        diagrams = _dias
      )
    }

  }

  def goals: DiaFactory[Data02] =
    (name: String, data: Seq[Data02]) => {

      val grpSize = 150
      val diaId = "goals"
      val title = "Goals vs own Goals"

      def diagram(diaId: String, name: String, diaData: Seq[Data02]): Viz.Diagram[Viz.XY] = {
        require(diaData.nonEmpty, "Cannot handle empty dataset")
        val rows = Seq(
          ("goals", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.otherGoalsMax)), grpSize)),
          ("own goals x 5000", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.ownGoalsMax)), grpSize)),
          //          ("score", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.score)), grpSize))
        )

        val vizDataRows =
          for ((nam, dat) <- rows) yield {
            Viz.DataRow(
              name = Some(nam),
              data = dat
            )
          }
        Viz.Diagram(
          id = diaId,
          title = name,
          yRange = Some(Viz.Range(Some(0), Some(2))),
          dataRows = vizDataRows
        )
      }

      require(data.nonEmpty, "Cannot handle empty dataset")
      val rows: Map[String, Seq[Data02]] = data.groupBy(d => d.trainGaNr)
      val _dias = for (nr <- rows.keys.toSeq.sorted) yield {
        diagram(nr, nr, rows(nr))
      }
      Viz.MultiDiagram[Viz.XY](
        id = diaId + name,
        columns = 2,
        title = Some(s"$title $name"),
        imgWidth = 1500,
        imgHeight = 1000,
        diagrams = _dias
      )
    }

  def kicks: DiaFactory[Data02] =
    (name: String, data: Seq[Data02]) => {
      val grpSize = 50
      val diaId = "kicks"
      val title = "kicks max mean min"

      def diagram(diaId: String, name: String, diaData: Seq[Data02]): Viz.Diagram[Viz.XY] = {
        require(diaData.nonEmpty, "Cannot handle empty dataset")
        val rows = Seq(
          ("kicks max", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.kicksMax)), grpSize)),
          ("kicks mean x 10", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.kicksMean * 10)), grpSize)),
          ("kicks min x 100", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.kicksMin * 100)), grpSize)),
          ("score", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.score)), grpSize))
        )

        val vizDataRows =
          for ((nam, dat) <- rows) yield {
            Viz.DataRow(
              name = Some(nam),
              data = dat
            )
          }
        Viz.Diagram(
          id = diaId,
          title = name,
          // yRange = Some(Viz.Range(Some(0), Some(10000))),
          dataRows = vizDataRows
        )
      }

      require(data.nonEmpty, "Cannot handle empty dataset")
      val rows: Map[String, Seq[Data02]] = data.groupBy(d => d.trainGaNr)
      val _dias = for (nr <- rows.keys.toSeq.sorted) yield {
        diagram(nr, nr, rows(nr))
      }
      Viz.MultiDiagram[Viz.XY](
        id = diaId + name,
        columns = 2,
        title = Some(s"$title $name"),
        imgWidth = 1500,
        imgHeight = 1000,
        diagrams = _dias
      )
    }

}
