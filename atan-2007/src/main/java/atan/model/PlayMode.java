package atan.model;

public enum PlayMode {

	BEFORE_KICK_OFF("beforeKickOff"),

	TIME_OVER("timeOver"),

	PLAY_ON("playOn"),

	KICK_OFF_OWN("kickOffOwn"),

	KICK_OFF_OTHER("kickOffOther"),

	KICK_IN_OWN("kickInOwn"),

	KICK_IN_OTHER("kickInOther"),

	FREE_KICK_OWN("freeKickOwn"),

	FREE_KICK_OTHER("freeKickOther"),

	CORNER_KICK_OWN("cornerKickOwn"),

	CORNER_KICK_OTHER("cornerKickOther"),

	GOAL_KICK_OWN("goalKickOwn"),

	GOAL_KICK_OTHER("goalKickOther"),

	GOAL_OWN("goalOwn"),

	GOAL_OTHER("goalOther");

	private String name;

	private PlayMode(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "PlayMode[" + name + "]";
	}

}
