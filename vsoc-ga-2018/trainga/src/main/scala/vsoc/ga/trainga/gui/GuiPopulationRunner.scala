package vsoc.ga.trainga.gui

import java.nio.file.{Files, Path, Paths}
import java.util.Comparator

import atan.model.Controller
import vsoc.ga.common.persist.Persistors
import vsoc.ga.genetic.Geno
import vsoc.ga.matches.gui.VsocMatchFrame
import vsoc.ga.matches.{Match, Matches, Team}
import vsoc.ga.trainga.ga.{TrainGa, TrainGaContainer, UtilTransformGeno}

import scala.util.Random

object GuiPopulationRunner {

  def run(trainGa: TrainGa[_], populationNr: String, generationNrOpt: Option[String])(implicit workBasic: Path): Unit = {

    val populationdir = workBasic.resolve(Paths.get(trainGa.id, populationNr))
    require(Files.exists(populationdir), s"Directory must exist '$populationdir'")
    require(Files.isDirectory(populationdir))
    val generationNr = generationNrOpt.getOrElse(latestGeneration(populationdir))

    val mf: () => Match = () => createMatch(trainGa, populationNr, generationNr, populationdir, workBasic)
    val f = new VsocMatchFrame(s"${trainGa.id}-$populationNr", s"Generation: $generationNr", mf)
    f.setSize(1000, 584)
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

  def createMatch(trainGa: TrainGa[_], populationNr: String, generationNr: String, popDir: Path, workDir: Path): Match = {

    def generationPath: Path = {
      Files.list(popDir)
        .filter(p => p.getFileName.toString.endsWith(s"$generationNr.ser"))
        .findFirst()
        .orElseThrow(() => new IllegalStateException(s"Could not find generation with nr '$generationNr'"))
    }

    def loadGenotype: Seq[Geno[Double]] = {

      val genPath: Path = generationPath
      val persistor = Persistors.nio(workDir)
      val genDir = Paths.get(trainGa.id, populationNr, genPath.getFileName.toString)
      persistor.load(genDir) { ois =>
        val cont = ois.readObject().asInstanceOf[TrainGaContainer]
        UtilTransformGeno.toSeqGeno(cont.population)
      }.getOrElse(throw new IllegalStateException(s"Error loading genotype from $genPath"))
    }

    val geno: Seq[Geno[Double]] = loadGenotype
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
