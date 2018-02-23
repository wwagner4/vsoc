package vsoc.ga.trainga

import java.nio.file.Paths
import java.util.Locale

import atan.model.{Flag, Player, ViewAngle, ViewQuality}
import org.scalatest.{FunSuite, MustMatchers}
import vsoc.behaviour.{DistDirVision, Sensors}
import vsoc.ga.common.UtilReflection
import vsoc.ga.common.persist.Persistors
import vsoc.ga.trainga.ga.impl.{InputMapperNnTeam, OutputMapperNnTeam, RandomElemsPicker}
import vsoc.ga.trainga.nn.{NeuralNet, NeuralNetPersist, NeuralNets}

import scala.collection.JavaConverters._
import scala.util.Random

class TrainGaSuite extends FunSuite with MustMatchers {

  test("Wrapper get param") {
    val nn = NeuralNets.test
    val p = nn.getParam
    p.length mustBe 63
  }

  test("Wrapper set param wrong size") {
    val p = Array(2.3, 1.2, 3.4, 5.6)
    val nn = NeuralNets.test
    an[IllegalStateException] must be thrownBy nn.setParam(p)
  }

  test("Wrapper set param right size") {
    val p = Stream.iterate(0.0)(a => a + 1.0).take(63).toArray
    val nn = NeuralNets.test
    nn.setParam(p)
    val p1 = nn.getParam
    p1.length mustBe p.length
    p1.map(f).mkString(" ") mustBe p.map(f).mkString(" ")
  }

  test("Wrapper set param and output") {
    val p1 = Stream.iterate(0.0)(a => a + 1.0).take(63).toArray
    val p2 = Stream.iterate(3.0)(a => a + 1.0).take(63).toArray
    val nn = NeuralNets.test
    val in = Array(1.0, 2.0)

    nn.setParam(p1)
    val out1 = nn.output(in)

    nn.setParam(p2)
    val out2 = nn.output(in)
    out2.map(f).mkString(" ") must not be out1.map(f).mkString(" ")

    nn.setParam(p1)
    val out3 = nn.output(in)
    out3.map(f).mkString(" ") mustBe out1.map(f).mkString(" ")

  }

  test("reflection load net") {
    val nn = UtilReflection.call(NeuralNets, "default", classOf[NeuralNet])
    nn.getParam.length mustBe 63
  }

  test("save load neural net") {
    val nn = NeuralNets.test
    val params = Stream.continually(1.0).take(63).toArray
    nn.setParam(params)

    val file = Paths.get(".test", "nn", "01.nn")
    val p = Persistors.workDir

    p.save(file)(oos => NeuralNetPersist.save(nn, oos))


    val nn1: NeuralNet = p.load(file) {
      ois =>
        val nn: AnyRef = NeuralNetPersist.load(ois)
        nn.asInstanceOf[NeuralNet]
    }

    nn1.getParam.map(f).mkString(" ") mustBe params.map(f).mkString(" ")
  }

  def f(d: Double): String = f"$d%2.0f"

  test("InputMapperNnTeam(1.0) empty sensor") {
    val sens: Sensors = emptySensor
    val m = new InputMapperNnTeam(1.0)
    m.mapSensors(sens) mustBe None
  }

  test("InputMapperNnTeam(1.0) sensor with irrelevant info flags right") {
    val sens: Sensors = sensorFlags(Flag.FLAG_RIGHT_10, Flag.FLAG_RIGHT_30)
    val m = new InputMapperNnTeam(1.0)
    m.mapSensors(sens) mustBe None
  }

  test("InputMapperNnTeam(1.0) sensor with irrelevant info flags other") {
    val sens: Sensors = sensorFlags(Flag.FLAG_OTHER_20, Flag.FLAG_OTHER_40)
    val m = new InputMapperNnTeam(1.0)
    m.mapSensors(sens) mustBe None
  }

  test("InputMapperNnTeam(1.0) sensor with irrelevant info flags own") {
    val sens: Sensors = sensorFlags(Flag.FLAG_OWN_20)
    val m = new InputMapperNnTeam(1.0)
    m.mapSensors(sens) mustBe None
  }

