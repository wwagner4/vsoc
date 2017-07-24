import org.slf4j.{Logger, LoggerFactory}

object Training extends App {
  val log: Logger = LoggerFactory.getLogger(classOf[Training])
  new Training(log).train()
}

class Training(log: Logger) {

  def train(): Unit = {
    log.info("start training")
  }

}