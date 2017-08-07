package vsoc.common

import java.io.{File, PrintWriter}
import java.util.Locale

import vsoc.common.Viz._

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
  case object Style_LINESDASHED extends Style
  case class Style_LINESDASHED(size: Double) extends Style
  case object Style_POINTS extends Style
  case class Style_POINTS(size: Double) extends Style
  case object Style_DOTS extends Style
  case object Style_LINESPOINTS extends Style
  case object Style_BOXPLOT extends Style

  sealed trait DataDim

  case object DataDim_1D extends DataDim
  case object DataDim_2D extends DataDim
  case object DataDim_3D extends DataDim

  trait Lineable {
    def line(f: Number => String): String
    def dataDim: DataDim
  }

  case class X (
                  x: Number
                ) extends Lineable {
    def line(f: Number => String): String = f(x)
    def dataDim = DataDim_1D
  }

  case class XY (
                  x: Number,
                  y: Number
                ) extends Lineable {
    def line(f: Number => String): String = {
      val sx = f(x)
      val sy = f(y)
      sx + " " + sy
    }
    def dataDim = DataDim_2D
  }

  case class XYZ (
                   x: Number,
                   y: Number,
                   z: Number
                ) extends Lineable {
    def line(f: Number => String): String = {
      val sx = f(x)
      val sy = f(y)
      val sz = f(z)
      sx + " " + sy + " " + sz
    }
    def dataDim = DataDim_3D
  }

  case class DataRow[T <: Lineable](
                      name: Option[String] = None,
                      style: Style = Style_LINES,
                      data: Seq[T] = Seq.empty
                    ) {
    def dataDim: DataDim = {
      if (data.isEmpty) throw new IllegalStateException("Cannot determine dimension because list of data is empty")
      else data(0).dataDim
    }
  }

  sealed trait Dia[T <: Lineable] {
    def id: String
    def dataDim: DataDim
  }

  case class Diagram[T <: Lineable](
                      id: String,
                      title: String,
                      imgWidth: Int = 800,
                      imgHeight: Int = 600,
                      xLabel: Option[String] = None,
                      yLabel: Option[String] = None,
                      zLabel: Option[String] = None,
                      xRange: Option[Range] = None,
                      yRange: Option[Range] = None,
                      zRange: Option[Range] = None,
                      xyGrid: Int = 100,
                      legendPlacement: LegendPlacement = LegendPlacement_LEFT,
                      legendTitle: Option[String] = None,
                      dataRows: Seq[DataRow[T]] = Seq.empty
                    ) extends Dia[T] {
    def dataDim: DataDim =
      if (dataRows.isEmpty) throw new IllegalStateException("Cannot determine data dimension because no data are defined")
      else dataRows(0).dataDim
  }

  case class MultiDiagram[T <: Lineable](
                           id: String,
                           columns: Int,
                           title: Option[String] = None,
                           imgWidth: Int = 800,
                           imgHeight: Int = 600,
                           diagrams: Seq[Diagram[T]]
                         ) extends Dia[T] {
    def rows: Int = math.ceil(diagrams.size.toDouble / columns).toInt
    def dataDim: DataDim =
      if (diagrams.isEmpty) throw new IllegalStateException("Cannot determine data dimension because no diagram is defined")
      else diagrams(0).dataDim
  }

  case class Range(
                    from: Option[Number],
                    to: Option[Number]
                  )

  def createDiagram[T <: Lineable](dia: Dia[T])(implicit creator: VizCreator[T]): Unit = {
    dia match {
      case d: Diagram[T] => creator.createDiagram(d)
      case md: MultiDiagram[T] => creator.createMultiDiagram(md)
    }
  }
}

/**
  * Interface for actual data visualisation
  */
trait VizCreator[T <: Lineable] {

  def createDiagram(dia: Diagram[T]): Unit = {
    val script1 = createDiagramInit(dia)
    val script2 = createDiagramData(dia, 0, script1)
    val script3 = createDiagramCommands(dia, 0, script2)
    create(dia, script3)
  }

