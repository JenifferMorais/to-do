package com.repository.todo.service;

import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.repository.todo.repository.UserRepository;
import com.repository.todo.security.CustomException;
import com.repository.todo.security.JwtTokenProvider;

import io.jsonwebtoken.Jwts;

import com.repository.todo.dto.UserDTO;
import com.repository.todo.entity.User;

@Service
public class UserService {
	@Value("${security.jwt.token.secret-key:secret-key}")
	private String secretKey;

	@Autowired
	AuthenticationManager authentication;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	JwtTokenProvider jwtToken;
	@Autowired
	UserRepository repository;

	public String auth(String username, String password) {
		authentication.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		return jwtToken.createToken(username);
	}

	public String encript(String password) {
		return passwordEncoder.encode(password);
	}

	public List<UserDTO> findAll() {
		return repository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
	}

	public Integer create(User user) {
		if (validateEmail(user.getEmail()) && validatePassword(user.getPassword())) {
			user.setPassword(encript(user.getPassword()));
			return repository.create(user);
		}
		return null;
	}

	public Boolean validatePassword(String password) {
		String standard = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&*]).+$";
		if (password.matches(standard)) {
			return true;
		} else {
			throw new CustomException(
					"Senha não corresponde aos requisitos mínimos de segurança. "
							+ "A senha deve conter letras maiúsculas, "
							+ "minúsculas, números e pelo menos 1 caractere especial EX:!@#$%&*”.",
					HttpStatus.NOT_FOUND);
		}
	}

	public Boolean validateEmail(String email) {
		User userBusca = repository.findByEmail(email);
		if (Objects.isNull(userBusca)) {
			return true;
		}
		throw new CustomException("E-mail already registered!", HttpStatus.NOT_FOUND);

	}

	public String getEmail(String token) {
		String key = Base64.getEncoder().encodeToString(secretKey.getBytes());
		return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
	}

	public void delete(Integer id) {
		repository.delete(id);
	}

	public void update(User user) {
		user.setPassword(encript(user.getPassword()));
		repository.update(user);
	}

	public Integer findByIdUser(String email) {
		return repository.findIdUser(email);
	}

	public User findById(Integer id) {
		return repository.findById(id);
	}

	public User findByEmail(String email) {
		return repository.findByEmail(email);
	}

}