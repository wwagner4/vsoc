package vsoc.ga.common.describe

import java.util.Locale

object DescribableFormatter {

  def format(desc: Describable, depth: Int): String = {
    indent(desc.fullDesc, depth)
  }

  def format(properties: Seq[(String, Any)], depth: Int): String = {
    val lines: Seq[String] = for ((prop, d) <- properties.zip(Stream.continually(depth))) yield {
      formatProp(prop, d)
    }
    indent(lines.mkString("\n"), depth)
  }


  /** -------- private --------- **/

  private val propSepa = " : "

  private def formatProp(prop: (String, Any), depth: Int): String = prop match {
    case (k: String, v: Describable) => formatKey(k) + propSepa + "\n" + format(v, depth + 1)
    case (k: String, v: String) => formatKey(k) + propSepa + formatStringValue(v)
    case (k: String, v: Double) => formatKey(k) + propSepa + formatDoubleValue(v)
    case (k: String, v: Any) => formatKey(k) + propSepa + formatAnyValue(v)
  }

  private def formatStringValue(v: String) =
    "%10s" formatLocal(Locale.ENGLISH, trim(v, 100))

  private def formatDoubleValue(v: Double) =
    "%f" formatLocal(Locale.ENGLISH, v)

  private def formatAnyValue(v: Any) =
    "%10s" formatLocal(Locale.ENGLISH, trim(v.toString, 100))

  private def formatKey(key: String) =
    "%10s" formatLocal(Locale.ENGLISH, trim(key, 10))


  private def trim(str: String, maxLen: Int): String = {
    require(maxLen > 6)

    def shorten(v: String) = {
      val v1 = v.substring(0, maxLen - 4)
      val v2 = v.substring(v.length - 2, v.length)
      v1 + ".." + v2
    }

    val v1 = str.trim
    if (v1.length <= maxLen) v1
    else shorten(v1)
  }

  private def indent(txt: String, depth: Int): String = {

    def prefix: String =
      Stream.fill(depth)("           : ").take(depth).mkString("")

    def addPrefix(l: String): String =
      prefix + l

    val lines = txt.split("\n")
    lines.map(addPrefix).mkString("\n")
  }


}
