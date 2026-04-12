package com.neobank.entity;

import java.time.LocalDateTime;

import com.neobank.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(name = "password_hash", nullable = false)
	private String passwordHash;

	@Column(name = "full_name", nullable = false)
	private String fullName;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Column(name = "is_active")
	private Boolean isActive;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@PrePersist
	void prePersist() {
		this.isActive = true;
		this.createdAt = LocalDateTime.now();
	}
}
