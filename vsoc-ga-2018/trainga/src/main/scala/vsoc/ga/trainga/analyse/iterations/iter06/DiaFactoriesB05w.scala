package vsoc.ga.trainga.analyse.iterations.iter06

import entelijan.viz.Viz
import vsoc.ga.common.viz.Smoothing
import vsoc.ga.trainga.analyse.common.dia.DataDia.FDia
import vsoc.ga.trainga.analyse.common.dia.DiaFactories
import vsoc.ga.trainga.ga.impl.team01.Data02

object DiaFactoriesB05w extends DiaFactories[Data02] {

  override def trainGaId: String = "trainGaB05"

  def diaFactories: Seq[FDia[Data02]] = Seq(
    Seq(scores),
    kicksAndGoalsAll,
    kicksAndGoalsKicker,
    goalsOtherOwn,
  ).flatten

  val catAll = Seq(
    Cat("All", "All", Seq("work001", "work002", "work003", "work004", "work005", "work006")),
  )

  val catKicker = Seq(
    Cat("Kicker", "K", Seq("work001", "work002","work005")),
  )

  val catGoalgetter = Seq(
    Cat("Goalgetter", "G", Seq("work003", "work004", "work006")),
  )

  def kicksAndGoalsAll: Seq[FDia[Data02]] =
    for (cat <- catAll) yield {
      kicksAndGoals(cat) _
    }

  def kicksAndGoalsKicker: Seq[FDia[Data02]] =
    for (cat <- catKicker) yield {
      kicksAndGoals1(cat) _
    }

  def goalsOtherOwn: Seq[FDia[Data02]] =
    for (cat <- catKicker) yield {
      goalsOtherOwn(cat) _
    }

  def smoothProp(data: Seq[Data02], f: Data02 => Double, grpSize: Int): Seq[Viz.XY] = {
    val xy = data.map(d => Viz.XY(d.iterations, f(d)))
    Smoothing.smooth(xy, grpSize)
  }

  def scores: FDia[Data02] =
    (trainingId: String, data: Seq[Data02]) => {

      val title = "score for " + trainingId

      def createVizData(rowData: Seq[Data02]): Seq[Viz.XY] =
        for (d <- rowData) yield Viz.XY(d.iterations, d.score)

      val rows: Map[String, Seq[Data02]] = data.groupBy(d => d.trainGaNr)
      val vizDataRows =
        for (pop <- rows.keys.toList.sorted) yield {
          val rowData = rows(pop)
          require(rowData.size > 4, s"Row data must contain at least 3 records. $pop")
          val vizData = Smoothing.smooth(createVizData(rowData), 40)
          Viz.DataRow(name = Some(pop), data = vizData)
        }
      Viz.Diagram(id = trainingId, title = title,
        imgWidth = 1000, imgHeight = 1000, dataRows = vizDataRows)
    }

