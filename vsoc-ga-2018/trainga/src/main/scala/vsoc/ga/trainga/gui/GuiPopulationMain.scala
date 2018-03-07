package vsoc.ga.trainga.gui

import java.nio.file.Path

import vsoc.behaviour.Behaviour
import vsoc.ga.matches.gui.VsocMatchFrame
import vsoc.ga.matches.{Match, Matches, Teams}
import vsoc.ga.trainga.commandline.WithPathTrainGaNrRunner
import vsoc.ga.trainga.ga.TrainGa

import scala.util.Random

object GuiPopulationMain extends App with WithPathTrainGaNrRunner {

  runWithArgs(args, run, GuiPopulationMain.getClass.getSimpleName)

  def run(workDirBase: Path, trainGa: TrainGa[Double], nr: String): Unit = {

    val m: Match = createMatch(workDirBase, trainGa, nr)
    new VsocMatchFrame(m).setVisible(true)

  }

  def createMatch(workBasic: Path, trainGa: TrainGa[Double], nr: String): Match = {
    val behavs: Seq[Seq[Behaviour]] = trainGa.createBehaviours(workBasic, nr)
    val idx = Random.shuffle(behavs.indices.toList)
    val t1 = Teams.behaviours(behavs(idx(0)), "T1")
    val t2 = Teams.behaviours(behavs(idx(1)), "T2")
    Matches.of(t1, t2)
  }


}
