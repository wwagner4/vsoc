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

      override def createDia(id: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {

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
          title = s"score $id",
          dataRows = vizDataRows
        )
      }
    }

  }

  def scoreCompositionB01: DiaFactory[Data02] = {
    val grpSize = 50
    val diaId = "scorecomp"
    val title = "score composition"

    new DiaFactory[Data02] {

      def diagram(diaId: String, name: String, diaData: Seq[Data02]): Viz.Diagram[Viz.XY] = {
        require(diaData.nonEmpty, "Cannot handle empty dataset")
        val rows = Seq(
          ("kicks", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.kicksMax)), grpSize)),
          ("kick out", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.kickOutMax)), grpSize)),
          ("goals x 100", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.otherGoalsMax * 100)), grpSize)),
          ("own goals x 100", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.ownGoalsMax * 100)), grpSize)),
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
          yRange = Some(Viz.Range(Some(0), Some(1600))),
          dataRows = vizDataRows
        )
      }


      override def createDia(name: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {
        require(data.nonEmpty, "Cannot handle empty dataset")
        val rows: Map[String, Seq[Data02]] = data.groupBy(d => d.trainGaNr)
        val _dias = for (nr <- rows.keys.toSeq.sorted) yield {
          diagram(nr, nr, rows(nr))
        }
        Viz.MultiDiagram[Viz.XY](
          id = diaId + name,
          columns = 2,
          title = Some(s"$title $name"),
          imgWidth = 2000,
          imgHeight = 2500,
          diagrams = _dias
        )
      }
    }

  }

  def scoreCompositionB02: DiaFactory[Data02] = {
    val grpSize = 50
    val diaId = "scorecomp"
    val title = "score composition"

    new DiaFactory[Data02] {

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
          yRange = Some(Viz.Range(Some(0), Some(10000))),
          dataRows = vizDataRows
        )
      }


      override def createDia(name: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {
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

  }

  def scoreCompositionB03: DiaFactory[Data02] = {
    val grpSize = 50
    val diaId = "scorecomp"
    val title = "score composition"

    new DiaFactory[Data02] {

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


      override def createDia(name: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {
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

  }

  def kicksToKickOutB02: DiaFactory[Data02] = {
    val grpSize = 50
    val diaId = "kicksToKickOut"
    val title = "Kicks Kickout"

    new DiaFactory[Data02] {

      def diagram(diaId: String, name: String, diaData: Seq[Data02]): Viz.Diagram[Viz.XY] = {
        require(diaData.nonEmpty, "Cannot handle empty dataset")
        val rows = Seq(
          ("kicks max", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.kicksMax)), grpSize)),
          ("kick out max", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.kickOutMax)), grpSize)),
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
          //yRange = Some(Viz.Range(Some(0), Some(10000))),
          dataRows = vizDataRows
        )
      }


      override def createDia(name: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {
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

  }

  def goalsB02: DiaFactory[Data02] = {
    val grpSize = 50
    val diaId = "goals"
    val title = "Goals"

    new DiaFactory[Data02] {

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
          //yRange = Some(Viz.Range(Some(0), Some(10000))),
          dataRows = vizDataRows
        )
      }


      override def createDia(name: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {
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

  }

  def goalsB03: DiaFactory[Data02] = {
    val grpSize = 150
    val diaId = "goals"
    val title = "Goals vs own Goals"

    new DiaFactory[Data02] {

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


      override def createDia(name: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {
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

  }

  def kicksB01: DiaFactory[Data02] = {
    val grpSize = 50
    val diaId = "kicks"
    val title = "kicks max mean min"

    new DiaFactory[Data02] {

      def diagram(diaId: String, name: String, diaData: Seq[Data02]): Viz.Diagram[Viz.XY] = {
        require(diaData.nonEmpty, "Cannot handle empty dataset")
        val rows = Seq(
          ("kicks max", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.kicksMax)), grpSize)),
          ("kicks mean", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.kicksMean)), grpSize)),
          ("kicks min x 10.000", Smoothing.smooth(diaData.map(d => Viz.XY(d.iterations, d.kicksMin * 10000)), grpSize)),
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
          yRange = Some(Viz.Range(Some(0), Some(1500))),
          dataRows = vizDataRows
        )
      }


      override def createDia(name: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {
        require(data.nonEmpty, "Cannot handle empty dataset")
        val rows: Map[String, Seq[Data02]] = data.groupBy(d => d.trainGaNr)
        val _dias = for (nr <- rows.keys.toSeq.sorted) yield {
          diagram(nr, nr, rows(nr))
        }
        Viz.MultiDiagram[Viz.XY](
          id = diaId + name,
          columns = 2,
          title = Some(s"$title $name"),
          imgWidth = 2000,
          imgHeight = 2500,
          diagrams = _dias
        )
      }
    }

  }

  def kicksB02: DiaFactory[Data02] = {
    val grpSize = 50
    val diaId = "kicks"
    val title = "kicks max mean min"

    new DiaFactory[Data02] {

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
          yRange = Some(Viz.Range(Some(0), Some(10000))),
          dataRows = vizDataRows
        )
      }


      override def createDia(name: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {
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

  }

  def kicksB03: DiaFactory[Data02] = {
    val grpSize = 50
    val diaId = "kicks"
    val title = "kicks max mean min"

    new DiaFactory[Data02] {

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


      override def createDia(name: String, data: Seq[Data02]): Viz.Dia[Viz.XY] = {
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

  }



}
