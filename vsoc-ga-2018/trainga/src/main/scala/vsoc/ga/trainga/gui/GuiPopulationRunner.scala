package vsoc.ga.trainga.gui

import java.nio.file.{Files, Path, Paths}
import java.util.Comparator

import atan.model.Controller
import vsoc.ga.common.persist.Persistors
import vsoc.ga.matches.gui.VsocMatchFrame
import vsoc.ga.matches.{Match, Matches, Team}
import vsoc.ga.trainga.config.ConfigTrainGa
import vsoc.ga.trainga.ga.{TrainGa, TrainGaPersist}

import scala.util.Random

object GuiPopulationRunner {

  val persistor = Persistors.nio

  def run(cfgTrainGa: ConfigTrainGa, generationNrOpt: Option[String] = None)(implicit workBasic: Path): Unit = {

    val trainDir = workBasic.resolve(Paths.get(cfgTrainGa.id, cfgTrainGa.nr))
    require(Files.exists(trainDir), s"Directory must exist '$trainDir'")
    require(Files.isDirectory(trainDir))

    def latestGeneration: String = {
      val path = Files.list(trainDir)
        .filter(p => p.getFileName.toString.endsWith("ser"))
        .sorted(Comparator.reverseOrder())
        .findFirst()
        .orElseThrow(() => new IllegalStateException("Could not find latest generation"))
      val fnam = path.getFileName.toString
      fnam.substring(0, fnam.length - 4)
    }

    val genNr = generationNrOpt.getOrElse(latestGeneration)

    def createMatch: Match = {

      def generationPath: Path = {
        Files.list(trainDir)
          .filter(p => p.getFileName.toString.endsWith(s"$genNr.ser"))
          .findFirst()
          .orElseThrow(() => new IllegalStateException(s"Could not find generation with nr '$genNr'"))
      }

      def loadTrainGa: TrainGa[_] = {
        val genPath: Path = generationPath
        persistor.load(genPath) { ois =>
          new TrainGaPersist().load(ois)
        }.getOrElse(throw new IllegalStateException(s"Error loading genotype from $genPath"))
      }

      val tga = loadTrainGa
      val teams = for ((t, i) <- tga.teamsFromPopulation.zipWithIndex) yield {
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

    val mf: () => Match = () => createMatch
    val f = new VsocMatchFrame(s"${cfgTrainGa.id}-${cfgTrainGa.nr}", s"Generation: $genNr", mf)
    f.setSize(1000, 584)
    f.setVisible(true)

  }


}
