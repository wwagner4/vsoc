package vsoc.model;

import vsoc.util.*;
import atan.model.*;


/**
 * Representation of a simulated Object relative to an
 * other object.
 */

public abstract class Vision {

  private Vec2D position;

  public abstract void informControlSystem (Controller c);
  public Vec2D getPosition() {
    return this.position;
  }
  public void setPosition(Vec2D p) {
    this.position = p;
  }
  protected double getDistance(){
    return this.position.length();
  }
  protected double getDirection(){
    return -this.position.phi()*57.29577951;
  }
}
