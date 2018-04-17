package vsoc.ga.trainga.nn.analyse

trait InputDataHandler {

  def handle(in: Array[Double]): Unit

  def close(): Unit

}
