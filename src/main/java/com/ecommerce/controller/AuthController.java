package com.ecommerce.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.entities.User;
import com.ecommerce.mapper.UserMapper;
import com.ecommerce.payloads.LoginRequest;
import com.ecommerce.payloads.UserDTO;
import com.ecommerce.repositories.UserRepository;
import com.ecommerce.security.JwtUtil;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	
	private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final  JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          BCryptPasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // 1. Find user by email
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid email or password");
        }

        // 2. Check password using BCrypt
        boolean isMatch = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!isMatch) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid email or password");
        }

        // 3. Generate JWT token
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        // 4. Return token in response
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
	
}
