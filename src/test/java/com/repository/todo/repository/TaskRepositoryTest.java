package com.repository.todo.repository;

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

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskRepositoryTest {

	@Autowired
	TaskRepository repository;
	
	private Task task;

	@BeforeAll
	void task() {
		task = new Task();
		task.setDescription("Task easy!");
		task.setPriority(1); // MEDIA
		task.setConcluded(false);
		task.setIdUser(1);
	}

	@Test()
	@Order(1)
	void create() {
		int idTask = repository.create(task);
		task.setId(idTask);
		assert (Objects.nonNull(idTask));
	}

	@Test
	@Order(2)
	void findPendingTaskByPriority() {
		List<Task> tasks = repository.findPendingTaskByPriority(1);
		assert (tasks.size() > 0);
	}
	
	@Test
	@Order(3)
	void concludedTask() {
		repository.concludedTask(task.getId());
		List<Task> tasks = repository.findPendingTaskByPriority(null);
		boolean idExists = tasks.stream().anyMatch(taskStream -> taskStream.getId() == task.getId());
		assert(!idExists);
	}
	
	@Test
	@Order(4)
	void findbyId() {
		Task taskFind = repository.findById(task.getId());
		assertEquals(taskFind.getDescription(), task.getDescription());
	}
	
	@Test
	@Order(5)
	void update() {
		task.setDescription("Task easy, task concluded");
		repository.update(task);
		
		Task taskFind = repository.findById(task.getId());
		assertEquals(taskFind.getDescription(), task.getDescription());
	}
	
	@Test
	@Order(6)
	void listAll() {
		List<Task> tasks = repository.findAll();
		for (Task task : tasks) {
			System.out.println(task.getId());
		}
		assert(tasks.size()>0);
	}
	
	@Test
	@Order(7)
	void delete() {
		repository.delete(task.getId());
	}

}
