package com.repository.todo.entity;

import lombok.Getter;

@Getter
public enum Priority {
	BAIXA(0, "BAIXA"), MEDIA(1, "MEDIA"), ALTA(2, "ALTA");

	private final int value;
	private final String name;

	private Priority(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public static Priority fromString(String name) {
		for (Priority e : Priority.values()) {
			if (e.getName().equals(name)) {
				return e;
			}
		}
		return null;
	}

	public static Priority fromInt(Integer id) {
		if (id == null) {
			return null;
		}

		for (Priority e : Priority.values()) {
			if (e.getValue() == id) {
				return e;
			}
		}
		return null;
	}

}
