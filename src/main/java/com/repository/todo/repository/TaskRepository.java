package com.repository.todo.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.repository.todo.dto.TaskRowMapper;
import com.repository.todo.entity.Task;

@Repository
public class TaskRepository {

	private final JdbcTemplate jdbcTemplate;

	public TaskRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Task> findAll() {
		String sql = "SELECT * FROM task";
		return jdbcTemplate.query(sql, (rs, rowNum) -> {
			Task task = new Task();
			task.setId(rs.getInt("id"));
			task.setDescription(rs.getString("description"));
			task.setPriority(rs.getInt("priority"));
			task.setConcluded(rs.getBoolean("concluded"));
			task.setIdUser(rs.getInt("fk_user"));
			return task;
		});
	}

	public Integer create(Task task) {
		String sql = "INSERT INTO task (description, priority, concluded, fk_user) VALUES (?, ?, ?, ?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, task.getDescription());
			preparedStatement.setInt(2, task.getPriority().getValue());
			preparedStatement.setBoolean(3, task.getConcluded());
			preparedStatement.setInt(4, task.getIdUser());
			return preparedStatement;
		}, keyHolder);

		return keyHolder.getKey().intValue();
	}

	public Task findById(int id) {
		try {
			String sql = "SELECT * FROM task WHERE id = ?";
			return jdbcTemplate.queryForObject(sql, new Object[] { id }, new TaskRowMapper());

		} catch (Exception e) {
			return null;
		}
	}

	public void delete(int id) {
		String sql = "DELETE FROM task WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

	public void update(Task task) {
		String sql = "UPDATE task SET description= ?, priority = ?, concluded = ? WHERE id = ?";
		jdbcTemplate.update(sql, task.getDescription(), task.getPriority().getValue(), task.getConcluded(),
				task.getId());
	}

	public void concludedTask(Integer idTask) {
		String sql = "UPDATE task SET concluded = ? WHERE id = ?";
		jdbcTemplate.update(sql, true, idTask);
	}

	public List<Task> findPendingTaskByPriority(Integer priority) {
		int isFalse = 0;
		String sql = "SELECT * FROM task WHERE concluded = " + isFalse;

		if (Objects.nonNull(priority)) {
			sql += " AND priority = ?";
			return jdbcTemplate.query(sql, new Object[] { priority }, new TaskRowMapper());
		}
		return jdbcTemplate.query(sql, new TaskRowMapper());
	}

}
