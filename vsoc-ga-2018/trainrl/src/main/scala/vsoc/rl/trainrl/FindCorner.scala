package vsoc.rl.trainrl

object FindCorner extends App {

  val DIM = 4 // Do not change

  println("STARTED Find Corner")

  val env = new Environment
  val ran = new Ran
  //val strat = Strategies.optimal
  val strat = Strategies.random(env)
  val valFunc = ValueFunctions.default _
  val reinfFunc = ReinforcementFunctions.pureDelayedReward _

  for (i <- 0 until DIM) {
    for (j <- 0 until DIM) {
      val v: Double = valFunc(State(i, j), env, strat, reinfFunc)
      val str = "%5.0f " format v
      print(str)
    }
    println()
  }

  println("FINISHED Find Corner")


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

  object ReinforcementFunctions {

    def minTimeToGoal(state: State, environment: Environment): Double = {
      val x = state.x
      val y = state.y
      val cell = environment.grid(x)(y)
      cell.state match {
        case STOP => 0
        case MOVE => -1
      }
    }

    def pureDelayedReward(state: State, environment: Environment): Double = {
      val x = state.x
      val y = state.y
      val cell = environment.grid(x)(y)
      cell.state match {
        case STOP => 1
        case MOVE => 0
      }
    }

  }

  object ValueFunctions {

    def default(state: State, environment: Environment, strategy: Strategy,
                reinforcementFunction: (State, Environment) => Double): Double = {

      def _default(currentState: State, value: Double, count: Int): Double = {
        if (count >= 99) value - 1
        else {
          val x = currentState.x
          val y = currentState.y
          val nextValue = reinforcementFunction(currentState, environment)
          val cell = environment.grid(x)(y)
          cell.state match {
            case STOP => value + nextValue
            case MOVE =>
              val dir = strat.move(x, y)
              val nextState = State(x + dir.xoff, y + dir.yoff)
              _default(nextState, value + nextValue, count + 1)
          }
        }
      }
      _default(state, 0.0, 0)
    }

  }

  case class State(x: Int, y: Int)

  case class Cell(state: CellState, dir: Direction*)

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

    def ranAgent: State =
      State(ranPos, ranPos)

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

    def optimal: Strategy = {
      val dirs = Array.ofDim[Direction](DIM, DIM)
      dirs(0)(0) = _
      dirs(1)(0) = L
      dirs(2)(0) = L
      dirs(3)(0) = L
      dirs(0)(1) = U
      dirs(1)(1) = U
      dirs(2)(1) = U
      dirs(3)(1) = D
      dirs(0)(2) = U
      dirs(1)(2) = U
      dirs(2)(2) = D
      dirs(3)(2) = D
      dirs(0)(3) = R
      dirs(1)(3) = R
      dirs(2)(3) = R
      dirs(3)(3) = _
      (x: Int, y: Int) => dirs(x)(y)
    }

  }


}
