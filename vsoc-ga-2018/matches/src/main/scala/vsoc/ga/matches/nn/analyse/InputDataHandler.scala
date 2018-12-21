package vsoc.ga.matches.nn.analyse

trait InputDataHandler {

  def handle(in: Array[Double]): Unit

  def close(): Unit

}
