package vsoc.ga.trainga.ga

import java.nio.file.{Files, Path, Paths}
import java.util.{Comparator, Optional}

import org.slf4j.LoggerFactory
import vsoc.ga.common.data.DataHandler
import vsoc.ga.common.persist.{Persistor, Persistors}
import vsoc.ga.common.{UtilReflection, UtilTransform}

object TrainGaRunner {

  private val log = LoggerFactory.getLogger(TrainGaRunner.getClass)

  def run (trainGaId: String, trainGaNr: String): Unit = {
    def p: Persistor = Persistors.workDir
    val workDir = Paths.get(trainGaId, trainGaNr)
    val workDirAbs = p.dir(workDir)

    val dh = new DataHandler(workDirAbs.resolve(s"$trainGaId-$trainGaNr-data.csv"))

    val fileNameOpt: Optional[Path] = Files.list(workDirAbs)
      .filter(p => p.getFileName.toString.endsWith("ser"))
      .sorted(Comparator.reverseOrder())
      .findFirst()

    val tga: TrainGa[Double] = UtilTransform.asOption(fileNameOpt)
      .map(p => p.getFileName.toString)
      .flatMap { file: String =>
        val path = workDir.resolve(file)
        log.info(s"loading population from $path")
        p.load(path)(ois => TrainGaPersist.load(ois))
      }
      .getOrElse {
        log.info(s"could not population from $workDir. creating a new one")
        UtilReflection.call(TrainGas, trainGaId, classOf[TrainGa[Double]])
      }

    tga.listeners = tga.listeners :+ persListener :+ dataListener

    log.info(s"starting " + tga.id + " at iteration " + tga.iterations.getOrElse(0))
    tga.run(trainGaId, trainGaNr)

    def persListener: TrainGaListener[Double] = (iteration: Int, _: Option[Double], _: Seq[(String, Any)]) => {
      val nr = f"$iteration%03d"
      val filename = s"pop$nr.ser"
      val filePath = workDir.resolve(filename)
      log.info(s"saving population to $filePath")
      p.save(filePath)(oos => TrainGaPersist.save(tga, oos))
    }
    def dataListener: TrainGaListener[Double] = (_: Int, _: Option[Double], data: Seq[(String, Any)]) => {
      dh.writeLine(data)
      log.info(s"wrote data to $dh")
    }
  }



}
