package vsoc.ga.analyse.dia

import vsoc.ga.analyse.dia.DataDia.FDia
import vsoc.ga.analyse.iterations.iter05.DiaFactoriesB04.Cat
import vsoc.ga.common.data.DataBase

trait DiaFactories[T <: DataBase] {

  case class Cat(
                  title: String,
                  id: String,
                  trainGaNrs: Seq[String],
                )

  def trainGaId: String

  def diaFactories: Seq[FDia[T]]

  def filterCat(data: Seq[T], cat: Cat): Seq[(String, Seq[T])] = {
    data.filter(d => cat.trainGaNrs.contains(d.trainGaNr))
      .groupBy(d => d.trainGaNr)
      .toSeq.sortBy { case (k, _) => k }
  }



}
