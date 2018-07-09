package vsoc.ga.trainga.ga

import java.io.{ObjectInputStream, ObjectOutputStream}

import vsoc.ga.common.UtilReflection

class TrainGaPersist[S] {

  import vsoc.ga.common.UtilTransform._

  def save(trainga: TrainGa[S], oos: ObjectOutputStream): Unit = {
    require(trainga.iterations.isDefined, "iterations must be defined")
    require(trainga.population.isDefined, "pupulation must be defined")
    val cont = TrainGaContainer(trainga.id, trainga.iterations.get, asArray(trainga.population.get))
    oos.writeObject(cont)
  }

  def load(ois: ObjectInputStream): TrainGa[S] = {
    val cont = ois.readObject().asInstanceOf[TrainGaContainer]
    val tga = UtilReflection.call(TrainGas, cont.id, classOf[TrainGa[S]])
    tga.iterations = Some(cont.iterations)
    tga.population = Some(toSeq(cont.population))
    tga
  }

}

case class TrainGaContainer(id: String, iterations: Int, population: Array[Array[Double]])
