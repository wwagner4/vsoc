package vsoc.ga.trainga.ga

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

}
