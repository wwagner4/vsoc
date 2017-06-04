package vsoc.server;

import atan.model.*;
import org.junit.Test;
import vsoc.server.initial.InitialPlacementNone;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Test what flags are seen by the players when they are placed at a certain position,
 * look in a certain direction,
 */
public class TestPlaceLookSee {

    @Test
    public void test_beforeTheOpponentGoal() {
        List<FlagDesc> flags = place(40, 0, 0);

        assertContains(flags, "GoalOther_FLAG_LEFT");
        assertContains(flags, "GoalOther_FLAG_CENTER");
        assertContains(flags, "GoalOther_FLAG_RIGHT");

        assertContains(flags, "Other_FLAG_LEFT_10");
        assertContains(flags, "Other_FLAG_RIGHT_10");
    }

    @Test
    public void test_beforeTheOwnGoal() {
        List<FlagDesc> flags = place(-40, 0, 180);

        assertContains(flags, "GoalOwn_FLAG_LEFT");
        assertContains(flags, "GoalOwn_FLAG_CENTER");
        assertContains(flags, "GoalOwn_FLAG_RIGHT");

        assertContains(flags, "Own_FLAG_LEFT_10");
        assertContains(flags, "Own_FLAG_RIGHT_10");
    }

    @Test
    public void test_beforeTheOwnGoalGreatDist() {
        List<FlagDesc> flags = place(-10, 0, 180);

        assertContains(flags, "GoalOwn_FLAG_LEFT");
        assertContains(flags, "GoalOwn_FLAG_CENTER");
        assertContains(flags, "GoalOwn_FLAG_RIGHT");

        assertContains(flags, "Own_FLAG_LEFT_10");
        assertContains(flags, "Own_FLAG_RIGHT_10");

        assertContains(flags, "PenaltyOwn_FLAG_LEFT");
        assertContains(flags, "PenaltyOwn_FLAG_CENTER");
        assertContains(flags, "PenaltyOwn_FLAG_RIGHT");
    }

    @Test
    public void test_atTheCenterLineLookRight_Far() {
        List<FlagDesc> flags = place(0, -20, 90);

        assertContains(flags, "Right_FLAG_OWN_10");
        assertContains(flags, "Right_FLAG_OTHER_10");
        // And many more

    }

    @Test
    public void test_atTheCenterLineLookRight_Near() {
        List<FlagDesc> flags = place(0, 20, 90);

        assertContains(flags, "Right_FLAG_OWN_10");
        assertContains(flags, "Right_FLAG_OTHER_10");

    }

    @Test
    public void test_distAlwaysPos() {
        List<FlagDesc> flags = place(0, 20, 90);
        flags.forEach(f -> assertTrue("distance smaller 0 " + f.getDist(), f.getDist() >= 0));
    }

    /**
     * @param x   Players X Position. Relative to own side
     * @param y   Players y Position. Relative to own side
     * @param dir Players look direction. Relative to own side.
     * @return List of Flags seen by the Player
     */
    private List<FlagDesc> place(double x, double y, double dir) {
        List<FlagDesc> westFlags = place(x, y, dir, false);
        List<FlagDesc> eastFlags = place(x, y, dir, true);
        printFlags("west", westFlags);
        printFlags("east", eastFlags);
        assertEqualFlags(westFlags, eastFlags);
        return westFlags;
    }

    private void printFlags(String sideName, List<FlagDesc> flags) {
        println("-- Flags for %s player --", sideName);
        for(FlagDesc fd : flags) {
            println("   %s", fd);
        }
    }

    private void println(String format, Object... params) {
        System.out.printf(format + "%n", params);
    }

    private List<FlagDesc> place(double x, double y, double dir, boolean eastPlayer) {

        InitialPlacement plWest;
        InitialPlacement plEast;
        if (eastPlayer) {
            plWest = new InitialPlacementNone();
            plEast = new InitialPlacementOneAt(x, y, dir);
        } else {
            plWest = new InitialPlacementOneAt(x, y, dir);
            plEast = new InitialPlacementNone();
        }
        Server server = ServerUtil.current().createServer(plWest, plEast);
        ControllerFlagDesc ctrl = new ControllerFlagDesc();
        int cnt = 0;
        for (VsocPlayer p : server.getPlayers()) {
            p.setController(ctrl);
            cnt++;
        }
        assertEquals("There must be exactly one player", 1, cnt);
        server.takeStep();
        return ctrl.getFlagDescs();
    }

    private void assertEqualFlags(List<FlagDesc> westFlags, List<FlagDesc> eastFlags) {
        int westCnt = westFlags.size();
        int eastCnt = eastFlags.size();
        assertEquals(String.format("Number of flags seen by the east player are not equal to the number of flags seen by the west player. west %d. east %d",
                westCnt, eastCnt), westCnt, eastCnt);
        for (FlagDesc wDesc : westFlags) {
            FlagDesc eDesc = find(eastFlags, wDesc.getName());
            assertFlagDescsEqual(wDesc, eDesc);
        }
    }

    private void assertFlagDescsEqual(FlagDesc wDesc, FlagDesc eDesc) {
        assertEquals("Names not equal", wDesc.getName(), eDesc.getName());
        assertEquals("Distance not equal", wDesc.getDist(), eDesc.getDist(), 0.001);
        assertEquals("Direction not equal", wDesc.getDir(), eDesc.getDir(), 0.001);
    }

