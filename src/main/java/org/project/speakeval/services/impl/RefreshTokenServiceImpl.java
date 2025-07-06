package org.project.speakeval.services.impl;

import org.project.speakeval.services.RefreshTokenService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final Map<String, String> store = new ConcurrentHashMap<>();

    public void store(String email, String token) {
        store.put(token, email);
    }
    public boolean isValid(String token) {
        return store.containsKey(token);
    }
    public void revoke(String token) {
        store.remove(token);
    }
}
