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

}
