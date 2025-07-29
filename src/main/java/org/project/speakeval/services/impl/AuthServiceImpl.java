package org.project.speakeval.services.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.project.speakeval.domain.User;
import org.project.speakeval.dto.client.request.auth.AuthRequest;
import org.project.speakeval.dto.client.request.auth.RegisterRequest;
import org.project.speakeval.dto.client.request.auth.TokenRefreshRequest;
import org.project.speakeval.dto.client.response.auth.AuthResponse;
import org.project.speakeval.dto.client.response.auth.RefreshTokenResponse;
import org.project.speakeval.dto.client.response.auth.RegisterResponse;
import org.project.speakeval.enums.Role;
import org.project.speakeval.mapper.UserMapper;
import org.project.speakeval.repository.UserRepository;
import org.project.speakeval.security.JwtTokenProvider;
import org.project.speakeval.services.AuthService;
import org.project.speakeval.services.RefreshTokenService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public RegisterResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = User.builder()
                .name(request.getName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_STUDENT)
                .build();
        return userMapper.toRegisterResponse(userRepository.save(user));
    }

    @Transactional
    public User updateUser(String userId, String newEmail, Role newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found with id: " + userId));
        if (newEmail != null && !newEmail.isBlank()) {
            user.setEmail(newEmail);
        }
        if (newRole != null) {
            user.setRole(newRole);
        }
        return user;
    }

    @Override
    public AuthResponse authenticateUser(AuthRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = (User) auth.getPrincipal();

        if (user.getRole() != Role.ROLE_STUDENT) {
            throw new AccessDeniedException("Only students can use this endpoint");
        }

        List<String> roles = user.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .toList();

        String accessToken  = jwtProvider.createAccessToken(user.getUsername(), roles);
        String refreshToken = jwtProvider.createRefreshToken(user.getUsername());
        refreshTokenService.store(user.getUsername(), refreshToken);

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public AuthResponse authenticateAdmin(AuthRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = (User) auth.getPrincipal();

        if (user.getRole() != Role.ROLE_ADMIN) {
            throw new AccessDeniedException("Only admins can use this endpoint");
        }

        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String accessToken  = jwtProvider.createAccessToken(user.getUsername(), roles);
        String refreshToken = jwtProvider.createRefreshToken(user.getUsername());
        refreshTokenService.store(user.getUsername(), refreshToken);

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public RefreshTokenResponse refreshToken(TokenRefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtProvider.validateToken(refreshToken)) {
            throw new BadCredentialsException("Invalid refresh token");
        }

        if (!refreshTokenService.isValid(refreshToken)) {
            throw new AccessDeniedException("Refresh token revoked or unknown");
        }

        String email = jwtProvider.getAuthentication(refreshToken).getName();

        List<String> roles = userDetailsService
                .loadUserByUsername(email)
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        String newAccessToken = jwtProvider.createAccessToken(email, roles);

        return new RefreshTokenResponse(newAccessToken);
    }

}
