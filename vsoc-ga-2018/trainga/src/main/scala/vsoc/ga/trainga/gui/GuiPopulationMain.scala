package vsoc.ga.trainga.gui

import java.nio.file.{Files, Path, Paths}
import java.util.Comparator

import vsoc.ga.common.persist.Persistors
import vsoc.ga.matches.gui.VsocMatchFrame
import vsoc.ga.matches.{Match, Matches, Team}
import vsoc.ga.trainga.commandline.WithPathTrainGaNrRunner
import vsoc.ga.trainga.ga.{TrainGa, TrainGaContainer}

import scala.util.Random

object GuiPopulationMain extends App with WithPathTrainGaNrRunner {

  runWithArgs(args, run, GuiPopulationMain.getClass.getSimpleName)

  def run(workDirBase: Path, trainGa: TrainGa[Double], nr: String): Unit = {

    val m: Match = createMatch(workDirBase, trainGa, nr)
    new VsocMatchFrame(m).setVisible(true)

  }

  def findLatestPop(dir: Path): Path =    {
    Files.list(dir)
      .filter(p => p.getFileName.toString.endsWith("ser"))
      .sorted(Comparator.reverseOrder())
      .findFirst()
      .orElseThrow(() => new IllegalStateException("Could not find latest population"))
  }


  def loadLatestGenotype(workBasic: Path, id: String, nr: String): Seq[Seq[Double]] = {

    import vsoc.ga.common.UtilTransform._

    val dir = workBasic.resolve(Paths.get(id, nr))
    require(Files.exists(dir))
    require(Files.isDirectory(dir))
    val latestPop: Path = findLatestPop(dir)
    val persistor = Persistors.nio(workBasic)
    val latestPopRel = Paths.get(id, nr, latestPop.getFileName.toString)
    persistor.load(latestPopRel){ ois =>
      val cont = ois.readObject().asInstanceOf[TrainGaContainer]
      toSeq(cont.population)
    }.getOrElse(throw new IllegalStateException(s"Error loading genotype from $latestPop"))
  }

  def createMatch(workBasic: Path, trainGa: TrainGa[Double], nr: String): Match = {
    val geno: Seq[Seq[Double]] = loadLatestGenotype(workBasic, trainGa.id, nr)
    val teams: Seq[Team] = trainGa.teamsFromGeno(geno)
    val idx = Random.shuffle(teams.indices.toList)
    val t1 = teams(idx(0))
    val t2 = teams(idx(1))
    Matches.of(t1, t2)
  }


}
