package vsoc.server;

import atan.model.PlayMode;

public class ServerUtil {

	private static ServerUtil current = null;

	private ServerUtil() {
		super();
	}

	public static ServerUtil current() {
		if (current == null) {
			current = new ServerUtil();
		}
		return current;
	}

	public Server createServer(int eastPlayerCount, int westPlayerCount) {
		Server srv = new Server();
		addGoalWest(srv, -Server.WIDTH / 2, 0);
		addGoalEast(srv, Server.WIDTH / 2, 0);
		addFlags(srv);
		srv.addSimObject(new Ball(0, 0));
		for (int i = 0; i < eastPlayerCount; i++) {
			addPlayerEast(srv, new VsocPlayerEast(20, i * 10 - 10, 180));
		}
		for (int i = 0; i < westPlayerCount; i++) {
			addPlayerWest(srv, new VsocPlayerWest(-20, i * 10 - 10, 0));
		}
		return srv;
	}

	public void addPlayerWest(Server srv, VsocPlayerWest p) {
		srv.addSimObject(p);
		p.setNumber(srv.getActualPlayerWestCount());
		if ((p.getController() != null) && (srv.getTime() == 0))
			p.getController().infoHearPlayMode(PlayMode.BEFORE_KICK_OFF);
	}

	public void addPlayerEast(Server srv, VsocPlayerEast p) {
		srv.addSimObject(p);
		p.setNumber(srv.getActualPlayerEastCount());
		if ((p.getController() != null) && (srv.getTime() == 0))
			p.getController().infoHearPlayMode(PlayMode.BEFORE_KICK_OFF);
	}

	protected void addFlags(Server srv) {
		addFlagNorth(srv, ServerFlag.WEST_50, -50, defaultYNorthFlag());
		addFlagNorth(srv, ServerFlag.WEST_40, -40, defaultYNorthFlag());
		addFlagNorth(srv, ServerFlag.WEST_30, -30, defaultYNorthFlag());
		addFlagNorth(srv, ServerFlag.WEST_20, -20, defaultYNorthFlag());
		addFlagNorth(srv, ServerFlag.WEST_10, -10, defaultYNorthFlag());

		addFlagNorth(srv, ServerFlag.EAST_10, 10, defaultYNorthFlag());
		addFlagNorth(srv, ServerFlag.EAST_20, 20, defaultYNorthFlag());
		addFlagNorth(srv, ServerFlag.EAST_30, 30, defaultYNorthFlag());
		addFlagNorth(srv, ServerFlag.EAST_40, 40, defaultYNorthFlag());
		addFlagNorth(srv, ServerFlag.EAST_50, 50, defaultYNorthFlag());

		addFlagSouth(srv, ServerFlag.WEST_50, -50, defaultYSouthFlag());
		addFlagSouth(srv, ServerFlag.WEST_40, -40, defaultYSouthFlag());
		addFlagSouth(srv, ServerFlag.WEST_30, -30, defaultYSouthFlag());
		addFlagSouth(srv, ServerFlag.WEST_20, -20, defaultYSouthFlag());
		addFlagSouth(srv, ServerFlag.WEST_10, -10, defaultYSouthFlag());

		addFlagSouth(srv, ServerFlag.EAST_10, 10, defaultYSouthFlag());
		addFlagSouth(srv, ServerFlag.EAST_20, 20, defaultYSouthFlag());
		addFlagSouth(srv, ServerFlag.EAST_30, 30, defaultYSouthFlag());
		addFlagSouth(srv, ServerFlag.EAST_40, 40, defaultYSouthFlag());
		addFlagSouth(srv, ServerFlag.EAST_50, 50, defaultYSouthFlag());

		addFlagWest(srv, ServerFlag.NORTH_30, defaultXWestFlag(), 30);
		addFlagWest(srv, ServerFlag.NORTH_20, defaultXWestFlag(), 20);
		addFlagWest(srv, ServerFlag.NORTH_10, defaultXWestFlag(), 10);
		addFlagWest(srv, ServerFlag.SOUTH_10, defaultXWestFlag(), -10);
		addFlagWest(srv, ServerFlag.SOUTH_20, defaultXWestFlag(), -20);
		addFlagWest(srv, ServerFlag.SOUTH_30, defaultXWestFlag(), -30);

		addFlagEast(srv, ServerFlag.NORTH_30, defaultXEastFlag(), 30);
		addFlagEast(srv, ServerFlag.NORTH_20, defaultXEastFlag(), 20);
		addFlagEast(srv, ServerFlag.NORTH_10, defaultXEastFlag(), 10);
		addFlagEast(srv, ServerFlag.SOUTH_10, defaultXEastFlag(), -10);
		addFlagEast(srv, ServerFlag.SOUTH_20, defaultXEastFlag(), -20);
		addFlagEast(srv, ServerFlag.SOUTH_30, defaultXEastFlag(), -30);

		addFlagPenaltyEast(srv, ServerFlag.NORTH, 20);
		addFlagPenaltyEast(srv, ServerFlag.CENTER, 0);
		addFlagPenaltyEast(srv, ServerFlag.SOUTH, -20);

		addFlagPenaltyWest(srv, ServerFlag.NORTH, 20);
		addFlagPenaltyWest(srv, ServerFlag.CENTER, 0);
		addFlagPenaltyWest(srv, ServerFlag.SOUTH, -20);

		addFlagGoalEast(srv, ServerFlag.NORTH, Server.WIDTH / 2, srv.getGoalEast().getGoalWidth() / 2);
		addFlagGoalEast(srv, ServerFlag.SOUTH, Server.WIDTH / 2, -srv.getGoalEast().getGoalWidth() / 2);
		addFlagGoalWest(srv, ServerFlag.NORTH, -Server.WIDTH / 2, srv.getGoalWest().getGoalWidth() / 2);
		addFlagGoalWest(srv, ServerFlag.SOUTH, -Server.WIDTH / 2, -srv.getGoalWest().getGoalWidth() / 2);
	}

