package vsoc.training.adaption

import java.io.File

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork
import org.deeplearning4j.util.ModelSerializer
import org.nd4j.linalg.factory.Nd4j
import vsoc.common.Viz.Range
import vsoc.common.{Dat, Viz}
import vsoc.training.util.{Param_b, UtilNetwork}

object ManualBiasAdaption extends App {

  import vsoc.common.UtilIO

  val dirRunName = "playerpos_x/20170824-153025"
  val dirWork = new File(UtilIO.dirWork, dirRunName)

  val ids = Seq(
    ("A_100", -1.1),
    ("A_200", -1.35),
    ("A_300", -1.5),
    ("A_500", 1.5),
    ("B_100", -0.8),
    ("B_200", 1.7),
    ("B_300", 1.6),
    ("B_500", 1.6),
    ("C_100", -1.0),
    ("C_200", -1.5),
    ("C_300", 1.4),
    ("C_500", 1.6)
  )

  def file(id: String): File = {
    new File(dirWork, s"nn_$id.ser")
  }

  def loadNet(f: File): MultiLayerNetwork = {
    ModelSerializer.restoreMultiLayerNetwork(f)
  }

  def adopt(f: File, adoption: Double): MultiLayerNetwork = {
    val ndAdoption = Nd4j.create(Array(adoption))
    require(f.exists(), s"File $f must exist")
    println(s"Adopting $f : $adoption")
    val nn = loadNet(f)
    println(s"Loaded NN $nn")
    val ndActual = UtilNetwork.getNetworkParam(nn, 2, Param_b)
    println(s"Actual bias $ndActual")
    val ndNewVal = ndActual.add(ndAdoption)
    println(s"New bias $ndNewVal")
    UtilNetwork.setNetworkParam(ndNewVal, nn, 2, Param_b)
    println("Set new value")
    //UtilNetwork.printParam(nn)
    nn
  }

  val adoptedNets = for ((id, adoption) <- ids) yield {
    val f = file(id)
    (id, adopt(f, adoption))
  }

  val testDatas = Seq(Dat.Id_C, Dat.Id_D, Dat.Id_E)
    .map(Dat.DataDesc(Dat.Data_PLAYERPOS_X, _, Dat.Size_1000))

  val mdia = Viz.MultiDiagram[Viz.X](
    id = "manualBiasAdoption",
    columns = 4,
    title = Some("Manual Bias Adoption"),
    imgWidth = 1800,
    imgHeight = 1500,
    diagrams = for ((id, nn) <- adoptedNets) yield {
      Viz.Diagram[Viz.X](
        id = id,
        title = s"""NN ${trim(id)}""",
        yRange = Some(Range(Some(-15), Some(15))),
        xLabel = Some("testdataset"),
        xZeroAxis = true,
        dataRows = for (testData <- testDatas) yield {
          Viz.DataRow[Viz.X] (
            style = Viz.Style_BOXPLOT,
            name = Some(testData.id.code),
            data = UtilNetwork.test(nn, testData)
          )
        }
      )
    }
  )

  Viz.createDiagram(mdia)(vsoc.common.VizCreatorGnuplot(dirWork, dirWork))

  def trim(id: String) = id.replaceAll("_", "-")

}