  def createMultiDiagram(mdia: MultiDiagram[T]): Unit = {
    val script1 = createMultiDiagramInit(mdia)
    val dias: Seq[(Diagram[T], Int)] = mdia.diagrams.reverse.zipWithIndex
    val script2 = dias.foldRight(script1) { (diaAndIdx, script) => createDiagramData(diaAndIdx._1, diaAndIdx._2,script) }
    val script3 = dias.foldRight(script2) { (diaAndIdx, script) => createDiagramCommands(diaAndIdx._1, diaAndIdx._2, script) }
    val script4 = createMultiDiagramClose(mdia, script3)
    create(mdia, script4)
  }

  def createDiagramInit(dia: Diagram[T]): String

  def createDiagramData(dia: Diagram[T], diaIndex: Int, script: String): String

  def createDiagramCommands(dia: Diagram[T], diaIndex: Int, script: String): String

  def createMultiDiagramInit(mdia: MultiDiagram[T]): String

  def createMultiDiagramClose(mdia: MultiDiagram[T], script: String): String

  def create(dia: Dia[T], script: String): Unit

}

/**
  * An implementation for data visualisation using gnuplot
  *
  * @param scriptDir Directory in which gnuplot scripts are created
  */
case class VizCreatorGnuplot[T <: Lineable](scriptDir: File, imageDir: File, execute: Boolean = true) extends VizCreator[T] {

  def create(dia: Dia[T], script: String): Unit = {

    val id = dia.id
    val filename = s"$id.gp"
    val f = new File(scriptDir, filename)
    UtilIO.use(new PrintWriter(f))(pw => pw.print(script))
    println(s"wrote diagram '$id' to $f")

    if (execute) {
      exec(f, scriptDir)
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

  def createMultiDiagramInit(mdia: MultiDiagram[T]): String = {
    val titleString = if(mdia.title.isDefined) s"title '${mdia.title.get}'" else ""
    s"""
       |set terminal pngcairo dashed enhanced size ${mdia.imgWidth}, ${mdia.imgHeight}
       |set output '${imageDir.getAbsolutePath}/${mdia.id}.png'
       |set multiplot layout ${mdia.rows}, ${mdia.columns} $titleString
       |""".stripMargin
  }

  def createMultiDiagramClose(mdia: MultiDiagram[T], script: String): String = {
    script + s"""
       |unset multiplot
       |""".stripMargin
  }

  def createDiagramInit(dia: Diagram[T]): String = {
    s"""
       |set terminal pngcairo dashed enhanced size ${dia.imgWidth}, ${dia.imgHeight}
       |set output '${imageDir.getAbsolutePath}/${dia.id}.png'
       |""".stripMargin
  }

  def createDiagramData(dia: Diagram[T], diaIndex: Int, script: String): String = {

    val loc = Locale.ENGLISH

    def values(values: Seq[T]) = values.map {
      lin => lin.line(formatNumber)
    }.mkString("\n")

    def formatNumber(n: Number): String = n match {
      case a :java.lang.Byte => "%d" formatLocal(loc, a.longValue())
      case a :java.lang.Integer => "%d" formatLocal(loc, a.longValue())
      case a :java.lang.Long => "%d" formatLocal(loc, a.longValue())
      case a: Any => "%f" formatLocal(loc, a.doubleValue())
    }

    def data(dataRows: Seq[DataRow[T]]): String = dataRows.zipWithIndex.map {
      case (dr, i) => s"""
                         |${datablockName(dia, diaIndex, i)} << EOD
                         |${values(dr.data)}
                         |EOD
                         |""".stripMargin.trim
    }.mkString("\n")

    script + "\n" + data(dia.dataRows)
  }

  def datablockName(dia: Dia[T], diaIndex: Int, dataIndex: Int): String = s"$$data_${dia.id}_${diaIndex}_$dataIndex"

  def createDiagramCommands(dia: Diagram[T], diaIndex: Int, script: String): String = {

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
      case Viz.Style_LINESDASHED => "lines dashtype 2"
      case Viz.Style_LINESDASHED(size) => s"lines lw $size dashtype 2"
      case Viz.Style_DOTS => "dots"
      case Viz.Style_LINESPOINTS => "linespoints"
      case Viz.Style_BOXPLOT => "boxplot"
    }

    def series(dataRows: Seq[DataRow[T]]) = dataRows.zipWithIndex.map {
      case (dr, i) =>
        def title: String = {
          if (dr.name.isDefined) "title '" + dr.name.get + "'"
          else "notitle"
        }

        def dim(nr: Int) = dr.dataDim match {
          case DataDim_1D => s"($nr):1"
          case DataDim_2D => "1:2"
          case DataDim_3D => "1:2:3"
        }
        val style = mapStyle(dr.style)
        s"""${datablockName(dia, diaIndex, i)} using ${dim(i + 1)} $title with $style"""
    }.mkString(", \\\n")


    def xLabel: String = if (dia.xLabel.isDefined) s"""set xlabel "${dia.xLabel.get}"""" else ""

    def yLabel: String = if (dia.yLabel.isDefined) s"""set ylabel "${dia.yLabel.get}"""" else ""

    def zLabel: String = if (dia.zLabel.isDefined) s"""set zlabel "${dia.zLabel.get}"""" else ""

    def xRange: String = if (dia.xRange.isDefined) s"""set xrange ${formatRange(dia.xRange.get)}""" else ""

    def yRange: String = if (dia.yRange.isDefined) s"""set yrange ${formatRange(dia.yRange.get)}""" else ""

    def zRange: String = if (dia.zRange.isDefined) s"""set zrange ${formatRange(dia.zRange.get)}""" else ""

    def legendTitle: String = if (dia.legendTitle.isDefined) s"""title "${dia.legendTitle.get}""" else ""

    def plotCmd: String =
      dia.dataDim match {
        case DataDim_1D => "plot"
        case DataDim_2D => "plot"
        case DataDim_3D => "splot"
      }

    def settings3D: String =
      dia.dataDim match {
        case DataDim_1D => ""
        case DataDim_2D => ""
        case DataDim_3D =>
          s"""
             |set dgrid3d ${dia.xyGrid},${dia.xyGrid}
             |set hidden3d
             |""".stripMargin
      }

    val lp = dia.legendPlacement match {
      case LegendPlacement_LEFT => "left"
      case LegendPlacement_RIGHT => "right"
    }

    def isBoxplot(dataRows: Seq[Viz.DataRow[T]]): Boolean = {
      if (dataRows.exists(_.style == Style_BOXPLOT )) {
        require(dataRows.exists(_.style == Style_BOXPLOT ), "If any datarow has style BOXPLOT all rows must be of style BOXPLOT")
        true
      } else {
        false
      }
    }

    def descr[U <: Lineable](dr: DataRow[U], idx: Int): String = {
      if (dr.name.isDefined) dr.name.get
      else "" + idx
    }

    def xtics(dataRows: Seq[Viz.DataRow[T]]): String =
      dataRows.zipWithIndex.map{ case(dr, idx) =>
        s"'${descr(dr, idx)}' ${idx + 1}"
      }.mkString(", ")

    def settings: String =
      if (isBoxplot(dia.dataRows))
        s"""
          |unset key
          |set style data boxplot
          |set xtics (${xtics(dia.dataRows)})
          |#set border 2
          |set xtics nomirror
          |set ytics nomirror
        """.stripMargin
      else
        s"""
          |set key inside $lp top vertical Right noreverse enhanced autotitle $legendTitle
        """.stripMargin

    script +
      s"""
         |$settings
         |set minussign
         |set title "${dia.title}"
         |$xLabel
         |$yLabel
         |$zLabel
         |$xRange
         |$yRange
         |$zRange
         |$settings3D
         |$plotCmd \\
         |${series(dia.dataRows)}
         |""".stripMargin

  }


}
