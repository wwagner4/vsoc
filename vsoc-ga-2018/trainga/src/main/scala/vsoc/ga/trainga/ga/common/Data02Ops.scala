package vsoc.ga.trainga.ga.common

import vsoc.ga.common.data.Data02

object Data02Ops {

  def div(data: Data02, divisor: Double): Data02 = {
    data.copy(
      kicksMax = data.kicksMax / divisor,
      kicksMean = data.kicksMean / divisor,
      kicksMin = data.kicksMin / divisor,
      kickOutMax = data.kickOutMax / divisor,
      kickOutMean = data.kickOutMean / divisor,
      kickOutMin = data.kickOutMin / divisor,
      otherGoalsMax = data.otherGoalsMax / divisor,
      otherGoalsMean = data.otherGoalsMean / divisor,
      otherGoalsMin = data.otherGoalsMin / divisor,
      ownGoalsMax = data.ownGoalsMax / divisor,
      ownGoalsMean = data.ownGoalsMean / divisor,
      ownGoalsMin = data.ownGoalsMin / divisor,
      goalDifference = data.goalDifference / divisor,
      score = data.score / divisor
    )
  }

  def sumData(data1: Data02, data2: Data02): Data02 = {
    data1.copy(
      kicksMax = data1.kicksMax + data2.kicksMax,
      kicksMean = data1.kicksMean + data2.kicksMean,
      kicksMin = data1.kicksMin + data2.kicksMin,
      kickOutMax = data1.kickOutMax + data2.kickOutMax,
      kickOutMean = data1.kickOutMean + data2.kickOutMean,
      kickOutMin = data1.kickOutMin + data2.kickOutMin,
      otherGoalsMax = data1.otherGoalsMax + data2.otherGoalsMax,
      otherGoalsMean = data1.otherGoalsMean + data2.otherGoalsMean,
      otherGoalsMin = data1.otherGoalsMin + data2.otherGoalsMin,
      ownGoalsMax = data1.ownGoalsMax + data2.ownGoalsMax,
      ownGoalsMean = data1.ownGoalsMean + data2.ownGoalsMean,
      ownGoalsMin = data1.ownGoalsMin + data2.ownGoalsMin,
      goalDifference = data1.goalDifference + data2.goalDifference,
      score = data1.score + data2.score
    )
  }


}
