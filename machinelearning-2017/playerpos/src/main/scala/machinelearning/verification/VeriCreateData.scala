package machinelearning.verification

import breeze.linalg.DenseVector
import common._

import VeriUtil._

object VeriCreateData {

  val random = new java.util.Random()

  val max = 100.0

  val min: Double = -100.0

  val sizes = List(10, 50, 100, 1000)

  val thetaOrig = DenseVector(15, 18.0E-2, 5.0E-4, -6.0E-6)

  val stdDev = 2.0

  case class DataSet(
                      size: Int,
                      randStrat: RandStrat
                    ) {
    def id: String = s"${randStrat.id}_$size"

    def filename: String = s"veri_$id.txt"
  }

  trait RandStrat {
    def id: String

    def name: String
  }

  case object RandStrat_ABS extends RandStrat {
    def id = "A"

    def name = "absolute"
  }

  case object RandStrat_REL extends RandStrat {
    def id = "R"

    def name = "relative"
  }

  val randStrats = List(RandStrat_ABS, RandStrat_REL)

  val datasets: List[DataSet] = {
    for (size <- sizes; randStrat <- randStrats) yield DataSet(size, randStrat)
  }

  def createData(): Unit = {

    implicit val creator = VizCreatorGnuplot(Util.scriptsDir)

    def polyRandomized(x: Double, deviation: Double, randStrat: RandStrat)(theta: DenseVector[Double]): Double = {

      def fact(x: Double): Double = math.pow(math.E, -(x * x) / 3000 )

      randStrat match {
        case RandStrat_ABS =>
          val ran = random.nextDouble() * 2.0 * deviation - deviation
          poly(x)(theta) + ran
        case RandStrat_REL =>
          val devRel = deviation * fact(x)
          val ran = random.nextDouble() * 2.0 * devRel - devRel
          poly(x)(theta) + ran
      }
    }

    datasets.foreach { ds: DataSet =>
      val steps = (max - min) / ds.size
      val file = Util.dataFile(ds.filename)
      val xs = min to(max, steps)
      val ys = xs.map { x => (x, polyRandomized(x, stdDev, ds.randStrat)(thetaOrig)) }
      Util.writeToFile(file, { pw =>
        ys.foreach {
          case (x, y) =>
            pw.println(Formatter.format(x, y))
        }
        println(s"created data and wrote it to $file")
      })
    }

    val dias = datasets.reverse.map { ds =>
      val steps = (max - min) / ds.size
      val xs = min to(max, steps)
      val ys = xs.map { x => (x, polyRandomized(x, stdDev, ds.randStrat)(thetaOrig)) }
      val data = ys.map { case (x, y) => Viz.XY(x, y) }
      val origData = (min to (max, 5.0)).map {x =>
        val y = poly(x)(thetaOrig)
        Viz.XY(x, y)
      }
      val dr = Viz.DataRow("ran points", style = Viz.Style_POINTS, data = data)
      val orig = Viz.DataRow("origs", style = Viz.Style_LINES(3.0), data = origData)
      Viz.Diagram(
        s"created_${ds.id}",
        s"size=${ds.size} rand:${ds.randStrat.name}",
        xRange = Some(Viz.Range(Some(-110), Some(110))),
        dataRows = List(dr, orig)
      )

    }
    val mdia = Viz.MultiDiagram(
      id = "generated",
      title = Some("generated data"),
      columns = 2,
      imgWidth = 1000,
      imgHeight = 1700,
      diagrams = dias
    )


    Viz.createDiagram(mdia)

  }
}

object VeriCreateDataMain extends App {
  VeriCreateData.createData()
}