package common

import java.io.File

import common.Viz._

/**
  * Created by wwagner4 on 04/03/2017.
  */
object Viz {

  sealed trait LegendPlacement
  case object LegendPlacement_LEFT extends LegendPlacement
  case object LegendPlacement_RIGHT extends LegendPlacement

  case class XY(
                 x: Number,
                 y: Number
               )

  case class DataRow(
                      name: String,
                      data: Seq[XY] = Seq.empty
                    )

  case class Diagram(
                      id: String,
                      title: String,
                      xLabel: Option[String] = None,
                      yLabel: Option[String] = None,
                      xRange: Option[Range] = None,
                      yRange: Option[Range] = None,
                      legendPlacement: LegendPlacement = LegendPlacement_LEFT,
                      dataRows: Seq[DataRow] = Seq.empty
                    )

  case class Range(
                    from: Option[Number],
                    to: Option[Number]
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

    def values(values: Seq[XY]) = values.map {
      xy: XY =>
        val x = formatNumber(xy.x)
        val y = formatNumber(xy.y)
        s"$x $y"
    }.mkString("\n")

    def formatNumber(n: Number): String = "" + n
    def formatRange(r: Range): String = {
      val from = formatRangeValue(r.from)
      val to = formatRangeValue(r.to)
      s"[$from:$to]"
    }
    def formatRangeValue(v: Option[Number]): String = if (v.isDefined) formatNumber(v.get) else "*"

    def data(dataRows: Seq[DataRow]) = dataRows.zipWithIndex.map {
      case (dr, i) => s"""
                         |$$Mydata$i << EOD
                         |${values(dr.data)}
                         |EOD
                         |""".stripMargin.trim
    }.mkString("\n")

    def series(dataRows: Seq[DataRow]) = dataRows.zipWithIndex.map {
      case (dr, i) => s"""$$Mydata$i using 1:2 title ' ${dr.name}' with lines"""
    }.mkString(", \\\n")


    def xLabel: String  = if (dia.xLabel.isDefined) s"""set xlabel "${dia.xLabel.get}"""" else ""
    def yLabel: String  = if (dia.yLabel.isDefined) s"""set ylabel "${dia.yLabel.get}"""" else ""
    def xRange: String  = if (dia.xRange.isDefined) s"""set xrange ${formatRange(dia.xRange.get)}""" else ""
    def yRange: String  = if (dia.yRange.isDefined) s"""set yrange ${formatRange(dia.yRange.get)}""" else ""

    val lp = dia.legendPlacement match {
      case LegendPlacement_LEFT => "left"
      case LegendPlacement_RIGHT => "right"
    }

    val script =
      s"""
         |set terminal pngcairo enhanced size 800, 400
         |set output '${dia.id}.png'
         |set key inside $lp top vertical Right noreverse enhanced autotitle box lt black linewidth 1.000 dashtype solid
         |set minussign
         |set title "${dia.title}"
         |$xLabel
         |$yLabel
         |$xRange
         |$yRange
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
