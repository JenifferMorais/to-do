package com.repository.todo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.repository.todo.entity.Task;
import com.repository.todo.entity.User;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskServiceTest {
	private Task task;
	private String token;
	@Autowired
	TaskService service;

	@Autowired
	UserService userService;

	@BeforeAll
	void task() {
		task = new Task();
		task.setDescription("Task easy!");
		task.setPriority(1); // MEDIA
		task.setConcluded(false);

		getToken();
	}

	@Test()
	@Order(1)
	void create() {
		int idTask = service.create(task, token);
		task.setId(idTask);
		assert (Objects.nonNull(idTask));
	}

	@Test
	@Order(2)
	void findPendingTaskByPriority() {
		List<Task> tasks = service.findPendingTaskByPriority(1);
		assert (tasks.size() > 0);
	}
	
	@Test
	@Order(3)
	void concludedTask() {
		service.concludedTask(task.getId(), token);
		List<Task> tasks = service.findPendingTaskByPriority(null);
		boolean idExists = tasks.stream().anyMatch(taskStream -> taskStream.getId() == task.getId());
		assert(!idExists);
	}
	
	@Test
	@Order(4)
	void findbyId() {
		Task taskFind = service.findById(task.getId());
		assertEquals(taskFind.getDescription(), task.getDescription());
	}
	
	@Test
	@Order(5)
	void update() {
		task.setDescription("Task easy, task concluded");
		service.update(task, token);
		
		Task taskFind = service.findById(task.getId());
		assertEquals(taskFind.getDescription(), task.getDescription());
	}
	
	@Test
	@Order(6)
	void listAll() {
		List<Task> tasks = service.findAll();
		for (Task task : tasks) {
			System.out.println(task.getId());
		}
		assert(tasks.size()>0);
	}
	
	@Test
	@Order(7)
	void delete() {
		service.delete(task.getId(), token);
		assert (Objects.isNull(service.findById(task.getId())));
	}

	void getToken() {
		User user = new User();
		user.setName("Jeniffer");
		user.setEmail("jenimorais28@gmail.com");
		user.setPassword("Kcm@269854");
		token = userService.auth(user.getEmail(), user.getPassword());
	}
}
