package common

import java.io.File

import common.Viz._

/**
  * Created by wwagner4 on 04/03/2017.
  */
object Viz {

  case class XY(
                 x: Number,
                 y: Number
               )

  case class DataRow(
                      name: String,
                      data: List[XY] = List.empty
                    )

  case class Diagram(
                      id: String,
                      title: String,
                      xLabel: String,
                      yLabel: String,
                      dataRows: List[DataRow] = List.empty
                    )

  def createDiagram(dia: Diagram)(implicit creator: VizCreator): Unit = {
    creator.createDiagram(dia)
  }
}

trait VizCreator {

  def createDiagram(dia: Diagram): Unit

}

case class VizCreatorGnuplot(outDir: File) extends VizCreator {


  def createDiagram(dia: Diagram): Unit = {

    def values(values: List[XY]) = values.map {
      xy: XY =>
        val x = formatNumber(xy.x)
        val y = formatNumber(xy.y)
        s"$x $y"
    }.mkString("\n")

    def formatNumber(n: Number): String = "" + n

    def data(dataRows: List[DataRow]) = dataRows.zipWithIndex.map {
      case (dr, i) => s"""
                         |$$Mydata$i << EOD
                         |${values(dr.data)}
                         |EOD
                         |""".stripMargin.trim
    }.mkString("\n")

    def series(dataRows: List[DataRow]) = dataRows.zipWithIndex.map {
      case (dr, i) => s"""$$Mydata$i using 1:2 title 'a dat' with lines"""
    }.mkString(", \\\n")


    val script =
      s"""
         |set terminal pngcairo  transparent enhanced font "arial,10" fontscale 1.0 size 600, 400
         |set output 'a.png'
         |set key inside left top vertical Right noreverse enhanced autotitle box lt black linewidth 1.000 dashtype solid
         |set minussign
         |${data(dia.dataRows)}
         |plot \\
         |${series(dia.dataRows)}
         |""".stripMargin

    val id = dia.id
    val filename = s"diagram_$id.gnuplot"
    val f = new File(outDir, filename)
    Util.writeToFile(f, pw => pw.print(script))
    println(s"wrote diagram $id to $f")
  }


}
