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


}
