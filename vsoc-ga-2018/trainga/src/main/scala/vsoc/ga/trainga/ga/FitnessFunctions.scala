package vsoc.ga.trainga.ga

import vsoc.ga.matches.TeamResult

object FitnessFunctions {

  def fitnessConsiderAll01: FitnessFunction = new FitnessFunction {

    override def fitness(tr: TeamResult): Double = {
      val og = tr.otherGoalCount * 100
      val kc = tr.kickCount * 10
      val ko = tr.kickOutCount * 2
      val ow = tr.ownGoalCount * 100
      og + kc - ko - ow
    }

    override def fullDesc: String = "Sum of weighted results"

  }

  def fitnessFactor01: FitnessFunction = new FitnessFunction {

    override def fitness(tr: TeamResult): Double = {
      val ogf = (tr.otherGoalCount + 1) * 2.0
      val owf = (tr.ownGoalCount + 1) * 0.5
      val kc = tr.kickCount * 10
      val ko = tr.kickOutCount * 8
      (kc - ko) * ogf * owf
    }

    override def fullDesc: String = "Goals are rewarded relative to kicks/kickouts"
  }

  def fitnessFactor02: FitnessFunction = new FitnessFunction {

    override def fitness(tr: TeamResult): Double = {
      val kp = kickingPlayers(tr)
      val og = tr.otherGoalCount * 100
      val kc = tr.kickCount * 10
      val ko = tr.kickOutCount * 2
      val ow = tr.ownGoalCount * 100
      (og + kc - ko - ow) * kp
    }

    override def fullDesc: String = "Number of kicking players is rewarded relative to kicking and goalss"
  }

  def fitnessConsiderAll01K0: FitnessFunction = new FitnessFunction {

    def fitness(tr: TeamResult): Double = {
      val og = tr.otherGoalCount * 100
      val kc = tr.kickCount * 10
      val ko = tr.kickOutCount * 8
      val ow = tr.ownGoalCount * 100
      og + kc - ko - ow
    }

    override def fullDesc: String = "Sum of weighted results. High goal factor"

  }

  def fitnessConsiderAll01G0: FitnessFunction = new FitnessFunction {

    override def fitness(tr: TeamResult): Double = {
      val og = tr.otherGoalCount * 10
      val kc = tr.kickCount * 10
      val ko = tr.kickOutCount * 2
      val ow = tr.ownGoalCount * 10
      og + kc - ko - ow
    }

    override def fullDesc: String = "Sum of weighted results. Low goal factor"

  }

  def fitnessKicks01: FitnessFunction = new FitnessFunction {
    override def fitness(tr: TeamResult): Double = tr.kickCount * 10

    override def fullDesc: String = "Consider only kicks"
  }

  def kickingPlayers(tr: TeamResult): Int = {
    tr.playerResults.count(p => p.kickCount > 0)
  }

}
