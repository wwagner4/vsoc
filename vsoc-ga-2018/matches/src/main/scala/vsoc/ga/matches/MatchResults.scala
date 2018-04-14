package vsoc.ga.matches

import vsoc.server.VsocPlayer

object MatchResults {

  private case class Chapter(
                              title: String,
                              properties: Seq[(String, Any)]
                            )

  object DefaultChapterFormatter {
    def formatKey(key: String): String = key.trim

    def formatValue(value: Any): String =
      value match {
        case v: Double => f"$v%.2f"
        case v: Float => f"$v%.2f"
        case v: Long => f"$v%d"
        case v: Int => f"$v%d"
        case v: Any => v.toString.trim
      }

    def formatProperty(prop: (String, Any)): String =
      s" ${formatKey(prop._1)} : ${formatValue(prop._2)}"

    def formatChapter(c: Chapter): String = {
      def props = c.properties.map(formatProperty).mkString("\n")

      c.title + "\n" + props
    }

  }

  def formatDefault(mr: MatchResult): String = {

    def formatScore: String = {
      val east = mr.teamEastResult.otherGoalCount + mr.teamWestResult.ownGoalCount
      val west = mr.teamWestResult.otherGoalCount + mr.teamEastResult.ownGoalCount
      s"$west/$east"
    }

    def formatKicks: String = {
      val east = mr.teamEastResult.kickCount
      val west = mr.teamWestResult.kickCount
      s"$west/$east"
    }

    def formatKickOuts: String = {
      val east = mr.teamEastResult.kickOutCount
      val west = mr.teamWestResult.kickOutCount
      s"$west/$east"
    }

    def formatOwnGoals: String = {
      val east = mr.teamEastResult.ownGoalCount
      val west = mr.teamWestResult.ownGoalCount
      s"$west/$east"
    }

    def formatOtherGoals: String = {
      val east = mr.teamEastResult.otherGoalCount
      val west = mr.teamWestResult.otherGoalCount
      s"$west/$east"
    }

    def chapters: Seq[Chapter] = Seq(
      Chapter(
        title = "TEAMS w/e",
        properties = Seq(
          ("steps", mr.matchSteps),
          ("score", formatScore),
          ("kicks", formatKicks),
          ("kick outs", formatKickOuts),
          ("other goals", formatOtherGoals),
          ("own goals", formatOwnGoals)
        )
      )
    )

    chapters.map(c => DefaultChapterFormatter.formatChapter(c)).mkString("\n")

  }

  def of(steps: Int, east: Seq[VsocPlayer], west: Seq[VsocPlayer]): MatchResult = {

    case class TeamResultImpl(
                               ownGoalCount: Int = 0,
                               otherGoalCount: Int = 0,
                               kickOutCount: Int = 0,
                               kickCount: Int = 0,
                               playerResults: Seq[PlayerResult]
                             ) extends TeamResult

    case class PayerResultImpl(
                                number: Int,
                                ownGoalCount: Int = 0,
                                otherGoalCount: Int = 0,
                                kickOutCount: Int = 0,
                                kickCount: Int = 0
                              ) extends PlayerResult


    def teamResult(players: Seq[VsocPlayer]): TeamResult = {
      val ps: Seq[PlayerResult] = players.map { p =>
        PayerResultImpl(
          p.getNumber,
          p.getOwnGoalCount,
          p.getOtherGoalCount,
          p.getKickOutCount,
          p.getKickCount
        )
      }

      val ini: TeamResultImpl = TeamResultImpl(playerResults = ps)
      ps.foldRight(ini) { (pr: PlayerResult, tr: TeamResultImpl) =>
        tr.copy(
          ownGoalCount = tr.ownGoalCount + pr.ownGoalCount,
          otherGoalCount = tr.otherGoalCount + pr.otherGoalCount,
          kickOutCount = tr.kickOutCount + pr.kickOutCount,
          kickCount = tr.kickCount + pr.kickCount
        )
      }
    }

    new MatchResult {

      override def teamWestResult: TeamResult = teamResult(west)

      override def teamEastResult: TeamResult = teamResult(east)

      override def matchSteps: Int = steps
    }

  }

}
