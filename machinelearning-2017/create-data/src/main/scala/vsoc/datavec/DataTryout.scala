package vsoc.datavec

import vsoc.common.Datasets
import vsoc.datavec.playerpos.CreateData

/**
  * Tryout the things you want to do
  */
object DataTryout extends App {


  val ds = Datasets.DataDesc(Datasets.Data_PLAYERPOS_X, Datasets.Id_B, Datasets.Size_100)

  val f = CreateData.createDataFile(ds)

  println("created " + f.getAbsolutePath)
}
