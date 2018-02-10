package vsoc.behaviour;

import atan.model.Flag;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TestSensors {

    @Test
    public void test_Ball_NotPresent() {
        Sensors s = new Sensors();
        assertNotNull(s.getBall());
        assertFalse(s.getBall().isPresent());
    }

    @Test
    public void test_Ball_Present() {
        Sensors s = new Sensors();
        s.setBall(dirDistVision());
        assertNotNull(s.getBall());
        assertTrue(s.getBall().isPresent());
    }

    @Test
    public void test_Messages_empty() {
        Sensors s = new Sensors();
        assertNotNull(s.getMessages());
        assertTrue(s.getMessages().isEmpty());
    }

    @Test
    public void test_Messages_one() {
        Sensors s = new Sensors();
        s.setMessages(Collections.singletonList(new Message("hallo", 0.1)));
        assertNotNull(s.getMessages());
        assertFalse(s.getMessages().isEmpty());
    }

    @Test
    public void test_BodyState() {
        Sensors s = new Sensors();
        assertNotNull(s.getBodyState());
        assertFalse(s.getBodyState().isPresent());
    }

    @Test
    public void test_BodyState_defined() {
        Sensors s = new Sensors();
        s.setBodyState(bodyState());
        assertNotNull(s.getBodyState());
        assertTrue(s.getBodyState().isPresent());
    }

    @Test
    public void test_PlayMode() {
        Sensors s = new Sensors();
        assertNotNull(s.getPlayMode());
    }

    @Test
    public void test_RefereeMessage() {
        Sensors s = new Sensors();
        assertNotNull(s.getRefereeMessage());
    }

    @Test
    public void test_sawFlagGoalOwn() {
        Sensors s = new Sensors();
        s.getFlagsGoalOwn();
        assertFalse(s.sawFlagGoalOwn());
    }

    @Test
    public void test_sawFlagGoalOwn_saw() {
        Sensors s = new Sensors();
        s.setFlagsGoalOwn(oneFlag());
        s.getFlagsGoalOwn();
        assertTrue(s.sawFlagGoalOwn());
    }

    @Test
    public void test_sawFlagLeft() {
        Sensors s = new Sensors();
        s.getFlagsLeft();
        assertFalse(s.sawFlagLeft());
    }

    @Test
    public void test_sawFlagOwn() {
        Sensors s = new Sensors();
        s.getFlagsOwn();
        assertFalse(s.sawFlagOwn());
    }

    private Map<Flag,DistDirVision> oneFlag() {
        Map<Flag, DistDirVision> re = new HashMap<>();
        re.put(Flag.FLAG_CENTER, dirDistVision());
        return re;
    }

    private BodyState bodyState() {
        return new BodyState();
    }

    private DistDirVision dirDistVision() {
        return new DistDirVision();
    }

}
