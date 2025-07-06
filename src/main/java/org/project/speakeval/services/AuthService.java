package org.project.speakeval.services;

import org.project.speakeval.domain.User;
import org.project.speakeval.dto.request.AuthRequest;
import org.project.speakeval.dto.request.RegisterRequest;
import org.project.speakeval.dto.request.TokenRefreshRequest;
import org.project.speakeval.dto.response.AuthResponse;
import org.project.speakeval.dto.response.RefreshResponse;

public interface AuthService {

    User registerUser(RegisterRequest request);
    AuthResponse authenticateUser(AuthRequest request);
    AuthResponse authenticateAdmin(AuthRequest request);
    RefreshResponse refreshToken(TokenRefreshRequest request);
}
