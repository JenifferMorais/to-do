package com.repository.todo.dto;

import com.repository.todo.entity.User;

import lombok.Data;

@Data
public class UserDTO {

	private Integer id;
	private String name;
	private String email;

	public UserDTO() {
		super();
	}

	public UserDTO(User user) {
		super();
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
	}

	public UserDTO(Integer id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}

}
