package vsoc.ga.trainga.ga

object TrainGaDescriptions extends App {

  val descs = Seq(
    TrainGas.trainGaPlayer01C,
  )

  for((d, i) <- descs.zipWithIndex) {
    if (i == 0) println("--------------------------------------------------")
    print(d.fullDesc)
    println("--------------------------------------------------")
  }



}