  test("InputMapperNnTeam(1.0) sensor with irrelevant info flags left") {
    val sens: Sensors = sensorFlags(Flag.FLAG_LEFT_10, Flag.FLAG_LEFT_20, Flag.FLAG_LEFT_30)
    val m = new InputMapperNnTeam(1.0)
    m.mapSensors(sens) mustBe None
  }

  test("InputMapperNnTeam(1.0) sensor goal own left") {
    val sens: Sensors = sensorGoalOwn(Flag.FLAG_LEFT)
    val m = new InputMapperNnTeam(1.0)
    val a = m.mapSensors(sens).get
    (0 to 4).exists(a(_) > 0.0) mustBe true
  }

  test("InputMapperNnTeam(1.0) sensor goal own center") {
    val sens: Sensors = sensorGoalOwn(Flag.FLAG_CENTER)
    val m = new InputMapperNnTeam(1.0)
    val a = m.mapSensors(sens).get
    (5 to 9).exists(a(_) > 0.0) mustBe true
  }

  test("InputMapperNnTeam(1.0) sensor goal own right") {
    val sens: Sensors = sensorGoalOwn(Flag.FLAG_RIGHT)
    val mapper = new InputMapperNnTeam(1.0)
    val a = mapper.mapSensors(sens).get
    (10 to 14).exists(a(_) > 0.0) mustBe true
  }

  test("InputMapperNnTeam(1.0) sensor penalty other center") {
    val sens: Sensors = sensorPenaltyOther(Flag.FLAG_CENTER)
    val mapper = new InputMapperNnTeam(0.1)
    val a = mapper.mapSensors(sens).get
    (65 to 69).exists(a(_) > 0.0) mustBe true
  }

  test("InputMapperNnTeam(1.0) sensor penalty other right") {
    val sens: Sensors = sensorPenaltyOther(Flag.FLAG_RIGHT)
    val mapper = new InputMapperNnTeam(1.0)
    val a = mapper.mapSensors(sens).get
    (70 to 74).exists(a(_) > 0.0) mustBe true
  }

  test("InputMapperNnTeam(1.0) sensor corner other right") {
    val sens: Sensors = sensorCornerOther(Flag.FLAG_RIGHT)
    val mapper = new InputMapperNnTeam(1.4)
    val a = mapper.mapSensors(sens).get
    (85 to 89).exists(a(_) > 0.0) mustBe true
  }

  test("InputMapperNnTeam(1.0) sensor corner other center") {
    val sens: Sensors = sensorCornerOther(Flag.FLAG_CENTER)
    val mapper = new InputMapperNnTeam(1.0)
    val a = mapper.mapSensors(sens).get
    (80 to 84).exists(a(_) > 0.0) mustBe true
  }

  test("InputMapperNnTeam(1.0) sensor center other center") {
    val sens: Sensors = sensorCenter(Flag.FLAG_CENTER)
    val mapper = new InputMapperNnTeam(1.2)
    val a = mapper.mapSensors(sens).get
    (95 to 99).exists(a(_) > 0.0) mustBe true
  }

  test("InputMapperNnTeam(1.0) sensor ball") {
    val sens: Sensors = sensorBall
    val mapper = new InputMapperNnTeam(1.0)
    val a = mapper.mapSensors(sens).get
    (135 to 139).exists(a(_) > 0.0) mustBe true
  }

  test("OutputMapperNnTeam no activation") {
    val p = PlayerTest(0)
    val out: Array[Double] = Array(0.0, 0.0, 0.0, 0.0)
    val m = new OutputMapperNnTeam
    m.applyOutput(p, out)
    p.result mustBe ""
  }

  test("OutputMapperNnTeam dash 1.0") {
    val p = PlayerTest(0)
    val out: Array[Double] = Array(1.0, 0.0, 0.0, 0.0)
    val m = new OutputMapperNnTeam
    m.applyOutput(p, out)
    p.result mustBe "dash[100]"
  }

  test("OutputMapperNnTeam dash 2.3 + kick") {
    val p = PlayerTest(0)
    val out: Array[Double] = Array(2.29, 4.1, 0.0, 0.0)
    val m = new OutputMapperNnTeam
    m.applyOutput(p, out)
    p.result mustBe "dash[229]kick[410,0.00]"
  }

