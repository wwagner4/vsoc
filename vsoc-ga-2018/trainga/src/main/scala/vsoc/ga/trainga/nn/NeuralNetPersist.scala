package vsoc.ga.trainga.nn

import java.io.{FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}
import java.nio.file.Path

import vsoc.ga.trainga.reflection.UtilReflection

object NeuralNetPersist {


  def save(nn: NeuralNet, file: Path): Unit = {

    def saveSer(s: Serializable, file: Path): Unit = {
      val oos = new ObjectOutputStream(new FileOutputStream(file.toFile))
      try {
        oos.writeObject(s)
      } finally {
        oos.close()
      }
    }
    saveSer(NeuralNetContainer(nn.id, nn.getParam), file)
  }

  def load(file: Path): NeuralNet = {
    var cont = Option.empty[NeuralNetContainer]
    val ois = new ObjectInputStream(new FileInputStream(file.toFile))
    try {
      cont = Some(ois.readObject.asInstanceOf[NeuralNetContainer])
    } finally {
      ois.close()
    }
    val nn = UtilReflection.call(NeuralNets, cont.get.id, classOf[NeuralNet])
    nn.setParam(cont.get.params)
    nn
  }

}

case class NeuralNetContainer(id: String, params: Array[Double]) extends Serializable
