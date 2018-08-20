package vsoc.ga.trainga.ga

import vsoc.ga.common.data.Data02

object FitnessFunctions {

  def data02A01: FitnessFunction[Data02] = new FitnessFunction[Data02] {

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

  def data02A02: FitnessFunction[Data02] = new FitnessFunction[Data02] {

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
         |Higher bebefit for kicksMin and kicksMean
         |That should avoid breeding only one kicking player per team
       """.stripMargin
  }

  def data02A03: FitnessFunction[Data02] = new FitnessFunction[Data02] {

    override def id: String = "data02A02"

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

}
