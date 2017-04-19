package tryout

object ForTryoutMain extends App {
  {
    val r = for (i <- 1 to 2; k <- List("A", "B", "C")) {
      val r = (i, k)
      println(r)
      r
    }
    println(r)
	}
  {
    val r = for (
      i <- 1 to 7 if (i % 3 == 0); 
      j <- 1 to 4 if (j % 2 == 0)
      ) yield (i, j) 
    println(r)
	}
}
