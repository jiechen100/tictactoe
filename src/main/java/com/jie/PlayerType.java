package com.jie;

public enum PlayerType {
	MACHINE(0, "Machine", "X"), PERSON(1, "Person", "O"), NONE(2, "Empty", " ");

	private final int value;
	private final String description;
	private final String mark;

	PlayerType(final int value, String desc, String mark) {
		this.value = value;
		this.description = desc;
		this.mark = mark;
	}

	public int value() {
		return value;
	}

	public String description() {
		return description;
	}

	public String mark() {
		return mark;
	}
}
