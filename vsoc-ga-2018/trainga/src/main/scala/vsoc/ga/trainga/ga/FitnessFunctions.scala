package vsoc.ga.trainga.ga

import vsoc.ga.common.data.Data02

object FitnessFunctions {

  def data02A: FitnessFunction[Data02] = new FitnessFunction[Data02] {

    override def id: String = "data02A"

    override def fitness(data: Data02): Double = {
      math.min(100, data.kicksMax) +
        math.min(200, data.kicksMin) -
        math.min(100, data.kickOutMax) -
        math.min(200, data.kickOutMin) +
        math.min(400, data.otherGoalsMax * 100) +
        math.min(800, data.otherGoalsMin * 100) -
        math.min(400, data.ownGoalsMin * 100) -
        math.min(800, data.ownGoalsMin * 100) +
        data.goalDifference * 100
    }

    override def fullDesc: String = s"'$id' - Summary Fitnessfunction with max values"
  }

}
