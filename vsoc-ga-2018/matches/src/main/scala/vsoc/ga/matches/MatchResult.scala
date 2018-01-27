package vsoc.ga.matches

trait MatchResult {

  def matchSteps: Int

  def teamEastResult: TeamResult

  def teamWestResult: TeamResult

}

trait TeamResult {

  def ownGoalCount: Int
  def otherGoalCount: Int
  def kickOutCount: Int
  def kickCount: Int
  def playerResults: Seq[PlayerResult]

}

trait PlayerResult {

  def number: Int
  def ownGoalCount: Int
  def otherGoalCount: Int
  def kickOutCount: Int
  def kickCount: Int

}

