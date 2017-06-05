package vsoc.behaviour;

import atan.model.Controller;
import atan.model.Flag;
import atan.model.Player;
import org.junit.Test;
import vsoc.server.CtrlServer;
import vsoc.server.ServerUtil;
import vsoc.server.VsocPlayer;
import vsoc.server.initial.InitialPlacementNone;
import vsoc.server.initial.InitialPlacementOnePlayerFull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Tests for behaviour and sensors
 */
public class TestBehaviour {

    @Test
    public void test_OwnGoal_Far() {
        Sensors s = runBehaviour(-15, 0.0, 180);
        assertTrue(s.sawFlagGoalOwn());
        Map<Flag, DistDirVision> flags = s.getFlagsGoalOwn();

        assertEquals(3, flags.size());

        {
            DistDirVision ddv = flags.get(Flag.FLAG_LEFT);
            assertTrue(ddv.getDistance() > 30.0);
            assertTrue(ddv.getDistance() < 50.0);
            assertTrue(ddv.getDirection() > 0.0);
            assertTrue(ddv.getDirection() < 30.0);
        }
        {
            DistDirVision ddv = flags.get(Flag.FLAG_CENTER);
            assertTrue(ddv.getDistance() > 30.0);
            assertTrue(ddv.getDistance() < 50.0);
            assertEquals(0.0, ddv.getDirection(), 0.01);
        }
        {
            DistDirVision ddv = flags.get(Flag.FLAG_RIGHT);
            assertTrue(ddv.getDistance() > 30.0);
            assertTrue(ddv.getDistance() < 50.0);
            assertTrue(ddv.getDirection() < 0.0);
            assertTrue(ddv.getDirection() > -30.0);
        }


    }

    @Test
    public void test_OwnGoal_Near() {
        Sensors s = runBehaviour(-35, 0.0, 180);
        assertTrue(s.sawFlagGoalOwn());
        Map<Flag, DistDirVision> flags = s.getFlagsGoalOwn();

        assertEquals(3, flags.size());

        {
            DistDirVision ddv = flags.get(Flag.FLAG_LEFT);
            assertTrue(ddv.getDistance() > 12.0);
            assertTrue(ddv.getDistance() < 20.0);
            assertTrue(ddv.getDirection() > 0.0);
            assertTrue(ddv.getDirection() < 30.0);
        }
        {
            DistDirVision ddv = flags.get(Flag.FLAG_CENTER);
            assertTrue(ddv.getDistance() > 12.0);
            assertTrue(ddv.getDistance() < 20.0);
            assertEquals(0.0, ddv.getDirection(), 0.01);
        }
        {
            DistDirVision ddv = flags.get(Flag.FLAG_RIGHT);
            assertTrue(ddv.getDistance() > 12.0);
            assertTrue(ddv.getDistance() < 20.0);
            assertTrue(ddv.getDirection() < 0.0);
            assertTrue(ddv.getDirection() > -30.0);
        }

    }

    private Sensors runBehaviour(double x, double y, double dir) {
        InitialPlacementOnePlayerFull east = new InitialPlacementOnePlayerFull(x, y, dir);
        InitialPlacementNone west = new InitialPlacementNone();
        CtrlServer server = ServerUtil.current().createServer(west, east);
        BehaviourSensorExtracting behav = new BehaviourSensorExtracting();
        Controller ctrl = new BehaviourController(behav);
        for(VsocPlayer player : server.getPlayers()) {
            player.setController(ctrl);
        }
        server.takeStep();
        return behav.getSens();
    }


}

class BehaviourSensorExtracting implements Behaviour {

    private Sensors sens = null;

    @Override
    public boolean shouldBeApplied(Sensors sens) {
        return true;
    }

    @Override
    public void apply(Sensors sens, Player player) {
        this.sens = sens;
    }

    @Override
    public Optional<Behaviour> getChild() {
        return Optional.empty();
    }

    public Sensors getSens() {
        return sens;
    }

}