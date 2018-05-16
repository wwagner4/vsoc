package vsoc.ga.trainga.ga

import vsoc.ga.matches.TeamResult

object FitnessFunctions {

  def fitnessConsiderAll01: (TeamResult) => Double =
    tr => {
      val og = tr.otherGoalCount * 100
      val kc = tr.kickCount * 10
      val ko = tr.kickOutCount * 2
      val ow = tr.ownGoalCount * 100
      og + kc - ko - ow
    }

  // Goals are rewarded by a relative to kicks. -> They matter always
  def fitnessFactor01: (TeamResult) => Double =
    tr => {
      val ogf = (tr.otherGoalCount + 1) * 2.0
      val owf = (tr.ownGoalCount + 1) * 0.5
      val kc = tr.kickCount * 10
      val ko = tr.kickOutCount * 8
      (kc - ko) * ogf * owf
    }


  // Kicking of multiple players is rewarded relative
  def fitnessFactor02: (TeamResult) => Double =
    tr => {
      val kp = kickingPlayers(tr)
      val og = tr.otherGoalCount * 100
      val kc = tr.kickCount * 10
      val ko = tr.kickOutCount * 2
      val ow = tr.ownGoalCount * 100
      (og + kc - ko - ow) * kp
    }

  def fitnessConsiderAll01K0: (TeamResult) => Double =
    tr => {
      val og = tr.otherGoalCount * 100
      val kc = tr.kickCount * 10
      val ko = tr.kickOutCount * 8
      val ow = tr.ownGoalCount * 100
      og + kc - ko - ow
    }

  def fitnessConsiderAll01G0: (TeamResult) => Double =
    tr => {
      val og = tr.otherGoalCount * 10
      val kc = tr.kickCount * 10
      val ko = tr.kickOutCount * 2
      val ow = tr.ownGoalCount * 10
      og + kc - ko - ow
    }


  def fitnessKicks01: (TeamResult) => Double =
    tr => tr.kickCount * 10

  def kickingPlayers(tr: TeamResult): Int = {
    tr.playerResults.filter(p => p.kickCount > 0).size
  }

}
