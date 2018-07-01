package vsoc.server;

public enum ServerFlag {

	NORTH("north"),

	CENTER("center"),

	SOUTH("south"),

	EAST_10("east10"),

	EAST_20("east20"),

	EAST_30("east30"),

	EAST_40("east40"),

	EAST_50("east50"),

	WEST_10("west10"),

	WEST_20("west20"),

	WEST_30("west30"),

	WEST_40("west40"),

	WEST_50("west50"),

	NORTH_10("north10"),

	NORTH_20("north20"),

	NORTH_30("north30"),

	SOUTH_10("south10"),

	SOUTH_20("south20"),

	SOUTH_30("south30");

	private String name;

	ServerFlag(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
