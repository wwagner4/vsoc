package machinelearning.verification

import breeze.linalg.DenseVector
import common._

import VeriUtil._

object VeriCreateData extends App {

  def max = 100.0
  def min: Double = -100.0
  def sizes = List(10, 50, 100, 1000)
  def thetaOrig = DenseVector(4400.0, -2000.0, -3, 0.7)

  trait RandStrat {
    def id: String
  }

  case object RandStrat_A extends RandStrat {
    def id = "RSA"
  }

  case object RandStrat_B extends RandStrat {
    def id = "RSB"
  }

  def randStrat: RandStrat = RandStrat_B

  def dataId(size: Int): String = s"poly_${randStrat.id}_$size"

  def dataFilename(id: String): String = s"$id.txt"

  def datasets: List[(Int, String, String)] = sizes.map { size => (size, dataId(size), dataFilename(dataId(size))) }

  def createData(): Unit = {

    implicit val creator = VizCreatorGnuplot(Util.scriptsDir)

    val random = new java.util.Random()

    def polyRandomized(x: Double, deviation: Double)(theta: DenseVector[Double]): Double = {

      def excludeGreat(v: Double, max: Double): Double = {
        if (v < 0.0 && v < -max) -max
        else if (v > 0.0 && v > max) max
        else v
      }

      VeriCreateData.randStrat match {
        case RandStrat_A =>
          val ran = random.nextDouble() * 2.0 * deviation - deviation
          poly(x)(theta) + ran
        case RandStrat_B =>
          val y = poly(x)(theta)
          val devRel = if (y > 1.0 || y < -1.0) {
            excludeGreat(100000.0 * deviation / y, deviation)
          } else {
            deviation
          }
          val ran = random.nextDouble() * 2.0 * devRel - devRel
          y + ran
      }
    }

    datasets.foreach { case (size, id, filename) =>
      val stdDev = 60000
      val steps = (max - min) / size
      val file = Util.dataFile(filename)
      val xs = min to(max, steps)
      val ys = xs.map { x => (x, polyRandomized(x, stdDev)(thetaOrig)) }
      Util.writeToFile(file, { pw =>
        ys.foreach {
          case (x, y) =>
            pw.println(Formatter.format(x, y))
        }
        println(s"created data and wrote it to $file")
      })

      val data = ys.map { case (x, y) => Viz.XY(x, y) }
      val thetaStr = Formatter.format(thetaOrig.toArray)
      val dr = Viz.DataRow(thetaStr, style = Viz.Style_POINTS, data = data)
      val dia = Viz.Diagram(s"created_data_$id", s"Polynom datasize=$size", dataRows = List(dr))

      Viz.createDiagram(dia)
    }
  }
}

object VeriCreateDataMain extends App {
  VeriCreateData.createData()
}