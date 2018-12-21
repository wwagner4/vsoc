package vsoc.ga.matches.nn

object NeuralNetsInfo extends App {

  val nets = Seq(
    NeuralNets.default,
    NeuralNets.team01,
    NeuralNets.team02,
    NeuralNets.test,
  )

  nets.foreach{nn =>
    val parLen = nn.getParam.length
    println(s"${nn.id} par len: $parLen")
  }

}
