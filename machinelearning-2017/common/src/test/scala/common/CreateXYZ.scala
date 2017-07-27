package common

import java.util.Locale

object CreateXYZ extends App {

  val data = for (x <- (0 to 10 by 1); y <- (0 to 30 by 5)) yield {
    val z = 2.5 * (math.sin(x / 20.0) + math.cos(y / 5.0))
    "%5d %5d %10.3f" formatLocal(Locale.ENGLISH, x, y, z)
  }

  println(data.mkString("\n"))
}
