package vsoc.ga.analyse.server

import java.nio.file._
import java.nio.file.attribute.BasicFileAttributeView
import java.time.format.DateTimeFormatter
import java.time.{Instant, LocalDateTime, ZoneId}
import java.util.concurrent.{Executors, TimeUnit}

import vsoc.ga.analyse.{Data01Dia, DiaConf_SUPRESS_TIMESTAMP}
import vsoc.ga.common.UtilReflection
import vsoc.ga.common.config.{Config, Configs}

object Server extends App {

  require(args.length == 3, "Three parameters required")

  val workDir = args(0)
  val httpDir = args(1)
  val configsList = args(2)

  val workPath = Paths.get(workDir)
  require(workPath.isAbsolute, s"'$workPath' must be an absolute path")

  val httpPath = Paths.get(httpDir)
  require(httpPath.isAbsolute, s"'$httpPath' must be an absolute path")

  val configs: Seq[Config] = configsList.split(",").toSeq.map(getConfig)

  ServerImpl.start(workPath, httpPath, configs)

  def getConfig(name: String): Config = UtilReflection.call(Configs, name, classOf[Config])

}

object ServerImpl {

  import collection.JavaConverters._

  def createIndexHtml(httpPath: Path): Unit = {
    require(Files.exists(httpPath))

    val x = Files.list(httpPath).iterator().asScala.toList.map(_.getFileName)


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

    println(s"Wrote to $file")

  }

  def start(workPath: Path, httpPath: Path, cfgs: Seq[Config]): Unit = {
    val exe = Executors.newScheduledThreadPool(10)
    exe.scheduleAtFixedRate(() => run(), 0, 60, TimeUnit.MINUTES)
    println("started server")

    def run(): Unit = {
      cfgs.foreach{c =>
        Data01Dia.run(c, diaConfs = Seq(DiaConf_SUPRESS_TIMESTAMP), diaDir = Some(httpPath))
        println(s"created data for configuration '${c.id}'")
      }
      createIndexHtml(httpPath)
    }
  }

}