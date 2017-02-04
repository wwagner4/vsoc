package vsoc.server;

import atan.model.*;
import java.io.PrintWriter;

public class DummyPlayer implements Player {

    public DummyPlayer() {
        // nothing to do
    }

    public void dash(int parm1) {
        // nothing to do
    }

    public void move(int parm1, int parm2) {
        // nothing to do
    }

    public void kick(int parm1, double parm2) {
        // nothing to do
    }

    public void say(String parm1) {
        // nothing to do
    }

    public void senseBody() {
        // nothing to do
    }

    public void turn(double parm1) {
        // nothing to do
    }

    public void turnNeck(double parm1) {
        // nothing to do
    }

    public void catchBall(double parm1) {
        // nothing to do
    }

    public void changeViewMode(ViewQuality parm1, ViewAngle parm2) {
        // nothing to do
    }

    public void bye() {
        // nothing to do
    }

    public void handleError(String parm1) {
        // nothing to do
    }

    public String getTeamName() {
        return "dummy";
    }

    public boolean isTeamEast() {
        return false;
    }

    public void setTeamEast(boolean parm1) {
        // nothing to do
    }

    public void setNumber(int parm1) {
        // nothing to do
    }

    public int getNumber() {
        return -1;
    }

    public PrintWriter getLogger() {
        return new PrintWriter(new NullLogger());
    }

    public int getLoglevel() {
        return 0;
    }
}