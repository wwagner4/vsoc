package common

import java.io.File

import common.Viz._

/**
  * Interface for data visualisation
  */
object Viz {

  sealed trait LegendPlacement
  case object LegendPlacement_LEFT extends LegendPlacement
  case object LegendPlacement_RIGHT extends LegendPlacement

  sealed trait Style
  case object Style_LINES extends Style
  case object Style_POINTS extends Style

  case class XY(
                 x: Number,
                 y: Number
               )

  case class DataRow(
                      name: String,
                      style: Style = Style_LINES,
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
                      legendTitle: Option[String] = None,
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

/**
  * Interface for actual data visualisation
  */
trait VizCreator {

  def createDiagram(dia: Diagram): Unit

}

/**
  * An implementation for data visualisation using gnuplot
  * @param outDir Directory in which gnuplot scripts are created
  */
case class VizCreatorGnuplot(outDir: File, execute: Boolean = true) extends VizCreator {


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


    def mapStyle(style: Viz.Style): String = style match {
      case Viz.Style_POINTS => "points"
      case Viz.Style_LINES => "lines"
    }

    def series(dataRows: Seq[DataRow]) = dataRows.zipWithIndex.map {
      case (dr, i) =>
        val style = mapStyle(dr.style)
        s"""$$Mydata$i using 1:2 title ' ${dr.name}' with $style"""
    }.mkString(", \\\n")


    def xLabel: String  = if (dia.xLabel.isDefined) s"""set xlabel "${dia.xLabel.get}"""" else ""
    def yLabel: String  = if (dia.yLabel.isDefined) s"""set ylabel "${dia.yLabel.get}"""" else ""
    def xRange: String  = if (dia.xRange.isDefined) s"""set xrange ${formatRange(dia.xRange.get)}""" else ""
    def yRange: String  = if (dia.yRange.isDefined) s"""set yrange ${formatRange(dia.yRange.get)}""" else ""
    def legendTitle: String  = if (dia.legendTitle.isDefined) s"""title "${dia.legendTitle.get}""" else ""

    val lp = dia.legendPlacement match {
      case LegendPlacement_LEFT => "left"
      case LegendPlacement_RIGHT => "right"
    }

    val script =
      s"""
         |${data(dia.dataRows)}
         |set terminal pngcairo enhanced size 800, 600
         |set output 'img_${dia.id}.png'
         |set key inside $lp top vertical Right noreverse enhanced autotitle box lt black linewidth 1.000 dashtype solid $legendTitle
         |set minussign
         |set title "${dia.title}"
         |$xLabel
         |$yLabel
         |$xRange
         |$yRange
         |plot \\
         |${series(dia.dataRows)}
         |""".stripMargin

    val id = dia.id
    val filename = s"diagram_$id.gnuplot"
    val f = new File(outDir, filename)
    Util.writeToFile(f, pw => pw.print(script))
    println(s"wrote diagram '$id' to $f")

    if (execute) {
      exec(f, outDir)
    }

    def exec(script: File, workdir: File): Unit = {
      import scala.sys.process._
      val cmd = s"gnuplot ${script.getName}"
      val result = Process(cmd, cwd=workdir).!
      if (result != 0) {
        println(s"executed -> '$cmd' -> ERROR '$result'")
      } else {
        println(s"executed -> '$cmd'")
      }
    }
  }


}
