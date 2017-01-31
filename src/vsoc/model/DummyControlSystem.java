package vsoc.model;

import java.io.Serializable;

import atan.model.*;

/**
 * A ControlSystem that does nothing. Can be used for initialization.
 */

@SuppressWarnings("serial")
class DummyControlSystem implements Controller, Serializable {

    public DummyControlSystem() {
        super();
    }

    public void setPlayer(Player p) {
        // nothing to do
    }

    public Player getPlayer() {
        return new DummyPlayer();
    }

    public void preInfo() {
        // nothing to do
    }

    public void postInfo() {
        // nothing to do
    }

    public void infoSeeFlagRight(Flag id, double distance, double direction) {
        // nothing to do
    }

    public void infoSeeFlagLeft(Flag id, double distance, double direction) {
        // nothing to do
    }

    public void infoSeeFlagOwn(Flag id, double distance, double direction) {
        // nothing to do
    }

    public void infoSeeFlagOther(Flag id, double distance, double direction) {
        // nothing to do
    }

    public void infoSeeFlagCenter(Flag id, double distance, double direction) {
        // nothing to do
    }

    public void infoSeeFlagCornerOwn(Flag id, double distance, double direction) {
        // nothing to do
    }

    public void infoSeeFlagCornerOther(Flag id, double distance,
            double direction) {
        // nothing to do
    }

    public void infoSeeFlagPenaltyOwn(Flag id, double distance, double direction) {
        // nothing to do
    }

    public void infoSeeFlagPenaltyOther(Flag id, double distance,
            double direction) {
        // nothing to do
    }

    public void infoSeeFlagGoalOwn(Flag id, double distance, double direction) {
        // nothing to do
    }

    public void infoSeeFlagGoalOther(Flag id, double distance, double direction) {
        // nothing to do
    }

    public void infoSeeLine(Line id, double distance, double direction) {
        // nothing to do
    }

    public void infoSeePlayerOther(int number, double distance, double direction) {
        // nothing to do
    }

    public void infoSeePlayerOwn(int number, double distance, double direction) {
        // nothing to do
    }

    public void infoSeeBall(double distance, double direction) {
        // nothing to do
    }

    public void infoHearReferee(RefereeMessage refereeMessage) {
        // nothing to do
    }

    public void infoHearPlayMode(PlayMode playMode) {
        // nothing to do
    }

    public void infoHear(double direction, String message) {
        // nothing to do
    }

    public void infoSenseBody(ViewQuality viewQuality, ViewAngle viewAngle,
            double stamina, double speed, double headAngle, int kickCount,
            int dashCount, int turnCount, int sayCount, int turnNeckCount) {
        // nothing to do
    }
}