package atan.model;

public enum RefereeMessage {

	FOUL_OWN("foulOwn"),

	FOUL_OTHER("foulOther"),

	HALF_TIME("halfTime"),

	TIME_UP("timeUp"),

	TIME_UP_WITHOUT_A_TEAM("timeUpWithoutATeam"),

	TIME_EXTENDED("timeExtended"),

	DROP_BALL("dropBall"),

	OFFSIDE_OWN("offsideOwn"),

	OFFSIDE_OTHER("offsideOther");

	private String name;

	private RefereeMessage(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "RefereeMessage[" + name + "]";
	}

}
