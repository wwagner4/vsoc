package vsoc.common

class Datasets {

  sealed trait Sizes {def size: Int}

  case object Sizes_10 extends Sizes {val size = 10}
  case object Sizes_100 extends Sizes {val size = 100}
  case object Sizes_1000 extends Sizes {val size = 1000}
  case object Sizes_5000 extends Sizes {val size = 5000}
  case object Sizes_10000 extends Sizes {val size = 10000}
  case object Sizes_50000 extends Sizes {val size = 50000}
  case object Sizes_100000 extends Sizes {val size = 100000}
  case object Sizes_500000 extends Sizes {val size = 500000}
  case object Sizes_1000000 extends Sizes {val size = 1000000}
  case object Sizes_5000000 extends Sizes {val size = 5000000}
  case object Sizes_10000000 extends Sizes {val size = 100000000}

}
