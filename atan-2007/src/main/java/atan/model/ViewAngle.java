package atan.model;

public enum ViewAngle {

	NARROW("narrow"),

	NORMAL("normal"),

	WIDE("wide");

	private String name;

	private ViewAngle(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ViewAngle[" + name + "]";
	}

}
