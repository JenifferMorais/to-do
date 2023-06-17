package com.repository.todo.entity;

import lombok.Data;

@Data
public class Task {
	Integer id;
	String description;
	Priority priority;
	Boolean concluded;
	Integer idUser;

	public Task() {
		super();
	}

	public Task(Integer id, String description, Integer priority, Boolean concluded, Integer idUser) {
		super();
		this.id = id;
		this.description = description;
		this.priority = Priority.fromInt(priority);
		this.concluded = concluded;
		this.idUser = idUser;
	}
	
	public void setPriority(Integer priority) {
		this.priority = Priority.fromInt(priority);
	}

}
