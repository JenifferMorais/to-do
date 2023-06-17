package com.repository.todo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.repository.todo.entity.Task;
import com.repository.todo.security.JwtTokenProvider;
import com.repository.todo.service.TaskService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/task")
public class TaskController {
	@Autowired
	TaskService service;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@GetMapping
	public List<Task> findAll() {
		return service.findAll();
	}

	@PostMapping
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error in request"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 404, message = "The task does not exist"),
			@ApiResponse(code = 500, message = "Invalid JWT Token || lack of permission") })
	@ApiOperation(value = "Save user")
	public void create(HttpServletRequest request, @RequestBody Task task) {
		String token = jwtTokenProvider.resolveToken(request);
		service.create(task, token);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete the task for id", authorizations = { @Authorization(value = "apiKey") })
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error in request"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 404, message = "The task does not exist"),
			@ApiResponse(code = 500, message = "Invalid JWT Token || lack of permission") })
	public void delete(HttpServletRequest request, @PathVariable("id") Integer id) {
		String token = jwtTokenProvider.resolveToken(request);
		service.delete(id, token);
	}

	@PutMapping("concluded/{id}")
	@ApiOperation(value = "Update task")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error in request"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 404, message = "The task does not exist"),
			@ApiResponse(code = 500, message = "Invalid JWT Token || lack of permission") })
	public void concludedTask(HttpServletRequest request, @PathVariable("id") Integer id) {
		String token = jwtTokenProvider.resolveToken(request);
		service.concludedTask(id, token);
	}

	@PutMapping
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error in request"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 404, message = "The task does not exist"),
			@ApiResponse(code = 500, message = "Invalid JWT Token || lack of permission") })
	@ApiOperation(value = "Update user")
	public void update(HttpServletRequest request, @RequestBody Task task) {
		String token = jwtTokenProvider.resolveToken(request);
		service.update(task, token);
	}

	@GetMapping("pending")
	@ApiOperation(value = "Returns all taks", authorizations = { @Authorization(value = "apiKey") })
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Error in request"),
			@ApiResponse(code = 403, message = "Access denied"),
			@ApiResponse(code = 404, message = "The task does not exist"),
			@ApiResponse(code = 500, message = "Invalid JWT Token") })
	public List<Task> findPendingTaskByPriority(@RequestParam(required = false) Integer priority) {
		return service.findPendingTaskByPriority(priority);
	}
}