    private FlagDesc find(List<FlagDesc> flags, String name) {
        List<FlagDesc> flagDescs = flags.stream().filter(ed -> ed.getName().equals(name)).collect(Collectors.toList());
        if (flagDescs.size() == 0) {
            throw new IllegalStateException("Found no entry named " + name);
        } else if (flagDescs.size() > 1) {
            throw new IllegalStateException("Found more than one entry named " + name);
        }
        return flagDescs.get(0);
    }

    private void assertContains(List<FlagDesc> flags, String name) {
        assertTrue("Found no flag named " + name, flags.stream().map(d -> d.getName()).anyMatch(n -> n.endsWith(name)));
    }

}

class FlagDesc {

    private String name;
    private double dist;
    private double dir;

    public FlagDesc(String name, double dist, double dir) {
        this.name = name;
        this.dist = dist;
        this.dir = dir;
    }

    public String getName() {
        return name;
    }

    public double getDist() {
        return dist;
    }

    public double getDir() {
        return dir;
    }

    @Override
    public String toString() {
        return String.format("F[%10s dist:%-5.2f dir:%-5.2f]", name, dist, dir);
    }
}

class InitialPlacementOneAt implements InitialPlacement {

    private double x;
    private double y;
    private double d;

    public InitialPlacementOneAt(double x, double y, double d) {
        this.x = x;
        this.y = y;
        this.d = d;
    }

    @Override
    public int numberOfPlayers() {
        return 1;
    }

    @Override
    public Values placementValuesWest(int number) {
        return new Values(this.x, this.y, this.d);
    }
}

class ControllerFlagDesc implements Controller {

    private List<FlagDesc> flagDescs;

    public List<FlagDesc> getFlagDescs() {
        return flagDescs;
    }

    @Override
    public void preInfo() {
        this.flagDescs = new ArrayList<>();
    }

    @Override
    public void postInfo() {
        // Nothing to do
    }

    @Override
    public Player getPlayer() {
        throw new IllegalStateException("Should not be called");
    }

    @Override
    public void setPlayer(Player c) {
        // Ignore Player
    }

    @Override
    public void infoSeeFlagRight(Flag flag, double distance, double direction) {
        this.flagDescs.add(new FlagDesc("Right_" + flag.name(), distance, direction));
    }

    @Override
    public void infoSeeFlagLeft(Flag flag, double distance, double direction) {
        this.flagDescs.add(new FlagDesc("Left_" + flag.name(), distance, direction));
    }

    @Override
    public void infoSeeFlagOwn(Flag flag, double distance, double direction) {
        this.flagDescs.add(new FlagDesc("Own_" + flag.name(), distance, direction));
    }

    @Override
    public void infoSeeFlagOther(Flag flag, double distance, double direction) {
        this.flagDescs.add(new FlagDesc("Other_" + flag.name(), distance, direction));
    }

    @Override
    public void infoSeeFlagCenter(Flag flag, double distance, double direction) {
        this.flagDescs.add(new FlagDesc("Center_" + flag.name(), distance, direction));
    }

    @Override
    public void infoSeeFlagCornerOwn(Flag flag, double distance, double direction) {
        this.flagDescs.add(new FlagDesc("CornerOwn_" + flag.name(), distance, direction));
    }

    @Override
    public void infoSeeFlagCornerOther(Flag flag, double distance, double direction) {
        this.flagDescs.add(new FlagDesc("CornerOther_" + flag.name(), distance, direction));
    }

    @Override
    public void infoSeeFlagPenaltyOwn(Flag flag, double distance, double direction) {
        this.flagDescs.add(new FlagDesc("PenaltyOwn_" + flag.name(), distance, direction));
    }

    @Override
    public void infoSeeFlagPenaltyOther(Flag flag, double distance, double direction) {
        this.flagDescs.add(new FlagDesc("PenaltyOther_" + flag.name(), distance, direction));
    }

    @Override
    public void infoSeeFlagGoalOwn(Flag flag, double distance, double direction) {
        this.flagDescs.add(new FlagDesc("GoalOwn_" + flag.name(), distance, direction));
    }

    @Override
    public void infoSeeFlagGoalOther(Flag flag, double distance, double direction) {
        this.flagDescs.add(new FlagDesc("GoalOther_" + flag.name(), distance, direction));
    }

    @Override
    public void infoSeeLine(Line line, double distance, double direction) {
        // Nothing to do
    }

    @Override
    public void infoSeePlayerOther(int number, double distance, double direction) {
        // Nothing to do
    }

    @Override
    public void infoSeePlayerOwn(int number, double distance, double direction) {
        // Nothing to do
    }

    @Override
    public void infoSeeBall(double distance, double direction) {
        // Nothing to do
    }

    @Override
    public void infoHearReferee(RefereeMessage refereeMessage) {
        // Nothing to do
    }

    @Override
    public void infoHearPlayMode(PlayMode playMode) {
        // Nothing to do
    }

    @Override
    public void infoHear(double direction, String message) {
        // Nothing to do
    }

    @Override
    public void infoSenseBody(ViewQuality viewQuality, ViewAngle viewAngle, double stamina, double speed, double headAngle, int kickCount, int dashCount, int turnCount, int sayCount, int turnNeckCount) {
        // Nothing to do
    }
}