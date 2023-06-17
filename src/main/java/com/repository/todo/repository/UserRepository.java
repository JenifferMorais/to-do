package com.repository.todo.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.repository.todo.dto.UserRowMapper;
import com.repository.todo.entity.User;

@Repository
public class UserRepository {

	private final JdbcTemplate jdbcTemplate;

	public UserRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<User> findAll() {
		String sql = "SELECT * FROM users";
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setName(rs.getString("name"));
			user.setEmail(rs.getString("email"));
			user.setPassword(rs.getString("password"));
			return user;
		});
	}

	public Integer create(User user) {
		String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getPassword());
			return preparedStatement;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	public void update(User user) {
		String sql = "UPDATE users SET name= ?, email = ?, password = ? WHERE id = ?";
		jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getId());
	}

	public void delete(int id) {
		String sql = "DELETE FROM USERS WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

	public User findByEmail(String email) {
		try {
			String sql = "SELECT * FROM users WHERE email LIKE ?";
			return jdbcTemplate.queryForObject(sql, new Object[] { "%" + email + "%" }, new UserRowMapper());
		} catch (Exception e) {
			return null;
		}
	}

	public User findById(int id) {
		try {
			String sql = "SELECT * FROM users WHERE id = ?";
			return jdbcTemplate.queryForObject(sql, new Object[] { id }, new UserRowMapper());
		} catch (Exception e) {
			return null;
		}
	}

	public Integer findIdUser(String email) {
		String sql = "SELECT id FROM users WHERE email = ?";
		return jdbcTemplate.queryForObject(sql, new Object[] { email }, Integer.class);
	}

}