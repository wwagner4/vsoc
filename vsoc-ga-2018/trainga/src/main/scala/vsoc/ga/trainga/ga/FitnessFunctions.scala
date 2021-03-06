package vsoc.ga.trainga.ga

import vsoc.ga.trainga.ga.impl.player01.DataPlayer01
import vsoc.ga.trainga.ga.impl.team01.Data02

object FitnessFunctions {

  def data02A01: TrainGaFitnessFunction[Data02] = new TrainGaFitnessFunction[Data02] {

    override def id: String = "data02A01"

    override def fitness(data: Data02): Double = {
      math.min(1000, data.kicksMax) +
        math.min(5000, data.kicksMin * 10) -
        math.min(1000, data.kickOutMean) +
        math.min(10000, data.otherGoalsMax * 100) +
        math.min(80000, data.otherGoalsMin * 1000) -
        math.min(10000, data.ownGoalsMean * 100)
    }

    override def fullDesc: String = s"'$id' - Summary Fitnessfunction with max mean values"
  }

  def data02A02: TrainGaFitnessFunction[Data02] = new TrainGaFitnessFunction[Data02] {

    override def id: String = "data02A02"

    override def fitness(data: Data02): Double = {
      math.min(1000, data.kicksMax) +
        math.min(2000, data.kicksMean) * 10 +
        math.min(5000, data.kicksMin * 100) -
        math.min(1000, data.kickOutMean) +
        math.min(10000, data.otherGoalsMax * 100) +
        math.min(80000, data.otherGoalsMin * 1000) -
        math.min(10000, data.ownGoalsMean * 100)
    }

    override def fullDesc: String =
      s"""'$id' - Summary Fitnessfunction with max mean values
         |Higher benefit for kicksMin and kicksMean
         |That should avoid breeding only one kicking player per team
       """.stripMargin
  }

  def data02A03: TrainGaFitnessFunction[Data02] = new TrainGaFitnessFunction[Data02] {

    override def id: String = "data02A03"

    override def fitness(data: Data02): Double = {
      math.min(100, data.kicksMax) +
        math.min(2000, data.kicksMean) * 10 +
        math.min(5000, data.kicksMin * 100) -
        math.min(1000, data.kickOutMean) +
        math.min(10000, data.otherGoalsMax * 500) +
        math.min(80000, data.otherGoalsMin * 1000) -
        math.min(10000, data.ownGoalsMean * 500)
    }

    override def fullDesc: String =
      s"""'$id' - Summary Fitnessfunction
         |Reduced top value for kicks max to avoid one player team
         |Increased factors for goals to get goal kickers
       """.stripMargin
  }

  def data02A04: TrainGaFitnessFunction[Data02] = new TrainGaFitnessFunction[Data02] {

    override def id: String = "data02A04"

    override def fitness(data: Data02): Double = {
      math.min(500, data.kicksMax) +
        math.min(50000, data.kicksMin * 100) -
        data.kickOutMean +
        math.min(5000, data.otherGoalsMax * 500) +
        data.otherGoalsMin * 1000 -
        data.ownGoalsMean * 500
    }

    override def fullDesc: String =
      s"""'$id' - Summary Fitnessfunction
         |Iteration 5
       """.stripMargin
  }

  def data02A05: TrainGaFitnessFunction[Data02] = new TrainGaFitnessFunction[Data02] {

    override def id: String = "data02A05"

    override def fitness(data: Data02): Double = {
      math.min(500, data.kicksMax) +
        math.min(10000, data.kicksMin * 100) -
        data.kickOutMean +
        math.min(2000, data.otherGoalsMax * 500) +
        data.otherGoalsMin * 1000 -
        data.ownGoalsMean * 500
    }

    override def fullDesc: String =
      s"""'$id' - Summary Fitnessfunction
         |Iteration 6
       """.stripMargin
  }

  def dataPlayer01A: TrainGaFitnessFunction[DataPlayer01] = new TrainGaFitnessFunction[DataPlayer01] {
    override def id: String = "dataPlayer01A"

    override def fitness(score: DataPlayer01): Double = {
      val k: Double = score.kicks
      val g: Double = score.goals
      val k1 = if (k > 150) 150 else k
      k1 + (g * 10.0)
    }

    override def fullDesc: String =
      """Uses only Kicks and Goals
        |Kicks limited to 150
        |Goals rated 10 times more than kicks
      """.stripMargin
  }

  def dataPlayer01B: TrainGaFitnessFunction[DataPlayer01] = new TrainGaFitnessFunction[DataPlayer01] {
    override def id: String = "dataPlayer01B"

    override def fitness(score: DataPlayer01): Double = {
      val k: Double = score.kicks
      val g: Double = score.goals
      val k1 = if (k > 150) 150 else k
      k1 + (g * 50.0)
    }

    override def fullDesc: String =
      """Uses only Kicks and Goals
        |Kicks limited to 150
        |Goals rated 50 times more than kicks
      """.stripMargin
  }

  def dataPlayer01C: TrainGaFitnessFunction[DataPlayer01] = new TrainGaFitnessFunction[DataPlayer01] {
    override def id: String = "dataPlayer01B"

    override def fitness(score: DataPlayer01): Double = {
      val k: Double = score.kicks
      val g: Double = score.goals
      val k1 = if (k > 150) 150 else k
      k1 + (g * 150.0)
    }

    override def fullDesc: String =
      """Uses only Kicks and Goals
        |Kicks limited to 150
        |Goals rated 150 times more than kicks
        |Higher rating of goals compared to dataPlayer01B
      """.stripMargin
  }

}
