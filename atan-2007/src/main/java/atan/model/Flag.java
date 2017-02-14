package atan.model;

public enum Flag {

	FLAG_CENTER("center"),

	FLAG_LEFT("left"),

	FLAG_RIGHT("right"),

	FLAG_OWN_50("own50"),

	FLAG_OWN_40("own40"),

	FLAG_OWN_30("own30"),

	FLAG_OWN_20("own20"),

	FLAG_OWN_10("own10"),

	FLAG_OTHER_10("other10"),

	FLAG_OTHER_20("other20"),

	FLAG_OTHER_30("other30"),

	FLAG_OTHER_40("other40"),

	FLAG_OTHER_50("other50"),

	FLAG_LEFT_10("left10"),

	FLAG_LEFT_20("left20"),

	FLAG_LEFT_30("left30"),

	FLAG_RIGHT_10("right10"),

	FLAG_RIGHT_20("right20"),

	FLAG_RIGHT_30("right30");

	private String name;

	private Flag(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Flag[" + name + "]";
	}

}
