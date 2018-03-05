package vsoc.ga.trainga.thinner.impl

import java.nio.file.{Files, Path}

import scala.collection.JavaConverters._
import scala.util.matching.Regex


object ThinnerFiles {

  def findFiles(dir: Path): Seq[Path] = {
    require(Files.exists(dir), s"Directory $dir does not exist")
    val files: Seq[Path] = Files.list(dir).iterator().asScala.toList
    val idxFile = files.filter(isPopFile).map(toIndexed)
    val indexes = ThinnerIndex.thin(idxFile.map(_._1))
    idxFile.filter(toBeDeleted(indexes)).map(_._2)
  }

  def isPopFile(file: Path): Boolean = {
    val fileName = file.getFileName.toString
    fileName.matches("pop[0-9]{3}\\.ser")
  }

  def toIndexed(file: Path): (Int, Path) = {
    val R: Regex = """pop([0-9]{3})\.ser""".r
    val fileName: String = file.getFileName.toString
    val i = fileName match {
      case R(nr) => nr.toInt
      case _ => throw new IllegalStateException(s"Illegal file name '$file'")
    }
    (i, file)
  }

  def toBeDeleted(idx: Seq[Int])(idxPath: (Int, Path)): Boolean = {
    !idx.contains(idxPath._1)
  }

}
