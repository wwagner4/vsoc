package tryout

object BellTryoutMain extends App {


  def f(x: Double): Double = math.pow(math.E, -(x * x) / 3000 )

  (-100.0 to (100.0, 20.0)).foreach{x =>
    val y = f(x)
    println(f"$x%10.3f $y%10.3f")

  }

}
