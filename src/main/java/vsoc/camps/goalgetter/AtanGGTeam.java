package vsoc.camps.goalgetter;

import vsoc.camps.Member;
import vsoc.util.Serializer;

import java.io.*;

import atan.model.Controller;
import atan.model.Team;

/*
 * Implements an atan Team. Reads the contents of a Camp
 * object file and takes the first 11 players as atan Players.
 */

public class AtanGGTeam extends Team {

    private GGCamp camp = null;

    public AtanGGTeam(String teamName, int port, String hostname) {
        super(teamName, port, hostname);
    }
    
    public Controller getNewController(int num) {
        try {
            if (this.camp == null) {
                File file = new File(getTeamName() + ".object");
				this.camp = (GGCamp) Serializer.current().deserialize(file);
            }
            Member member = this.camp.getMember(num);
            return member.getNeuroControlSystem();
        } catch (IOException ex) {
            throw new IllegalStateException(ex.getMessage(), ex);
        }
    }

    public static void main(String[] args) {
        AtanGGTeam team = new AtanGGTeam(args[0], 6000, "localhost");
        team.connectAll();
    }
}

