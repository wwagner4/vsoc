package vsoc.ga.common.analyse.iterations.iter04

import java.nio.file.{Files, Path, Paths}

import vsoc.ga.common.UtilPath._

object CreateAllResourcesB04 {

  def create(baseDir: Path): Path = {

    val iterDir = baseDir.resolve("iter05")
    if (Files.exists(iterDir)) {
      println(s"Resources already exist $iterDir")
    } else {
      Files.createDirectories(iterDir)

      val workDir = iterDir.resolve("work")
      if (!Files.exists(workDir)) Files.createDirectories(workDir)

      val workDir1 = workDir.resolve("trainGaB04")
      if (!Files.exists(workDir1)) Files.createDirectories(workDir1)

      val tmpDir = iterDir.resolve("tmp")
      if (Files.exists(tmpDir)) delDirectory(tmpDir)
      Files.createDirectories(tmpDir)

      un7z(Paths.get("doc/training-results/trainGaB04work.7z"), tmpDir)
      un7z(Paths.get("doc/training-results/trainGaB04Bob.7z"), tmpDir)
      println(s"Extracted results to $tmpDir")

      moveTo(tmpDir, "trainGaB04work", "work", workDir1)
      moveTo(tmpDir, "trainGaB04Bob", "bob", workDir1)
      println(s"Moved results to $workDir")

      println(s"Created all resources for iteration 5 in $iterDir")
    }
    iterDir
  }

  def moveTo(fromDir: Path, subDir: String, prefix: String, toDir: Path): Unit = {
    val fd = fromDir.resolve(subDir)
    Files.list(fd)
      .filter(file => file.getFileName.toString.startsWith(prefix))
      .forEach(file => {
        val toFile = toDir.resolve(file.getFileName)
        Files.move(file, toFile)
      })
  }

}
