package vsoc.ga.trainga.ga

import java.io.{ObjectInputStream, ObjectOutputStream}

import vsoc.ga.common.UtilReflection
import vsoc.ga.genetic.PopGeno

class TrainGaPersist[S] {

  def save(trainga: TrainGa[S], oos: ObjectOutputStream): Unit = {
    require(trainga.iterations.isDefined, "iterations must be defined")
    require(trainga.population.isDefined, "pupulation must be defined")
    val cont = TrainGaContainer(trainga.id, trainga.iterations.get, UtilTransformGeno.asArrayGenoDouble(trainga.population.get.genos))
    oos.writeObject(cont)
  }

  def load(ois: ObjectInputStream): TrainGa[S] = {
    val cont = ois.readObject().asInstanceOf[TrainGaContainer]
    val tga = UtilReflection.call(TrainGas, cont.id, classOf[TrainGa[S]])
    tga.iterations = Some(cont.iterations)
    tga.population = Some(PopGeno(UtilTransformGeno.toSeqGeno(cont.population)))
    tga
  }

}

@SerialVersionUID(-3164640124303372341L)
case class TrainGaContainer(id: String, iterations: Int, population: Array[Array[Double]]) {}
