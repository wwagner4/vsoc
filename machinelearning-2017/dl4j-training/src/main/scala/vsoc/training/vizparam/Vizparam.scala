package vsoc.training.vizparam

import java.io.{File, PrintWriter}

import vsoc.common.{Dat, Formatter}
import vsoc.training.{MetaParam, MetaParamRun}

object Vizparam {

  import vsoc.common.UtilIO._

  def fileHtml(run: MetaParamRun, dir: File, fileName: String): Unit = {
    val file = new File(dir, fileName)
    val content = html(run)
    use(new PrintWriter(file)) { _.print(content) }
  }

  def html(run: MetaParamRun): String = {
    s"""
       |<html>
       |<head>
       |$css
       |</head
       |<body>
       |</body>
       |${table(run)}
       |</html>
     """.stripMargin
  }

  def css: String = {
    s"""
       |<style>
       |$cssProps
       |</style>
     """.stripMargin
  }

  def cssProps: String = {
    """
      |table.darkTable {
      |  font-family: Tahoma, Geneva, sans-serif;
      |  border: 1px solid #000000;
      |  background-color: #777777;
      |  width: 100%;
      |  text-align: center;
      |  border-collapse: collapse;
      |  text-padding: 20px;
      |}
      |table.darkTable td, table.darkTable th {
      |  border: 1px solid #000000;
      |  padding: 5px;
      |}
      |table.darkTable tbody td {
      |  font-size: 13px;
      |  color: #E6E6E6;
      |}
      |table.darkTable tr:nth-child(even) {
      |  padding: 5px;
      |  background: #666666;
      |}
    """.stripMargin
  }

  def table(run: MetaParamRun): String = {
    s"""
       |<table class="darkTable">
       |${rows(run)}
       |</table>
     """.stripMargin
  }

  def rows(run: MetaParamRun): String = {
    (for ((k, v) <- PropsManager.toMultiProps(run)) yield {
      s"""
         |<tr>
         |<td>$k</td>
         |<td>${v.mkString(", ")}</td>
         |</tr>
         """.stripMargin
    }).mkString("")
  }


  object PropsManager {

    def toMultiProps(run: MetaParamRun): Seq[(String, Seq[String])] = {
      val mps: Seq[Seq[(String, String)]] = run.series.flatMap(ser => ser.metaParams.map(mp => toProps(mp)))
      val re0: Seq[(String, Seq[String])] = reduce(mps)
      val re1 = ("class", Seq(run.clazz)) +: re0
      if (run.description.isDefined) {
        ("title", Seq(run.description.get)) +: re1
      } else {
        ("title", Seq(run.series(0).description)) +: re1
      }
    }

    def toProps(mp: MetaParam): Seq[(String, String)] = Seq(
      ("learningRate", formatDoubleExp(mp.learningRate)),
      ("trainingData", formatDataDesc(mp.trainingData)),
      ("batchSizeTrainingDataRelative", formatDouble(mp.batchSizeTrainingDataRelative)),
      ("testData", formatDataDesc(mp.testData)),
      ("iterations", "" + mp.iterations),
      ("seed", "" + mp.seed)
    )

    def reduce(mps: Seq[Seq[(String, String)]]): Seq[(String, Seq[String])] = {
      mps match {
        case Nil => throw new IllegalStateException("mps must not be empty")
        case a :: Nil =>
          val empty = createEmpty(a)
          merge(empty, a)
        case a :: b =>
          val reduced = reduce(b)
          merge(reduced, a)
      }
    }

    def createEmpty(mp: Seq[(String, String)]): Seq[(String, Seq[String])] = {
      mp.map { case (k, v) => (k, Seq.empty[String]) }
    }

    def merge(multiMp: Seq[(String, Seq[String])], mp: Seq[(String, String)]): Seq[(String, scala.Seq[String])] = {
      for (((multiKey, multiVal), i) <- multiMp.zipWithIndex) yield {
        val (propKey, propVal) = mp(i)
        if (multiKey != propKey) throw new IllegalStateException(s"Keys not matching. $multiMp <--> $mp")
        if (multiVal.contains(propVal)) {
          (multiKey, multiVal)
        } else {
          (multiKey, propVal +: multiVal)
        }
      }
    }

    def formatDouble(value: Double): String = {
      Formatter.formatNumber("%.2f", value)
    }

    def formatDoubleExp(value: Double): String = {
      Formatter.formatNumber("%.1E", value)
    }

    def formatDataDesc(value: Dat.DataDesc): String = {
      s"${value.data.code} ${value.id.code} ${value.size.size}"
    }

  }

}
