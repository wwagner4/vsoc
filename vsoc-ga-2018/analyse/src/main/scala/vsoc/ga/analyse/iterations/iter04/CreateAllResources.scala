package vsoc.ga.analyse.iterations.iter04

import java.nio.file._

import vsoc.ga.common.UtilPath._

object CreateAllResources {

  def create: Path = {
    val tmpDirStr = System.getProperty("java.io.tmpdir")
    val javaTmpDir = Paths.get(tmpDirStr)
    if (!Files.exists(javaTmpDir)) throw new IllegalStateException(s"No tmp dir available. '$tmpDirStr'")

    createDataFromZip(javaTmpDir)
  }


  private def createDataFromZip(javaTmpDir: Path): Path = {

    def moveTo(fromDir: Path, subDir: String, prefix: String, toDir: Path): Unit = {
      val fd = fromDir.resolve(subDir)
      Files.list(fd)
        .filter(file => file.getFileName.toString.startsWith(prefix))
        .forEach(file => {
          val toFile = toDir.resolve(file.getFileName)
          Files.move(file, toFile)
        })
    }

    val iterDir = javaTmpDir.resolve("iter04")
    if (Files.exists(iterDir)) delDirectory(iterDir)
    Files.createDirectories(iterDir)

    val workDir = iterDir.resolve("work")
    if (!Files.exists(workDir)) Files.createDirectories(workDir)

    val workDir1 = workDir.resolve("trainGaB03")
    if (!Files.exists(workDir1)) Files.createDirectories(workDir1)

    val tmpDir = iterDir.resolve("tmp")
    if (Files.exists(tmpDir)) delDirectory(tmpDir)
    Files.createDirectories(tmpDir)

    unzip(Paths.get("doc/training-results/trainGaB03bob.zip"), tmpDir)
    unzip(Paths.get("doc/training-results/trainGaB03work.zip"), tmpDir)
    println(s"Extracted results to $tmpDir")

    moveTo(tmpDir, "trainGaB03", "work", workDir1)
    moveTo(tmpDir, "trainGaB03bob", "bob", workDir1)
    println(s"Moved results to $workDir")

    println(s"Created all resources for iteration 4 in $iterDir")

    iterDir
  }

}
