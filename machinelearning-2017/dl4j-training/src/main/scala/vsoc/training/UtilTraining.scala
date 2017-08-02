package vsoc.training

import common.Viz
import org.nd4j.linalg.api.ndarray.INDArray

object UtilTraining {

  /**
    * @param data NDArray containing the data
    * @param colIdx Indexes of the colums to be selected
    * @return A sequence of XYZ values from tree colums of an NDArray
    */
  def convert(data: INDArray, colIdx: (Int, Int, Int)): Seq[Viz.XYZ] = {
    val x = data.getColumns(colIdx._1).data.asDouble()
    val y = data.getColumns(colIdx._2).data.asDouble()
    val z = data.getColumns(colIdx._3).data.asDouble()
    x.zip(y.zip(z)).map { case (cx, (cy, cz)) => Viz.XYZ(cx, cy, cz) }
  }

}
