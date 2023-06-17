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

import com.repository.todo.dto.UserDTO;
import com.repository.todo.entity.User;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

	@Autowired
	private UserService service;
	private User user;

	@BeforeAll
	void user() {
		user = new User();
		user.setName("Jeniffer");
		user.setEmail("jenimorais28.3@gmail.com");
		user.setPassword("Kcm@269854");
	}

	@Test()
	@Order(1)
	void create() {
		int idUser = service.create(user);
		user.setId(idUser);
		assert (Objects.nonNull(user.getId()));
	}

	@Test
	@Order(2)
	void findByEmail() {
		User userEmail = service.findByEmail(user.getEmail());
		assert (Objects.nonNull(userEmail));
	}

	@Test
	@Order(3)
	void findByIdUser() {
		Integer idUser = service.findByIdUser(user.getEmail());
		assertEquals(idUser, user.getId());
	}

	@Test
	@Order(4)
	void update() {
		user.setName("Jeniffer Morais");
		service.update(user);

		User userFind = service.findById(user.getId());
		assertEquals(user.getName(), userFind.getName());
	}

	@Test
	@Order(5)
	void findAll() {
		List<UserDTO> result = service.findAll();
		for (UserDTO user : result) {
			System.out.println(user.getName());
		}
		assert (result.size() > 0);
	}

	@Test
	@Order(6)
	void delete() {
		service.delete(user.getId());
		assert (Objects.isNull(service.findById(user.getId())));
	}

}
