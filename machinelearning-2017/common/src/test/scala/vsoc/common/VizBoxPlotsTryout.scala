package vsoc.common

import Viz._

object VizBoxPlotsTryout extends App {

  /*
set terminal pngcairo dashed enhanced size 800, 600
set output 'bb01.png'

set title "Boxplot"

#set style fill solid 0.25 border -1
#set style boxplot outliers pointtype 7
#set boxwidth  0.2
#set pointsize 0.5

set style data boxplot

unset key
#set border 2
set xtics ("Lauf2" 1, "Lauf3" 2)
set xtics nomirror
set ytics nomirror
set yrange [0:100]

plot 'silver.dat' using (1):2, '' using (2):(3*$3)
   */


  implicit val creator = VizCreatorGnuplot[X](UtilIO.dirScripts, UtilIO.dirSub(UtilIO.dirScripts, "test-img"), true)

  case class Gaussian(id: String, mean: Double, vari: Double)

  val ran = new java.util.Random()

  val dataConfList = List(
    Gaussian("Hallo", 2, 5.1),
    Gaussian("wo", -5, 1.1),
    Gaussian("ist", 1, 5.5),
    Gaussian("der", 0, 0.6)
  )

  val data: Seq[DataRow[X]] = dataConfList.map { c =>
    val d = (1 to 100).map { _ =>
      val v = c.mean + ran.nextGaussian() * c.vari
      X(v)
    }
    new DataRow[X](style = Style_BOXPLOT, name = Some(c.id), data = d)
  }

  val diagram = new Diagram[X](id= "BP01", title="Boxplot Tryout", dataRows = data)

  Viz.createDiagram[X](diagram)

}
