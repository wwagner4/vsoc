package vsoc.ga.matches

import atan.model.Controller

trait Team {


  def controller(i: Int): Controller

  def playersCount: Int

}
