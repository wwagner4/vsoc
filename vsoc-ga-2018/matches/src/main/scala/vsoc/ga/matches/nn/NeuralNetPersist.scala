package vsoc.ga.matches.nn

import java.io.{ObjectInputStream, ObjectOutputStream}

import vsoc.ga.common.UtilReflection

object NeuralNetPersist {

  def save(nn: NeuralNet, oos: ObjectOutputStream): Unit = {
    val obj = NeuralNetContainer(nn.id, nn.getParam)
    oos.writeObject(obj)
  }

  def load(ois: ObjectInputStream): NeuralNet = {
    val cont = ois.readObject.asInstanceOf[NeuralNetContainer]
    val nn = UtilReflection.call(NeuralNets, cont.id, classOf[NeuralNet])
    nn.setParam(cont.params)
    nn
  }

}

case class NeuralNetContainer(id: String, params: Array[Double]) extends Serializable
