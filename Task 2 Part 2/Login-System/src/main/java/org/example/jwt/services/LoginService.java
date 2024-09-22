package org.example.jwt.services;

import jakarta.transaction.Transactional;
import org.example.jwt.DTO.UserDTO;
import org.example.jwt.model.RefreshToken;
import org.example.jwt.model.TokenPair;
import org.example.jwt.repositories.RefreshTokenRepository;
import org.example.jwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    final UserRepository userRepository;
    final JwtService jwtService;
    final RefreshTokenRepository refreshTokenRepository;

    public LoginService(UserRepository userRepository, JwtService jwtService, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Transactional
    public TokenPair loginUser(UserDTO userDTO) {
        String refreshToken = jwtService.generateRefreshToken(userDTO.getEmail());
        String accessToken = jwtService.generateAccessToken(userDTO.getEmail());

        // deleting invalid tokens that has same email

        refreshTokenRepository.deleteByUserEmail(userDTO.getEmail());

        RefreshToken refresh = new RefreshToken
                (userDTO.getEmail(), jwtService.extractIssuedAtRefreshToken(refreshToken)
                ,jwtService.extractExpiryDateRefreshToken(refreshToken),
                        refreshToken);
        refreshTokenRepository.save(refresh);


        return new TokenPair(accessToken, refreshToken);
    }
}
