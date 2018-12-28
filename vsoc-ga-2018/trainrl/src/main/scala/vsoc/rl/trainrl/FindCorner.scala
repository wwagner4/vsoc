package vsoc.rl.trainrl


object FindCorner extends App {

  val DIM = 4 // Do not change

  println("STARTED Find Corner")
  val env = new Environment
  val ran = new Ran
  val strat = Strategies.random(env)
  val sizes = (1 to 1000) map { _ =>
    val mvs = moves(ran.ranAgent, 0)
    mvs.size
  }
  val sizes1 = sizes
    .groupBy(s => s)
    .toSeq
    .map { case (n: Int, l) => (n, l.size) }
    .sortBy{case (n, _) => n}

  println(sizes1)
  println("FINISHED Find Corner")


  def moves(agent: Agent, len: Int): List[Agent] = {
    if (len >= 100) Nil
    else {
      val x = agent.x
      val y = agent.y
      val cell = env.grid(x)(y)
      cell.state match {
        case STOP => agent :: Nil
        case MOVE =>
          val dir = strat.move(x, y)
          agent :: moves(Agent(x + dir.xoff, y + dir.yoff), len + 1)
      }
    }
  }

  sealed trait Direction {
    def xoff: Int

    def yoff: Int
  }

  case object U extends Direction {
    def xoff: Int = 0

    def yoff: Int = -1
  }

  case object D extends Direction {
    def xoff: Int = 0

    def yoff: Int = 1
  }

  case object R extends Direction {
    def xoff: Int = 1

    def yoff: Int = 0
  }

  case object L extends Direction {
    def xoff: Int = -1

    def yoff: Int = 0
  }

  sealed trait CellState

  case object MOVE extends CellState

  case object STOP extends CellState

  class Environment {

    val grid: Array[Array[Cell]] = Array.ofDim[Cell](DIM, DIM)
    grid(0)(0) = Cell(STOP, R, D)
    grid(1)(0) = Cell(MOVE, L, R, D)
    grid(2)(0) = Cell(MOVE, L, R, D)
    grid(3)(0) = Cell(MOVE, L, D)
    grid(0)(1) = Cell(MOVE, R, D)
    grid(1)(1) = Cell(MOVE, L, R, U, D)
    grid(2)(1) = Cell(MOVE, L, R, U, D)
    grid(3)(1) = Cell(MOVE, L, U, D)
    grid(0)(2) = Cell(MOVE, R, U, D)
    grid(1)(2) = Cell(MOVE, L, R, U, D)
    grid(2)(2) = Cell(MOVE, L, R, U, D)
    grid(3)(2) = Cell(MOVE, L, U, D)
    grid(0)(3) = Cell(MOVE, R, U)
    grid(1)(3) = Cell(MOVE, L, R, U)
    grid(2)(3) = Cell(MOVE, L, R, U)
    grid(3)(3) = Cell(STOP, L, U)

  }

  case class Cell(state: CellState, dir: Direction*)

  case class Agent(x: Int, y: Int)

  trait Strategy {

    def move(x: Int, y: Int): Direction

  }

  class Ran {
    private val ran = new scala.util.Random()

    private def ranPos: Int = ran.nextInt(DIM)

    def ranDir(cell: Cell): Direction = {
      val possibleDirs: Seq[Direction] = cell.dir
      val shuffeled: Seq[Direction] = ran.shuffle(possibleDirs)
      shuffeled(0)
    }

    def ranAgent: Agent =
      Agent(ranPos, ranPos)

  }

  object Strategies {

    def random(env: Environment): Strategy = {
      val dirs = Array.ofDim[Direction](DIM, DIM)
      for (x <- 0 until DIM)
        for (y <- 0 until DIM) {
          val cell = env.grid(x)(y)
          dirs(x)(y) = ran.ranDir(cell)
        }
      (x: Int, y: Int) => dirs(x)(y)
    }

  }


}
