package vsoc.ga.trainga.nn.analyse

object InputDataModelMain extends App {

  val runId = "00"

  val nums = Seq((5000, s"A$runId"), (5000, s"B$runId"), (5000, s"C$runId"), (5000, s"D$runId"))

  for ((num, id) <- nums.par) {
    new InputDataModel().run(InputDataHandlers.nnTeam01BoxPlots(id), num)
  }
  for ((num, id) <- nums.par) {
    new InputDataModel().run(InputDataHandlers.nnTeam02BoxPlots(id), num)
  }
}
