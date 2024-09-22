package org.example.jwt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshService {
    final JwtService jwtService;

    public RefreshService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String refreshUserToken(String refreshToken) {
        String userEmail = jwtService.extractSubjectRefreshToken(refreshToken);
        return jwtService.generateAccessToken(userEmail);
    }
}
