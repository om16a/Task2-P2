package org.example.jwt.services;

import jakarta.transaction.Transactional;
import org.example.jwt.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogoutService {
    final RefreshTokenRepository refreshTokenRepository;
    final JwtService jwtService;

    public LogoutService(JwtService jwtService, RefreshTokenRepository refreshTokenRepository) {
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
    }
    public String extractBearerToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
    @Transactional
    public void logoutUser(String accessToken) {
        String userEmail = jwtService.extractSubjectAccessToken(accessToken);
        refreshTokenRepository.deleteByUserEmail(userEmail);
    }
}
