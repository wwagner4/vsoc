package vsoc.ga.trainga.ga

object TrainGaDescriptions extends App {

  val descs = Seq(
    TrainGas.trainGa04G0,
    TrainGas.trainGa04K0,
    TrainGas.trainGa04M0,
    TrainGas.trainGa04M0om02,
    TrainGas.trainGa04M0om02varL,
    TrainGas.trainGa05fitFac01,
    TrainGas.trainGa05fitFac02,
    TrainGas.trainGa05fitFac03,
    TrainGas.trainGa05fitFac03a,
  )

  for((d, i) <- descs.zipWithIndex) {
    if (i == 0) println("--------------------------------------------------")
    println(d.fullDesc)
    println("--------------------------------------------------")
  }



}