  test("OutputMapperNnTeam dash 2.71 + kick + turn") {
    val p = PlayerTest(0)
    val out: Array[Double] = Array(2.716, 4.1, 1.0, 0.01)
    val m = new OutputMapperNnTeam
    m.applyOutput(p, out)
    p.result mustBe "dash[272]kick[410,10.00]turn[0.10]"
  }

  test("OutputMapperNnTeam kick 4.1 + turn") {
    val p = PlayerTest(0)
    val out: Array[Double] = Array(0.00499, 4.1, 1.0, 3.0)
    val m = new OutputMapperNnTeam
    m.applyOutput(p, out)
    p.result mustBe "kick[410,10.00]turn[30.00]"
  }

  test("OutputMapperNnTeam kick 4.2 + turn") {
    val p = PlayerTest(0)
    val out: Array[Double] = Array(-2, 4.2, 1.0, -3.1111)
    val m = new OutputMapperNnTeam
    m.applyOutput(p, out)
    p.result mustBe "kick[420,10.00]turn[-31.11]"
  }

  test("RandomElemsPicker") {
    val i = collection.mutable.Set.empty[Int]
    val ran = new Random(123123L)
    val seq = 0 to 9
    for (_ <- 1 to 50) {
      val (a, b) = RandomElemsPicker.pick(seq, ran)
      i.add(a)
      i.add(b)
      a must not be b
    }
    (0 to 9).foreach(v => assert(i.contains(v)))
  }

  def emptySensor: Sensors = new Sensors

  def nonemptyDdv = new DistDirVision(1.0, 2.0)

  def sensorBall: Sensors = {
    val sensors = new Sensors
    sensors.setBall(nonemptyDdv)
    sensors
  }

  def sensorFlags(flags: Flag*): Sensors = {
    val sensors = new Sensors
    val map = flags.map(f => (f, new DistDirVision(1.0, 2.0))).toMap.asJava
    sensors.setFlagsRight(map)
    sensors
  }

  def sensorGoalOwn(flag: Flag): Sensors = {
    val sensors = new Sensors
    val flags = Map(flag -> nonemptyDdv).asJava
    sensors.setFlagsGoalOwn(flags)
    sensors
  }

  def sensorPenaltyOther(flag: Flag): Sensors = {
    val sensors = new Sensors
    val flags = Map(flag -> nonemptyDdv).asJava
    sensors.setFlagsPenaltyOther(flags)
    sensors
  }

  def sensorCornerOther(flag: Flag): Sensors = {
    val sensors = new Sensors
    val flags = Map(flag -> nonemptyDdv).asJava
    sensors.setFlagsCornerOther(flags)
    sensors
  }

  def sensorCenter(flag: Flag): Sensors = {
    val sensors = new Sensors
    val flags = Map(flag -> nonemptyDdv).asJava
    sensors.setFlagsCenter(flags)
    sensors
  }

  case class PlayerTest(num: Int) extends Player {

    var result = ""

    override def turnNeck(angle: Double): Unit = ()

    override def changeViewMode(quality: ViewQuality, angle: ViewAngle): Unit = ()

    override def turn(angle: Double): Unit = result += f"turn[${fmt(angle)}]"

    override def getNumber: Int = num

    override def handleError(error: String): Unit = ()

    override def getTeamName: String = "TEAM Test"

    override def senseBody(): Unit = ()

    override def move(x: Int, y: Int): Unit = ()

    override def say(message: String): Unit = result += s"say[$message]"

    override def setTeamEast(is: Boolean): Unit = ()

    override def setNumber(num: Int): Unit = ()

    override def bye(): Unit = ()

    override def kick(power: Int, direction: Double): Unit = result += f"kick[${fmt(power)},${fmt(direction)}]"

    override def isTeamEast: Boolean = true

    override def dash(power: Int): Unit = result += f"dash[${fmt(power)}]"

    override def catchBall(direction: Double): Unit = ()
  }

  def fmt(v: Int): String = "%d".formatLocal(Locale.ENGLISH, v)

  def fmt(v: Double): String = "%.2f".formatLocal(Locale.ENGLISH, v)

}


