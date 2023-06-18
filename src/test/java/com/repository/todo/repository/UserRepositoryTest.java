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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.repository.todo.entity.User;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {

	@Autowired
	UserRepository repository;
	@Autowired
	PasswordEncoder passwordEncoder;
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
		int idUser = repository.create(user);
		user.setId(idUser);
		user.setPassword(encript(user.getPassword()));
		assert (Objects.nonNull(user.getId()));
	}

	@Test
	@Order(2)
	void findByEmail() {
		User userEmail = repository.findByEmail(user.getEmail());
		assert (Objects.nonNull(userEmail));
	}

	@Test
	@Order(3)
	void findByIdUser() {
		User findUser = repository.findByEmail(user.getEmail());
		assertEquals(findUser.getId(), user.getId());
	}

	@Test
	@Order(4)
	void update() {
		user.setName("Jeniffer Morais");
		repository.update(user);

		User userFind = repository.findById(user.getId());
		assertEquals(user.getName(), userFind.getName());
	}

	@Test
	@Order(5)
	void findAll() {
		List<User> result = repository.findAll();
		for (User user : result) {
			System.out.println(user.getName());
		}
		assert (result.size() > 0);
	}

	@Test
	@Order(6)
	void delete() {
		repository.delete(user.getId());
		assert (Objects.isNull(repository.findById(user.getId())));
	}
	
	public String encript(String password) {
		return passwordEncoder.encode(password);
	}
}
