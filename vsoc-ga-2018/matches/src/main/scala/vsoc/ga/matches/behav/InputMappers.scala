package vsoc.ga.matches.behav

import atan.model.Flag
import vsoc.behaviour.Sensors
import vsoc.ga.matches.retina.Retinas

object InputMappers {

  def default: InputMapperNn = new _InputMapperNn(1.0)

  def withActivationFactor(activationFactor: Double): InputMapperNn = new _InputMapperNn(activationFactor)


  private class _InputMapperNn(activationFactor: Double) extends InputMapperNn {

    private val size = 140

    private val retinas: Retinas = Retinas(_activationFactor = activationFactor)

    private val _retinas = Seq(
      retinas.goalOwn(0, 5, Flag.FLAG_LEFT),
      retinas.goalOwn(5, 5, Flag.FLAG_CENTER),
      retinas.goalOwn(10, 5, Flag.FLAG_RIGHT),
      retinas.penaltyOwn(15, 5, Flag.FLAG_LEFT),
      retinas.penaltyOwn(20, 5, Flag.FLAG_CENTER),
      retinas.penaltyOwn(25, 5, Flag.FLAG_RIGHT),
      retinas.cornerOwn(30, 5, Flag.FLAG_LEFT),
      retinas.cornerOwn(35, 5, Flag.FLAG_CENTER),
      retinas.cornerOwn(40, 5, Flag.FLAG_RIGHT),

      retinas.goalOther(45, 5, Flag.FLAG_LEFT),
      retinas.goalOther(50, 5, Flag.FLAG_CENTER),
      retinas.goalOther(55, 5, Flag.FLAG_RIGHT),
      retinas.penaltyOther(60, 5, Flag.FLAG_LEFT),
      retinas.penaltyOther(65, 5, Flag.FLAG_CENTER),
      retinas.penaltyOther(70, 5, Flag.FLAG_RIGHT),
      retinas.cornerOther(75, 5, Flag.FLAG_LEFT),
      retinas.cornerOther(80, 5, Flag.FLAG_CENTER),
      retinas.cornerOther(85, 5, Flag.FLAG_RIGHT),

      retinas.center(90, 5, Flag.FLAG_LEFT),
      retinas.center(95, 5, Flag.FLAG_CENTER),
      retinas.center(100, 5, Flag.FLAG_RIGHT),

      retinas.playerOwn(105, 5, 0),
      retinas.playerOwn(110, 5, 1),
      retinas.playerOwn(115, 5, 2),

      retinas.playerOther(120, 5, 0),
      retinas.playerOther(125, 5, 1),
      retinas.playerOther(130, 5, 2),

      retinas.ball(135, 5),

    )

    override def mapSensors(sens: Sensors): Option[Array[Double]] = {
      val activations = Array.fill(size)(0.0)
      _retinas.foreach(_.see(sens, activations))
      if (activations.exists(v => v != 0.0)) Some(activations)
      else None
    }

  }


}
