package org.project.speakeval.services;

import org.project.speakeval.dto.client.request.auth.AuthRequest;
import org.project.speakeval.dto.client.request.auth.RegisterRequest;
import org.project.speakeval.dto.client.request.auth.TokenRefreshRequest;
import org.project.speakeval.dto.client.response.auth.AuthResponse;
import org.project.speakeval.dto.client.response.auth.RefreshTokenResponse;
import org.project.speakeval.dto.client.response.auth.RegisterResponse;

public interface AuthService {

    RegisterResponse registerUser(RegisterRequest request);
    AuthResponse authenticateUser(AuthRequest request);
    AuthResponse authenticateAdmin(AuthRequest request);
    RefreshTokenResponse refreshToken(TokenRefreshRequest request);
}
