package vsoc.ga.analyse.dia

import entelijan.viz.Viz
import entelijan.viz.Viz.Dia

trait DiaFactory[T] {

  def createDia(id: String, data: Seq[T]): Dia[Viz.XY]

}
