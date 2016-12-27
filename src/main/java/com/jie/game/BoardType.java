package com.jie.game;

public enum BoardType {
	CONSOLE(0, "Console"), WEB(1, "Web");

	private final int value;
	private final String description;

	BoardType(final int value, String desc) {
		this.value = value;
		this.description = desc;
	}

	public int value() {
		return value;
	}

	public String description() {
		return description;
	}
}
