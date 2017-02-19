package vsoc.camps;

import java.io.*;

import atan.model.*;
import vsoc.util.Serializer;

/*
 * Implements an atan Team. Reads the contents of a Camp
 * object file and takes the first 11 players as atan Players.
 */

public class AtanCampTeam extends Team {

	private Camp<?> camp = null;

	public AtanCampTeam(String teamName, int port, String hostname) {
		super(teamName, port, hostname);
	}

	public Controller getNewController(int num) {
		try {
			if (this.camp == null) {
				File file = new File(getTeamName() + ".object");
				this.camp = (Camp<?>) Serializer.current().deserialize(file);
			}
			return camp.getController(num);
		} catch (IOException ex) {
			throw new IllegalStateException(ex.getMessage(), ex);
		}
	}

	public static void main(String[] args) {
		AtanCampTeam team = new AtanCampTeam(args[0], 6000, "localhost");
		team.connectAll();
	}
}
