package vsoc.ga.trainga.ga

import vsoc.ga.matches.TeamResult

object FitnessFunctions {

  def fitnessConsiderAll01: FitnessFunction = new FitnessFunction {

    override def id: String = "fitnessConsiderAll01"

    override def fitness(tr: TeamResult): Double = {
      val og = tr.otherGoalCount * 100
      val kc = tr.kickCount * 10
      val ko = tr.kickOutCount * 2
      val ow = tr.ownGoalCount * 100
      og + kc - ko - ow
    }

    override def fullDesc: String = s"'$id' - Sum of weighted results"

  }

  def fitnessFactor01: FitnessFunction = new FitnessFunction {

    override def id: String = "fitnessFactor01"

    override def fitness(tr: TeamResult): Double = {
      val ogf = (tr.otherGoalCount + 1) * 2.0
      val owf = (tr.ownGoalCount + 1) * 0.5
      val kc = tr.kickCount * 10
      val ko = tr.kickOutCount * 8
      (kc - ko) * ogf * owf
    }

    override def fullDesc: String = s"'$id' - Sum of kicks are rewarded relative to number of goals/owngoals"
  }

  def fitnessFactor02: FitnessFunction = new FitnessFunction {

    override def id: String = "fitnessFactor02"

    override def fitness(tr: TeamResult): Double = {
      val kp = kickingPlayers(tr)
      val og = tr.otherGoalCount * 100
      val kc = tr.kickCount * 10
      val ko = tr.kickOutCount * 2
      val ow = tr.ownGoalCount * 100
      (og + kc - ko - ow) * kp
    }

    override def fullDesc: String = s"'$id' - Sum of kicks and goals are rewarded relative to the number of kicking players"
  }

  def fitnessFactor03: FitnessFunction = new FitnessFunction {

    override def id: String = "fitnessFactor03"

    override def fitness(tr: TeamResult): Double = {
      val kp = kickingPlayers(tr)
      val ogf = (tr.otherGoalCount + 1) * 2.0
      val owf = (tr.ownGoalCount + 1) * 0.5

      val kc = tr.kickCount * 10
      val ko = tr.kickOutCount * 2

      (kc - ko) * kp * ogf * owf
    }

    override def fullDesc: String =
      s"""'$id' - Sum of kicks are rewarded relative to the number of kicking players and nuber of goals goals"""
  }

  def fitnessFactor03a: FitnessFunction = new FitnessFunction {

    override def id: String = "fitnessFactor03a"

    override def fitness(tr: TeamResult): Double = {
      val kp = kickingPlayers(tr)

      val other =
        if (tr.otherGoalCount <= 0) 1.0
        else tr.otherGoalCount * 2.0

      val own =
        if (tr.ownGoalCount <= 0) 1.0
        else tr.ownGoalCount * 0.5

      val kc = tr.kickCount * 10
      val ko = tr.kickOutCount * 2

      (kc - ko) * kp * other * own
    }

    override def fullDesc: String =
      s"""'$id' - Sum of kicks are rewarded relative to the number of kicking players and the number of goals goals.
        |No Goals result in a factor of 1.""".stripMargin
  }

  def fitnessConsiderAll01K0: FitnessFunction = new FitnessFunction {

    override def id: String = "fitnessConsiderAll01K0"

    def fitness(tr: TeamResult): Double = {
      val og = tr.otherGoalCount * 100
      val kc = tr.kickCount * 10
      val ko = tr.kickOutCount * 8
      val ow = tr.ownGoalCount * 100
      og + kc - ko - ow
    }

    override def fullDesc: String = s"'$id' - Sum of weighted results. High goal factor"

  }

  def fitnessConsiderAll01G0: FitnessFunction = new FitnessFunction {

    override def id: String = "fitnessConsiderAll01G0"

    override def fitness(tr: TeamResult): Double = {
      val og = tr.otherGoalCount * 10
      val kc = tr.kickCount * 10
      val ko = tr.kickOutCount * 2
      val ow = tr.ownGoalCount * 10
      og + kc - ko - ow
    }

    override def fullDesc: String = s"'$id' - Sum of weighted results. Low goal factor"

  }

  def fitnessKicks01: FitnessFunction = new FitnessFunction {

    override def id: String = "fitnessKicks01"

    override def fitness(tr: TeamResult): Double = tr.kickCount * 10

    override def fullDesc: String = s"'$id' - Consider only kicks"
  }

  def kickingPlayers(tr: TeamResult): Int = {
    tr.playerResults.count(p => p.kickCount > 0)
  }

}
