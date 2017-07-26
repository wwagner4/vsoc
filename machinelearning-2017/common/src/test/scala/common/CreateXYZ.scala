package common

import java.util.Locale

object CreateXYZ extends App {

  val data = for (x <- (0 to 20 by 5); y <- (0 to 30 by 5)) yield {
    val z = 5.0 * (math.sin((x + 50.0) / 30.0) + math.cos(y / 10.0))
    "%5d %5d %10.3f" formatLocal(Locale.ENGLISH, x, y, z)
  }

  println(data.mkString("\n"))
}
