package com.repository.todo.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.repository.todo.entity.Task;

public class TaskRowMapper implements RowMapper<Task> {

    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();
        task.setId(rs.getInt("id"));
    	task.setDescription(rs.getString("description"));
		task.setPriority(rs.getInt("priority"));
		task.setConcluded(rs.getBoolean("concluded"));
		task.setIdUser(rs.getInt("fk_user"));
        return task;
    }
}
