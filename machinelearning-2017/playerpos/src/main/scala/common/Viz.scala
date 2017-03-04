package common

import java.io.File

import common.Viz.Diagram

/**
  * Created by wwagner4 on 04/03/2017.
  */
object Viz {

  case class XY(
                 x: Number,
                 y: Number
               )

  case class DataRow(
                      name: String,
                      data: List[XY] = List.empty
                    )

  case class Diagram(
                      id: String,
                      title: String,
                      xLabel: String,
                      yLabel: String,
                      dataRows: List[DataRow] = List.empty
                    )

  def createDiagram(dia: Diagram)(implicit creator: VizCreator): Unit = {
    creator.createDiagram(dia)
  }
}

trait VizCreator {

  def createDiagram(dia: Diagram): Unit

}

case class VizCreatorGnuplot(outDir: File) extends VizCreator {


  def createDiagram(dia: Diagram): Unit = {
    val script =
      """
        |
        |
      """.stripMargin
    val id = dia.id
    val filename = s"diagram_$id.gnuplot"
    val f = new File(outDir, filename)
    Util.writeToFile(f, pw => pw.print(script))
    println(s"wrote diagram $id to $f")
  }


}
