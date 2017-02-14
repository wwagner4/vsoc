package atan.model;

public enum ViewQuality {

	HIGH("high"),

	LOW("low");

	private String name;

	private ViewQuality(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ViewQuality[" + name + "]";
	}

}
