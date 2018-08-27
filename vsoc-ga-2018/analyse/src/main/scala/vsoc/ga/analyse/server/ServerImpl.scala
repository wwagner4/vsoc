package vsoc.ga.analyse.server

import java.nio.file.attribute.BasicFileAttributeView
import java.nio.file.{Files, Path}
import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime, ZoneId}
import java.util.concurrent.Executors

import org.slf4j.LoggerFactory
import vsoc.ga.analyse.{Data02Dia, DiaFactoriesB03}

object ServerImpl {

  private val log = LoggerFactory.getLogger(ServerImpl.getClass)

  import collection.JavaConverters._

  def createIndexHtml(httpPath: Path): Unit = {
    require(Files.exists(httpPath), s"$httpPath does not exist")

    val imgFiles = Files.list(httpPath).iterator().asScala.toList
      .filter(p => p.getFileName.toString.endsWith("png"))
      .map { p =>
        val attr: BasicFileAttributeView = Files.getFileAttributeView(p, classOf[BasicFileAttributeView])
        val name = p.getFileName
        val millis = attr.readAttributes().creationTime().toMillis
        val inst = Instant.ofEpochMilli(millis)
        val ldt = LocalDateTime.ofInstant(inst, ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val creationTimeStr = formatter.format(ldt)
        s"""<img src="$name"></img><p>creation time: $creationTimeStr</p>"""
      }.mkString("\n")

    val content =
      s"""
         |<html>
         |<head>
         |<title>vsoc results</title>
         |<style>
         |body {
         |     font-family: sans-serif;
         |     font-size: large;
         |}
         |h3 {
         |    text-align: center;
         |}
         |</style>
         |</head>
         |<body>
         |<h3>vsoc results</h3>
         |$imgFiles
         |<body/>
         |</html>
    """.stripMargin

    val file = httpPath.resolve("index.html")

    val pw = Files.newBufferedWriter(file)
    pw.write(content)
    pw.close()

    log.info(s"Wrote to $file")

  }

  def clearDir(base: Path): Unit = {
    require(Files.exists(base))
    require(Files.isDirectory(base))
    Files.list(base).iterator().asScala.foreach { p => Files.delete(p) }
  }

  def start(httpPath: Path, trainGa: String, period: Period): Unit = {
    log.info(s"Started Server: path:    $httpPath")
    log.info(s"                trainGa: $trainGa")
    log.info(s"                period:  $period")
    val exe = Executors.newScheduledThreadPool(10)
    exe.scheduleAtFixedRate(() => run(), 0, period.period, period.unit)
    log.info(s"started server for trainga: '$trainGa'")

    def run(): Unit = {
      clearDir(httpPath)
      new Data02Dia().createDiaTrainGa(trainGa, DiaFactoriesB03.scoreGroupedByPopulation, Some(httpPath))
    }
    createIndexHtml(httpPath)
  }

}