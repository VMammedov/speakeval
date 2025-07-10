package org.project.speakeval.controller.auth;

import lombok.RequiredArgsConstructor;
import org.project.speakeval.dto.request.auth.AuthRequest;
import org.project.speakeval.dto.request.auth.RegisterRequest;
import org.project.speakeval.dto.request.auth.TokenRefreshRequest;
import org.project.speakeval.dto.response.auth.AuthResponse;
import org.project.speakeval.dto.response.auth.RefreshTokenResponse;
import org.project.speakeval.dto.response.auth.RegisterResponse;
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
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest request) {
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
    public ResponseEntity<RefreshTokenResponse> refresh(@RequestBody TokenRefreshRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }

}