  private def kicksAndGoals(cat: Cat)
                           (name: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {

    val mdiaId = "kicksAndGoals"
    val mdiaTitle = "Categories by Kicks and Goals"
    val grpSize = 50

    def diagram(diaId: String, name: String, diaData: Seq[Data02]): Viz.Diagram[Viz.XY] = {

      val rows = Seq(
        Viz.DataRow(Some("kicks max x 2"), data = smoothProp(diaData, d => d.kicksMax * 2, grpSize)),
        Viz.DataRow(Some("kicks min x 100"), data = smoothProp(diaData, d => d.kicksMin * 100, grpSize)),
        Viz.DataRow(Some("goals max x 1000"), data = smoothProp(diaData, d => d.otherGoalsMax * 1000, grpSize)),
        Viz.DataRow(Some("score"), data = smoothProp(diaData, d => d.score, grpSize))
      )

      Viz.Diagram(id = diaId, title = name,
        //yRange = Some(Viz.Range(Some(0), Some(25000))),
        //xRange = Some(Viz.Range(Some(0), Some(2500))),
        dataRows = rows
      )
    }

    def mdiagram(catData: Seq[(String, Seq[Data02])]): Viz.Dia[Viz.XY] = {
      val dias = for ((nr, data) <- catData) yield diagram(nr, nr, data)
      Viz.MultiDiagram[Viz.XY](id = cat.id + name + mdiaId, title = Some(s"$mdiaTitle ${cat.title} $name"),
        columns = 3, imgWidth = 2000, imgHeight = 1000, diagrams = dias
      )
    }

    mdiagram(filterCat(data, cat))

  }

  private def kicksAndGoals1(cat: Cat)
                            (name: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {

    val mdiaId = "kicksAndGoalsKicker"
    val mdiaTitle = "Kicks and Goals"
    val grpSize = 50

    def diagram(diaId: String, name: String, diaData: Seq[Data02]): Viz.Diagram[Viz.XY] = {
      require(diaData.nonEmpty, "Cannot handle empty dataset")

      val rows = Seq(
        Viz.DataRow(Some("kicks max x 20"), data = smoothProp(diaData, d => d.kicksMax * 20, grpSize)),
        Viz.DataRow(Some("kicks min x 20"), data = smoothProp(diaData, d => d.kicksMin * 20, grpSize)),
        Viz.DataRow(Some("goals max x 10.000"), data = smoothProp(diaData, d => d.otherGoalsMax * 10000, grpSize)),
        Viz.DataRow(Some("own goals max x 10.000"), data = smoothProp(diaData, d => d.ownGoalsMax * 10000, grpSize)),
        Viz.DataRow(Some("score"), data = smoothProp(diaData, d => d.score, grpSize))
      )

      Viz.Diagram(id = diaId, title = name, dataRows = rows,
        //yRange = Some(Viz.Range(Some(0), Some(30000))),
        //xRange = Some(Viz.Range(Some(0), Some(5000))),
      )
    }

    def mdiagram(catData: Seq[(String, Seq[Data02])]): Viz.Dia[Viz.XY] = {
      val dias = for ((nr, data) <- catData) yield diagram(nr, nr, data)
      Viz.MultiDiagram[Viz.XY](id = cat.id + name + mdiaId, title = Some(s"$mdiaTitle ${cat.title} $name"), diagrams = dias,
        columns = 3,
        imgWidth = 2000,
        imgHeight = 800,
      )
    }

    require(data.nonEmpty, "Cannot handle empty dataset")

    mdiagram(filterCat(data, cat))

  }

  private def kicksMinToScore(cat: Cat)
                             (name: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {

    val mdiaId = "kicksMinScore"
    val mdiaTitle = "Kicks min Score"
    val grpSize = 20

    def diagram(diaId: String, name: String, diaData: Seq[Data02]): Viz.Diagram[Viz.XY] = {
      require(diaData.nonEmpty, "Cannot handle empty dataset")

      val rows = Seq(
        Viz.DataRow(Some("score"), data = smoothProp(diaData, d => d.score, grpSize)),
        Viz.DataRow(Some("kicks min x 80"), data = smoothProp(diaData, d => d.kicksMin * 80, grpSize)),
      )

      Viz.Diagram(id = diaId, title = name, dataRows = rows,
        yRange = Some(Viz.Range(Some(0), Some(55000))),
        // xRange = Some(Viz.Range(Some(0), Some(5000))),
      )
    }

    def mdiagram(catData: Seq[(String, Seq[Data02])]): Viz.Dia[Viz.XY] = {
      val dias = for ((nr, data) <- catData) yield diagram(nr, nr, data)
      Viz.MultiDiagram[Viz.XY](id = cat.id + name + mdiaId, title = Some(s"$mdiaTitle ${cat.title} $name"), diagrams = dias,
        columns = 2,
        imgWidth = 1500,
        imgHeight = 1200,
      )
    }

    require(data.nonEmpty, "Cannot handle empty dataset")

    mdiagram(filterCat(data, cat))

  }

  private def goalsToScore(cat: Cat)
                             (name: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {

    val mdiaId = "goalsToScore"
    val mdiaTitle = "Goals Score"
    val grpSize = 20

    def diagram(diaId: String, name: String, diaData: Seq[Data02]): Viz.Diagram[Viz.XY] = {
      require(diaData.nonEmpty, "Cannot handle empty dataset")

      val rows = Seq(
        Viz.DataRow(Some("score"), data = smoothProp(diaData, d => d.score, grpSize)),
        Viz.DataRow(Some("goals max x 400"), data = smoothProp(diaData, d => d.otherGoalsMax * 400, grpSize)),
      )

      Viz.Diagram(id = diaId, title = name, dataRows = rows,
        yRange = Some(Viz.Range(Some(0), Some(6000))),
        // xRange = Some(Viz.Range(Some(0), Some(5000))),
      )
    }

    def mdiagram(catData: Seq[(String, Seq[Data02])]): Viz.Dia[Viz.XY] = {
      val dias = for ((nr, data) <- catData) yield diagram(nr, nr, data)
      Viz.MultiDiagram[Viz.XY](id = cat.id + name + mdiaId, title = Some(s"$mdiaTitle ${cat.title} $name"), diagrams = dias,
        columns = 2,
        imgWidth = 1500,
        imgHeight = 1600,
      )
    }

    require(data.nonEmpty, "Cannot handle empty dataset")

    mdiagram(filterCat(data, cat))

  }

  private def goalsOtherOwn(cat: Cat)
                           (name: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {

    val grpSize = 50

    val mdiaId = "goalsOtherOwn"
    val mdiaTitle = "Goals Other vs. Own"

    def diagram(diaId: String, name: String, diaData: Seq[Data02]): Viz.Diagram[Viz.XY] = {
      require(diaData.nonEmpty, "Cannot handle empty dataset")

      val rows = Seq(
        Viz.DataRow(Some("goals max"), data = smoothProp(diaData, d => d.otherGoalsMax, grpSize)),
        Viz.DataRow(Some("own goals max"), data = smoothProp(diaData, d => d.ownGoalsMax, grpSize)),
      )

      Viz.Diagram(id = diaId, title = name, dataRows = rows,
        //yRange = Some(Viz.Range(Some(0), Some(1.2))),
        //xRange = Some(Viz.Range(Some(0), Some(2000))),
      )
    }

    def mdiagram(gdata: Seq[(String, Seq[Data02])]): Viz.Dia[Viz.XY] = {
      val dias = for ((nr, data) <- gdata) yield diagram(nr, nr, data)
      Viz.MultiDiagram[Viz.XY](id = cat.id + name + mdiaId, title = Some(s"$mdiaTitle ${cat.title} $name"), diagrams = dias,
        columns = 3,
        imgWidth = 2000,
        imgHeight = 800,
      )
    }

    mdiagram(filterCat(data, cat))

  }


}
