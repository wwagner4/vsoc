package atan.model;

public enum Line {

	LINE_CENTER("center"),

	LINE_OWN("own"),

	LINE_OTHER("other"),

	LINE_LEFT("left"),

	LINE_RIGHT("right");

	private String name;

	private Line(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Line[" + name + "]";
	}

}
