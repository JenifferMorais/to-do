package com.repository.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.repository.todo.dto.UserDTO;
import com.repository.todo.entity.User;
import com.repository.todo.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService service;

	@GetMapping
	@ApiOperation(value = "Returns all users", authorizations = { @Authorization(value = "apiKey") })
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error in request"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 404, message = "The user does not exist"),
			@ApiResponse(code = 500, message = "Invalid JWT Token") })
	public List<UserDTO> findAll() {
		return service.findAll();
	}

	@PostMapping("/create")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error in request"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 404, message = "The user does not exist"),
			@ApiResponse(code = 500, message = "Invalid JWT Token || E-mail already registered! || lack of permission") })
	@ApiOperation(value = "Save user")
	public void create(@RequestBody User user) {
		service.create(user);
	}

	@PutMapping
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error in request"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 404, message = "The user does not exist"),
			@ApiResponse(code = 500, message = "Invalid JWT Token") })
	@ApiOperation(value = "Update user")
	public void update(@RequestBody User user) {
		service.update(user);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete the user for id", authorizations = { @Authorization(value = "apiKey") })
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error in request"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 404, message = "The user does not exist"),
			@ApiResponse(code = 500, message = "Invalid JWT Token") })
	public void delete(@PathVariable("id") Integer id) {
		service.delete(id);
	}

}
