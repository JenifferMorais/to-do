package com.repository.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.repository.todo.entity.Task;
import com.repository.todo.repository.TaskRepository;
import com.repository.todo.security.CustomException;

@Service
public class TaskService {
	@Autowired
	TaskRepository repository;
	@Autowired
	UserService userService;

	public List<Task> findAll() {
		return repository.findAll();
	}

	public Integer create(Task task, String token) {
		String email = getEmailUser(token);
		task.setIdUser(userService.findByIdUser(email));
		return repository.create(task);
	}

	public String getEmailUser(String token) {
		return userService.getEmail(token);
	}

	public Boolean validUser(Integer idTask, String token) {
		Integer idUserTask = repository.findById(idTask).getIdUser();
		Integer idUserToken = userService.findByIdUser(getEmailUser(token));
		if (idUserToken == idUserTask) {
			return true;
		}
		throw new CustomException("You do not have permission to perform this action.", HttpStatus.NOT_FOUND);
	}

	public void concludedTask(Integer idTask, String token) {
		if (validUser(idTask, token)) {
			repository.concludedTask(idTask);
		}
	}

	public void update(Task task, String token) {
		if (validUser(task.getId(), token)) {
			repository.update(task);
		}
	}

	public void delete(Integer idTask, String token) {
		if (validUser(idTask, token)) {
			repository.delete(idTask);
		}
	}

	public List<Task> findPendingTaskByPriority(Integer priority) {
		return repository.findPendingTaskByPriority(priority);
	}

	public Task findById(Integer id) {
		try {
			return repository.findById(id);
		} catch (Exception e) {
			return null;
		}

	}
}
