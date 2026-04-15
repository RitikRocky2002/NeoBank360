package com.neobank.controller;

import com.neobank.entity.User;
import com.neobank.enums.Role;
import com.neobank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    // ✅ Get all users
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ Delete user
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    // ✅ Update user role
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/users/{id}/role")
    public String updateRole(@PathVariable Long id, @RequestParam String role) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setRole(Role.valueOf(role));
        userRepository.save(user);

        return "Role updated successfully";
    }
}