package vsoc.ga.trainga.ga

object TrainGaDescriptions extends App {

  val descs = Seq(
    TrainGas.trainGaB,
  )

  for((d, i) <- descs.zipWithIndex) {
    if (i == 0) println("--------------------------------------------------")
    println(d.fullDesc)
    println("--------------------------------------------------")
  }



}
