package vsoc.ga.trainga.gui

import java.nio.file.{Files, Path, Paths}
import java.util.Comparator

import atan.model.Controller
import vsoc.ga.common.UtilPath
import vsoc.ga.common.config.ConfigHelper
import vsoc.ga.common.persist.Persistors
import vsoc.ga.matches.gui.VsocMatchFrame
import vsoc.ga.matches.{Match, Matches, Team}
import vsoc.ga.trainga.ga.{TrainGa, TrainGaContainer}

import scala.util.Random

object GuiPopulationRunner {

  private val workBasic = UtilPath.workDir

  def run(trainGa: TrainGa[_], populationNr: String, generationNrOpt: Option[String]): Unit = {

    val populationdir = workBasic.resolve(Paths.get(trainGa.id, populationNr))
    require(Files.exists(populationdir), s"Directory must exist '$populationdir'")
    require(Files.isDirectory(populationdir))
    val generationNr = generationNrOpt.getOrElse(latestGeneration(populationdir))

    val mf: () => Match = () => createMatch(trainGa, populationNr, generationNr, populationdir)
    val f = new VsocMatchFrame(s"${trainGa.id}-$populationNr", s"Generation: $generationNr", mf)
    f.setSize(960, 584)
    f.setVisible(true)

  }

  def latestGeneration(popDir: Path): String = {
    val path = Files.list(popDir)
      .filter(p => p.getFileName.toString.endsWith("ser"))
      .sorted(Comparator.reverseOrder())
      .findFirst()
      .orElseThrow(() => new IllegalStateException("Could not find latest generation"))
    val fnam = path.getFileName.toString
    fnam.substring(0, fnam.length - 4)
  }

  def createMatch(trainGa: TrainGa[_], populationNr: String, generationNr: String, popDir: Path): Match = {

    def generationPath: Path = {
      Files.list(popDir)
        .filter(p => p.getFileName.toString.endsWith(s"$generationNr.ser"))
        .findFirst()
        .orElseThrow(() => new IllegalStateException(s"Could not find generation with nr '$generationNr'"))
    }

    def loadGenotype: Seq[Seq[Double]] = {

      import vsoc.ga.common.UtilTransform._

      val genPath: Path = generationPath
      val persistor = Persistors.nio(workBasic)
      val genDir = Paths.get(trainGa.id, populationNr, genPath.getFileName.toString)
      persistor.load(genDir) { ois =>
        val cont = ois.readObject().asInstanceOf[TrainGaContainer]
        toSeq(cont.population)
      }.getOrElse(throw new IllegalStateException(s"Error loading genotype from $genPath"))
    }

    val geno: Seq[Seq[Double]] = loadGenotype
    val teams = for ((t, i) <- trainGa.teamsFromGeno(geno).zipWithIndex) yield {
      new Team {
        private val _name = s"$i"
        private val _inner = t

        override def name: String = _name

        override def controller(i: Int): Controller = _inner.controller(i)

        override def playersCount: Int = _inner.playersCount
      }
    }
    val idx = Random.shuffle(teams.indices.toList)
    val t1: Team = teams(idx(0))
    val t2: Team = teams(idx(1))
    Matches.of(t1, t2)
  }



}
