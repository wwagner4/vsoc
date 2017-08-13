package vsoc.datavec

import vsoc.common.Dat
import vsoc.datavec.playerpos.CreateData

/**
  * Tryout the things you want to do
  */
object DataTryout extends App {


  val ds = Dat.DataDesc(Dat.Data_PLAYERPOS_X, Dat.Id_B, Dat.Size_100)

  val f = CreateData.createDataFile(ds)

  println("created " + f.getAbsolutePath)
}
