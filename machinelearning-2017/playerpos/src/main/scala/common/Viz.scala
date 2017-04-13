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
  case class Style_LINES(size: Double) extends Style
  case object Style_POINTS extends Style
  case class Style_POINTS(size: Double) extends Style
  case object Style_DOTS extends Style
  case object Style_LINEPOINTS extends Style
  
  case class XY(
                 x: Number,
                 y: Number
               )

  case class DataRow(
                      name: String,
                      style: Style = Style_LINES,
                      data: Seq[XY] = Seq.empty
                    )

  sealed trait Dia {
    def id: String
  }

  case class Diagram(
                      id: String,
                      title: String,
                      imgWidth: Int = 800,
                      imgHeight: Int = 600,
                      xLabel: Option[String] = None,
                      yLabel: Option[String] = None,
                      xRange: Option[Range] = None,
                      yRange: Option[Range] = None,
                      legendPlacement: LegendPlacement = LegendPlacement_LEFT,
                      legendTitle: Option[String] = None,
                      dataRows: Seq[DataRow] = Seq.empty
                    ) extends Dia

  case class MultiDiagram(
                           id: String,
                           rows: Int,
                           columns: Int,
                           title: Option[String] = None,
                           imgWidth: Int = 800,
                           imgHeight: Int = 600,
                           diagrams: Seq[Diagram]
                         ) extends Dia

  case class Range(
                    from: Option[Number],
                    to: Option[Number]
                  )

  def createDiagram(dia: Dia)(implicit creator: VizCreator): Unit = {
    dia match {
      case d: Diagram => creator.createDiagram(d)
      case md: MultiDiagram => creator.createMultiDiagram(md)
    }
  }
}

/**
  * Interface for actual data visualisation
  */
trait VizCreator {

  def createDiagram(dia: Diagram): Unit = {
    val script1 = createDiagramInit(dia)
    val script2 = createDiagramData(dia, script1)
    val script3 = createDiagramCommands(dia, script2)
    create(dia, script3)
  }

  def createMultiDiagram(mdia: MultiDiagram): Unit = {
    val script1 = createMultiDiagramInit(mdia)
    val script2 = mdia.diagrams.foldRight(script1) { (dia, script) => createDiagramData(dia, script) }
    val script3 = mdia.diagrams.foldRight(script2) { (dia, script) => createDiagramCommands(dia, script) }
    val script4 = createMultiDiagramClose(mdia, script3)
    create(mdia, script4)
  }

  def createDiagramInit(dia: Diagram): String

  def createDiagramData(dia: Diagram, script: String): String

  def createDiagramCommands(dia: Diagram, script: String): String

  def createMultiDiagramInit(mdia: MultiDiagram): String

  def createMultiDiagramClose(mdia: MultiDiagram, script: String): String

  def create(dia: Dia, script: String): Unit

}

/**
  * An implementation for data visualisation using gnuplot
  *
  * @param outDir Directory in which gnuplot scripts are created
  */
case class VizCreatorGnuplot(outDir: File, execute: Boolean = true) extends VizCreator {

  def create(dia: Dia, script: String): Unit = {

    val id = dia.id
    val filename = s"$id.gp"
    val f = new File(outDir, filename)
    Util.writeToFile(f, pw => pw.print(script))
    println(s"wrote diagram '$id' to $f")

    if (execute) {
      exec(f, outDir)
    }

    def exec(script: File, workdir: File): Unit = {
      import scala.sys.process._
      val cmd = s"gnuplot ${script.getName}"
      val result = Process(cmd, cwd = workdir).!
      if (result != 0) {
        println(s"executed -> '$cmd' -> ERROR '$result'")
      } else {
        println(s"executed -> '$cmd'")
      }
    }

  }

  def createMultiDiagramInit(mdia: MultiDiagram): String = {
    val titleString = if(mdia.title.isDefined) s"title '${mdia.title.get}'" else ""
    s"""
       |set terminal pngcairo enhanced size ${mdia.imgWidth}, ${mdia.imgHeight}
       |set output '${mdia.id}.png'
       |set multiplot layout ${mdia.rows}, ${mdia.columns} $titleString
       |""".stripMargin
  }

  def createMultiDiagramClose(mdia: MultiDiagram, script: String): String = {
    script + s"""
       |unset multiplot
       |""".stripMargin
  }

  def createDiagramInit(dia: Diagram): String = {
    s"""
       |set terminal pngcairo enhanced size ${dia.imgWidth}, ${dia.imgHeight}
       |set output '${dia.id}.png'
       |""".stripMargin
  }

  def createDiagramData(dia: Diagram, script: String): String = {

    def values(values: Seq[XY]) = values.map {
      xy: XY =>
        val x = formatNumber(xy.x)
        val y = formatNumber(xy.y)
        s"$x $y"
    }.mkString("\n")

    def formatNumber(n: Number): String = "" + n

    def data(dataRows: Seq[DataRow]) = dataRows.zipWithIndex.map {
      case (dr, i) => s"""
                         |${datablockName(dia, i)} << EOD
                         |${values(dr.data)}
                         |EOD
                         |""".stripMargin.trim
    }.mkString("\n")

    script + "\n" + data(dia.dataRows)
  }

  def datablockName(dia: Dia, i: Int): String = s"$$data_${dia.id}_$i"

  def createDiagramCommands(dia: Diagram, script: String): String = {

    def formatNumber(n: Number): String = "" + n

    def formatRange(r: Range): String = {
      val from = formatRangeValue(r.from)
      val to = formatRangeValue(r.to)
      s"[$from:$to]"
    }

    def formatRangeValue(v: Option[Number]): String = if (v.isDefined) formatNumber(v.get) else "*"

    def mapStyle(style: Viz.Style): String = style match {
      case Viz.Style_POINTS => "points"
      case Viz.Style_POINTS(size) => s"points ps $size"
      case Viz.Style_LINES => "lines"
      case Viz.Style_LINES(size) => s"lines lw $size"
      case Viz.Style_DOTS => "dots"
      case Viz.Style_LINEPOINTS => "linepoints"
    }

    def series(dataRows: Seq[DataRow]) = dataRows.zipWithIndex.map {
      case (dr, i) =>
        val style = mapStyle(dr.style)
        s"""${datablockName(dia, i)} using 1:2 title '${dr.name}' with $style"""
    }.mkString(", \\\n")


    def xLabel: String = if (dia.xLabel.isDefined) s"""set xlabel "${dia.xLabel.get}"""" else ""

    def yLabel: String = if (dia.yLabel.isDefined) s"""set ylabel "${dia.yLabel.get}"""" else ""

    def xRange: String = if (dia.xRange.isDefined) s"""set xrange ${formatRange(dia.xRange.get)}""" else ""

    def yRange: String = if (dia.yRange.isDefined) s"""set yrange ${formatRange(dia.yRange.get)}""" else ""

    def legendTitle: String = if (dia.legendTitle.isDefined) s"""title "${dia.legendTitle.get}""" else ""

    val lp = dia.legendPlacement match {
      case LegendPlacement_LEFT => "left"
      case LegendPlacement_RIGHT => "right"
    }

    script +
      s"""
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

  }


}
