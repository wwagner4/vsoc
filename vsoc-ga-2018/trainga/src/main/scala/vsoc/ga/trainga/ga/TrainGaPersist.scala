package vsoc.ga.trainga.ga

import java.io.{ObjectInputStream, ObjectOutputStream}

import vsoc.ga.common.UtilReflection

class TrainGaPersist[S] {

  import vsoc.ga.common.UtilTransform._

  def save(trainga: TrainGa[S], oos: ObjectOutputStream): Unit = {
    val cont = TrainGaContainer(trainga.id, trainga.iterations, asArray(trainga.population))
    oos.writeObject(cont)
  }

  def load(ois: ObjectInputStream): TrainGa[S] = {
    val cont = ois.readObject().asInstanceOf[TrainGaContainer]
    val tga = UtilReflection.call(TrainGas, cont.id, classOf[TrainGa[S]])
    tga.iterations = cont.iterations
    tga.population = toSeq(cont.population)
    tga
  }

}

@SerialVersionUID(-3164640124303372341L)
case class TrainGaContainer(id: String, iterations: Int, population: Array[Array[Double]]) {}
