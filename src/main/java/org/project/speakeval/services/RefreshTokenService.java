package org.project.speakeval.services;

public interface RefreshTokenService {
    public void store(String email, String token);
    public boolean isValid(String token);
    public void revoke(String token);
}
