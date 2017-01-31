package vsoc.model;

import java.awt.*;
import java.awt.geom.*;
import vsoc.util.*;

/**
 * Player of the east team.
 */

@SuppressWarnings("serial")
public class VsocPlayerEast extends VsocPlayer {

  public VsocPlayerEast(double x, double y, double direction) {
    super(x, y, direction);
  }
  Vision createVision (SimObject o) {
    return o.createVisionForEastPlayer();
  }
  Vision createVisionForWestPlayer () {
    return new VisionPlayerOther(this.getNumber());
  }
  Vision createVisionForEastPlayer () {
    return new VisionPlayerOwn(this.getNumber());
  }
  void increaseWestGoalCount () {
    increaseOtherGoalCount();
  }
  void increaseEastGoalCount () {
    increaseOwnGoalCount();
  }
  public String getType () {
    return "EAST";
  }
  public void paint (Graphics2D g) {
    double x = getPosition().getX();
    double y = getPosition().getY();
    double w = 2;
    double h = 2;
    Shape s = new Rectangle2D.Double(x-1, y-1, w, h);
    g.setColor(Color.orange);
    g.fill(s);
    g.setColor(Color.black);
    g.draw(s);
    double a = 1 * Math.cos(getDirection());
    double b = 1 * Math.sin(getDirection());
    Line2D l = new Line2D.Double(x, y, x+a, y+b);
    g.draw(l);
  }
  public String getTeamName(){
    return ("VsocEast");
  }
  public boolean isTeamEast(){
    return true;
  }
  public void move (int x, int y) {
    setPosition(new Vec2D(-x, y));
  }
}