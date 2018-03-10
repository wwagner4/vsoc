package vsoc.ga.trainga.gui

import java.nio.file.{Files, Path, Paths}
import java.util.Comparator

import atan.model.Controller
import vsoc.ga.common.persist.Persistors
import vsoc.ga.matches.gui.VsocMatchFrame
import vsoc.ga.matches.{Match, Matches, Team}
import vsoc.ga.trainga.commandline.WithPathTrainGaNrRunner
import vsoc.ga.trainga.ga.{TrainGa, TrainGaContainer}

import scala.util.Random

object GuiPopulationMain extends App with WithPathTrainGaNrRunner {

  runWithArgs(args, run, GuiPopulationMain.getClass.getSimpleName)

  def run(workDirBase: Path, trainGa: TrainGa[Double], trainGaNr: String, popNr: Option[String]): Unit = {

    val m: Match = createMatch(workDirBase, trainGa, trainGaNr, popNr)
    val f = new VsocMatchFrame(m)
    f.setSize(800, 700)
    f.setVisible(true)

  }


  def createMatch(workBasic: Path, trainGa: TrainGa[Double], trainGaNr: String, popNr: Option[String]): Match = {

    def findPop(absPath: Path): Path = {
      if (popNr.isDefined) {
        Files.list(absPath)
          .filter(p => p.getFileName.toString.endsWith(s"${popNr.get}.ser"))
          .findFirst()
          .orElseThrow(() => new IllegalStateException(s"Could not find population with nr '${popNr.get}'"))
      } else {
        Files.list(absPath)
          .filter(p => p.getFileName.toString.endsWith("ser"))
          .sorted(Comparator.reverseOrder())
          .findFirst()
          .orElseThrow(() => new IllegalStateException("Could not find latest population"))
      }
    }

    def loadLatestGenotype(workBasic: Path, id: String, nr: String): Seq[Seq[Double]] = {

      import vsoc.ga.common.UtilTransform._

      val dir = workBasic.resolve(Paths.get(id, nr))
      require(Files.exists(dir), s"Directory must exist '$dir'")
      require(Files.isDirectory(dir))
      val latestPop: Path = findPop(dir)
      val persistor = Persistors.nio(workBasic)
      val latestPopRel = Paths.get(id, nr, latestPop.getFileName.toString)
      persistor.load(latestPopRel) { ois =>
        val cont = ois.readObject().asInstanceOf[TrainGaContainer]
        toSeq(cont.population)
      }.getOrElse(throw new IllegalStateException(s"Error loading genotype from $latestPop"))
    }

    val geno: Seq[Seq[Double]] = loadLatestGenotype(workBasic, trainGa.id, trainGaNr)
    val teams = for ((t, i) <- trainGa.teamsFromGeno(geno).zipWithIndex) yield {
      new Team {
        private val _name = popNr.map(nr => s"$trainGaNr-$nr-$i").getOrElse(s"$trainGaNr-latest-$i")
        private val _inner = t

        override def name: String = _name

        override def controller(i: Int): Controller = _inner.controller(i)

        override def playersCount: Int = _inner.playersCount
      }
    }
    val idx = Random.shuffle(teams.indices.toList)
    val t1 = teams(idx(0))
    val t2 = teams(idx(1))
    Matches.of(t1, t2)
  }


}
