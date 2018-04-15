package vsoc.ga.analyse.server

import java.nio.file.attribute.BasicFileAttributeView
import java.nio.file.{Files, Path}
import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime, ZoneId}
import java.util.concurrent.{Executors, TimeUnit}

import org.slf4j.LoggerFactory
import vsoc.ga.analyse.{Data01Dia, DiaConf_SUPRESS_TIMESTAMP}
import vsoc.ga.common.config.Config

object ServerImpl {

  private val log = LoggerFactory.getLogger(ServerImpl.getClass)

  import collection.JavaConverters._

  def createIndexHtml(httpPath: Path): Unit = {
    require(Files.exists(httpPath))

    val imgFiles = Files.list(httpPath).iterator().asScala.toList
      .filter(p => p.getFileName.toString.endsWith("png"))
      .map{p =>
        val attr: BasicFileAttributeView = Files.getFileAttributeView(p, classOf[BasicFileAttributeView])
        val name = p.getFileName
        val millis = attr.readAttributes().creationTime().toMillis
        val  inst = Instant.ofEpochMilli(millis)
        val ldt = LocalDateTime.ofInstant(inst, ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val creationTimeStr = formatter.format(ldt)
        s"""<img src="$name"></img><p>creation time: $creationTimeStr</p>"""
      }.mkString("\n")

    val content = s"""
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

  def clearDir(base: Path):Unit = {
    require(Files.exists(base))
    require(Files.isDirectory(base))
    Files.list(base).iterator().asScala.foreach{p => Files.delete(p)}
  }

  def start(workPath: Path, httpPath: Path, cfgs: Seq[Config]): Unit = {
    val cfgsStr = cfgs.map(_.id).mkString(", ")
    val exe = Executors.newScheduledThreadPool(10)
    exe.scheduleAtFixedRate(() => run(), 0, 60, TimeUnit.MINUTES)
    log.info(s"started server for configurations '$cfgsStr'")

    def run(): Unit = {
      cfgs.foreach{c =>
        log.info(s"creating data for configuration '${c.id}'")
        clearDir(httpPath)
        Data01Dia.createDiaConfig(c, workPath, diaConfs = Seq(DiaConf_SUPRESS_TIMESTAMP), diaDir = Some(httpPath))
      }
      createIndexHtml(httpPath)
    }
  }

}