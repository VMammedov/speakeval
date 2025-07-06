package org.project.speakeval.controller;

import lombok.RequiredArgsConstructor;
import org.project.speakeval.domain.User;
import org.project.speakeval.dto.request.AuthRequest;
import org.project.speakeval.dto.request.RegisterRequest;
import org.project.speakeval.dto.request.TokenRefreshRequest;
import org.project.speakeval.dto.response.AuthResponse;
import org.project.speakeval.dto.response.RefreshResponse;
import org.project.speakeval.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/user/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/user")
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticateUser(request));
    }

    @PostMapping("/admin")
    public ResponseEntity<AuthResponse> authenticateAdmin(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.authenticateAdmin(request));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(@RequestBody TokenRefreshRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }
}
