package org.example.jwt.repositories;

import org.example.jwt.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findRefreshTokenByToken(String token);
    @Modifying
    void deleteByUserEmail(String userEmail);
    @Modifying
    void deleteByToken(String token);
}
