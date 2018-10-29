package vsoc.ga.analyse.iterations.iter05

import entelijan.viz.Viz
import vsoc.ga.analyse.dia.DataDia.FDia
import vsoc.ga.analyse.dia.DiaFactories
import vsoc.ga.analyse.smooth.Smoothing
import vsoc.ga.common.data.Data02

object DiaFactoriesB04bob extends DiaFactories[Data02] {

  override def trainGaId: String = "trainGaB04"

  val catWorkAll = Seq(
    Cat("Bob [BOB]", "BOB", Seq("bob001", "bob002", "bob003", "bob004")),
  )

  val catWorkKicker = Seq(
    Cat("Bob Kicker [BOBK]", "BOBK", Seq("bob001")),
  )

  val catGoalGetters = Seq(
    Cat("Bob GoalGetter [BOBG]", "BOBG", Seq("bob002", "bob003", "bob004")),
  )

  def diaFactories: Seq[FDia[Data02]] = Seq(
    Seq(scores),
    kicksAndGoalsAll,
    kicksAndGoalsKicker,
    goalsOtherOwnAll,
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

  case class Cat(
                  title: String,
                  id: String,
                  trainGaNrs: Seq[String],
                )

  def kicksAndGoalsAll: Seq[FDia[Data02]] =
    for (cat <- catWorkAll) yield {
      kicksAndGoals(cat) _
    }

  def kicksAndGoalsKicker: Seq[FDia[Data02]] =
    for (cat <- catWorkKicker) yield {
      kicksAndGoalsKicker(cat) _
    }

  def goalsOtherOwnAll: Seq[FDia[Data02]] =
    for (cat <- catWorkAll) yield {
      goalsOtherOwn(cat) _
    }

  private def kicksAndGoals(cat: Cat)
                           (name: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {

    val mdiaId = "kicksAndGoals"
    val grpSize = 50

    def diagram(diaId: String, name: String, diaData: Seq[Data02]): Viz.Diagram[Viz.XY] = {
      require(diaData.nonEmpty, "Cannot handle empty dataset")

      val rows = Seq(
        Viz.DataRow(Some("kicks max x 20"), data = smoothProp(diaData, d => d.kicksMax * 20, grpSize)),
        Viz.DataRow(Some("kicks min x 20"), data = smoothProp(diaData, d => d.kicksMin * 20, grpSize)),
        Viz.DataRow(Some("goals max x 1000"), data = smoothProp(diaData, d => d.otherGoalsMax * 1000, grpSize)),
        Viz.DataRow(Some("score"), data = smoothProp(diaData, d => d.score, grpSize))
      )

      Viz.Diagram(
        id = diaId,
        title = name,
        yRange = Some(Viz.Range(Some(0), Some(25000))),
        //xRange = Some(Viz.Range(Some(0), Some(5000))),
        dataRows = rows
      )
    }

    def mdiagram(gdata: Seq[(String, Seq[Data02])]): Viz.Dia[Viz.XY] = {
      val dias = for ((nr, data) <- gdata) yield diagram(nr, nr, data)
      Viz.MultiDiagram[Viz.XY](
        id = cat.id + name + mdiaId,
        columns = 2,
        title = Some(s"${cat.title} $name"),
        imgWidth = 1600,
        imgHeight = 1400,
        diagrams = dias
      )
    }

    val gdata = data
      .filter(d => cat.trainGaNrs.contains(d.trainGaNr))
      .groupBy(d => d.trainGaNr)
      .toSeq.sortBy { case (k, _) => k }
    mdiagram(gdata)

  }

  private def kicksAndGoalsKicker(cat: Cat)
                                 (name: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {

    val mdiaId = "kicksAndGoalsKicker"
    val grpSize = 50

    def diagram(diaId: String, name: String, diaData: Seq[Data02]): Viz.Diagram[Viz.XY] = {
      require(diaData.nonEmpty, "Cannot handle empty dataset")

      val rows = Seq(
        Viz.DataRow(Some("kicks max x 20"), data = smoothProp(diaData, d => d.kicksMax * 20, grpSize)),
        Viz.DataRow(Some("kicks min x 20"), data = smoothProp(diaData, d => d.kicksMin * 20, grpSize)),
        Viz.DataRow(Some("goals max x 10.000"), data = smoothProp(diaData, d => d.otherGoalsMax * 10000, grpSize)),
        Viz.DataRow(Some("goals min x 10.000"), data = smoothProp(diaData, d => d.otherGoalsMin * 10000, grpSize)),
        Viz.DataRow(Some("score"), data = smoothProp(diaData, d => d.score, grpSize))
      )

      Viz.Diagram(
        id = diaId,
        title = name,
        yRange = Some(Viz.Range(Some(0), Some(20000))),
        //xRange = Some(Viz.Range(Some(0), Some(5000))),
        dataRows = rows
      )
    }

    def mdiagram(gdata: Seq[(String, Seq[Data02])]): Viz.Dia[Viz.XY] = {
      val dias = for ((nr, data) <- gdata) yield diagram(nr, nr, data)
      Viz.MultiDiagram[Viz.XY](
        id = cat.id + name + mdiaId,
        columns = 1,
        title = Some(s"${cat.title} $name"),
        imgWidth = 1000,
        imgHeight = 800,
        diagrams = dias
      )
    }

    require(data.nonEmpty, "Cannot handle empty dataset")

    val gdata = data
      .filter(d => cat.trainGaNrs.contains(d.trainGaNr))
      .groupBy(d => d.trainGaNr)
      .toSeq.sortBy { case (k, _) => k }
    mdiagram(gdata)

  }

  private def goalsOtherOwn(cat: Cat)
                           (name: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {

    val grpSize = 50

    val mdiaId = "goalsOtherOwn"

    def diagram(diaId: String, name: String, diaData: Seq[Data02]): Viz.Diagram[Viz.XY] = {
      require(diaData.nonEmpty, "Cannot handle empty dataset")

      val rows = Seq(
        Viz.DataRow(Some("goals max"), data = smoothProp(diaData, d => d.otherGoalsMax, grpSize)),
        Viz.DataRow(Some("own goals max"), data = smoothProp(diaData, d => d.ownGoalsMax, grpSize)),
      )

      Viz.Diagram(
        id = diaId,
        title = name,
        yRange = Some(Viz.Range(Some(0), Some(5))),
        //xRange = Some(Viz.Range(Some(0), Some(5000))),
        dataRows = rows
      )
    }

    def mdiagram(gdata: Seq[(String, Seq[Data02])]): Viz.Dia[Viz.XY] = {
      val dias = for ((nr, data) <- gdata) yield diagram(nr, nr, data)
      Viz.MultiDiagram[Viz.XY](
        id = cat.id + name + mdiaId,
        columns = 2,
        title = Some(s"${cat.title} $name"),
        imgWidth = 1600,
        imgHeight = 1400,
        diagrams = dias
      )
    }

    require(data.nonEmpty, "Cannot handle empty dataset")

    val gdata = data
      .filter(d => cat.trainGaNrs.contains(d.trainGaNr))
      .groupBy(d => d.trainGaNr)
      .toSeq.sortBy { case (k, _) => k }
    mdiagram(gdata)

  }

}
