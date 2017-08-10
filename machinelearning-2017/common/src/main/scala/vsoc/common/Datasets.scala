package vsoc.common

object Datasets {

  sealed trait Size {
    def size: Int
  }

  case object Size_10 extends Size {
    val size = 10
  }

  case object Size_100 extends Size {
    val size = 100
  }

  case object Size_1000 extends Size {
    val size = 1000
  }

  case object Size_5000 extends Size {
    val size = 5000
  }

  case object Size_10000 extends Size {
    val size = 10000
  }

  case object Size_50000 extends Size {
    val size = 50000
  }

  case object Size_100000 extends Size {
    val size = 100000
  }

  case object Size_500000 extends Size {
    val size = 500000
  }

  case object Size_1000000 extends Size {
    val size = 1000000
  }

  case object Size_5000000 extends Size {
    val size = 5000000
  }

  case object Size_10000000 extends Size {
    val size = 100000000
  }

  sealed trait Id {
    def code: String
  }

  case object Id_A extends Id {
    val code = "A"
  }

  case object Id_B extends Id {
    val code = "B"
  }

  case object Id_C extends Id {
    val code = "C"
  }

  case object Id_D extends Id {
    val code = "D"
  }

  case object Id_E extends Id {
    val code = "E"
  }

  case object Id_F extends Id {
    val code = "F"
  }

  sealed trait Data {
    def code: String
  }

  case object Data_PLAYERPOS_X extends Data {
    val code = "playerpos_x"
  }

  case class DataDesc(data: Data, id: Id, size: Size) {
    def filename = s"vsoc_${data.code}_${id.code}_${size.size}.csv"
  }

}

