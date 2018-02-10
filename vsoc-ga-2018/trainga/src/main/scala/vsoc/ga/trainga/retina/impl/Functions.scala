package vsoc.ga.trainga.retina.impl

object Functions {

  def linpeak(center: Double, width: Double)(x: Double): Double = {
    val x1 = x - center
    val w1 = width / 2.0
    val k = 1 / w1
    if (x1 <= -w1 || x1 >=  w1) 0.0
    else if (x1 < 0 ) 1 - (x1 * - k)
    else 1 - (x1 * k)
  }

}
