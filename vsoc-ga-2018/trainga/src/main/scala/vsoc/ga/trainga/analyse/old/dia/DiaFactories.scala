package vsoc.ga.trainga.analyse.old.dia

import vsoc.ga.trainga.analyse.old.dia.DataDia.FDia
import vsoc.ga.trainga.ga.impl.team01.DataBase

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
