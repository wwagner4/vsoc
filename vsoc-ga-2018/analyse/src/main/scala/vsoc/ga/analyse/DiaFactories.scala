package vsoc.ga.analyse

import entelijan.viz.Viz
import vsoc.ga.analyse.smooth.Smoothing
import vsoc.ga.common.data.Data02

object DiaFactories {

  def scoreGroupedByPopulation: DiaFactory[Data02] = {
    new DiaFactory[Data02] {

      def createVizData(rowData: Seq[Data02]): Seq[Viz.XY] = {
        for (d <- rowData) yield {
          Viz.XY(d.iterations, d.score)
        }
      }

      override def createDia(data: Seq[Data02]): Viz.Dia[Viz.XY] = {
        require(data.size > 0, "Cannot handle empty dataset")
        val trainGaName = data(0).trainGaId
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
          id = trainGaName,
          title = trainGaName,
          dataRows = vizDataRows.toSeq
        )
      }
    }

  }

}
