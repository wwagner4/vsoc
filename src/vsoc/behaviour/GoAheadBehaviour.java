package vsoc.behaviour;

import atan.model.Player;

public class GoAheadBehaviour implements Behaviour {
    
    public GoAheadBehaviour() {
        super();
    }

    public boolean shouldBeApplied(Sensors sens) {
        return true;
    }

    public void apply(Sensors sens, Player player) {
        player.dash(50);
    }

    public Behaviour getChild() {
        return null;
    }

}
