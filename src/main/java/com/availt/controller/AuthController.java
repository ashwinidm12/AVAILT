package com.availt.controller;

import com.availt.model.User;
import com.availt.security.JwtTokenProvider;
import com.availt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "https://ashwinidm12.github.io"})
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Email/Password Login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            User user = userService.findByEmail(request.getEmail());
            if (user == null) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid email or password"));
            }

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid email or password"));
            }

            String token = jwtTokenProvider.generateToken(user.getEmail());
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", userToDTO(user));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Email/Password Signup
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            // Check if email exists
            if (userService.findByEmail(request.getEmail()) != null) {
                return ResponseEntity.status(409).body(Map.of("error", "Email already registered"));
            }

            // Create new user
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setPassword(passwordEncoder.encode(request.getPassword()));

            User savedUser = userService.saveUser(user);
            String token = jwtTokenProvider.generateToken(savedUser.getEmail());

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", userToDTO(savedUser));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * OAuth2 Success Callback - Called after Google/Facebook/Apple login
     * Frontend receives JWT token and user info
     */
    @GetMapping("/oauth2-success")
    public ResponseEntity<?> oauth2Success(@RequestParam String email, @RequestParam String name) {
        try {
            User user = userService.findByEmail(email);

            // If user doesn't exist, create new one
            if (user == null) {
                user = new User();
                user.setEmail(email);
                user.setName(name);
                user.setPassword(passwordEncoder.encode("oauth2-" + System.nanoTime())); // Random password
                user = userService.saveUser(user);
            }

            String token = jwtTokenProvider.generateToken(user.getEmail());
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("user", userToDTO(user));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Verify Token
     */
    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken(@RequestHeader("Authorization") String bearerToken) {
        try {
            if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
                return ResponseEntity.status(401).body(Map.of("error", "Missing or invalid token"));
            }

            String token = bearerToken.substring(7);
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(401).body(Map.of("error", "Invalid or expired token"));
            }

            String email = jwtTokenProvider.getEmailFromJWT(token);
            User user = userService.findByEmail(email);

            return ResponseEntity.ok(Map.of(
                "valid", true,
                "user", userToDTO(user)
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Token validation failed"));
        }
    }

    /**
     * Logout
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }

    private UserDTO userToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        return dto;
    }

    // Request/Response DTOs
    public static class LoginRequest {
        public String email;
        public String password;

        public String getEmail() { return email; }
        public String getPassword() { return password; }
    }

    public static class SignupRequest {
        public String name;
        public String email;
        public String phone;
        public String password;

        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
        public String getPassword() { return password; }
    }

    public static class UserDTO {
        public Long id;
        public String name;
        public String email;
        public String phone;

        public void setId(Long id) { this.id = id; }
        public void setName(String name) { this.name = name; }
        public void setEmail(String email) { this.email = email; }
        public void setPhone(String phone) { this.phone = phone; }
    }
}