	public void addFlagWest(Server srv, ServerFlag type, double x, double y) {
		FlagWest f = new FlagWest(type, x, y);
		srv.addSimObject(f);
	}

	public void addFlagEast(Server srv, ServerFlag type, double x, double y) {
		FlagEast f = new FlagEast(type, x, y);
		srv.addSimObject(f);
	}

	public void addFlagNorth(Server srv, ServerFlag type, double x, double y) {
		FlagNorth f = new FlagNorth(type, x, y);
		srv.addSimObject(f);
	}

	public void addFlagSouth(Server srv, ServerFlag type, double x, double y) {
		FlagSouth f = new FlagSouth(type, x, y);
		srv.addSimObject(f);
	}

	protected void addFlagGoalEast(Server srv, ServerFlag type, double x, double y) {
		FlagGoalEast f = new FlagGoalEast(type, x, y);
		srv.addSimObject(f);
	}

	protected void addFlagGoalWest(Server srv, ServerFlag type, double x, double y) {
		FlagGoalWest f = new FlagGoalWest(type, x, y);
		srv.addSimObject(f);
	}

	public void addGoalWest(Server srv, double x, double y) {
		GoalWest goalWest = new GoalWest(x, y);
		srv.setGoalWest(goalWest);
		srv.addSimObject(goalWest);
	}

	public void addGoalEast(Server srv, double x, double y) {
		GoalEast goalEast = new GoalEast(x, y);
		srv.setGoalEast(goalEast);
		srv.addSimObject(goalEast);
	}

	protected void addFlagPenaltyEast(Server srv, ServerFlag type, double y) {
		FlagPenaltyEast f = new FlagPenaltyEast(type, Server.WIDTH * 0.338, y);
		srv.addSimObject(f);
	}

	protected void addFlagPenaltyWest(Server srv, ServerFlag type, double y) {
		FlagPenaltyWest f = new FlagPenaltyWest(type, -Server.WIDTH * 0.338, y);
		srv.addSimObject(f);
	}

	private double defaultXWestFlag() {
		return -Server.WIDTH / 2 - 5;
	}

	private double defaultYSouthFlag() {
		return -Server.HEIGHT / 2 - 5;
	}

	private double defaultYNorthFlag() {
		return Server.HEIGHT / 2 + 5;
	}

	private double defaultXEastFlag() {
		return Server.WIDTH / 2 + 5;
	}

}
