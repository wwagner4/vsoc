package vsoc.ga.trainga.nn.analyse

import vsoc.ga.trainga.nn.NeuralNets

object InputDataHandlers {

  def stdOut: InputDataHandler = {
    (in: Array[Double]) => println(in.toList.mkString(", "))
  }

  def nnTeam01: InputDataHandler = {
     val nn = NeuralNets.team01

    (in: Array[Double]) => {
      println(nn.output(in).toList.mkString(", "))
    }
  }

}
