package com.neobank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.neobank.dto.AuthResponse;
import com.neobank.dto.LoginRequest;
import com.neobank.dto.RegisterRequest;
import com.neobank.entity.User;
import com.neobank.enums.Role;
import com.neobank.repository.UserRepository;
import com.neobank.security.JwtUtil;

@Service
public class AuthService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	public User register(RegisterRequest request) {
		if (userRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new RuntimeException("Email already exists");
		}

		User user = User.builder().fullName(request.getFullName()).email(request.getEmail())
				.passwordHash(passwordEncoder.encode(request.getPassword())).role(Role.CUSTOMER).build();

		return userRepository.save(user);
	}

	public AuthResponse login(LoginRequest request) {

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("Invalid email or password"));

		if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
			throw new RuntimeException("Invalid email or password");
		}

		if (!user.getIsActive()) {
			throw new RuntimeException("User is inactive");
		}

		String token = jwtUtil.generateToken(user.getEmail());

		return new AuthResponse(token);
	}
}
