package machinelearning.verification

import breeze.linalg.DenseVector
import common._

import VeriUtil._

object VeriCreateData {

  val max = 100.0

  val min: Double = -100.0

  val sizes = List(10, 50, 100, 1000)

  val thetaOrig = DenseVector(4400.0, -2000.0, -3, 0.7)

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

    val random = new java.util.Random()

    def polyRandomized(x: Double, deviation: Double, randStrat: RandStrat)(theta: DenseVector[Double]): Double = {

      def excludeGreat(v: Double, max: Double): Double = {
        if (v < 0.0 && v < -max) -max
        else if (v > 0.0 && v > max) max
        else v
      }

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
      val stdDev = 60000
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

    val dias = datasets.map { ds =>
      val stdDev = 60000
      val steps = (max - min) / ds.size
      val xs = min to(max, steps)
      val ys = xs.map { x => (x, polyRandomized(x, stdDev, ds.randStrat)(thetaOrig)) }
      val data = ys.map { case (x, y) => Viz.XY(x, y) }
      val dr = Viz.DataRow("ran points", style = Viz.Style_POINTS, data = data)
      Viz.Diagram(
        s"created_${ds.id}",
        s"size=${ds.size} rand:${ds.randStrat.name}",
        dataRows = List(dr)
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